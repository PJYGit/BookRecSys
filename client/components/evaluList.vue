<template>
    <div>
        <div v-if="evalList.length===0">
            暂无评价
        </div>
        <div v-for="item in evalList" :key="item.cid">
            <div>
                {{item.sname}}
                <el-rate
                        v-model="item.mark"
                        disabled
                        show-score
                        text-color="#eb7a67"
                        :colors="['#eb7a67','#eb7a67']"
                        style="margin-left: 10px;float: left">
                </el-rate>
                {{item.content}}
            </div>
            <el-divider></el-divider>
        </div>
    </div>
</template>

<script>
    export default {
        name: "evaluList",
        props:{
            evalList:{
                type:Array,
            }
        }
    }
</script>

<style scoped>
    >>> .el-rate__text{
        font-size: 18px;
    }
</style>