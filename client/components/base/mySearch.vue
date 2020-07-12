<template>
    <el-container>
    <el-input v-model="searchMsg" placeholder="请输入图书名称/作者/出版社">
    </el-input>
        <el-select v-model="value" placeholder="请选择" style="width: 150px;">
            <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
            </el-option>
        </el-select>
        <el-button icon="el-icon-search" @click="searchMessage">搜索</el-button>
    </el-container>
</template>

<script>
    export default {
        name: "mySearch",
        data(){
            return{
                searchMsg:'',
                value:1,
                options:[{
                    value: 1,
                    label: '搜索图书'
                },{
                    value: 2,
                    label: '搜索店铺'
                },]
            }
        },

        methods:{
            searchMessage(){
                if(this.value===1){
                    this.$emit("searchMsg",this.searchMsg);
                }else{
                    this.$router.push({path:"/searchShopList",query:{word: this.searchMsg}});
                }
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