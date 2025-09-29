<template>
    <header>
        <div class="l-content">
            <el-button plain icon="el-icon-s-fold" size="mini" @click="collapseMenu"></el-button>
            <el-breadcrumb separator="/">
                <el-breadcrumb-item v-for="(item,index) in tabList" v-if="index < 12 " :key="item.path" :to="{ path: item.path }">
                    {{ item.label }}
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="r-content">
            <el-dropdown trigger="click" size="mini">
                <span class="el-dropdown-link"><img :src="userInfo.avatar" class="user"/></span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item @click.native="goHome">个人中心</el-dropdown-item>
                    <el-dropdown-item @click.native="logOut">退出</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </header>
</template>

<script>
    import {mapState} from "vuex";
    import {logout} from "../../api/user";

    export default {
        name: 'CommonHeader',
        data() {
            return {
                userImg: require('../../assets/images/user.png')
            }
        },
        computed: {
            ...mapState('tab', ['tabList']),
            ...mapState('user', ['userInfo']),
        },
        methods: {
            //控制左侧菜单是否折叠
            collapseMenu() {
                this.$store.commit('tab/collapseMenu')
            },
            goHome() {
                this.$router.push({name: 'home'});
            },
            //退出登陆
            logOut() {
                logout().then(res => {
                    if (res.code === 200) {
                        this.$router.push({path: '/'});
                        this.$message({
                            type: 'info',
                            message: '退出成功'
                        });
                    }
                }).catch(err => {

                })
            }
        },
    }
</script>

<style lang="scss" scoped>
  header {
    display: flex;
    height: 100%;
    align-items: center;
    justify-content: space-between;
  }

  .l-content {
    display: flex;
    align-items: center;

    .el-button {
      margin-right: 20px;
    }
  }

  .el-breadcrumb__item {
    .el-breadcrumb__inner {
      color: #666666;
      font-weight: normal;
    }

    &:last-child {
      .el-breadcrumb__inner {
        color: #ffffff;
      }
    }
  }

  .r-content {
    .user {
      width: 40px;
      height: 40px;
      border-radius: 50%;
    }
  }

  .el-breadcrumb__item:last-child .el-breadcrumb__inner {
    color: #303133;
    font-weight: 700;
  }
</style>
