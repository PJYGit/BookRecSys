<template>
    <flow-board>
        <template v-slot:header>
            <my-title></my-title>
        </template>

        <el-row>
            <el-col :span="6">
                <classfy-menu style="width: 80%;margin-left: 15%"></classfy-menu>
            </el-col>
            <el-col :span="18">

            </el-col>
        </el-row>

    </flow-board>
</template>

<script>
    import FlowBoard from "../components/board/FlowBoard";
    import MyTitle from "../components/base/myTitle";
    import ClassfyMenu from "../components/base/classfyMenu";
    export default {
        name: "classfyBookList",
        components: {ClassfyMenu, MyTitle, FlowBoard}
    }
</script>

<style scoped>

</style>