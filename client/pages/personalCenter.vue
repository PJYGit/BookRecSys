<template>
    <flow-board>
        <template v-slot:header>
            <my-title></my-title>
        </template>
        <div style="font-size: 34px;color: #EB7A67;margin-left: 16%;margin-top: 30px;font-weight: 600">个人中心</div>
        <el-tabs :tab-position="'left'" v-model="activeName" style="min-height: 600px;margin-top: 40px;margin-left: 8%">
            <el-tab-pane label="修改信息" name="1">

            </el-tab-pane>
            <el-tab-pane label="修改密码" name="2">

            </el-tab-pane>
            <el-tab-pane label="地址管理" name="3">
                <address-list :address-list="userInfo.address" style="margin-left: 5%;width: 80%"></address-list>
            </el-tab-pane>
            <el-tab-pane label="我的店铺" name="4">
                <shop-list style="width: 80%;margin-left: 5%"></shop-list>
            </el-tab-pane>
            <el-tab-pane label="管理" name="5">

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
    export default {
        name: "personalCenter",
        components: {AddressList, ShopList, MyTitle, FlowBoard},
        data(){
            return{
                activeName:'3',
                token:Cookies.get("token"),
                uid:Cookies.get("uid"),
                userInfo:{},
            }
        },

        mounted(){
            this.getMsg();
        },
        methods:{
            getMsg(){
                let data={
                    uid:this.uid,
                    token:this.token,
                };

                API.userGetInfo(data).then(res=>{
                    if (res.state) {
                        alert("获取个人信息失败");
                        //return;
                    }
                    this.userInfo = res.item;
                    //this.userInfo.address=[];
                    console.log(this.userInfo);
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