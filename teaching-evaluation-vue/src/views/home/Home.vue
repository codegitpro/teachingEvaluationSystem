<template>
    <el-row class="home" :gutter="20">
        <!-- 添加驾驶舱头部 -->
        <el-col :span="24">
            <div class="dashboard-header">
                <div class="left">
                    <div class="title">
                        <i class="el-icon-monitor"></i>
                        <span>学生评教系统数据中心</span>
                    </div>
                    <div class="subtitle">Student Evaluation Data Center</div>
                </div>
                <div class="right">
                    <div class="time">
                        <i class="el-icon-time"></i>
                        <span>{{ currentTime }}</span>
                    </div>
                </div>
            </div>
        </el-col>

        <el-col :span="24" style="margin-top: 20px">
            <!--shadow属性设置卡片阴影出现的时机-->
            <el-card shadow="hover">
                <div class="user">
                    <div class="user-left">
                        <img :src="userInfo.avatar" class="avatar" />
                        <div class="user-info">
                            <h3 class="username">{{ userInfo.userName }}</h3>
                            <p class="email">邮箱：{{ userInfo.email }}</p>
                            <p class="phone">电话：{{ userInfo.phoneNumber }}</p>
                            <p class="gender">性别：{{ userInfo.sex === '0' ? '男' : '女' }}</p>
                            <div class="role-list">
                                <el-tag v-for="roleName in userInfo.roleNameList" :key="roleName" size="small"
                                    effect="plain">
                                    {{ roleName }}
                                </el-tag>
                            </div>
                        </div>
                    </div>

                    <div class="password-form">
                        <el-form label-position="right" label-width="80px">
                            <el-form-item label="原密码">
                                <el-input type="password" v-model="srcPassword" placeholder="请输入原密码">
                                </el-input>
                            </el-form-item>
                            <el-form-item label="新密码">
                                <el-input type="password" v-model="newPassword1" placeholder="请输入新密码">
                                </el-input>
                            </el-form-item>
                            <el-form-item label="确认密码">
                                <el-input type="password" v-model="newPassword2" placeholder="请再次输入新密码">
                                </el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="updatePassword">修改密码</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                </div>
                <div class="login-info">
                    <!-- 保持原有的登录信息部分不变 -->
                </div>
            </el-card>

            <!-- 添加统计数据卡片 -->
            <div class="statistics">
                <el-row :gutter="20">
                    <el-col :span="6">
                        <div class="stat-card blue">
                            <div class="icon-box">
                                <i class="el-icon-edit"></i>
                            </div>
                            <div class="stat-info">
                                <div class="stat-value">{{ statistics.evaluationCount }}</div>
                                <div class="stat-label">{{ isStudent ? '待评教数量' : '评教总数' }}</div>
                            </div>
                        </div>
                    </el-col>

                    <el-col :span="6">
                        <div class="stat-card green">
                            <div class="icon-box">
                                <i class="el-icon-document"></i>
                            </div>
                            <div class="stat-info">
                                <div class="stat-value">{{ statistics.questionnaireCount }}</div>
                                <div class="stat-label">{{ isStudent ? '待填问卷数' : '问卷总数' }}</div>
                            </div>
                        </div>
                    </el-col>

                    <el-col :span="6">
                        <div class="stat-card orange">
                            <div class="icon-box">
                                <i class="el-icon-reading"></i>
                            </div>
                            <div class="stat-info">
                                <div class="stat-value">{{ statistics.courseCount }}</div>
                                <div class="stat-label">{{ isAdmin ? '教师总数' : '课程总数' }}</div>
                            </div>
                        </div>
                    </el-col>

                    <el-col :span="6">
                        <div class="stat-card red">
                            <div class="icon-box">
                                <i class="el-icon-user"></i>
                            </div>
                            <div class="stat-info">
                                <div class="stat-value">{{ statistics.peopleCount }}</div>
                                <div class="stat-label">{{ isAdmin ? '学生总数' : (userInfo.roleNameList.includes('教师') ?
                                    '学生数量' : '教师数量') }}</div>
                            </div>
                        </div>
                    </el-col>
                </el-row>
            </div>

            <!-- 添加底部版权信息 -->
            <div class="footer">
                <p class="copyright">© {{ new Date().getFullYear() }} 学生评教系统 All Rights Reserved</p>
                <p class="info">推荐使用Chrome浏览器访问本系统</p>
            </div>
        </el-col>
        <!--        <el-col :span="16" style="margin-top: 20px">-->
        <!--            <div class="num">-->
        <!--                <el-card shadow="hover" v-for="item in countData" :key="item.name"-->
        <!--                         :body-style="{ display: 'flex', padding: 0}">-->
        <!--                    <i class="icon" :class="`el-icon-${item.icon}`" :style="{ background: item.color }"></i>-->
        <!--                    <div class="detail">-->
        <!--                        <p class="num">￥ {{ item.value }}</p>-->
        <!--                        <p class="txt">{{ item.name }}</p>-->
        <!--                    </div>-->
        <!--                </el-card>-->
        <!--            </div>-->
        <!--            <el-card shadow="hover" style="height: 280px">-->
        <!--            </el-card>-->
        <!--            <div class="graph">-->
        <!--                <el-card shadow="hover" style="height: 260px">-->
        <!--                </el-card>-->
        <!--                <el-card shadow="hover" style="height: 260px">-->
        <!--                </el-card>-->
        <!--            </div>-->
        <!--        </el-col>-->
    </el-row>
</template>

<script>
import { mapState } from "vuex";
import MessageUtil from "../../utils/MessageUtil";
import { updatePassword } from "../../api/user";
import { getHomeData } from "../../api/home";

