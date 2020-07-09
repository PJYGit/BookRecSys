<template>
    <BlockBoard style="min-width: 600px">
        <template v-slot:header>
            <my-title hideMenu></my-title>
        </template>

        <div class="loginContentHower">
            <LoginRegBox style="margin-right: 50px"></LoginRegBox>
        </div>

    </BlockBoard>
</template>

<script>
    import MyTitle from "../components/base/myTitle";
    import FlowBoard from "../components/board/FlowBoard";
    import BlockBoard from "../components/board/BlockBoard";
    import LoginRegBox from "../components/login/LoginRegBox";
    import API from "../api";

    export default {
        components: {LoginRegBox, FlowBoard, MyTitle, BlockBoard},
        name: "login",
        mounted() {
            let ckuid = this.$cookie.get("uid");
            let cktoken = this.$cookie.get("token");
            if( ckuid == null || cktoken == null) return;

            let req = {
                uid: ckuid,
                token: cktoken,
            };

            let failOpt = (function (ck) {
                return function () {
                    console.log("Bad token, clean.");
                    //waiting for Gakki to redeploy.
                    ck.delete("uid");
                    ck.delete("token");
                }
            })(this.$cookie);

            API.userGetInfo(JSON.stringify(req)).then(rsp => {
                if(rsp.state == 0){
                    this.$router.push('/');
                }else{
                    failOpt();
                }
            }, err => {
                failOpt();
            });

        },
    }
</script>

<style scoped>
    .loginContentHower {
        width: 100%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: flex-end;
        background: #7f828b;
    }
</style>