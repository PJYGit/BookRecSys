<template>
    <flow-board>
        <template v-slot:header>
            <my-title></my-title>
        </template>
        <div style="width: 70%;margin-left: 15%;font-size: 18px;margin-top: 20px">
            <div style="background-color: #ef9585;color: white;line-height: 30px;padding: 5px">
                订单编号：{{orderMsg.cid}}
            </div>
            <div style="margin-top: 10px;font-weight: 600;font-size: 20px;color: gray;margin-left: 5px">
                订单状态:
            </div>
            <div style="margin-left: 5px;margin-top: 5px;line-height: 30px">
                商家名称：{{orderMsg.sname}}
            </div>
            <div style="margin-left: 5px;margin-top: 5px;line-height: 30px">
                收货地址：{{orderMsg.address}}
            </div>
            <div style="margin-left: 5px;margin-top: 5px;line-height: 40px">
                进行状态：
                <span v-if="orderMsg.type===0">待付款</span>
                <el-button round size="small" v-if="orderMsg.type===0" class="buttonClass">
                    付款</el-button>
                <span v-if="orderMsg.type===1">待发货</span>
                <el-button round size="small" v-if="orderMsg.type===0||orderMsg.type===1" class="buttonClass">
                    取消订单</el-button>
                <span v-if="orderMsg.type===2">待确认收货</span>
                <el-button round size="small" v-if="orderMsg.type===2" class="buttonClass">
                    确认收货</el-button>
                <span v-if="orderMsg.type===3">待评价</span>
                <el-button round size="small" v-if="orderMsg.type===3" class="buttonClass">
                    评价</el-button>
                <span v-if="orderMsg.type===4">已完成</span>
                <span v-if="orderMsg.type===-1" style="color: red">已取消</span>
            </div>
            <el-divider></el-divider>
            <div style="margin-bottom: 10px;font-weight: 600;font-size: 20px;color: gray;margin-left: 5px">
                订单详情:
            </div>
            <el-table :data="orderMsg.items" style="margin-bottom: 20px">
                <el-table-column
                        type="index"
                        width="100">
                </el-table-column>
                <el-table-column label="图片">
                    <template slot-scope="scope">
                        <img :src="scope.row.pic" alt="图书图片" style="width: 40%"/>
                    </template>
                </el-table-column>
                <el-table-column label="图书名称">
                    <template slot-scope="scope">
                        {{scope.row.name}}
                    </template>
                </el-table-column>
                <el-table-column label="数量">
                    <template slot-scope="scope">
                        {{scope.row.cnt}}
                    </template>
                </el-table-column>
                <el-table-column label="总价">
                    <template slot-scope="scope">
                        {{scope.row.money}}
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </flow-board>
</template>

<script>
    import FlowBoard from "../../components/board/FlowBoard";
    import MyTitle from "../../components/base/myTitle";
    export default {
        name: "orderDetail",
        components: {MyTitle, FlowBoard},
        data(){
            return{
                orderMsg:{
                    cid:10000000,
                    type:1,
                    sid:99999,
                    sname:'first',
                    address:"北京市北京交通大学",
                    items:[{
                        bid:1,
                        name:'图书1',
                        cnt:5,
                        pic:'http://img3m2.ddimg.cn/81/12/24184692-1_b_3.jpg',
                        money:300.00,
                    },{
                        bid:2,
                        name:'图书1',
                        cnt:2,
                        pic:'http://img3m2.ddimg.cn/81/12/24184692-1_b_3.jpg',
                        money:300.00,
                    },{
                        bid:3,
                        name:'图书1',
                        cnt:1,
                        pic:'http://img3m2.ddimg.cn/81/12/24184692-1_b_3.jpg',
                        money:300.00,
                    },{
                        bid:4,
                        name:'图书1',
                        cnt:9,
                        pic:'http://img3m2.ddimg.cn/81/12/24184692-1_b_3.jpg',
                        money:300.00,
                    },],
                },

            }
        },
    }
</script>

<style scoped>
    >>> .el-button{
        border-color: #ef9585;
    }

    .buttonClass{
        margin-left: 10px;
        color: #ef9585;
    }

</style>