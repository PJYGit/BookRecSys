<template>
    <div class="loginRegBoxBackHower">
        <el-menu default-active="0" mode="horizontal" @select="tryChangeBoard" style="height: 60px">
            <el-menu-item index="0">登录</el-menu-item>
            <el-menu-item index="1">注册</el-menu-item>
        </el-menu>
        <LoginForm v-if="activeIndex == 0"></LoginForm>
        <RegForm  v-if="activeIndex == 1"></RegForm>
    </div>
</template>

<script>
    import LoginForm from "./LoginForm";
    import RegForm from "./RegForm";

    export default {
        name: "LoginRegBox",
        components: {RegForm, LoginForm},
        data() {
            return {
                activeIndex: "0",
            }
        },
        methods: {
            tryChangeBoard: function (id) {
                this.activeIndex = id;
            }
        }
    }
</script>

<style scoped>
    .loginRegBoxBackHower {
        display: block;
        height: 500px;
        width: 500px;
        background: white;
        border: outset black 1px;
        box-shadow: 4px 4px 4px #000000;
    }
    .el-menu-item {
        width: 249px;
        text-align: center;
    }
</style>