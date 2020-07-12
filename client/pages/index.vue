<template>
    <FlowBoard>

        <template v-slot:header>
            <MyTitle></MyTitle>
        </template>

        <template style="padding: 0px; margin: 0px">
        <ads></ads>
        <el-container>
            <el-aside style="margin-top: 20px;margin-left: 30px;width: 20%">
                <classfy-menu :active-index="'0'" :is-main="0"></classfy-menu>
            </el-aside>
            <el-main>
                <my-search style="margin-top: 2px"></my-search>
                <homeshow :title="'新书上架'" :book-list="bookList" style="margin-top: 10px;margin-right: 4px"></homeshow>
                <homeshow :title="'图书推荐'" :book-list="bookList" style="margin-top: 10px;margin-right: 4px"></homeshow>
                <homeshow :title="'图书Top榜'" :book-list="bookList" style="margin-top: 10px;margin-right: 4px"></homeshow>
            </el-main>
        </el-container>
        </template>

    </FlowBoard>

</template>

<script>
    import Cookies from 'js-cookie'
    import API from "../api";
    import qs from "qs";
    import Book from "../components/base/book";
    import MyTitle from "../components/base/myTitle";
    import MyFooter from "../components/base/myFooter";
    import ClassfyMenu from "../components/base/classfyMenu";
    import MySearch from "../components/base/mySearch";
    import Homeshow from "../components/homeshow";
    import ads from "../components/home/ads";
    import FlowBoard from "../components/board/FlowBoard";

    export default {
        components: {FlowBoard, MyFooter, MyTitle, Book, ads, Homeshow, MySearch, ClassfyMenu},
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
                },

                bookList:[{
                    bid:1,
                    pic:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                    bname:"九年级上册历史书",
                    author:"作者1 作者2等",
                    price:24,
                },{
                    bid:1,
                    pic:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                    bname:"九年级上册历史书",
                    author:"作者1 作者2等",
                    price:24,
                },{
                    bid:1,
                    pic:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                    bname:"九年级上册历史书",
                    author:"作者1 作者2等",
                    price:24,
                },{
                    bid:1,
                    pic:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                    bname:"九年级上册历史书",
                    author:"作者1 作者2等",
                    price:24,
                },{
                    bid:1,
                    pic:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                    bname:"九年级上册历史书",
                    author:"作者1 作者2等",
                    price:24,
                },{
                    bid:1,
                    pic:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                    bname:"九年级上册历史书",
                    author:"作者1 作者2等",
                    price:24,
                },]
            }
        },

        mounted() {
            console.log(Cookies.get('vipRate'));
        },

        methods: {

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

