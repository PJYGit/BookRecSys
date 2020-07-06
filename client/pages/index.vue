<template>
    <el-container style="height:100%;">

        <el-header style="padding: 0px; z-index: 10">
            <my-title></my-title>
        </el-header>

        <el-main style="padding: 60px 0 0 ;top: 0px; position: absolute; width: 100%; height: 100%;">
            <el-scrollbar style="height:100% ;overflow-x: hidden;">
                <div style="width: 100%; height: 100%;left:0;top:0; z-index: -1;">
                    <!--<img style="height: 100%;width: 100%;" src="" alt="背景图片">-->
                    <ads></ads>
                    <book></book>
                </div>
                <my-footer style="bottom: 0"></my-footer>
            </el-scrollbar>
        </el-main>

    </el-container>
</template>

<script>
    import Cookies from 'js-cookie'
    import API from "../api";
    import qs from "qs";
    import Book from "../components/base/book";
    import MyTitle from "../components/base/myTitle";
    import MyFooter from "../components/base/myFooter";
    import ads from "../components/home/ads";

    export default {
        components: {MyFooter, MyTitle, Book, ads},
        data() {
            let validateRepeatPassword = (rule, value, cb) => {
                console.log(value)
                if (value !== this.register_module.user_pwd) {
                    cb(new Error('两次输入密码不一致!'))
                } else {
                    cb()
                }
            }
            return {
                login_module: {
                    user_name: '',
                    user_pwd: '',
                },
                register_module: {
                    user_name: '',
                    user_phone: '',
                    user_pwd: '',
                    user_confirm_pwd: ''
                },
                loginRule: {
                    user_name: [
                        {required: true, message: '请输入手机号', trigger: 'blur'}
                    ],
                    user_pwd: [
                        {required: true, message: '请输入密码', trigger: 'blur'}
                    ]
                },
                registerRule: {
                    registerPsw: [
                        {validate: validateRepeatPassword, trigger: 'blur'}
                    ]
                }
            }
        },

        mounted() {

        },

        methods: {
            submitForm() {
                let data = {
                    type: 'phone',
                    urn: this.login_module.user_name,
                    pw: this.login_module.user_pwd
                };
                data = qs.stringify(data);
                API.userLogin(data).then(res => {
                    if (res.state === 0) {
                        Cookies.set("token", res.token);
                        Cookies.set("uid", res.uid);
                        let data1 = {
                            token: res.token,
                            uid: res.uid,
                        };
                        data1 = qs.stringify(data1);
                        API.userInfo(data1).then(res1 => {
                            if (res1.state === 0) {
                                Cookies.set("name", res1.nickname);
                                Cookies.set("isVIP", res1.isVIP);
                                Cookies.set("regtime", res1.regtime);
                            } else {
                                alert("获取用户名失败")
                            }
                        }).catch(msg => {
                            alert(msg);
                        });
                        this.$router.push({path: '/home'});
                    } else {
                        this.$alert('密码或用户名错误', '登录失败', {
                            confirmButtonText: '确认',
                        })
                    }
                }).catch(msg => {
                    alert(msg);
                })
            },
            registerUser: function () {
                let data = {
                    nickname: this.register_module.user_name,
                    phone: this.register_module.user_phone,
                    password: this.register_module.user_pwd
                }
                data = qs.stringify(data)
                API.register(data)
                    .then(res => {
                        if (res.state === 0) {
                            Cookies.set("uid", res.uid)
                            Cookies.set("token", res.token)
                            let data1 = {
                                token: res.token,
                                uid: res.uid,
                            };
                            data1 = qs.stringify(data1);
                            API.userInfo(data1).then(res1 => {
                                if (res1.state === 0) {
                                    Cookies.set("name", res1.nickname);
                                    Cookies.set("isVIP", res1.isVIP);
                                    Cookies.set("regtime", res1.regtime);
                                } else {
                                    alert("获取用户名失败")
                                }
                            }).catch(msg => {
                                alert(msg);
                            });
                            this.$router.push({path: '/home'})
                        }
                    })
                    .catch(res => {
                        alert(res)
                    })
            }
        },

    }
</script>

<style scoped>
    .title {
        font-size: 1.8rem;
        text-align: center;
    }

    .card {
        width: 25rem;
        min-height: 15rem;
        margin: 10% auto;
        border-radius: 10px;
    }

</style>

