<template>
    <div style="border: solid 1px #ef9585;padding: 25px;text-align: center;">
        <el-avatar shape="square" :size="150" :fit="'contain'" :src="shopMsg.head"></el-avatar>
        <div style="margin-bottom: 15px;margin-top: 20px;">
            <i class="el-icon-s-shop" style="font-size: 22px;margin-top: 3px"></i>
            <span style="font-size: 20px;margin-left: 5px;">{{shopMsg.sname}}</span>
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
                shopMsg:{
                    sid:1,
                    sname:"有书自营",
                    boss:{
                        uid:1,
                        name:"店主1",
                    },
                    content:'店铺介绍介绍介绍店铺介绍介绍介绍店铺介绍介绍介绍店铺介绍介绍介绍店铺介绍介绍介绍',
                    //head:'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594294678581&di=0f148dc1c6aeac2f1c3ba6cc1d7c0560&imgtype=0&src=http%3A%2F%2Fa2.att.hudong.com%2F86%2F10%2F01300000184180121920108394217.jpg',
                    head:'http://imgpub.chuangkit.com/design/2019/10/18/494287150_thumb',
                    code:1,
                    mark:4.7,
                }
            }
        },

        mounted(){
            this.shopId = this.$route.query.sid;
            console.log(this.shopId);
        },

        methods:{

        }
    }
</script>

<style scoped>

</style>