package com.geqian.evalution.teachingevalution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.common.utils.ExcelUtils;
import com.geqian.evalution.teachingevalution.common.dto.MarkSubmitDTO;
import com.geqian.evalution.teachingevalution.common.vo.MarkExportData;
import com.geqian.evalution.teachingevalution.common.vo.SelecionVo;
import com.geqian.evalution.teachingevalution.entity.EvaluationInfo;
import com.geqian.evalution.teachingevalution.entity.MarkComment;
import com.geqian.evalution.teachingevalution.entity.MarkInfo;
import com.geqian.evalution.teachingevalution.entity.Student;
import com.geqian.evalution.teachingevalution.entity.Course;
import com.geqian.evalution.teachingevalution.entity.IndexInfo;
import com.geqian.evalution.teachingevalution.mapper.MarkCommentMapper;
import com.geqian.evalution.teachingevalution.mapper.MarkInfoMapper;
import com.geqian.evalution.teachingevalution.service.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【mark_info(打分表)】的数据库操作Service
 * @createDate 2025-09-19 12:29:24
 */
@Service
public class MarkInfoServiceImpl extends ServiceImpl<MarkInfoMapper, MarkInfo> implements MarkInfoService {

    @Resource
    private EvaluationInfoService evaluationInfoService;
    @Resource
    private MarkCommentMapper markCommentMapper; // 添加这个注入

    @Override
    public ResponseResult<PageResult<MarkInfo>> pageSearch(PageRequest pageRequest, String token) {
        return null;
    }

    @Override
    public ResponseResult<Object> submitMarking(MarkSubmitDTO markSubmitDTO) {
        if (markSubmitDTO != null && !CollectionUtils.isEmpty(markSubmitDTO.getMarkInfoList())) {
            // 删除旧的评分记录
            this.baseMapper.deleteByEvaluationId(markSubmitDTO.getEvaluationId());

            // 为每个评分记录设置必要字段
            markSubmitDTO.getMarkInfoList().forEach(markInfo -> {
                markInfo.setTeacherId(markSubmitDTO.getTeacherId());
                markInfo.setStudentId(markSubmitDTO.getStudentId());
                markInfo.setCourseId(markSubmitDTO.getCourseId());
                markInfo.setEvaluationId(markSubmitDTO.getEvaluationId());
                markInfo.setMarkTime(new Date());
            });

            // 保存新的评分记录
            this.saveBatch(markSubmitDTO.getMarkInfoList());

            // 保存评语
            if (StringUtils.hasText(markSubmitDTO.getComment())) {
                MarkComment comment = new MarkComment();
                comment.setEvaluationId(markSubmitDTO.getEvaluationId());
                comment.setTeacherId(markSubmitDTO.getTeacherId());
                comment.setStudentId(markSubmitDTO.getStudentId());
                comment.setCourseId(markSubmitDTO.getCourseId());
                comment.setComment(markSubmitDTO.getComment());
                comment.setCreateTime(new Date());
                markCommentMapper.insert(comment);
            }

            // 更新评教状态
            EvaluationInfo evaluationInfo = new EvaluationInfo();
            evaluationInfo.setStatus(1);
            evaluationInfo.setEvaluationId(markSubmitDTO.getEvaluationId());
            evaluationInfoService.updateById(evaluationInfo);

            return ResponseResult.success();
        }
        return ResponseResult.fail();
    }

    @Resource
    private StudentService studentService; // 添加学生服务
    @Resource
    private CourseService courseService; // 添加课程服务
    @Resource
    private IndexInfoService indexService; // 添加指标服务

