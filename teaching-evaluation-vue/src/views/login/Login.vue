<template>
  <div class="login-page" v-loading="loading" element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)">
    <div class="login-container">
      <div class="login-content">
        <div class="login-header">
          <div class="logo">
            <i class="el-icon-reading"></i>
          </div>
          <h2>学生评教系统</h2>
          <p>Student Teaching Evaluation System</p>
        </div>

        <el-form :model="user" ref="ruleForm" :rules="rules" class="login-form">
          <el-form-item prop="userName">
            <el-input v-model="user.userName" prefix-icon="el-icon-user" placeholder="请输入用户名">
            </el-input>
          </el-form-item>

          <el-form-item prop="password">
            <el-input type="password" v-model="user.password" prefix-icon="el-icon-lock" placeholder="请输入密码">
            </el-input>
          </el-form-item>

          <el-form-item class="login-buttons">
            <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import { getUserInfo, login } from "../../api/user";
import { mapMutations } from "vuex";
import MessageUtil from "../../utils/MessageUtil";

export default {
  name: 'Login',
  data() {
    var validateUserName = (rule, value, callback) => {
      if (value === '') {
        return callback(new Error(this.$t('login.usernameNotNull')));
      } else {
        callback();
      }
    };
    var validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error(this.$t('login.passwordNotNull')));
      } else {
        callback();
      }
    };
    return {
      loading: false,
      user: {
        userName: 'admin',
        password: '123456',
      },
      rules: {
        userName: [
          { validator: validateUserName, trigger: 'blur' }
        ],
        password: [
          { validator: validatePassword, trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    changeLanguage() {
      let language = sessionStorage.getItem("language");
      language = language === "zh" ? "en" : "zh";
      this.$i18n.locale = language;
      // 这样保存起来可以防止刷新页面就回到了初始值
      sessionStorage.setItem('language', language);
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.login();
        } else {
          return false;
        }
      });
    },
    resetForm() {
      // this.$refs[formName].resetFields();
      this.user.userName = '';
      this.user.password = '';
    },
    // init() {
    // 动画
    // const siginBtn = document.getElementById('signin')
    // const sigupBtn = document.getElementById('signup')
    // const container = document.querySelector(".container")
    //
    // siginBtn.addEventListener('click', (e) => {
    //     container.classList.remove("right-panel-active")
    // })
    //
    // sigupBtn.addEventListener('click', (e) => {
    //     container.classList.add("right-panel-active")
    // })

    // const fistForm = document.getElementById('from1')
    // const secondForm = document.getElementById('from2')
    // fistForm.addEventListener("submit", (e) => {
    //     return e.preventDefault()
    // })
    // secondForm.addEventListener('submit', (e) => {
    //     return e.preventDefault()
    // })
    // },
    login() {
      this.loading = true
      login(this.user).then(res => {
        console.log(res)
        if (res.code === 200) {
          let token = res.data.token;
          window.sessionStorage.setItem('token', token)
          setTimeout(() => {
            this.getUserInfo(token);
            this.loading = false;
            MessageUtil.success('登录成功')
          }, 1000)
        } else {
          setTimeout(() => {
            this.loading = false;
            MessageUtil.error("用户名或密码错误")
          }, 1000)
        }
      }).catch(err => {
        setTimeout(() => {
          this.loading = false;
          MessageUtil.error('登陆失败')
        }, 1000)
      })
    },
    getUserInfo(token) {
      getUserInfo({ token }).then(res => {
        // debugger
        if (res.code === 200) {
          this.setUserInfo(res.data)
          this.$router.push({ path: '/home' });

        }
      })
    },
    ...mapMutations('user', ['setUserInfo'])
  },
  mounted() {
    // this.init();
  },

};
</script>

<style lang="scss" scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(45deg, #2b5876, #4e4376);

  .login-container {
    width: 100%;
    max-width: 420px;
    padding: 20px;

    .login-content {
      background: rgba(255, 255, 255, 0.95);
      border-radius: 12px;
      padding: 40px;
      box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);

      .login-header {
        text-align: center;
        margin-bottom: 40px;

        .logo {
          width: 80px;
          height: 80px;
          margin: 0 auto 20px;
          background: #409EFF;
          border-radius: 50%;
          display: flex;
          align-items: center;
          justify-content: center;

          i {
            font-size: 40px;
            color: #fff;
          }
        }

        h2 {
          font-size: 24px;
          color: #303133;
          margin-bottom: 8px;
          font-weight: 600;
        }

        p {
          color: #909399;
          font-size: 14px;
        }
      }

      .login-form {
        .el-form-item {
          margin-bottom: 25px;

          &:last-child {
            margin-bottom: 0;
          }
        }

        .el-input {
          :deep(.el-input__inner) {
            height: 45px;
            line-height: 45px;
            border-radius: 8px;
            background: #f5f7fa;
            border: 1px solid #e4e7ed;
            padding-left: 45px;

            &:focus {
              background: #fff;
              border-color: #409EFF;
              box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.1);
            }
          }

          :deep(.el-input__prefix) {
            left: 15px;

            i {
              font-size: 18px;
              color: #909399;
              line-height: 45px;
            }
          }
        }

        .login-buttons {
          margin-top: 40px;

          .el-button {
            width: 100%;
            height: 45px;
            border-radius: 8px;
            font-size: 16px;

            &+.el-button {
              margin-top: 15px;
              margin-left: 0;
            }
          }
        }
      }
    }
  }
}
</style>
