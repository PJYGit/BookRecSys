<template>
    <BlockBoard>
        <template v-slot:header>
            <my-title></my-title>
        </template>

        <div style="width: 100%; height: 100%; background: #7f828b;">
            <el-container>
                <LoginRegBox></LoginRegBox>
            </el-container>
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
    .bg-purple-dark {
        background: #99a9bf;
    }
    .bg-purple {
        background: #d3dce6;
    }
    .bg-purple-light {
        background: #e5e9f2;
    }
    .grid-content {
        border-radius: 4px;
        min-height: 36px;
    }
</style>