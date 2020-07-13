<template>
    <div>
        <div style="display: table;width: 100%">
            <div style="display: table-row">
                <div class="tableTitle">
                    序号
                </div>
                <div class="tableTitle">
                    商店名称
                </div>
                <div class="tableTitle" style="width: 170px">
                    店内书本均分
                </div>
                <div class="tableTitle">
                </div>
            </div>
            <div :class="{'grayLine': (index % 2 === 0)}" style="display: table-row"
                 v-for="(item,index) in myShopList" :key="item.sid">
                <div class="tableCell">
                    {{index+1}}
                </div>
                <div class="tableCell">
                    {{item.sname}}
                </div>
                <div class="tableCell">
                    <el-rate
                            v-model="item.mark"
                            disabled
                            show-score
                            text-color="#eb7a67"
                            :colors="['#eb7a67','#eb7a67','#eb7a67']"
                            style="margin-left: 10px;float: left">
                    </el-rate>
                </div>
                <div class="tableCell">
                    <el-button type="text" @click="toShopManage(item.sid)">进入店铺 >></el-button>
                </div>

            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "myShop",
        props:{
            myShopList:{
                type:Array,
            }
        },

        methods:{
            toShopManage: function (sid) {
                let link = this.$router.resolve({ path: '/manage',
                    query: { sid: sid }});
                window.open(link.href, '_blank');
            }
        },
    }
</script>

<style scoped>
    @import "../../assets/tableStyle.css";
</style>