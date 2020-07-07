<template>
    <div style="border: solid 1px #ef9585;">
        <div style="background-color: #ef9585;color: white;font-weight: 600;font-size: 22px">
            浏览本书的人还喜欢
        </div>
        <div v-for="item in bookList" :key="item.bid">
            <book :book-item="item"></book>
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