<template>
    <div class="regFormBackHower">
        <el-form :model="regForm" :rules="rules" ref="regForm" label-position="top"
                 label-width="100px" >
            <el-form-item label="手机号" prop="username" >
                <el-input v-model="regForm.username"></el-input>
            </el-form-item>
            <el-form-item label="验证码" prop="code" >
                <el-container>
                    <el-input v-model="regForm.code" style="padding-right: 10px"></el-input>
                    <el-button @click="doSentCode"  type="primary"
                        :loading="locks.codeLock" :disabled="disables.codeDisables">
                        {{codeWord}}
                    </el-button>
                </el-container>
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input v-model="regForm.password" type="password"></el-input>
            </el-form-item>
            <el-form-item>
                <br/>
                <el-button type="primary" style="width: 100%" @click="checkAndSubmit()">注册</el-button>
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
                },
                locks : {
                    codeLock : false,
                    regLock : false,
                },
                disables : {
                    codeDisables : false,
                },
                codeWord : "获取",
                codeTime : -1,
            }
        },
        methods: {
            doReg : function(){
                let req = {
                    urn: this.regForm.username,
                    code: this.regForm.code,
                    psw: this.regForm.password,
                };

                let failFunc = (function (that) {
                    return function () {
                        that.locks.regLock = false;
                        that.$message.error("注册失败");
                    }
                })(this);

                this.locks.regLock = true;
                API.userRegister(req).then(
                    rsp => {
                        console.log(rsp);
                        if(rsp.state == 0){
                            this.locks.regLock = false;
                            this.$message.success("注册成功");
                            this.$emit('onRegSuccess',this.regForm.username);
                        }else {
                            failFunc();
                        }
                    }
                    ,err => {
                        failFunc();
                    }
                )
            },
            doSentCode : function(){
                let req = {
                    urn: this.regForm.username,
                };

                this.locks.codeLock = true;
                //wait for gakki to fix phone api.
                API.userGetPhoneCode(req).then(
                    rsp => {
                        this.locks.codeLock = false;
                        this.$message.success('发送成功');
                        this.codeCountDown();
                    }
                    ,err => {
                        this.locks.codeLock = false;
                        this.$message.error('发送失败');
                    }
                )
            },
            codeCountDown : function(){
                if(this.codeTime == 0){
                    this.disables.codeDisables = false;
                    this.codeTime = -1;
                    this.codeWord = "获取";
                }else if(this.codeTime == -1){
                    this.disables.codeDisables = true;
                    this.codeTime = 60;
                    this.codeCountDown();
                }else{
                    this.codeWord = "等待(" + this.codeTime +"s)";
                    this.codeTime--;
                    setTimeout(this.codeCountDown,1000);
                }
            },
            checkAndSubmit : function(){
                this.$refs['regForm'].validate((valid) => {
                    if (valid) {
                        this.doReg();
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