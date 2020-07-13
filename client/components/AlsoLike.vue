<template>
    <div style="border: solid 1px #ef9585;">
        <div style="background-color: #ef9585;color: white;font-weight: 500;font-size: 22px;
        padding: 10px;text-align: center;margin-bottom: 10px;line-height: 30px">
            猜你喜欢
        </div>
        <div v-for="item in bookList" :key="item.bid">
            <book :book-item="item" style="margin: auto"></book>
            <el-divider style="width: 20%"></el-divider>
        </div>
    </div>
</template>

<script>
    import Book from "./base/book";
    export default {
        name: "AlsoLike",
        components: {Book},
        props:{
            bookList:{
                type:Array,
            }
        }
    }
</script>

<style scoped>

</style>