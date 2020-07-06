<template>
    <div style="width: 100%;height: 80px;">
        <img src="../../assets/image/title.png" style="height: 90px;margin-left: 90px">
        <div style="float: right;line-height: 80px;text-align: center;font-weight: 500">
            <span style="color: #EB7A67;cursor: pointer" @click="toLogin">登录/注册</span>
            <span style="margin-left: 40px;cursor: pointer" @click="toCart">购物车</span>
            <span style="margin-left: 40px;cursor: pointer" @click="toMyOrder">我的订单</span>
            <span style="margin-left: 40px;cursor: pointer" @click="toSelfCenter">个人中心</span>
            <span style="margin-left: 40px;margin-right: 80px;cursor: pointer" @click="toService">
                联系客服</span>
        </div>
    </div>
</template>

<script>
    export default {
        name: "myTitle",
        methods:{
            toLogin(){
                //console.log("click1");
            },
            toCart(){
                //console.log("click2");
            },
            toMyOrder(){
                //console.log("click3");
            },
            toSelfCenter(){
                //console.log("click4");
            },
            toService(){
                //console.log("click5");
            }
        }
    }
</script>

<style scoped>

</style>