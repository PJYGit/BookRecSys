<template>
    <el-container style="height:100% ; width: 100%; position: absolute">

        <el-header style="padding: 0px; z-index: 10">
            <slot name="header"></slot>
        </el-header>

        <el-main style="padding: 0px;">
            <el-scrollbar style="height: 100%; display: grid; grid-template-rows: 1fr auto;">
                <div class="content">
                    <div class="content-inside">
                        <slot></slot>
                    </div>
                </div>
                <my-footer class="footer" style="grid-row-start: 2; grid-row-end: 3;"></my-footer>
            </el-scrollbar>
        </el-main>

    </el-container>
</template>

<script>
    import MyTitle from "../base/myTitle";
    import MyFooter from "../base/myFooter";

    export default {
        components: {MyFooter, MyTitle},
        name: "FlowBoard"
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
        padding: 0px 0px 85px;
    }
    .footer {
        height: 85px;
        margin-top: -85px;
    }
</style>