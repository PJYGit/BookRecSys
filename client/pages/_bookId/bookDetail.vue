<template>
    <FlowBoard>
        <template v-slot:header>
            <my-title></my-title>
        </template>

        <el-container style="margin-top: 30px;margin-bottom: 20px;">
            <el-aside style="margin-left: 8%;width: 28%">
                <img :src="bookItem.pic" style="border: solid 1px #ef9585;width: 470px;margin-left: 20px;margin-top: 25px"/>
            </el-aside>
            <el-main style="width: 15%">
                <div style="font-size: 36px;font-weight: 700">
                    {{bookItem.bname}}
                </div>
                <div style="color: gray;font-size: 18px;line-height: 40px;margin-top: 10px;margin-bottom: 10px">
                    <span style="font-weight: 600">作者：</span>{{bookItem.author}}
                </div>

                <div style="font-size: 18px;line-height: 40px;width: 90%;margin-bottom: 20px">
                    <span style="font-weight: 600;font-size: 20px">内容简介：</span>{{bookItem.content | ellipsis}}
                </div>

                <div style="overflow: hidden;margin-bottom: 20px">
                    <div style="color: #eb7a67;font-weight: 600;float: left">综合评分</div>
                    <el-rate
                            v-model="bookItem.mark"
                            disabled
                            show-score
                            text-color="#eb7a67"
                            :colors="['#eb7a67','#eb7a67']"
                            style="margin-left: 10px;float: left">
                    </el-rate>
                    <span style="margin-left: 25px;color: gray;font-weight: 500;">月销 {{bookItem.sales}} 笔</span>
                </div>

                <el-container style="margin-bottom: 15px;color: #B5B386;">
                    <i class="el-icon-s-shop" style="font-size: 22px;margin-top: 3px"></i>
                    <span style="font-size: 20px;margin-left: 5px;text-decoration: underline;
                    cursor: pointer" @click="toShop">{{bookItem.sname}}</span>
                </el-container>

                <div style="line-height: 40px;margin-bottom: 20px">
                    <span style="font-size: 30px;color: red">￥{{(bookItem.price*0.9).toFixed(2)}}</span>
                    <span style="margin-left: 15px;color: gray;text-decoration:line-through;font-size: 20px">
                                定价:￥{{bookItem.price.toFixed(2)}}
                    </span>
                </div>

                <div>
                    <el-input-number v-model="buyNum" :min="1" :max="bookItem.remain" label="购买数量"></el-input-number>
                    <span style="font-size: 18px;margin-left: 20px;color: gray;">剩余库存 {{bookItem.remain}} 本</span>
                </div>
                <!--<div style="font-size: 17px;margin-bottom: 10px;margin-left: 40px">
                    月销 {{bookItem.sales}} 笔
                </div>-->

                <div style="margin-top: 20px">
                    <el-button icon="el-icon-shopping-cart-2" style="background-color: #eb7a67;color: white;border: solid 1px #ef9585;font-size: 20px">加入购物车</el-button>
                    <el-button icon="el-icon-star-off" style="color: #eb7a67;border: solid 1px #ef9585;font-size: 18px">收藏</el-button>
                </div>
            </el-main>

            <el-container style="margin-top: 30px;margin-bottom: 20px;min-height: 650px;margin-left: 20px">

            </el-container>

        </el-container>

        <el-container style="margin-bottom: 20px;margin-left: 8%">
            <el-tabs v-model="activeName" type="card">
                <el-tab-pane label="图书详情" name="first">用户管理</el-tab-pane>
                <el-tab-pane label="商品评价" name="second">配置管理</el-tab-pane>
            </el-tabs>
        </el-container>

    </FlowBoard>
</template>

<script>
    import MyTitle from "../../components/base/myTitle";
    import MyFooter from "../../components/base/myFooter";
    import FlowBoard from "../../components/board/FlowBoard";
    export default {
        name: "bookDetail",
        components: {FlowBoard, MyFooter, MyTitle},
        filters: {
            ellipsis (value) {
                if (!value) return ''
                if (value.length > 57) {
                    return value.slice(0,57) + '...'
                }
                return value
            }
        },
        data(){
            return{
                buyNum:1,
                bookItem: {
                    bid:1,
                    sid:1,
                    tid:[],
                    bname:'图书1',
                    sname:'有书专卖',
                    author:'作者1',
                    content:'这是一个内容简介简介这是一个内容简介简介这是一个内容简介简介这是一个内容简介简介这是一个内容简介简介这是一个这是一个内容简介简介这是一个个内容简介简介这是一个这是一个内容简介简介这是一个',
                    pic:'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg',
                    mark:3.4,
                    sales:300,
                    remain:23,
                    price:20.00,
                },
            }
        },

        toShop(){

        }
    }
</script>

<style scoped>
    >>> .el-rate__text{
        font-size: 18px;
    }
</style>