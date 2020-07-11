<template>
    <flow-board>
        <template v-slot:header>
            <my-title></my-title>
        </template>

        <el-row style="margin-top: 30px">
            <el-col :span="6">
                <shop-message style="width: 70%;margin-left: 20%"></shop-message>
            </el-col>
            <el-col :span="17" style="margin-left: 50px">
                <el-tabs v-model="activeName" type="card">
                    <el-tab-pane label="图书列表" name="first">
                        <div v-for="item in shopBookList" :key="item.bid">
                            <el-col :span="4" style="margin-bottom: 30px">
                                <book :book-item="item"></book>
                            </el-col>
                        </div>
                    </el-tab-pane>

                </el-tabs>
            </el-col>
        </el-row>
    </flow-board>
</template>

<script>
    import MyTitle from "../components/base/myTitle";
    import FlowBoard from "../components/board/FlowBoard";
    import Book from "../components/base/book";
    import ShopMessage from "../components/shop/shopMessage";
    import API from '../api';
    import Cookies from 'js-cookie';
    export default {
        name: "shopBookList",
        components: {ShopMessage, Book, FlowBoard, MyTitle},
        data(){
            return{
                activeName:'first',
                uid:Cookies.get('uid'),
                token:Cookies.get('token'),
                shopId:0,
                shopBookList:[],
            }
        },

        mounted(){
            this.shopId = this.$route.query.sid;
            this.getShopBookList();
        },

        methods:{
            getShopBookList(){
                let fd = new FormData();
                fd.append('uid',this.uid);
                fd.append('token',this.token);
                fd.append('sid',this.shopId);

                API.getShopBookInfo(fd).then(res=>{
                    if (res.state) {
                        alert("获取店内图书列表失败");
                        return;
                    }
                    console.log(res);
                    this.shopBookList = res.books;

                }).catch(msg => {
                    alert(msg)
                })
            },
        }
    }
</script>

<style scoped>
    >>> .el-tabs__item:hover{
        color: #EB7A67;
    }

    >>> .el-tabs__item.is-active{
        color: #EB7A67;
    }

    >>> .el-tabs__item{
        font-size: 16px;
    }

    >>> .el-tabs--card > .el-tabs__header{
        border-bottom: 1px solid  #ef9585;
    }

    >>> .el-tabs--card > .el-tabs__header .el-tabs__nav{
        border-left: 1px solid #ef9585;
        border-right: 1px solid #ef9585;
        border-top: 1px solid #ef9585;
    }

</style>