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
                <el-table-column label="状态"
                                 :filters="filterList"
                                 :filter-method="filterTag"
                                 filter-placement="bottom-end">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.type===0" type="warning">待付款</el-tag>
                        <el-tag v-if="scope.row.type===1" type="info">待发货</el-tag>
                        <el-tag v-if="scope.row.type===2" type="info">待确认收货</el-tag>
                        <el-tag v-if="scope.row.type===3">待评价</el-tag>
                        <el-tag v-if="scope.row.type===4" type="success">已完成</el-tag>
                        <el-tag v-if="scope.row.type===-1" type="danger">已取消</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="">
                    <template slot-scope="scope">
                        <el-button type="text" style="color: #EB7A67" @click="toOrderDetail(scope.row.cid)">查看详情</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-container>
    </flow-board>
</template>

<script>
    import FlowBoard from "../components/board/FlowBoard";
    import MyTitle from "../components/base/myTitle";
    import API from "../api";
    import Cookies from 'js-cookie';
    export default {
        name: "orderList",
        components: {MyTitle, FlowBoard},
        data(){
            return{
                uid:Cookies.get('uid'),
                token:Cookies.get('token'),
                filterList:[{
                    text: '待付款',
                    value: 0
                },{
                    text: '待发货',
                    value: 1
                },{
                    text: '待确认收货',
                    value: 2
                },{
                    text: '待评价',
                    value: 3
                },{
                    text: '已完成',
                    value: 4
                },{
                    text: '已取消',
                    value: -1
                }],
                orderList: [],
            }
        },

        mounted(){
            this.getMyOrderList();
        },

        methods:{
            filterTag(value, row) {
                return row.type === value;
            },

            toOrderDetail(cid){
                let link = this.$router.resolve({ path: `/orderDetail`,
                    query: { cid: cid }});
                window.open(link.href, '_blank');
            },

            getMyOrderList(){
                let data = new FormData();
                data.append('uid',this.uid);
                data.append('token',this.token);
                data.append('type',999);

                API.getOList(data).then(res=>{
                    if (res.state) {
                        alert("获取订单列表失败");
                        return;
                    }
                    this.orderList = res.items;

                }).catch(msg => {
                    alert(msg)
                })
            },
        }
    }
</script>

<style scoped>
    >>> .el-table th > .cell.highlight{
        color: #EB7A67;
    }
</style>
<style>
    .el-scrollbar{
        padding-bottom: 21px;
    }

    .el-checkbox__input.is-checked .el-checkbox__inner{
        background-color: #ef9585;
        border-color: #ef9585;
    }

    .el-checkbox__input.is-checked + .el-checkbox__label{
        color: #ef9585;
    }
</style>