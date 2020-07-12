<template>
    <div>
        <div v-for="item in myShop" :key="item.sid">
            <el-card class="box-card" style="margin-bottom: 20px;padding: 25px">
                <el-row>
                    <el-col :span="3">
                        <el-avatar shape="square" :size="70" :fit="'contain'" :src="item.head"
                        style="margin-left: 30px"></el-avatar>
                    </el-col>
                    <el-col :span="16">
                        <el-container>
                            <div style="font-size: 18px">{{item.sname}}</div>
                            <el-rate
                                    v-model="item.mark"
                                    disabled
                                    show-score
                                    text-color="#eb7a67"
                                    :colors="['#eb7a67','#eb7a67','#eb7a67']"
                                    style="margin-left: 20px;margin-top: 2px">
                            </el-rate>
                        </el-container>
                        <div style="margin-top: 20px">
                            {{item.content | message}}
                        </div>
                    </el-col>
                    <el-col :span="4" style="line-height: 74px">
                        <div @click="toShop(item.sid)" v-if="isUser===0" style="margin-left: 50%;cursor: pointer">
                            进店逛逛 >>
                        </div>
                    </el-col>
                </el-row>

            </el-card>
        </div>
    </div>
</template>

<script>
    export default {
        name: "shopList",
        filters: {
            message (value) {
                if (!value) return '';
                if (value.length > 40) {
                    return value.slice(0,40) + '...'
                }
                return value;
            }
        },
        data(){
            return{
                isUser:0,
                myShop:[{
                    sid:1,
                    sname:"有书自营1",
                    boss:{
                        uid:1,
                        name:"店主1",
                    },
                    content:'店铺介绍介绍介绍店铺介绍介绍介绍店铺介绍介绍介绍店铺介绍介绍介绍店铺介绍介绍介绍店铺介绍介绍介',
                    //head:'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594294678581&di=0f148dc1c6aeac2f1c3ba6cc1d7c0560&imgtype=0&src=http%3A%2F%2Fa2.att.hudong.com%2F86%2F10%2F01300000184180121920108394217.jpg',
                    head:'http://imgpub.chuangkit.com/design/2019/10/18/494287150_thumb',
                    code:1,
                    mark:4.7,
                },{
                    sid:2,
                    sname:"有书自营2",
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
                    sid:3,
                    sname:"有书自营3",
                    boss:{
                        uid:1,
                        name:"店主1",
                    },
                    content:'店铺介绍介绍介绍店铺介绍介绍介绍店铺介绍介绍介绍店铺介绍介绍介绍店铺介绍介绍介绍',
                    //head:'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594294678581&di=0f148dc1c6aeac2f1c3ba6cc1d7c0560&imgtype=0&src=http%3A%2F%2Fa2.att.hudong.com%2F86%2F10%2F01300000184180121920108394217.jpg',
                    head:'http://imgpub.chuangkit.com/design/2019/10/18/494287150_thumb',
                    code:1,
                    mark:4.7,
                }],
            }
        },
        methods: {
            toShop(sid){
                let link = this.$router.resolve({ path: `/shopBookList`,
                    query: { sid: sid}});
                window.open(link.href, '_blank');
            }
        }
    }
</script>

<style scoped>

</style>