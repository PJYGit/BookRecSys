<template>
    <FlowBoard v-loading="loading">
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
                    <homeshow :title="'新书上架'" :book-list="newBookList"
                              style="margin-top: 10px;margin-right: 4px"></homeshow>
                    <homeshow v-if="recommendBook.length!==0" :title="'图书推荐'" :book-list="recommendBook"
                              style="margin-top: 10px;margin-right: 4px"></homeshow>
                    <homeshow :title="'图书Top榜'" :book-list="topNBooks"
                              style="margin-top: 10px;margin-right: 4px"></homeshow>
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
            return {
                bookList: [],
                topNBooks: [],
                recommendBook:[],
                newBookList:[],
                loading:false,
            }
        },

        mounted() {
            this.getTopNBook();
            this.getRecommendBook();
            this.getNewBook();

        },

        methods: {

            getTopNBook: function () {
                this.loading=true;
                API.getTopBook().then(res => {
                    if (res.state === 0) {
                        if(res.list.length>6){
                            this.topNBooks = res.list.slice(0,6);
                        }
                        this.loading=false;
                    } else{
                        this.$message.error('获取TOP图书榜失败')
                        this.loading=false;
                    }
                }).catch(_ => {})
            },

            getRecommendBook: function () {
                this.loading=true;
                if(this.$cookie.get('uid')!==null){
                    let data = new FormData();
                    data.append('uid', this.$cookie.get('uid'));
                    data.append('token', this.$cookie.get('token'));
                    API.getRecommendBook(data).then(res => {
                        if (res.state === 0) {
                            if(res.list.length>6){
                                this.recommendBook = res.list.slice(0,6);
                            }
                            this.loading=false;
                        } else{
                            this.$message.error('获取推荐图书榜失败')
                            this.loading=false;
                        }
                    }).catch(_ => {})
                }
            },

            getNewBook(){
                this.loading=true
                API.getNew().then(res => {
                    if (res.state === 0) {
                        if(res.list.length>6){
                            this.newBookList = res.list.slice(0,6);
                        }
                        this.loading=false;
                    } else{
                        this.$message.error('获取新书榜图书失败')
                        this.loading=false;
                    }
                }).catch(_ => {})
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

<style>
    .el-scrollbar{
        padding-bottom: 21px;
    }

    .el-select .el-input.is-focus .el-input__inner{
        border-color: #ef9585;
    }

    .el-select-dropdown__item.selected{
        color: #ef9585;
    }

    .el-select .el-input__inner:focus{
        border-color: #ef9585;
    }
</style>

