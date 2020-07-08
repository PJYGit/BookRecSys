<template>
    <div class="regFormBackHower">
        <el-form :model="regForm" :rules="rules" ref="regForm" label-position="top"
                 label-width="100px" >
            <el-form-item label="手机号" prop="username" >
                <el-input v-model="regForm.username"></el-input>
            </el-form-item>
            <el-form-item label="验证码" prop="code" >
                <el-input v-model="regForm.code"></el-input>
            </el-form-item>
            <el-form-item label="注册" prop="password">
                <el-input v-model="regForm.password" type="password"></el-input>
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
        name: "RegForm",
        data() {
            return {
                regForm : {
                    username : "",
                    password : "",
                    code: "",
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
                    code: [
                        { required: true, message: '请输入验证码', trigger: 'blur' },
                        { min: 6, max: 6, message: '请输入正确验证码', trigger: 'blur' }
                    ],
                }
            }
        },
        methods: {
            doReg : function(){
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
                this.$refs['regForm'].validate((valid) => {
                    if (valid) {
                        this.doLogin();
                    }
                });
            }
        }
    }
</script>

<style scoped>
    .regFormBackHower{
        width: 100%;
        height: 440px;
        background: #fcfcfc;
        padding: 20px 50px;
    }
    .el-form-item {
        margin-bottom: 10px;
    }
</style>