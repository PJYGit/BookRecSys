<template>
    <FlowBoard>
        <template v-slot:header >
            <MyTitle></MyTitle>
        </template>

        666

    </FlowBoard>
</template>

<script>
    import FlowBoard from "../components/board/FlowBoard";
    import MyTitle from "../components/base/myTitle";
    export default {
        name: "car",
        components: {MyTitle, FlowBoard}
    }
</script>

<style scoped>

</style>