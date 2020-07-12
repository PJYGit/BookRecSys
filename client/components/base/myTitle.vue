<template>
    <div style="width: 100%; height: 60px; box-shadow: 0px 4px 4px #bbbaaa">
        <img src="../../assets/image/title.png" style="height: 60px; margin-left: 90px" @click="toHome">
        <div style="float: right;line-height: 60px;text-align: center;font-weight: 500" v-if="hideMenu == null">
            <span style="color: #EB7A67;cursor: pointer" @click="toLogin">{{nicknameShowing}}</span>
            <span style="margin-left: 40px;cursor: pointer" @click="toCart">购物车</span>
            <span style="margin-left: 40px;cursor: pointer" @click="toMyOrder">我的订单</span>
            <span style="margin-left: 40px;cursor: pointer" @click="toSelfCenter">个人中心</span>
            <span style="margin-left: 40px;margin-right: 80px;cursor: pointer" @click="toService">
                联系客服</span>
        </div>
    </div>
</template>

<script>
    import API from "../../api";

    export default {
        name: "myTitle",
        props: ['hideMenu'],
        data()  {
            return {
                onLine: false,
                nicknameShowing: "登录/注册",
                info: null,
            }
        },
        mounted() {
            let ckuid = this.$cookie.get("uid");
            let cktoken = this.$cookie.get("token");
            if( ckuid == null || cktoken == null) return;

            let fd = new FormData();
            fd.append('uid',ckuid);
            fd.append('token',cktoken);

            API.userGetInfo(fd).then(rsp => {
                if(rsp.state == 0){
                    this.onLine = true;
                    this.info = rsp;
                    let nickname = rsp.nickname || "无名氏";
                    let urn = rsp.urn;
                    this.nicknameShowing = nickname + "(" + urn + ")";
                }else{
                    this.$message.info("登录已失效");
                }
            });

        },
        methods:{
            toLogin(){
                if(this.onLine){
                    this.$confirm('要退出登录吗？')
                        .then(_ => {
                            this.$cookie.delete("uid");
                            this.$cookie.delete("token");
                            this.$cookie.delete("vipRate");
                            window.location.reload();
                        })
                        .catch(_ => {});
                    this.$router.push("/");
                } else {
                    this.$router.push("/login");
                }
            },
            toCart(){
                if(this.onLine){
                    this.$router.push("/car");
                } else {
                    this.$router.push("/login");
                }
            },
            toMyOrder(){
                if(this.onLine){
                    this.$router.push("/orderList");
                } else {
                    this.$router.push("/login");
                }
            },
            toSelfCenter(){
                if(this.onLine){
                    this.$router.push("/personalCenter");
                } else {
                    this.$router.push("/login");
                }
            },
            toService(){
                if(this.onLine){
                    //this.$router.push("/car");
                } else {
                    this.$router.push("/login");
                }
            },
            toHome(){
                this.$router.push("/");
            }
        }
    }
</script>

<style scoped>

</style>