<template>
    <div style="border: solid 1px #ef9585;padding: 20px;border-radius:4px;overflow: hidden;">
        <div style="margin-left: 15px;overflow: hidden;display: flex;align-items: center">
            <div style="font-size: 28px;font-weight: 500;float: left">{{this.title}}</div>
            <div style="margin-left: 25px;color: #068CF0;float: left;">更多>></div>
        </div>
        <div style="width: 100%;margin-top: 25px">
            <!--<div v-for="item in BookList" :key="item.bid">
                <div style="width: 20%;float: left;align-items: center">
                    <book :book-item="item" style="margin: auto"></book>
                </div>
            </div>-->
            <el-col :span="4" v-for="item in BookList" :key="item.bid">
                <book :book-item="item" style="margin: auto"></book>
            </el-col>
        </div>
    </div>
</template>

<script>
    import Book from "./base/book";
    export default {
        name: "homeshow",
        components: {Book},
        props:{
            title:{
                type:String,
            },
            BookList:{
                type: Array,
            }
        },
    }
</script>

<style scoped>

</style>