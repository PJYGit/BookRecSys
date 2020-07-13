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
                <el-button round size="small" v-if="orderMsg.type===0" class="buttonClass"
                           @click="setOrderState(1)">付款</el-button>
                <span v-if="orderMsg.type===1">待发货</span>
                <el-button round size="small" v-if="orderMsg.type===0||orderMsg.type===1" class="buttonClass"
                           @click="setOrderState(2)">取消订单</el-button>
                <span v-if="orderMsg.type===2">待确认收货</span>
                <el-button round size="small" v-if="orderMsg.type===2" class="buttonClass"
                           @click="setOrderState(3)">确认收货</el-button>
                <span v-if="orderMsg.type===3">待评价</span>
                <el-button round size="small" v-if="orderMsg.type===3" class="buttonClass"
                           @click="commentOrder=true">评价</el-button>
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

        <el-dialog title="评价订单" :visible.sync="commentOrder">
            <div v-for="(item,index) in orderMsg.items" :key="item.bid" style="margin-bottom: 20px">
                <div style="font-size: 16px;margin-bottom: 10px">
                    图书名称：{{item.name}}
                </div>
                <el-rate
                        v-model="commentList[index].mark"
                        show-score
                        text-color="#eb7a67"
                        :colors="['#eb7a67','#eb7a67','#eb7a67']"
                        style="margin-left: 10px;float: left">
                </el-rate>
                <el-input
                        type="textarea"
                        :rows="3"
                        placeholder="请输入评价"
                        v-model="commentList[index].comment"
                        maxlength="200"
                        show-word-limit
                        style="margin-top: 10px">
                </el-input>
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button style="color: #ef9585" @click="commentOrder = false">取 消</el-button>
                <el-button style="background-color: #ef9585" type="primary" @click="commentBook">确 定</el-button>
            </div>
        </el-dialog>
    </flow-board>
</template>

<script>
    import FlowBoard from "../components/board/FlowBoard";
    import MyTitle from "../components/base/myTitle";
    import API from "../api";
    import Cookies from '_js-cookie@2.2.1@js-cookie';
    export default {
        name: "orderDetail",
        components: {MyTitle, FlowBoard},
        data(){
            return{
                orderId:0,
                uid:Cookies.get('uid'),
                token:Cookies.get('token'),
                orderMsg:{},
                commentOrder:false,
                commentList:[],

            }
        },

        mounted(){
            this.orderId = this.$route.query.cid;
            this.getOrderMsg();
        },

        methods:{
            getOrderMsg(){
                let data = new FormData();
                data.append('uid',this.uid);
                data.append('token',this.token);
                data.append('cid',this.orderId);

                API.getOrderDetail(data).then(res=>{
                    if (res.state) {
                        alert("获取订单列表失败");
                        return;
                    }
                    this.orderMsg = res;
                    for(let i=0;i<this.orderMsg.items.length;i++){
                        let temp={
                            bid:this.orderMsg.items[i].bid,
                            mark:0,
                            comment:'',
                        };
                        this.commentList.push(temp);
                    }
                }).catch(msg => {
                    alert(msg)
                })
            },

            setOrderState(opcode){
                let data = new FormData();
                data.append('uid',this.uid);
                data.append('token',this.token);
                data.append('cid',this.orderId);
                data.append('opcode',opcode);

                API.setOState(data).then(res=>{
                    if (res.state === -114514) {
                        alert("支付失败，账户余额不足！");
                        return;
                    }else if(res.state){
                        if(opcode===1){
                            alert("支付失败");
                            return
                        }else if(opcode===2){
                            alert("取消订单失败");
                            return
                        }else if(opcode===3){
                            alert("确认收货失败");
                            return
                        }
                        alert("未知错误！");
                        return;
                    }
                    this.getOrderMsg();
                }).catch(msg => {
                    alert(msg)
                })
            },

            commentBook(){
                let data = new FormData();
                data.append('uid',this.uid);
                data.append('token',this.token);
                data.append('cid',this.orderId);

                this.commentList.forEach(item => {
                    data.append('items',JSON.stringify({'bid':item.bid,'mark':item.mark,
                        'comment':item.comment}));
                });

                data.append('items',"");

                API.CMOrder(data).then(res=>{
                    console.log(res);
                    if (res.state) {
                        alert("提交评价失败");
                        return;
                    }
                    alert("评价成功！");
                    this.commentOrder=false;
                    this.getOrderMsg();
                }).catch(msg => {
                    alert(msg)
                })
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