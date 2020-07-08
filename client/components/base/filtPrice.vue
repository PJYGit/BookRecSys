<template>
    <el-container style="border-top: solid 1px #ef9585;border-bottom: solid 1px #ef9585">
        <div style="width: 120px;line-height: 45px;background-color: #ef9585;
        text-align: center;color: white">
            售价
        </div>
        <el-row style="width: 80%;line-height: 45px;margin-left: 6%">
            <el-col :span="3" class="hrefClass">0-20元</el-col>
            <el-col :span="3" class="hrefClass">20-50元</el-col>
            <el-col :span="3" class="hrefClass">50-70元</el-col>
            <el-col :span="3" class="hrefClass">70-100元</el-col>
            <el-col :span="3" class="hrefClass">100元以上</el-col>
            <el-col :span="8">
                <el-container>
                    <el-input size="mini" v-model="rangemin" placeholder="价格" style="width: 80px;margin-right: 5px"></el-input>
                     -
                    <el-input size="mini" v-model="rangemax" placeholder="价格" style="width: 80px;margin-left: 5px"></el-input>
                    <div style="margin-left: 15px;text-decoration: underline;color: #eb7a67;font-size: 17px;
                    margin-top: 2px;cursor: pointer">筛选</div>
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