<template>
    <el-container style="height:100% ; width: 100%; position: absolute">

        <el-header style="padding: 0px">
            <slot name="header"></slot>
        </el-header>

        <el-main style="padding: 0px">
            <slot></slot>
        </el-main>

        <el-footer style="padding: 0px; height: unset">
            <my-footer></my-footer>
        </el-footer>

    </el-container>
</template>

<script>
    import MyTitle from "../base/myTitle";
    import MyFooter from "../base/myFooter";

    export default {
        components: {MyFooter, MyTitle},
        name: "BlockBoard"
    }
</script>

<style scoped>

</style>