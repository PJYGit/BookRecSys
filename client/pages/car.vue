<template>
    <FlowBoard>
        <template v-slot:header>
            <MyTitle></MyTitle>
        </template>

<!--        TODO 不想写了，心态爆炸-->
<!--        <el-button @click="removeCarInfo" type="danger" size="small"-->
<!--                   style="position: relative; margin: 20px; left: 75%;">删除勾选订单-->
<!--        </el-button>-->
        <el-button @click="chooseAddress2=true" type="primary" size="small"
                   style="position: relative; margin: 20px; left: 76%;right: 10%">结算所有勾选物品
        </el-button>
        <div v-for="(item, index) in carList" :key="item.bid">
            <div class="car-item">
                <el-card shadow="hover">
                    <el-checkbox @change="addToSubmitList(index)" style="margin: auto">
                    </el-checkbox>
                    <div style="">
                        <img :src="item.pic" alt="bookImage" style="display: inline-block"/>
                        <span style="margin-left: 10%;color: #eb7a67;font-size: 1.2rem">
                            {{item.bname}}
                        </span>
                        <span style="margin-left: 10%;color: #eb7a67; font-size: 1.1rem">
                            {{item.sname}}
                        </span>
                        <span style="margin-left: 10%;color: #eb7a67; font-size: 1.1rem">
                            {{item.cnt}}
                        </span>
                        <el-button @click="chooseAddress = true" size="mini" type="primary" style="margin-left: 20%">
                            结算
                        </el-button>
                    </div>
                </el-card>

            </div>
        </div>
        <el-dialog
                title="请选择收货地址"
                :visible.sync="chooseAddress"
                width="60%">
            <address-list :is-shopping="1" ref="getAddress"></address-list>
            <span slot="footer" class="dialog-footer">
                <el-button @click="chooseAddress = false">取 消</el-button>
                <el-button type="primary" @click="createOrder">确 定</el-button>
            </span>
        </el-dialog>
        <el-dialog
                title="请选择收货地址"
                :visible.sync="chooseAddress2"
                width="60%">
            <address-list :is-shopping="1" ref="getAddress2"></address-list>
            <span slot="footer" class="dialog-footer">
                <el-button @click="chooseAddress2 = false">取 消</el-button>
                <el-button type="primary" @click="createAllOrder">确 定</el-button>
            </span>
        </el-dialog>
    </FlowBoard>
</template>

<script>
    import FlowBoard from "../components/board/FlowBoard";
    import MyTitle from "../components/base/myTitle";
    import API from "../api";
    import AddressList from "../components/addressList";

    export default {
        name: "car",
        components: {AddressList, MyTitle, FlowBoard},
        data() {
            return {
                carList: [
                    {
                        bid: 1,
                        bname: "null",
                        sid: 1,
                        sname: "null",
                        pic: "",
                        cnt: 2
                    },
                ],
                carIndex: 0,
                submitCarList: [],
                checkCarList: [],
                modifyCarList: [],
                chooseAddress: false,
                chooseAddress2:false,
            }
        },
        mounted() {
            this.getMyCarList()
            this.initCheckCarList()
        },
        methods: {
            getMyCarList: function () {
                let data = new FormData();
                data.append('uid', this.$cookie.get("uid"))
                data.append('token', this.$cookie.get("token"))
                API.getCarList(data).then(res => {
                    this.carList = res.items
                }).catch(res => {
                    console.log(res)
                })
            },

            initCheckCarList: function () {
                let length = this.carList.length
                for (let i = 0; i < length; i++) {
                    this.checkCarList[i] = false
                    this.modifyCarList[i] = false
                }
            },

            addToSubmitList: function (index) {
                this.checkCarList[index] = !this.checkCarList[index]
                this.carIndex = index
                console.log(this.carList[index])
                if (this.checkCarList[index]) {
                    this.submitCarList.push(this.carList[index])
                    console.log(this.submitCarList)
                } else {
                    let curBID = this.carList[index].bid
                    let curSID = this.carList[index].sid
                    // 筛选元素
                    this.submitCarList = this.submitCarList.filter(item => {
                        return item.bid !== curBID || item.sid !== curSID
                    })
                }
            },

            createOrder: function () {
                if (this.submitCarList.length === 0) this.$message.info('未勾选订单')
                else {
                    let buy = []
                    buy.push(this.carList[this.carIndex])
                    buy.push("")
                    let data = new FormData();
                    data.append('uid', this.$cookie.get("uid"));
                    data.append('token', this.$cookie.get("token"));
                    data.append('address',this.$refs.getAddress.returnAddressContent());
                    for (let i = 0; i < buy.length; i++)
                        data.append('buy', JSON.stringify(buy[i]))
                    API.submitCarOrder(data).then(res => {
                        if (res.state === 0)
                            this.$message.success('创建订单成功');
                        else
                            this.$message.error('创建订单失败');
                        this.chooseAddress = !this.chooseAddress
                    }).catch(res => {
                        console.log(res)
                    })
                }
            },

            createAllOrder: function () {
                if (this.submitCarList.length === 0) this.$message.info('未勾选订单')
                else {
                    let data = new FormData();
                    data.append('uid', this.$cookie.get("uid"))
                    data.append('token', this.$cookie.get("token"))
                    for (let i = 0; i < this.submitCarList.length; i++)
                        data.append('buy', JSON.stringify(this.submitCarList[i]))
                    data.append('address',this.$refs.getAddress2.returnAddressContent());
                    API.submitCarOrder(data).then(res => {
                        if (res.state === 0)
                            this.$message.success('创建订单成功');
                        else
                            this.$message.error('创建订单失败');
                    }).catch(res => {
                        console.log(res)
                    })
                    this.chooseAddress2=false;
                }
            },

            removeCarInfo: function () {
                if (this.submitCarList.length === 0) this.$message.info('未勾选订单')
                else {
                    this.newCarList = this.carList.filter(item => {

                    })
                    let data = new FormData();
                    data.append('uid', this.$cookie.get("uid"))
                    data.append('token', this.$cookie.get("token"))
                    data.append('car', JSON.stringify(this.submitCarList))
                    API.editCarOrder(data).then(res => {
                        if (res.state === 0)
                            this.$message.success('删除订单成功');
                        else
                            this.$message.error('删除订单失败');
                    }).catch(res => {
                        console.log(res)
                    })
                }
            }
        }
    }
</script>

<style scoped>
    .car-item {
        width: 75%;
        margin: 20px auto;
    }
</style>