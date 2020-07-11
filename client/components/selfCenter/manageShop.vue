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
                    {{item.bossname}}
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
                    <el-button type="text" style="font-size: 16px" v-if="item.code===1" @click="checkingShop(item)">审核</el-button>
                    <el-button type="text" style="font-size: 16px" v-if="item.code===2" @click="setShopState(item)">封禁</el-button>
                    <el-button type="text" style="font-size: 16px" v-if="item.code===3" @click="checkingShop(item)">解封</el-button>
                </div>

            </div>
        </div>

        <el-dialog title="审核商店信息" :visible.sync="checkShop">
            <el-form :inline="true" :model="shopMsg" label-width="130px" style="font-size: 16px">
                <el-form-item label="商店编码">
                    {{shopMsg.sid}}
                </el-form-item>
                <el-form-item label="商店名称">
                    {{shopMsg.name}}
                </el-form-item>
            </el-form>
            <el-form :inline="true" :model="shopMsg" label-width="130px">
                <el-form-item label="店主编码">
                    {{shopMsg.bossid}}
                </el-form-item>
                <el-form-item label="店主名称">
                    {{shopMsg.bossname}}
                </el-form-item>
            </el-form>
            <el-form :model="shopMsg" label-width="130px">
                <el-form-item label="店铺简介">
                    {{shopMsg.content}}
                </el-form-item>
                <el-form-item label="店内书本评分">
                    <el-rate
                            v-model="shopMsg.mark"
                            disabled
                            show-score
                            text-color="#eb7a67"
                            :colors="['#eb7a67','#eb7a67','#eb7a67']"
                            style="margin-top: 10px">
                    </el-rate>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="updateUser = false">取 消</el-button>
                <el-button type="primary" @click="setShopState(shopMsg)">通过审核</el-button>
            </div>
        </el-dialog>

        <el-pagination
                layout="prev, pager, next"
                :current-page.sync="currentPage"
                :page-count="pagesize"
                @current-change="getShopList" style="margin-top: 20px;float: right;margin-right: 20px">
        </el-pagination>
    </div>
</template>

<script>
    import EvaluList from "../evaluList";
    import API from "../../api";
    import Cookies from 'js-cookie';
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
                token:Cookies.get('token'),
                uid:Cookies.get('uid'),
                shopList:[],
                pagesize:0,
                currentPage:1,
                checkShop:false,
                shopMsg:{},
            }
        },

        mounted(){
            this.getShopList();
        },

        methods:{
            getShopList(){
                let data = new FormData();
                data.append('uid',this.uid);
                data.append('token',this.token);
                data.append('page',this.currentPage);

                API.getSList(data).then(res=>{
                    if (res.state) {
                        alert("获取店铺列表失败");
                        return;
                    }
                    this.shopList = res.shops;
                    this.pagesize=res.pageCnt;

                }).catch(msg => {
                    alert(msg)
                })
            },

            checkingShop(item){
                this.checkShop=true;
                this.shopMsg=item;
            },

            setShopState(msg){
                let data = new FormData();
                data.append('uid',this.uid);
                data.append('token',this.token);
                data.append('sid',msg.sid);

                if(msg.code===2){
                    msg.code=3;
                }else{
                    msg.code=2;
                }

                data.append('code',msg.code);

                API.setShopInfo(data).then(res=>{
                    if (res.state) {
                        alert("修改店铺信息失败");
                        return;
                    }

                    alert("修改店铺信息成功");
                    this.checkShop=false;
                }).catch(msg => {
                    alert(msg)
                })
            },


        }
    }
</script>

<style scoped>
    @import "../../assets/tableStyle.css";
    >>> .el-form-item__label{
        font-size: 16px;
    }

    >>> .el-form-item__content{
        font-size: 16px;
    }
</style>