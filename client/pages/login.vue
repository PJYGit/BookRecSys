<template>
    <BlockBoard>
        <template v-slot:header>
            <my-title></my-title>
        </template>

        666

    </BlockBoard>
</template>

<script>
    import MyTitle from "../components/base/myTitle";
    import FlowBoard from "../components/board/FlowBoard";
    import BlockBoard from "../components/board/BlockBoard";

    export default {
        components: {FlowBoard, MyTitle, BlockBoard},
        name: "login"
    }
</script>

<style scoped>
    >>> .el-scrollbar__view {
        height: 100%;
    }
    .content {
        min-height: 100%;
    }
    .content-inside {
        padding: 20px 20px 160px;
    }
    .footer {
        height: 160px;
        margin-top: -160px;
    }
</style>