<template>
    <flow-board>
        <template v-slot:header>
            <my-title></my-title>
        </template>
        <el-container style="width: 80%;margin-left: 10%;margin-top: 20px">
            <el-table :data="orderList">
                <el-table-column
                        type="index"
                        width="100">
                </el-table-column>
                <el-table-column label="订单封面">
                    <template slot-scope="scope">
                        <img :src="scope.row.items[0].pic" alt="图书图片" style="width: 40%"/>
                    </template>
                </el-table-column>
                <el-table-column label="商家名称">
                    <template slot-scope="scope">
                        {{scope.row.sname}}
                    </template>
                </el-table-column>
                <el-table-column label="订单内容">
                    <template slot-scope="scope">
                        <div v-for="(item, index) in scope.row.items" :key="index">
                            <el-container>
                                <p style="font-size: 1rem;color: #EB7A67;">书名：{{item.name}}</p>
                                <p style="font-size: 1rem;color: #EB7A67;margin-left: 15px;margin-bottom: 10px">数量：{{item.cnt}}</p>
                            </el-container>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="状态">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.type===0" type="warning">待付款</el-tag>
                        <el-tag v-if="scope.row.type===1" type="info">待发货</el-tag>
                        <el-tag v-if="scope.row.type===2" type="info">待确认发货</el-tag>
                        <el-tag v-if="scope.row.type===3">待评价</el-tag>
                        <el-tag v-if="scope.row.type===4" type="success">已完成</el-tag>
                        <el-tag v-if="scope.row.type===-1" type="danger">已取消</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="">
                    <template slot-scope="scope">
                        <el-button type="text">查看详情</el-button>
                    </template>
                </el-table-column></el-table>
        </el-container>
    </flow-board>
</template>

<script>
    import FlowBoard from "../components/board/FlowBoard";
    import MyTitle from "../components/base/myTitle";
    export default {
        name: "orderList",
        components: {MyTitle, FlowBoard},
        data(){
            return{
                orderList: [
                    {
                        cid: 1,
                        type: 0,
                        sname:"hhhh",
                        items: [
                            {
                                bid: 1,
                                name: 'test',
                                cnt: 5,
                                pic: 'http://img3m2.ddimg.cn/81/12/24184692-1_b_3.jpg',
                                money: 123
                            },
                            {
                                bid: 2,
                                name: 'asdada',
                                cnt: 3,
                                pic: 'http://img3m2.ddimg.cn/81/12/24184692-1_b_3.jpg',
                                money: 11223
                            }
                        ]
                    },
                ],
            }
        }
    }
</script>

<style scoped>

</style>