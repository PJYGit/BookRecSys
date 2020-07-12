<template>
    <div style="border: solid 1px #ef9585;padding: 25px;text-align: center;">
        <el-avatar shape="square" :size="150" :fit="'contain'" :src="shopMsg.head"></el-avatar>
        <div style="margin-bottom: 15px;margin-top: 20px;">
            <i class="el-icon-s-shop" style="font-size: 22px;margin-top: 3px"></i>
            <span style="font-size: 20px;margin-left: 5px;">{{shopMsg.name}}</span>
        </div>
        <div style="margin-bottom: 15px;color: #8c939d">
            <i class="el-icon-user" style="font-size: 20px;margin-top: 3px"></i>
            <span style="font-size: 18px;margin-left: 5px;">
                <span style="font-weight: 600">店主：</span>{{shopMsg.boss.name}}</span>
        </div>

        <div style="overflow: hidden;margin-bottom: 20px;width: 78%;margin-left: 13%">
            <div style="color: #eb7a67;font-weight: 600;float: left">综合评分</div>
            <el-rate
                    v-model="shopMsg.mark"
                    disabled
                    show-score
                    text-color="#eb7a67"
                    :colors="['#eb7a67','#eb7a67','#eb7a67']"
                    style="margin-left: 10px;float: left">
            </el-rate>
        </div>

        <div style="line-height: 30px;width: 79%;margin-left: 13%;text-align: left">
            <span style="font-weight: 600">店铺介绍：</span>{{shopMsg.content}}
        </div>

    </div>
</template>

<script>
    import API from "../../api";
    import Cookies from 'js-cookie';
    export default {
        name: "shopMessage",
        filters: {
            message (value) {
                if (!value) return '';
                if (value.length > 20) {
                    return value.slice(0,20) + '...'
                }
                return value;
            }
        },
        data(){
            return{
                shopId:'',
                uid:Cookies.get('uid'),
                token:Cookies.get('token'),
                shopMsg:{
                    boss:{},
                },
            }
        },

        mounted(){
            this.shopId = this.$route.query.sid;
            this.getSMsg();
        },

        methods:{
            getSMsg(){
                let data = new FormData();
                data.append('uid',this.uid);
                data.append('token',this.token);
                data.append('sid',this.shopId);

                API.getShopBaseInfo(data).then(res=>{
                    console.log(res);
                    if (res.state) {
                        alert("获取店铺信息失败");
                        return;
                    }
                    this.shopMsg = res;
                }).catch(msg => {
                    alert(msg)
                })
            }
        }
    }
</script>

<style scoped>

</style>