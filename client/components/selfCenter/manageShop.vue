<template>
    <div style="min-height: 500px">
        <div style="display: table;width: 100%">
            <div style="display: table-row">
                <div class="tableTitle">
                    序号
                </div>

            </div>
            <div :class="{'grayLine': (index % 2 === 0)}" style="display: table-row"
                 v-for="(item,index) in shopList" :key="item.uid">
                <div class="tableCell">
                    {{index+1}}
                </div>

            </div>
        </div>
        <el-pagination
                layout="prev, pager, next"
                :current-page.sync="currentPage"
                :page-count="pagesize"
                @current-change="getUserList" style="margin-top: 20px;float: right;margin-right: 20px">
        </el-pagination>
    </div>
</template>

<script>
    export default {
        name: "manageShop",
        data(){
            return{
                shopList:[],
            }
        }
    }
</script>

<style scoped>

</style>