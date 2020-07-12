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
                <el-button type="primary" style="width: 100%" :loading="locks.submitLock"
                           @click="checkAndSubmit()">登录</el-button>
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
                    username : "18876002015",
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
                },
                locks : {
                    submitLock : false,
                }
            }
        },
        methods: {
            doLogin : function(){

                let fd = new FormData();
                fd.append('urn',this.loginForm.username);
                fd.append('pw',this.loginForm.password);

                this.locks.submitLock = true;
                API.userLogin(fd).then(rsp => {
                    if(rsp.state == 0){
                        this.doLoginSuccess(rsp);
                    }else{
                        this.doLoginFail();
                    }
                }, err => {
                    console.log("ERR" + err);
                    this.doLoginFail();
                })
            },
            doLoginFail : function(err){
                this.locks.submitLock = false;
                this.$message.error('登录失败');
            },
            doLoginSuccess : function(data){
                this.$cookie.set("uid",data.uid);
                this.$cookie.set("token",data.token);

                let fd = new FormData();
                fd.append('uid',data.uid);
                fd.append('token',data.token);
                API.userGetInfo(fd).then(res=>{
                    if (res.state) {
                        return;
                    }

                    this.$cookie.set("vipRate",res.vipRate);
                }).catch(msg => {
                    alert(msg)
                });
                this.$router.push('/');
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