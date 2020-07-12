<template>
    <flow-board>
        <template v-slot:header>
            <my-title></my-title>
        </template>

        <el-row style="margin-top: 20px">
            <el-col :span="6">
                <classfy-menu :active-index="activeClassfy" :is-main="1" @MenuMethod="changeClassfy"
                              style="width: 80%;margin-left: 15%"></classfy-menu>
            </el-col>
            <el-col :span="17" style="margin-left: 50px">
                <my-search style="margin-top: 2px;margin-bottom: 30px;width: 98%"></my-search>
                <filt-price style="margin-bottom: 40px;width: 98%"></filt-price>
                <el-radio-group v-model="order" @change="changeOrder" style="margin-bottom: 20px">
                    <el-radio-button label="0">按销量排序</el-radio-button>
                    <el-radio-button label="1">按价格降序</el-radio-button>
                    <el-radio-button label="2">按价格升序</el-radio-button>
                </el-radio-group>
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
                activeClassfy:'0',
                searchContent:'',
                order:0,
                uid:Cookies.get('uid'),
                token:Cookies.get('token'),
                classfyBookList:[{
                    bid:1,
                    pic:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                    bname:"九年级上册历史书",
                    author:"作者1 作者2等",
                    price:24,
                },{
                    bid:2,
                    pic:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                    bname:"九年级上册历史书",
                    author:"作者1 作者2等",
                    price:24,
                },{
                    bid:3,
                    pic:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                    bname:"九年级上册历史书",
                    author:"作者1 作者2等",
                    price:24,
                },{
                    bid:4,
                    pic:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                    bname:"九年级上册历史书",
                    author:"作者1 作者2等",
                    price:24,
                },{
                    bid:5,
                    pic:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                    bname:"九年级上册历史书",
                    author:"作者1 作者2等",
                    price:24,
                },{
                    bid:6,
                    pic:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                    bname:"九年级上册历史书",
                    author:"作者1 作者2等",
                    price:24,
                },{
                    bid:7,
                    pic:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                    bname:"九年级上册历史书",
                    author:"作者1 作者2等",
                    price:24,
                }],
            }
        },

        mounted() {
            this.activeClassfy=this.$route.query.tid;
            this.searchContent = this.$route.query.word;
            this.searchBook();
        },

        methods:{
            changeClassfy(item){
                this.activeClassfy=item.tid;
                this.searchContent="";
                this.searchBook();
            },

            changeOrder(){
                this.searchBook();
            },

            searchBook(){
                let fd = new FormData();
                fd.append('uid',this.uid);
                fd.append('token',this.token);
                if(this.activeClassfy===undefined){
                    fd.append('tid',0);
                }else{
                    fd.append('tid',this.activeClassfy);
                }

                if(this.searchContent!==""){
                    fd.append('word',this.searchContent);
                }

                fd.append('order',this.order);

                API.SBook(fd).then(res=>{
                    console.log(res);
                    if (res.state) {
                        alert("获取图书列表失败");
                        return;
                    }
                    this.classfyBookList = res.list;

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