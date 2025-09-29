<template>
  <div class="app-container">
    <el-card class="box-card">
      <div class="table-page">
        <el-table v-loading="loading" :data="pageResult.dataList" border>
          <el-table-column prop="project_name" label="项目名称" align="center" />
          <el-table-column prop="student_name" label="学生姓名" align="center" />
          <el-table-column label="评分" align="center">
            <template slot-scope="{row}">
              <el-rate v-model="row.score" disabled show-score :max="5" />
            </template>
          </el-table-column>
          <el-table-column prop="comment" label="评价内容" align="center" />
          <el-table-column prop="create_time" label="评分时间" align="center">
            <template slot-scope="{row}">
              {{ row.create_time ? row.create_time.substring(0, 19).replace('T', ' ') : '' }}
            </template>
          </el-table-column>
        </el-table>

        <el-pagination v-if="pageResult.total > 0" :current-page="pageRequest.pageNum" :page-sizes="[10, 20, 50]"
          :page-size="pageRequest.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="pageResult.total"
          @size-change="handleSizeChange" @current-change="handleCurrentChange"
          style="margin-top: 20px; text-align: right" />
      </div>
    </el-card>
  </div>
</template>

<script>
import { getEvaluationList } from '@/api/moral'
import { mapState } from 'vuex'

export default {
  name: 'MoralEvaluation',
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
      }
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
        const res = await getEvaluationList(this.pageRequest)
        if (res.code === 200 && res.data) {
          this.pageResult = {
            ...res.data,
            dataList: res.data.dataList || []
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
        this.$message.error('获取评分列表失败')
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
}
</style>