<template>
    <flow-board>
        <template v-slot:header>
            <my-title></my-title>
        </template>

        <el-row style="margin-top: 20px">
            <el-col :span="6">
                <shop-message></shop-message>
            </el-col>
            <el-col :span="17" style="margin-left: 50px">
                <div v-for="item in shopBookList" :key="item.bid">
                    <el-col :span="4" style="margin-bottom: 30px">
                        <book :book-item="item"></book>
                    </el-col>
                </div>

            </el-col>
        </el-row>
    </flow-board>
</template>

<script>
    import MyTitle from "../components/base/myTitle";
    import FlowBoard from "../components/board/FlowBoard";
    import Book from "../components/base/book";
    import ShopMessage from "../components/shop/shopMessage";
    export default {
        name: "shopBookList",
        components: {ShopMessage, Book, FlowBoard, MyTitle},
        data(){
            return{
                shopBookList:[{
                    bid:1,
                    src:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                    bname:"九年级上册历史书",
                    author:"作者1 作者2等",
                    price:'￥24.00',
                    oldPrice:'￥30.00 '
                },{
                    bid:2,
                    src:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                    bname:"九年级上册历史书",
                    author:"作者1 作者2等",
                    price:'￥24.00',
                    oldPrice:'￥30.00 '
                },{
                    bid:3,
                    src:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                    bname:"九年级上册历史书",
                    author:"作者1 作者2等",
                    price:'￥24.00',
                    oldPrice:'￥30.00 '
                },{
                    bid:4,
                    src:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                    bname:"九年级上册历史书",
                    author:"作者1 作者2等",
                    price:'￥24.00',
                    oldPrice:'￥30.00 '
                },{
                    bid:5,
                    src:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                    bname:"九年级上册历史书",
                    author:"作者1 作者2等",
                    price:'￥24.00',
                    oldPrice:'￥30.00 '
                },{
                    bid:6,
                    src:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                    bname:"九年级上册历史书",
                    author:"作者1 作者2等",
                    price:'￥24.00',
                    oldPrice:'￥30.00 '
                },{
                    bid:7,
                    src:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                    bname:"九年级上册历史书",
                    author:"作者1 作者2等",
                    price:'￥24.00',
                    oldPrice:'￥30.00 '
                }],
            }
        }
    }
</script>

<style scoped>

</style>