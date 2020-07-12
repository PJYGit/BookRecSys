<template>
    <div>
        <div v-for="item in myShop" :key="item.sid">
            <el-card class="box-card" style="margin-bottom: 20px;padding: 20px">
                <el-row>
                    <el-col :span="3">
                        <el-avatar shape="square" :size="70" :fit="'contain'" :src="item.head"
                        style="margin-left: 30px"></el-avatar>
                    </el-col>
                    <el-col :span="16">
                        <div style="font-size: 18px;margin-bottom: 15px">{{item.name}}</div>
                        <el-container>
                            <div style="font-size: 18px">
                                评分：
                            </div>
                            <el-rate
                                    v-model="item.mark"
                                    disabled
                                    show-score
                                    text-color="#eb7a67"
                                    :colors="['#eb7a67','#eb7a67','#eb7a67']"
                                    style="margin-left: 20px;margin-top: 2px">
                            </el-rate>
                        </el-container>
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

        props:{
            myShop:{
                type:Array,
            }
        },

        data(){
            return{
                isUser:0,
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