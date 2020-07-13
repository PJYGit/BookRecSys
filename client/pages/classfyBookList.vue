<template>
    <flow-board v-loading="loading">
        <template v-slot:header>
            <my-title></my-title>
        </template>

        <el-row style="margin-top: 20px">
            <el-col :span="6">
                <classfy-menu :active-index="activeClassfy.toString()" :is-main="1" @MenuMethod="changeClassfy"
                              style="width: 80%;margin-left: 15%"></classfy-menu>
            </el-col>
            <el-col :span="17" style="margin-left: 50px">
                <my-search @searchMsg="searchBook" style="margin-top: 2px;margin-bottom: 30px;width: 98%"></my-search>
                <filt-price @selecBook="filterBook" style="margin-bottom: 40px;width: 98%"></filt-price>
                <el-radio-group v-model="order" @change="changeOrder" style="margin-bottom: 20px">
                    <el-radio-button label="0">按销量排序</el-radio-button>
                    <el-radio-button label="1">按价格降序</el-radio-button>
                    <el-radio-button label="2">按价格升序</el-radio-button>
                </el-radio-group>
                <div>
                    <el-pagination
                            layout="prev, pager, next"
                            :current-page.sync="currentPage"
                            :page-count="pagesize"
                            @current-change="changeClassfy" style="margin-bottom: 20px">
                    </el-pagination>
                </div>
                <div v-for="item in classfyBookList" :key="item.bid">
                    <el-col :span="4" style="margin-bottom: 30px">
                        <book :book-item="item"></book>
                    </el-col>
                </div>

            </el-col>
        </el-row>
    </flow-board>
</template>

<script>
    import FlowBoard from "../components/board/FlowBoard";
    import MyTitle from "../components/base/myTitle";
    import ClassfyMenu from "../components/base/classfyMenu";
    import Book from "../components/base/book";
    import MySearch from "../components/base/mySearch";
    import FiltPrice from "../components/base/filtPrice";
    import API from "../api"
    import Cookies from 'js-cookie';
    export default {
        name: "classfyBookList",
        components: {FiltPrice, MySearch, Book, ClassfyMenu, MyTitle, FlowBoard},
        data(){
            return{
                loading:false,
                activeClassfy:1,
                searchContent:'',
                order:0,
                uid:Cookies.get('uid'),
                token:Cookies.get('token'),
                classfyBookList:[],
                currentPage:1,
                pagesize:1,
            }
        },

        mounted() {
            this.activeClassfy=this.$route.query.tid;
            this.searchContent = this.$route.query.word;
            if(this.$route.query.word!==undefined){
                this.searchBook(this.$route.query.word);
            }else{
                this.changeClassfy({tid:this.$route.query.tid});
            }

        },

        methods:{
            changeClassfy(item){
                this.loading=true;
                if(item.tid!==undefined){
                    this.activeClassfy=item.tid;
                }else{
                    this.activeClassfy=this.$route.query.tid;
                }
                this.searchContent="";
                let fd = new FormData();
                fd.append('uid',this.uid);
                fd.append('token',this.token);
                fd.append('tid',this.activeClassfy);
                fd.append('page',this.currentPage);

                API.getBookByTag(fd).then(res=>{
                    console.log(res);
                    if (res.state) {
                        alert("获取图书列表失败");
                        this.loading=false;
                        return;
                    }
                    this.classfyBookList = res.list;
                    this.pagesize=res.pagesize;
                    this.loading=false;
                }).catch(msg => {
                    alert(msg)
                })
            },

            changeOrder(){
                if(this.$route.query.word===undefined || this.$route.query.word===null){
                    alert("请输入关键字搜索后排序！");
                    return;
                }
                this.searchBook(this.$route.query.word);
            },

            filterBook(min,max){
                this.loading=true;
                let fd = new FormData();
                fd.append('uid',this.uid);
                fd.append('token',this.token);
                fd.append('tid',this.activeClassfy);
                fd.append('word',this.searchContent);
                fd.append('order',this.order);
                fd.append('rangemin',min);
                fd.append('rangemax',max);

                API.SBook(fd).then(res=>{
                    console.log(res);
                    if (res.state) {
                        alert("获取图书列表失败");
                        this.loading=false;
                        return;
                    }
                    this.classfyBookList = res.list;
                    this.pagesize=1;
                    this.currentPage=1;
                    this.loading=false;
                }).catch(msg => {
                    alert(msg)
                })
            },

            searchBook(cont){
                this.$router.push({path:"/classfyBookList",query:{tid:this.activeClassfy,word: cont}});
                this.loading=true;
                let fd = new FormData();
                fd.append('uid',this.uid);
                fd.append('token',this.token);
                fd.append('tid',this.activeClassfy);
                fd.append('word',cont);
                fd.append('order',this.order);

                API.SBook(fd).then(res=>{
                    console.log(res);
                    if (res.state) {
                        alert("获取图书列表失败");
                        this.loading=false;
                        return;
                    }
                    this.pagesize=1;
                    this.currentPage=1;
                    this.classfyBookList = res.list;
                    this.loading=false;
                }).catch(msg => {
                    alert(msg)
                })
            },
        }
    }
</script>

<style scoped>

    >>> .el-radio-button__orig-radio:checked + .el-radio-button__inner{
        background-color: #ef9585;
        border-color:#ef9585;
        box-shadow:-1px 0 0 0 #ef9585;
    }

    /*>>> .el-radio-button .is-active .is-focus:hover{
        color: #ef9585;//鼠标移过单选组字体颜色修改未成功
    }*/
</style>