    @Override
    public ResponseResult<Object> getTeacherMarkDetail(Long teacherId, Long courseId) {
        // 查询该教师的所有评分记录
        LambdaQueryWrapper<MarkInfo> markWrapper = new LambdaQueryWrapper<>();
        markWrapper.eq(MarkInfo::getTeacherId, teacherId);
        if (courseId != null) {
            markWrapper.eq(MarkInfo::getCourseId, courseId);
        }
        List<MarkInfo> markList = this.list(markWrapper);

        // 查询该教师的所有评语
        LambdaQueryWrapper<MarkComment> commentWrapper = new LambdaQueryWrapper<>();
        commentWrapper.eq(MarkComment::getTeacherId, teacherId);
        if (courseId != null) {
            commentWrapper.eq(MarkComment::getCourseId, courseId);
        }
        List<MarkComment> commentList = markCommentMapper.selectList(commentWrapper);

        // 获取所有相关ID
        Set<Long> studentIds = new HashSet<>();
        Set<Long> courseIds = new HashSet<>();
        Set<Integer> indexIds = new HashSet<>();

        markList.forEach(mark -> {
            studentIds.add(mark.getStudentId());
            courseIds.add(mark.getCourseId());
            indexIds.add(mark.getIndexId().intValue()); // 转换为Integer
        });

        // 批量查询名称信息
        Map<Long, String> studentNames = studentService.listByIds(studentIds).stream()
                .collect(Collectors.toMap(Student::getStdId, Student::getStdName));
        Map<Long, String> courseNames = courseService.listByIds(courseIds).stream()
                .collect(Collectors.toMap(Course::getCosId, Course::getCosName));
        Map<Integer, String> indexNames = indexService.listByIds(indexIds).stream()
                .collect(Collectors.toMap(IndexInfo::getIndexId, IndexInfo::getIndexName));

        // 按学生和课程分组整理数据
        Map<String, Map<String, Object>> resultMap = new HashMap<>();

        // 处理评分数据
        markList.forEach(mark -> {
            String key = mark.getStudentId() + "-" + mark.getCourseId();
            Map<String, Object> data = resultMap.computeIfAbsent(key, k -> {
                Map<String, Object> newData = new HashMap<>();
                newData.put("studentId", mark.getStudentId());
                newData.put("studentName", studentNames.get(mark.getStudentId()));
                newData.put("courseId", mark.getCourseId());
                newData.put("courseName", courseNames.get(mark.getCourseId()));
                newData.put("scores", new HashMap<String, Integer>());
                return newData;
            });

            // 添加评分，使用指标名称作为key
            Map<String, Integer> scores = (Map<String, Integer>) data.get("scores");
            String indexName = indexNames.get(mark.getIndexId().intValue()); // 转换为Integer
            if (indexName != null) {
                scores.put(indexName, mark.getScore());
            }
        });

        // 处理评语数据
        commentList.forEach(comment -> {
            String key = comment.getStudentId() + "-" + comment.getCourseId();
            resultMap.computeIfAbsent(key, k -> {
                Map<String, Object> newData = new HashMap<>();
                newData.put("studentId", comment.getStudentId());
                newData.put("studentName", studentNames.get(comment.getStudentId()));
                newData.put("courseId", comment.getCourseId());
                newData.put("courseName", courseNames.get(comment.getCourseId()));
                newData.put("scores", new HashMap<String, Integer>());
                return newData;
            }).put("comment", comment.getComment());
        });

        return ResponseResult.success(new ArrayList<>(resultMap.values()));
    }

    @Override
    public ResponseResult<Object> getMarkChartData(Long teacherId, Long indexId, Long courseId) {
        List<Long> scoreList = this.baseMapper.selectMarkChartData(teacherId, indexId, courseId);
        List<SelecionVo> selecionVoList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            Float count = (float) scoreList.stream()
                    .filter(score -> score >= (20 * finalI) && score <= 20 * (finalI + 1)).count();
            SelecionVo selecionVo = new SelecionVo();
            selecionVo.setName((20 * finalI) + "-" + (20 * (finalI + 1)));
            selecionVo.setValue(count);
            selecionVoList.add(selecionVo);
        }
        return ResponseResult.success(selecionVoList);
    }

    @Override
    public ResponseResult<Object> getIndexAvgScore(Long teacherId, Long courseId) {
        List<SelecionVo> selecionVoList = this.baseMapper.selectIndexAvgScore(teacherId, courseId);
        return ResponseResult.success(selecionVoList);
    }

    @SneakyThrows
    @Override
    public void exportMarkData(Long teacherId, HttpServletResponse response) {
        List<MarkExportData> dataList = this.baseMapper.selectMarkExportData(teacherId);
        byte[] bytes = ExcelUtils.generateExcel(dataList, MarkExportData.class);
        ExcelUtils.download(bytes, "评教评分数据", response);
    }
}