export default {
    data() {
        return {
            srcPassword: '',
            newPassword1: '',
            newPassword2: '',
            userImg: require('../../assets/images/user.png'),
            statistics: {
                evaluationCount: 0,
                questionnaireCount: 0,
                courseCount: 0,
                userCount: 0
            },
            currentTime: '',
            timer: null
        }
    },
    computed: {
        ...mapState('user', ['userInfo']),
        isAdmin() {
            return this.userInfo.roleNameList.includes('超级管理员');
        },
        isStudent() {
            return this.userInfo.roleNameList.includes('学生');
        }
    },
    methods: {
        updatePassword() {
            if (this.srcPassword === '') {
                MessageUtil.error("原密码不能为空")
                return
            } if (this.newPassword1 === '') {
                MessageUtil.error("新密码不能为空")
                return
            } if (this.newPassword2 === '') {
                MessageUtil.error("再次输入新密码不能为空")
                return
            }
            if (this.newPassword1 !== this.newPassword2) {
                MessageUtil.error("两次输入新密码不同")
                return
            }
            updatePassword({ srcPassword: this.srcPassword, newPassword: this.newPassword1 }).then(res => {
                if (res.code === 200) {
                    MessageUtil.success("修改成功")
                    this.$router.push({ path: '/' });
                } else {
                    MessageUtil.error(res.message)
                }
            }).catch(err => {
                MessageUtil.error("修改失败")
            })

        },
        getStatistics() {
            getHomeData().then(res => {
                if (res.code === 200) {
                    this.statistics = res.data;
                }
            }).catch(err => {
                MessageUtil.error('获取统计数据失败');
            });
        },
        updateTime() {
            const now = new Date();
            const year = now.getFullYear();
            const month = String(now.getMonth() + 1).padStart(2, '0');
            const day = String(now.getDate()).padStart(2, '0');
            const hours = String(now.getHours()).padStart(2, '0');
            const minutes = String(now.getMinutes()).padStart(2, '0');
            const seconds = String(now.getSeconds()).padStart(2, '0');
            this.currentTime = `${year}年${month}月${day}日 ${hours}:${minutes}:${seconds}`;
        }
    },
    mounted() {
        console.log("userInfo:", this.userInfo)
        this.getStatistics();
    },
    created() {
        this.updateTime();
        this.timer = setInterval(this.updateTime, 1000);
    },
    beforeDestroy() {
        if (this.timer) {
            clearInterval(this.timer);
        }
    }
}


</script>

<style lang="scss" scoped>
@import '~@/assets/scss/home';

.user {
    display: flex;
    justify-content: space-between;
    padding-bottom: 20px;
    margin-bottom: 20px;
    border-bottom: 1px solid #ebeef5;

    .user-left {
        display: flex;
        align-items: flex-start;

        .avatar {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            margin-right: 30px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .user-info {
            .username {
                font-size: 24px;
                color: #303133;
                margin: 0 0 15px;
            }

            .email,
            .phone,
            .gender {
                color: #606266;
                margin-bottom: 8px;
                font-size: 14px;
            }

            .role-list {
                margin-top: 15px;

                .el-tag {
                    margin-right: 8px;
                    margin-bottom: 8px;
                }
            }
        }
    }

    .password-form {
        width: 300px;
        padding-left: 40px;
        border-left: 1px solid #ebeef5;

        .el-form-item {
            margin-bottom: 18px;
        }

        .el-button {
            width: 100%;
        }
    }
}

.login-info {
    p {
        line-height: 28px;
        font-size: 14px;
        color: #909399;

        span {
            color: #606266;
            margin-left: 20px;
        }
    }
}

.statistics {
    margin: 20px 0;

    .stat-card {
        display: flex;
        align-items: center;
        padding: 20px;
        border-radius: 8px;
        color: #fff;
        transition: all 0.3s;
        cursor: pointer;

        &:hover {
            transform: translateY(-5px);
        }

        .icon-box {
            width: 64px;
            height: 64px;
            border-radius: 16px;
            background: rgba(255, 255, 255, 0.2);
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 20px;

            i {
                font-size: 32px;
            }
        }

        .stat-info {
            .stat-value {
                font-size: 28px;
                font-weight: bold;
                line-height: 1;
                margin-bottom: 8px;
            }

            .stat-label {
                font-size: 14px;
                opacity: 0.9;
            }
        }
    }

    .blue {
        background: linear-gradient(135deg, #1890ff 0%, #36a6ff 100%);
    }

    .green {
        background: linear-gradient(135deg, #52c41a 0%, #73d13d 100%);
    }

    .orange {
        background: linear-gradient(135deg, #faad14 0%, #ffc53d 100%);
    }

    .red {
        background: linear-gradient(135deg, #ff4d4f 0%, #ff7875 100%);
    }
}

.footer {
    text-align: center;
    padding: 20px 0;
    color: #909399;
    font-size: 14px;
    border-top: 1px solid #ebeef5;
    margin-top: 40px;

    .copyright {
        margin-bottom: 8px;
    }

    .info {
        color: #c0c4cc;
    }
}

.dashboard-header {
    background: linear-gradient(90deg, #1c3046 0%, #1d3d65 100%);
    padding: 20px;
    border-radius: 8px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    color: #fff;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

    .left {
        .title {
            display: flex;
            align-items: center;
            font-size: 24px;
            font-weight: bold;

            i {
                margin-right: 10px;
                font-size: 28px;
            }
        }

        .subtitle {
            margin-top: 5px;
            font-size: 14px;
            color: #a7b7cc;
        }
    }

    .right {
        .time {
            display: flex;
            align-items: center;
            font-size: 16px;

            i {
                margin-right: 8px;
                font-size: 18px;
            }
        }
    }
}
</style>
