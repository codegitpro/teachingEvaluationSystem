## 本项目完整源码是收费的  接毕业设计和论文

### 作者微信：grapro666 QQ：3642795578 (支持部署调试、支持代做毕设)

### 接javaweb、python、小程序、H5、APP、各种管理系统、单片机、嵌入式等开发

### 选题+开题报告+任务书+程序定制+安装调试+论文+答辩ppt

**博客地址：
[https://blog.csdn.net/2303_76227485/article/details/152260623](https://blog.csdn.net/2303_76227485/article/details/152260623)**

**视频演示：
[https://www.bilibili.com/video/BV1Z8HczTEpk/](https://www.bilibili.com/video/BV1Z8HczTEpk/)**

**毕业设计所有选题地址：
[https://github.com/codegitpro/allProject](https://github.com/codegitpro/allProject)**

## 基于Java+Springboot+vue的教学评价系统(源代码+数据库)239
## 一、系统介绍
本项目前后端分离，分为用户、教师、管理员三种角色(角色和菜单可自定义)。
### 1、用户：
- 登录、问卷填写、学生评教、教师评学、密码修改
### 2、教师：
- 评教详情、评分数据图表、导出、教师评学、教师课程管理、学生课程管理
### 3、管理员：
- 问卷管理、问卷填写、数据图表、指标管理、学生评教管理、课程管理、教师评学管理、教师管理、教师课程管理、学生管理、学生课程管理、用户管理、菜单管理、角色管理
## 二、所用技术
后端技术栈：
- Springboot
- mybatisPlus
- Mysql
- Maven

前端技术栈：
- Vue
- Vue-router
- axios
- elementui
- echarts

## 三、环境介绍
基础环境 :IDEA/eclipse, JDK1.8, Mysql5.7及以上, Maven3.6, node14, navicat

所有项目以及源代码本人均调试运行无问题 可支持远程调试运行

## 四、页面截图
### 1、用户：
![contents](./picture/picture1.png)
![contents](./picture/picture2.png)
![contents](./picture/picture3.png)
![contents](./picture/picture4.png)
![contents](./picture/picture5.png)
![contents](./picture/picture6.png)
![contents](./picture/picture7.png)
### 2、教师：
![contents](./picture/picture8.png)
![contents](./picture/picture9.png)
![contents](./picture/picture10.png)
![contents](./picture/picture11.png)
![contents](./picture/picture12.png)
![contents](./picture/picture13.png)
![contents](./picture/picture14.png)
![contents](./picture/picture15.png)
![contents](./picture/picture16.png)
### 3、管理员：
![contents](./picture/picture17.png)
![contents](./picture/picture18.png)
![contents](./picture/picture19.png)
![contents](./picture/picture40.png)
![contents](./picture/picture20.png)
![contents](./picture/picture21.png)
![contents](./picture/picture22.png)
![contents](./picture/picture23.png)
![contents](./picture/picture24.png)
![contents](./picture/picture25.png)
![contents](./picture/picture26.png)
![contents](./picture/picture27.png)
![contents](./picture/picture28.png)
![contents](./picture/picture29.png)
![contents](./picture/picture30.png)
![contents](./picture/picture31.png)
![contents](./picture/picture32.png)
![contents](./picture/picture33.png)
## 五、浏览地址
前台访问地址：http://localhost:8080

用户账号密码：zhangsan/123456

管理员账号密码：admin/123456

教师账号密码：lisi/123456

## 六、部署教程
1. 使用Navicat或者其它工具，在mysql中创建对应名称的数据库，并执行项目的sql文件

2. 使用IDEA/Eclipse导入F:\educationProject\教务管理系统\teaching-evaluation-system项目，若为maven项目请选择maven，等待依赖下载完成

3. 修改application-dev.yml里面的数据库配置,src/main/java/com/geqian/evalution/teachingevalution/TeachingEvalutionApplication.java启动后端项目

4. vscode或idea打开teaching-evaluation-vue项目

5. 在编译器中打开terminal，执行npm install 依赖下载完成后执行 npm run serve,执行成功后会显示后台访问地址

## 七、创新点
1、可以实现教师和学生的双向评价

## 八、部分代码
```
@Api(tags = "学生课程管理")
@RestController
@RequestMapping("/studentCourse")
public class CourseStudentController {

    @Resource
    private CourseStudentService courseStudentService;

    @ApiOperation("获取学生课程列表")
    @GetMapping("/getStudentCourseList")
    public ResponseResult<Object> getStudentCourseList(PageRequest pageRequest,@RequestHeader("token")String token){
        return courseStudentService.getStudentCourseList(pageRequest,token);
    }

    @ApiOperation("删除学生课程信息")
    @PostMapping("/deleteStudentCourse")
    public ResponseResult<Object> deleteStudentCourse(@RequestBody List<StudentCourseVo> studentCourseVoList){
        return courseStudentService.deleteStudentCourse(studentCourseVoList);
    }
}

/**
 * @author Administrator
 * @description 针对表【course_student】的数据库操作Service实现
 * @createDate 2025-09-19 11:53:51
 */
@Service
public class CourseStudentServiceImpl extends ServiceImpl<CourseStudentMapper, CourseStudent>
        implements CourseStudentService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseResult<Object> getStudentCourseList(PageRequest pageRequest,String token) {
        String userId = JwtUtils.parseToken(token);
        User user = userMapper.selectById(userId);
        String teacherId = null;
        if(user!=null && !user.getUserType().equals("3")){
            teacherId = userId;
        }
        //teacherId = null;
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<StudentCourseVo> studentCourseList = this.baseMapper.selectStudentCourseList(pageRequest.getKey(),teacherId);
        PageInfo<StudentCourseVo> pageInfo = new PageInfo<>(studentCourseList);
        PageResult<StudentCourseVo> pageResult = PageUtils.getPageResult(pageInfo);
        return ResponseResult.success(pageResult);
    }

    @Override
    public ResponseResult<Object> deleteStudentCourse(List<StudentCourseVo> studentCourseVoList) {
        if (!CollectionUtils.isEmpty(studentCourseVoList)) {
            for (StudentCourseVo studentCourseVo : studentCourseVoList) {
                LambdaQueryWrapper<CourseStudent> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(CourseStudent::getCourseId, studentCourseVo.getCourseId())
                        .eq(CourseStudent::getStudentId, studentCourseVo.getStudentId());
                this.remove(wrapper);
            }
            return ResponseResult.success();
        }
        return ResponseResult.fail();
    }
}
```

