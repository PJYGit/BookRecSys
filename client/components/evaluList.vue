<template>
    <div>
        <div v-if="evalList.length===0">
            暂无评价
        </div>
        <div v-for="item in evalList" :key="item.cid" style="width: 100%;">
            <el-container style="margin-left: 15px">
                <div style="font-size: 18px;font-weight: 600;margin-top: -2px">
                    {{item.sname}}
                </div>
                <el-rate
                        v-model="item.mark"
                        disabled
                        show-score
                        text-color="#eb7a67"
                        :colors="['#eb7a67','#eb7a67','#eb7a67']"
                        style="margin-left: 20px">
                </el-rate>
            </el-container>
            <div style="margin-top: 15px;text-indent: 2em;width: 99%;margin-left: 15px;line-height: 30px">
                {{item.content}}
            </div>
            <el-divider style="width: 100%"></el-divider>
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