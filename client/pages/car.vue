<template>
    <FlowBoard>
        <template v-slot:header>
            <MyTitle></MyTitle>
        </template>

        <div v-for="(item, index) in carList" :key="item.bid">
            <div class="car-item">
                <el-card shadow="hover">
                    <el-checkbox @change="addToSubmitList(index)" size="medium" style="margin: auto"></el-checkbox>
                    <img :src="item.pic" alt="bookImage" style="display: inline-block"/>
                    <span style="margin-left: 10%">
                    {{item.bname}}
                </span>
                    <span style="margin-left: 10%">
                    {{item.sname}}
                </span>
                    <div style="margin-left: 10%; display: inline-block">
                        <el-input-number @change="modifyCarInfo" v-model="item.cnt" size="mini"/>
                    </div>
                    <el-button size="mini" type="primary" style="margin-left: 20%" @click="createOrder(index)">
                        创建订单
                    </el-button>
                </el-card>
            </div>
        </div>

    </FlowBoard>
</template>

<script>
    import FlowBoard from "../components/board/FlowBoard";
    import MyTitle from "../components/base/myTitle";

    export default {
        name: "car",
        components: {MyTitle, FlowBoard},
        data() {
            return {
                carList: [
                    {
                        bid: 1,
                        bname: "123",
                        sid: 1,
                        sname: "1234",
                        pic: "http://img3m2.ddimg.cn/81/12/24184692-1_b_3.jpg",
                        cnt: 2
                    },
                    {
                        bid: 2,
                        bname: "123",
                        sid: 1,
                        sname: "1234",
                        pic: "http://img3m2.ddimg.cn/81/12/24184692-1_b_3.jpg",
                        cnt: 2
                    },
                ],
                submitCarList: []
            }
        },
        mounted() {
            this.getMyCarList()
        },
        methods: {
            getMyCarList: function () {

            },

            modifyCarInfo: function (cv, ov) {
            },

            addToSubmitList: function (index) {

            },

            createOrder: function (index) {
                console.log(this.carList[index])
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