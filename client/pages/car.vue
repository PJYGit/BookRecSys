<template>
    <FlowBoard>
        <template v-slot:header>
            <MyTitle></MyTitle>
        </template>

        <el-button @click="removeCarInfo" type="danger" size="small" style="position: relative; margin: 20px; left: 75%;">删除勾选订单</el-button>
        <el-button @click="createAllOrder" type="primary" size="small" style="position: relative; margin: 20px; left: 76%;right: 10%">结算所有勾选物品</el-button>
        <div v-for="(item, index) in carList" :key="item.bid">
            <div class="car-item">
                <el-card shadow="hover">
                    <el-checkbox @change="addToSubmitList(index)" style="margin: auto">
                    </el-checkbox>
                    <div style="">
                        <img :src="item.pic" alt="bookImage" style="display: inline-block"/>
                        <span style="margin-left: 5%">
                            {{item.bname}}
                        </span>
                        <span style="margin-left: 10%">
                            {{item.sname}}
                        </span>
                        <div style="margin-left: 10%; display: inline-block">
                            <el-input-number v-model="item.cnt" size="mini"/>
                        </div>
                        <el-button @click="addBook(index)" size="mini" type="primary" style="margin-left: 5%">更新数量</el-button>
                        <el-button @click="createOrder(index)" size="mini" type="primary" style="margin-left: 15%">
                            创建订单
                        </el-button>
                    </div>
                </el-card>
            </div>
        </div>

    </FlowBoard>
</template>

<script>
    import FlowBoard from "../components/board/FlowBoard";
    import MyTitle from "../components/base/myTitle";
    import API from "../api";

    export default {
        name: "car",
        components: {MyTitle, FlowBoard},
        data() {
            return {
                carList: [
                    {
                        bid: 1,
                        bname: "null",
                        sid: 1,
                        sname: "null",
                        pic: "http://img3m2.ddimg.cn/81/12/24184692-1_b_3.jpg",
                        cnt: 2
                    },
                    {
                        bid: 2,
                        bname: "null",
                        sid: 1,
                        sname: "null",
                        pic: "http://img3m2.ddimg.cn/81/12/24184692-1_b_3.jpg",
                        cnt: 2
                    },
                ],
                submitCarList: [],
                checkCarList: [],
                modifyCarList: [],
            }
        },
        mounted() {
            this.getMyCarList()
            this.initCheckCarList()
        },
        methods: {
            getMyCarList: function () {
                let data = {
                    uid: this.$cookie.get("uid"),
                    token: this.$cookie.get("token")
                }
                API.getCarList(JSON.stringify(data)).then(res => {
                    this.carList = res.list
                }).catch(res => {
                    console.log(res)
                })
            },

            initCheckCarList: function() {
                let length = this.carList.length
                for (let i = 0; i < length; i++) {
                    this.checkCarList[i] = false
                    this.modifyCarList[i] = false
                }
            },

            addBook: function (index) {
                let data = {
                    uid: this.$cookie.get("uid"),
                    token: this.$cookie.get("token"),
                    bid: this.carList[index].bid,
                    sid: this.carList[index].sid,
                    cnt: this.carList[index].cnt
                }
                API.modifyCarBookCnt(data).then(res => {
                    if (res.state === 0)
                        this.$message.success('创建订单成功');
                    else
                        this.$message.error('创建订单失败');
                }).catch(res => {

                })
            },

            addToSubmitList: function (index) {
                this.checkCarList[index] = !this.checkCarList[index]
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

            createOrder: function (index) {
                if (this.submitCarList.length === 0) this.$message.info('未勾选订单')
                else {
                    let buy = []
                    buy.push(this.carList[index])
                    let data = {
                        uid: this.$cookie.get("uid"),
                        token: this.$cookie.get("token"),
                        buy: buy
                    }
                    API.submitCarOrder(data).then(res => {
                        if (res.state === 0)
                            this.$message.success('创建订单成功');
                        else
                            this.$message.error('创建订单失败');
                    }).catch(res => {
                        console.log(res)
                    })
                }
            },

            createAllOrder: function () {
                if (this.submitCarList.length === 0) this.$message.info('未勾选订单')
                else {
                    let data = {
                        uid: this.$cookie.get("uid"),
                        token: this.$cookie.get("token"),
                        buy: this.submitCarList
                    }
                    API.submitCarOrder(data).then(res => {
                        if (res.state === 0)
                            this.$message.success('创建订单成功');
                        else
                            this.$message.error('创建订单失败');
                    }).catch(res => {
                        console.log(res)
                    })
                }
            },

            removeCarInfo: function () {
                if (this.submitCarList.length === 0) this.$message.info('未勾选订单')
                else {
                    let data = {
                        uid: this.$cookie.get("uid"),
                        token: this.$cookie.get("token"),
                        car: this.submitCarList
                    }
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