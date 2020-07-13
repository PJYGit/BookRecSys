<template>
    <el-container style="border-top: solid 1px #ef9585;border-bottom: solid 1px #ef9585">
        <div style="width: 120px;line-height: 45px;background-color: #ef9585;
        text-align: center;color: white">
            售价
        </div>
        <el-row style="width: 80%;line-height: 45px;margin-left: 6%">
            <el-col :span="3" class="hrefClass"><div @click="filBook(0,20)">0-20元</div></el-col>
            <el-col :span="3" class="hrefClass"><div @click="filBook(20,50)">20-50元</div></el-col>
            <el-col :span="3" class="hrefClass">
                <div @click="filBook(50,100)">50-100元</div></el-col>
            <el-col :span="3" class="hrefClass">
                <div @click="filBook(100,200)">100-200元</div></el-col>
            <el-col :span="3" class="hrefClass">
                <div @click="filBook(200,9999)">200元以上</div></el-col>
            <el-col :span="8">
                <el-container>
                    <!--限制数字输入还没做-->
                    <el-input size="mini" onkeyup="value=value.replace(/[^\d]/g,'')" v-model="rangemin" placeholder="价格" style="width: 80px;margin-right: 5px"></el-input>
                     -
                    <el-input size="mini" v-model="rangemax" placeholder="价格" style="width: 80px;margin-left: 5px"></el-input>
                    <div style="margin-left: 15px;text-decoration: underline;color: #eb7a67;font-size: 17px;
                    margin-top: 2px;cursor: pointer" @click="filBook(rangemin,rangemax)">筛选</div>
                </el-container>
            </el-col>
        </el-row>
    </el-container>
</template>

<script>
    export default {
        name: "filtPrice",
        data(){
            return{
                rangemin:'',
                rangemax:'',

            }
        },

        methods:{
            filBook(min,max){
                this.$emit('selecBook',min,max);
            }
        }
    }
</script>

<style scoped>
    .hrefClass{
    }

    .hrefClass:hover{
        text-decoration: underline;
        color: #ef9585;
        cursor: pointer;
    }
</style>