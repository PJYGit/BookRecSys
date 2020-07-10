<template>
    <div style="min-height: 500px">
        <div style="display: table;width: 100%">
            <div style="display: table-row">
                <div class="tableTitle">
                    序号
                </div>
                <div class="tableTitle">
                    商店名称
                </div>
                <div class="tableTitle">
                    店主
                </div>
                <div class="tableTitle">
                    商店简介
                </div>
                <div class="tableTitle" style="width: 170px">
                    商店书本均分
                </div>
                <div class="tableTitle">
                    商店状态
                </div>
                <div class="tableTitle">
                </div>
            </div>
            <div :class="{'grayLine': (index % 2 === 0)}" style="display: table-row"
                 v-for="(item,index) in shopList" :key="item.sid">
                <div class="tableCell">
                    {{index+1}}
                </div>
                <div class="tableCell">
                    {{item.name}}
                </div>
                <div class="tableCell">
                    {{item.boss.name}}
                </div>
                <div class="tableCell">
                    {{item.content | message}}
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
                    <el-tag v-if="item.code===1">待审核</el-tag>
                    <el-tag type="success" v-if="item.code===2">正常</el-tag>
                    <el-tag type="danger" v-if="item.code===3">封禁</el-tag>
                </div>
                <div class="tableCell">
                    <el-button type="text" style="font-size: 16px" v-if="item.code===1">审核</el-button>
                    <el-button type="text" style="font-size: 16px" v-if="item.code===2">封禁</el-button>
                    <el-button type="text" style="font-size: 16px" v-if="item.code===3">解封</el-button>
                </div>

            </div>
        </div>
        <!--<el-pagination
                layout="prev, pager, next"
                :current-page.sync="currentPage"
                :page-count="pagesize"
                @current-change="getUserList" style="margin-top: 20px;float: right;margin-right: 20px">
        </el-pagination>-->
    </div>
</template>

<script>
    import EvaluList from "../evaluList";
    export default {
        name: "manageShop",
        components: {EvaluList},
        filters:{
            message (value) {
                if (!value) return '';
                if (value.length > 20) {
                    return value.slice(0,20) + '...'
                }
                return value;
            },
        },
        data(){
            return{
                shopList:[{
                    sid:1,
                    name:"有书自营",
                    boss:{
                        uid:1,
                        name:"店主1",
                    },
                    content:'店铺介绍介绍介绍店铺介绍介绍介绍店铺介绍介绍介绍店铺介绍介绍介绍店铺介绍介绍介绍',
                    //head:'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594294678581&di=0f148dc1c6aeac2f1c3ba6cc1d7c0560&imgtype=0&src=http%3A%2F%2Fa2.att.hudong.com%2F86%2F10%2F01300000184180121920108394217.jpg',
                    head:'http://imgpub.chuangkit.com/design/2019/10/18/494287150_thumb',
                    code:1,
                    mark:4.7,
                },{
                    sid:2,
                    name:"有书自营",
                    boss:{
                        uid:1,
                        name:"店主1",
                    },
                    content:'店铺介绍介绍介绍店铺介绍介绍介绍店铺介绍介绍介绍店铺介绍介绍介绍店铺介绍介绍介绍',
                    //head:'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594294678581&di=0f148dc1c6aeac2f1c3ba6cc1d7c0560&imgtype=0&src=http%3A%2F%2Fa2.att.hudong.com%2F86%2F10%2F01300000184180121920108394217.jpg',
                    head:'http://imgpub.chuangkit.com/design/2019/10/18/494287150_thumb',
                    code:2,
                    mark:4.7,
                },{
                    sid:3,
                    name:"有书自营",
                    boss:{
                        uid:1,
                        name:"店主1",
                    },
                    content:'店铺介绍介绍介绍店铺介绍介绍介绍店铺介绍介绍介绍店铺介绍介绍介绍店铺介绍介绍介绍',
                    //head:'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594294678581&di=0f148dc1c6aeac2f1c3ba6cc1d7c0560&imgtype=0&src=http%3A%2F%2Fa2.att.hudong.com%2F86%2F10%2F01300000184180121920108394217.jpg',
                    head:'http://imgpub.chuangkit.com/design/2019/10/18/494287150_thumb',
                    code:3,
                    mark:4.7,
                }],
            }
        }
    }
</script>

<style scoped>
    @import "../../assets/tableStyle.css";
</style>