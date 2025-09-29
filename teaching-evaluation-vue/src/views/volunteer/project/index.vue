<template>
  <div class="app-container">
    <el-card class="box-card">
      <div class="table-page">
        <div class="filter-container">
          <el-input v-model="pageRequest.key" placeholder="项目名称" class="filter-item" style="width: 200px;"
            @keyup.enter.native="getList" />
          <el-button class="filter-item" type="primary" icon="el-icon-search" @click="getList">
            搜索
          </el-button>
          <el-button class="filter-item" type="primary" icon="el-icon-plus" @click="handleCreate">
            新增
          </el-button>
        </div>

        <!-- 添加切换按钮 -->
        <div class="search-box">
          <el-radio-group v-if="isStudent" v-model="viewType" @change="handleViewChange" style="margin-bottom: 15px;">
            <el-radio-button label="all">全部项目</el-radio-button>
            <el-radio-button label="evaluated">已评价</el-radio-button>
            <el-radio-button label="unevaluated">未评价</el-radio-button>
          </el-radio-group>
        </div>

        <el-table v-loading="loading" :data="pageResult.dataList" border style="width: 100%; margin-top: 10px;">
          <el-table-column prop="projectName" label="项目名称" align="center" />
          <el-table-column prop="description" label="项目描述" align="center" />
          <el-table-column label="状态" align="center">
            <template slot-scope="{row}">
              <el-tag :type="row.status === 1 ? 'success' : 'info'">
                {{ row.status === 1 ? '启用' : '禁用' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="creatorName" label="创建人" align="center" />
          <el-table-column prop="createTime" label="创建时间" align="center">
            <template slot-scope="{row}">
              {{ row.createTime ? row.createTime.substring(0, 19).replace('T', ' ') : '' }}
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="{row}">
              <el-button type="primary" size="mini" @click="handleUpdate(row)">
                编辑
              </el-button>
              <el-button v-if="isStudent" type="success" size="mini" @click="handleEvaluation(row)">
                评分
              </el-button>
              <el-button type="danger" size="mini" @click="handleDelete(row)">
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-pagination v-if="pageResult.total > 0" :current-page="pageRequest.pageNum" :page-sizes="[10, 20, 50]"
          :page-size="pageRequest.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="pageResult.total"
          @size-change="handleSizeChange" @current-change="handleCurrentChange"
          style="margin-top: 20px; text-align: right" />
      </div>
    </el-card>

    <!-- 项目表单对话框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="100px"
        style="width: 400px; margin-left:50px;">
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="temp.projectName" />
        </el-form-item>
        <el-form-item label="项目描述" prop="description">
          <el-input v-model="temp.description" type="textarea" :rows="4" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch v-model="temp.status" :active-value="1" :inactive-value="0" active-text="启用" inactive-text="禁用" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="submitForm">
          确定
        </el-button>
      </div>
    </el-dialog>

    <!-- 评分对话框 -->
    <el-dialog title="项目评分" :visible.sync="evaluationDialogVisible">
      <el-form ref="evaluationForm" :rules="evaluationRules" :model="evaluationForm" label-position="left"
        label-width="100px" style="width: 400px; margin-left:50px;">
        <el-form-item label="评分" prop="score">
          <el-rate v-model="evaluationForm.score" :max="5" :allow-half="true" show-score></el-rate>
        </el-form-item>
        <el-form-item label="评价内容" prop="comment">
          <el-input v-model="evaluationForm.comment" type="textarea" :rows="4" placeholder="请输入评价内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="evaluationDialogVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="submitEvaluation">
          提交
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getProjectList, addProject, updateProject, deleteProject, submitEvaluation, getEvaluatedProjects, getUnevaluatedProjects } from '@/api/volunteer'
import { mapState } from 'vuex'

export default {
  name: 'VolunteerProject',
  data() {
    return {
      loading: false,
      pageRequest: {
        pageNum: 1,
        pageSize: 10,
        key: ''
      },
      pageResult: {
        dataList: [],
        total: 0,
        pageNum: 1,
        pageSize: 10
      },
      temp: {
        projectId: undefined,
        projectName: '',
        description: '',
        status: 1
      },
      evaluationForm: {
        projectId: undefined,
        score: 0,
        comment: ''
      },
      dialogFormVisible: false,
      evaluationDialogVisible: false,
      dialogTitle: '',
      rules: {
        projectName: [{ required: true, message: '请输入项目名称', trigger: 'blur' }],
        description: [{ required: true, message: '请输入项目描述', trigger: 'blur' }]
      },
      evaluationRules: {
        score: [{ required: true, message: '请给出评分', trigger: 'change' }],
        comment: [{ required: true, message: '请输入评价内容', trigger: 'blur' }]
      },
      viewType: 'all' // 默认显示全部项目
    }
  },
  computed: {
    ...mapState('user', ['userInfo']),
    isStudent() {
      return this.userInfo.roleNameList.includes('学生')
    }
  },
  created() {
    this.getList()
  },
  methods: {
    async getList() {
      try {
        this.loading = true
        const res = await getProjectList(this.pageRequest)
        if (res.code === 200 && res.data) {
          // 如果是学生角色，只显示启用状态的项目
          if (this.isStudent) {
            this.pageResult = {
              ...res.data,
              dataList: res.data.dataList?.filter(item => item.status === 1) || []
            }
          } else {
            this.pageResult = {
              ...res.data,
              dataList: res.data.dataList || []
            }
          }

          // 如果当前页没有数据且不是第一页，则自动回退到上一页
          if (this.pageResult.dataList.length === 0 && this.pageRequest.pageNum > 1) {
            this.pageRequest.pageNum--
            await this.getList()
          }
        } else {
          this.$message.error(res.message || '获取数据失败')
          // 重置数据
          this.pageResult = {
            dataList: [],
            total: 0,
            pageNum: 1,
            pageSize: 10
          }
        }
      } catch (error) {
        this.$message.error('获取项目列表失败')
        console.error(error)
        // 重置数据
        this.pageResult = {
          dataList: [],
          total: 0,
          pageNum: 1,
          pageSize: 10
        }
      } finally {
        this.loading = false
      }
    },
    handleSizeChange(val) {
      this.pageRequest.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.pageRequest.pageNum = val
      this.getList()
    },
    handleCreate() {
      this.dialogTitle = '新增项目'
      this.temp = {
        projectId: undefined,
        projectName: '',
        description: '',
        status: 1
      }
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleUpdate(row) {
      this.dialogTitle = '编辑项目'
      this.temp = Object.assign({}, row)
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleEvaluation(row) {
      this.evaluationForm = {
        projectId: row.projectId,
        score: 0,
        comment: ''
      }
      this.evaluationDialogVisible = true
      this.$nextTick(() => {
        this.$refs['evaluationForm'].clearValidate()
      })
    },
    async handleDelete(row) {
      try {
        await this.$confirm('确认删除该项目吗？', '提示', {
          type: 'warning'
        })
        const res = await deleteProject(row.projectId)
        if (res.code === 200) {
          this.$message.success('删除成功')
          this.getList()
        } else {
          this.$message.error(res.message || '删除失败')
        }
      } catch (error) {
        console.error(error)
      }
    },
    submitForm() {
      this.$refs['dataForm'].validate(async (valid) => {
        if (valid) {
          try {
            const api = this.temp.projectId ? updateProject : addProject
            const res = await api(this.temp)
            if (res.code === 200) {
              this.$message.success(this.temp.projectId ? '更新成功' : '创建成功')
              this.dialogFormVisible = false
              this.getList()
            } else {
              this.$message.error(res.message || (this.temp.projectId ? '更新失败' : '创建失败'))
            }
          } catch (error) {
            this.$message.error(this.temp.projectId ? '更新失败' : '创建失败')
            console.error(error)
          }
        }
      })
    },
    submitEvaluation() {
      this.$refs['evaluationForm'].validate(async (valid) => {
        if (valid) {
          try {
            const res = await submitEvaluation(this.evaluationForm)
            if (res.code === 200) {
              this.$message.success('提交评分成功')
              this.evaluationDialogVisible = false
              this.getList()
            } else {
              this.$message.error(res.message || '提交评分失败')
            }
          } catch (error) {
            this.$message.error('提交评分失败')
            console.error(error)
          }
        }
      })
    },
    /** 切换视图 */
    async handleViewChange() {
      this.loading = true
      try {
        if (this.viewType === 'unevaluated') {
          const res = await getEvaluatedProjects()
          if (res.code === 200 && res.data) {
            this.pageResult = {
              ...this.pageResult,
              dataList: res.data.list || [],
              total: res.data.total || 0
            }
          }
        } else if (this.viewType === 'evaluated') {
          const res = await getUnevaluatedProjects()
          if (res.code === 200 && res.data) {
            this.pageResult = {
              ...this.pageResult,
              dataList: res.data.list || [],
              total: res.data.total || 0
            }
          }
        } else {
          await this.getList()
        }
      } catch (error) {
        console.error('获取项目列表失败:', error)
        this.$message.error('获取项目列表失败')
        this.pageResult = {
          dataList: [],
          total: 0,
          pageNum: 1,
          pageSize: 10
        }
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  padding: 20px;

  .table-page {
    background-color: #fff;
    padding: 20px;
    border-radius: 4px;
  }

  .filter-container {
    margin-bottom: 20px;

    .filter-item {
      margin-right: 10px;
    }
  }

  .search-box {
    margin-bottom: 20px;
  }
}
</style>