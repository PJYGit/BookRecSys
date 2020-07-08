<template>
    <BlockBoard style="min-width: 600px">
        <template v-slot:header>
            <my-title></my-title>
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

    export default {
        components: {LoginRegBox, FlowBoard, MyTitle, BlockBoard},
        name: "login"
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