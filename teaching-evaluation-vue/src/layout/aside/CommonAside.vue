<template>
    <!--collapse 是否水平折叠收起菜单-->
    <el-menu :collapse="isCollapse" :default-active="$route.path" class="el-menu-vertical-demo"
        background-color="#2f4158" text-color="#fff" active-text-color="#2b9dfc">
        <div class="logo">
            <template v-if="!isCollapse">
                <i class="el-icon-reading"></i>
                <span>学生评教系统</span>
            </template>
            <i v-else class="el-icon-reading"></i>
        </div>
        <TreeMenu :menu-list="GETMENULIST"></TreeMenu>
    </el-menu>



</template>

<script>
import { mapState, mapMutations, mapGetters, mapActions } from 'vuex';
import { getMenuList } from "../../api/menu";
import router from "vue-router";
import TreeMenu from "./TreeMenu";

export default {
    name: 'CommonAside',
    components: { TreeMenu },
    //计算属性
    computed: {
        //没有子菜单
        noChildren() {
            return this.menuList.filter(item => !item.children)
        },
        //有子菜单 (这样设置会有一个问题 就是有子菜单的 永远会在没有子菜单的下面
        hasChildren() {
            return this.menuList.filter(item => item.children)
        },
        ...mapState('tab', ['tabList', 'isCollapse']),
        ...mapState('user', ['userInfo']),
        ...mapGetters('menu', ['GETMENULIST'])
    },
    data() {
        return {
            menuList: []
        }
    },
    methods: {
        ...mapActions('menu', ['refreshMenu']),
    },
    mounted() {
        this.refreshMenu()
    }
}
</script>

<style lang="scss" scoped>
.el-menu {
    height: 100%;
    border: none;

    &.el-menu--collapse {
        width: 64px;

        .el-submenu__title {
            padding: 0 20px !important;

            span {
                height: 0;
                width: 0;
                overflow: hidden;
                visibility: hidden;
                display: inline-block;
            }

            .el-submenu__icon-arrow {
                display: none;
            }
        }
    }
}

.logo {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 55px;
    background-color: #2b3c51;
    border-bottom: 1px solid #3a4b5f;
    padding: 0 16px;

    i {
        font-size: 24px;
        color: #409EFF;
    }

    span {
        color: #ffffff;
        font-size: 16px;
        font-weight: 600;
        margin-left: 10px;
    }
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
}

:deep(.el-menu-item),
:deep(.el-submenu__title) {
    height: 50px;
    line-height: 50px;
    padding: 0 20px !important;

    &:hover {
        background-color: #1f2d3d !important;
    }

    i {
        color: #909399;
        margin-right: 10px;
        font-size: 18px;
    }
}

:deep(.el-menu-item.is-active) {
    background-color: #1f2d3d !important;
    border-right: 3px solid #409EFF;

    i {
        color: #409EFF;
    }
}

:deep(.el-submenu .el-menu-item) {
    min-width: 200px;
    padding-left: 48px !important;

    &:hover {
        background-color: #1f2d3d !important;
    }
}
</style>
