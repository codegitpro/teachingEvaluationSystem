<template>
    <div>
        <template v-for="item in menuList">
            <el-submenu v-if="item.path && !item.component" :index="item.menuId.toString()">
                <template slot="title">
                    <i :class="getIconClass(item)"></i>
                    <span slot="title">{{ item.label }}</span>
                </template>
                <TreeMenu :menu-list="item.children"></TreeMenu>
            </el-submenu>
            <el-menu-item v-if="item.path && item.component" @click="clickMenu(item)" :index="item.path"
                :key="item.menuId.toString()">
                <i :class="getIconClass(item)"></i>
                <span slot="title">{{ item.label }}</span>
            </el-menu-item>
        </template>
    </div>
</template>

<script>
import { mapMutations } from "vuex";
import router from "@/router";

export default {
    name: 'TreeMenu',
    props: {
        menuList: {
            type: Array,
            default: () => []
        }
    },
    data() {
        return {};
    },
    //监听属性 类似于data概念
    computed: {},
    //监控data中的数据变化
    watch: {},
    //方法集合
    methods: {
        ...mapMutations('tab', ['updateMenu', 'stepNext']),
        getIconClass(item) {
            const menuIconMap = {
                '系统管理': 'el-icon-setting',
                '评教管理': 'el-icon-s-data',
                '问卷管理': 'el-icon-document',
                '问卷填写': 'el-icon-edit-outline',
                '问卷数据图表': 'el-icon-pie-chart',
                '评分数据图表': 'el-icon-s-data',
                '指标管理': 'el-icon-s-operation',
                '课程管理': 'el-icon-reading',
                '教师管理': 'el-icon-s-custom',
                '学生管理': 'el-icon-user',
                '学生课程': 'el-icon-notebook-2',
                '教师课程': 'el-icon-notebook-1',
                '首页': 'el-icon-s-home',
                '菜单管理': 'el-icon-menu',
                '用户管理': 'el-icon-user',
                '角色管理': 'el-icon-s-check',
                '问题管理': 'el-icon-question',
                //'问卷填写': 'el-icon-edit',
                '问卷数据': 'el-icon-s-data'
            };

            // 调试用
            console.log('Menu item:', item.label, 'Icon:', menuIconMap[item.label]);

            if (item.icon && item.icon !== '#') {
                return item.icon.startsWith('el-icon-') ? item.icon : `el-icon-${item.icon}`;
            }

            return menuIconMap[item.label] || 'el-icon-folder';
        },
        clickMenu(item) {
            let { routeName } = item;
            this.stepNext(routeName);
            this.updateMenu(item);
            this.$router.push({ name: routeName });
        }
    },
    //生命周期 - 创建完成（可以访问当前this实例）
    created() {

    },
    //生命周期 - 挂载完成（可以访问DOM元素）
    mounted() {
        console.log('Menu List:', this.menuList); // 调试用
    },
    beforeCreate() {
    },//生命周期 - 创建之前
    beforeMount() {
    }, //生命周期 - 挂载之前
    beforeUpdate() {
    }, //生命周期 - 更新之前
    updated() {
    }, //生命周期 - 更新之后
    beforeDestroy() {
    }, //生命周期 - 销毁之前
    destroyed() {
    }, //生命周期 - 销毁完成
    activated() {
    }, //如果页面有keep-alive缓存功能，路由组件激活触发
    deactivated() {
    } //如果页面有keep-alive缓存功能，路由组件失活触发
};
</script>

<style scoped></style>
