<template>
    <div class="loginFormBackHower">
        <el-form :model="loginForm" :rules="rules" ref="loginForm" label-position="top"
                 label-width="100px" >
            <el-form-item label="账号" prop="username" >
                <el-input v-model="loginForm.username"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input v-model="loginForm.password" type="password"></el-input>
            </el-form-item>
            <el-form-item>
                <br/>
                <el-button type="primary" style="width: 100%" @click="checkAndSubmit()">登录</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import API from "../../api";

    export default {
        name: "LoginForm",
        data() {
            return {
                loginForm : {
                    username : "",
                    password : "",
                },
                rules: {
                    username: [
                        { required: true, message: '请输入账号', trigger: 'blur' },
                        { min: 11, max: 11, message: '请输入正确的账号', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                        { min: 6, max: 18, message: '请输入正确的密码', trigger: 'blur' }
                    ],
                }
            }
        },
        methods: {
            doLogin : function(){
                let uname = this.loginForm.username;
                let pwd = this.loginForm.password;

                let req = {
                    urn: uname,
                    pw: pwd,
                };

                API.userLogin(req).then(
                    rsp => {
                        console.log("suc");
                    }
                ).catch(
                    err => {
                        console.log("err");
                    }
                )

                console.log("LOGIN with " + uname + " , " + pwd);
            },
            checkAndSubmit : function(){
                this.$refs['loginForm'].validate((valid) => {
                    if (valid) {
                        this.doLogin();
                    }
                });
            }
        }
    }
</script>

<style scoped>
    .loginFormBackHower{
        width: 100%;
        height: 440px;
        background: #fcfcfc;
        padding: 50px;
    }
</style>