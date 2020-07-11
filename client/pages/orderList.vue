<template>
    <flow-board>
        <template v-slot:header>
            <my-title></my-title>
        </template>

    </flow-board>
</template>

<script>
    import FlowBoard from "../components/board/FlowBoard";
    import MyTitle from "../components/base/myTitle";
    export default {
        name: "orderList",
        components: {MyTitle, FlowBoard}
    }
</script>

<style scoped>

</style>