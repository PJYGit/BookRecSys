<template>
    <div style="display: inline-block;width: 100%;">
        <el-input v-model="searchMsg" placeholder="请输入图书名称/作者/出版社" style="float: left;width: 92%"></el-input>
        <el-button icon="el-icon-search" style="float: left;">搜索</el-button>
    </div>
</template>

<script>
    export default {
        name: "mySearch",
        data(){
            return{
                searchMsg:'',
            }
        }
    }
</script>

<style scoped>
    >>> .el-input__inner{
        border: solid 1px #ef9585;
        height: 50px;
        line-height: 50px;
        border-radius:4px 0 0 4px;
        padding:0 25px;
    }

    >>> .el-input{
        font-size: 16px;
    }

    >>> .el-button{
        font-size: 16px;
        font-weight: 600;
        height: 50px;
        width: 110px;
        color: white;
        background-color: #ef9585;
        border: solid 1px #ef9585;
        border-radius:0 4px 4px 0;
    }
</style>