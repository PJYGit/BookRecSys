<template>
    <flow-board>
        <template v-slot:header>
            <my-title></my-title>
        </template>
        <div style="font-size: 34px;color: #EB7A67;margin-left: 16%;margin-top: 30px;font-weight: 600">个人中心</div>
        <el-tabs :tab-position="'left'" v-model="activeName" style="min-height: 600px;margin-top: 40px;margin-left: 8%">
            <el-tab-pane label="修改信息" name="1">
                <user-info :user-info="userInfo" style="margin-left: 5%;width: 40%"></user-info>
            </el-tab-pane>
            <!--<el-tab-pane label="修改密码" name="2">
            </el-tab-pane>-->
            <el-tab-pane label="地址管理" name="3">
                <address-list :address-list="userInfo.address" style="margin-left: 5%;width: 80%"></address-list>
            </el-tab-pane>
            <el-tab-pane label="我的店铺" name="4">
                <el-button v-if="noSelfShop" style="background-color: #ef9585;color: white;margin-left: 5%;margin-bottom: 20px;font-size: 16px" @click="addAddress=true">
                    我要开店</el-button>
                <div v-if="noShop" style="margin-left: 5%">还没有店铺，自己开一个吧</div>
                <my-shop v-if="!noShop" :my-shop-list="userInfo.managed" style="width: 80%;margin-left: 5%;"></my-shop>
            </el-tab-pane>
            <el-tab-pane v-if="userInfo.role!==0" label="管理" name="5">
                <manage-block :user-role="userInfo.role" style="width: 90%;"></manage-block>
            </el-tab-pane>
        </el-tabs>

    </flow-board>
</template>

<script>
    import FlowBoard from "../components/board/FlowBoard";
    import MyTitle from "../components/base/myTitle";
    import ShopList from "../components/shop/shopList";
    import AddressList from "../components/addressList";
    import API from "../api";
    import Cookies from 'js-cookie';
    import UserInfo from "../components/selfCenter/userInfo";
    import ManageBlock from "../components/selfCenter/manageBlock";
    import MyShop from "../components/shop/myShop";
    export default {
        name: "personalCenter",
        components: {MyShop, ManageBlock, UserInfo, AddressList, ShopList, MyTitle, FlowBoard},
        data(){
            return{
                activeName:'1',
                token:Cookies.get("token"),
                uid:Cookies.get("uid"),
                userInfo:{},
                noSelfShop:true,
                noShop:false,
            }
        },

        mounted(){
            this.getMsg();
        },
        methods:{
            getMsg(){

                let data = new FormData();
                data.append('uid',this.uid);
                data.append('token',this.token);

                API.userGetInfo(data).then(res=>{

                    console.log(res);
                    if (res.state) {
                        alert("获取个人信息失败");
                        return;
                    }
                    this.userInfo = res;

                    if(this.userInfo.managed.length===0){
                        this.noShop=true;
                    }

                    for(let i=0;i<this.userInfo.managed.length;i++){
                        if(this.userInfo.managed[i].boss===1){
                            this.noSelfShop=false;
                            break;
                        }
                    }
                }).catch(msg => {
                    alert(msg)
                })

            },
        }
    }
</script>

<style scoped>
    >>> .el-tabs--left .el-tabs__header.is-left{
        margin-right: -1px;
    }

    >>> .el-tabs__item:hover{
        color: #EB7A67;
    }

    >>> .el-tabs__item.is-active{
        color: #EB7A67;
    }

    >>> .el-tabs__item{
        font-size: 16px;
        height: 60px;
        line-height: 60px;
        padding: 0 20px 0 60px;
    }
    >>> .el-tabs__active-bar{
        background-color:#ef9585;
    }

    >>> .el-tabs__content{
        border-left: solid 2px #e4e7ed;
        min-height: 600px;
        padding-left: 10px;
    }
</style>