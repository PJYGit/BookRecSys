<template>
    <flow-board>
        <template slot="header">
            <my-title></my-title>
        </template>
        <template slot="default">
            <el-row :gutter="20">
                <el-col :span="8">
                    <el-card style="margin: 30px; text-align: center">
                        <div slot="header">
                            <span style="font-size: 34px;color: #EB7A67;">
                                店铺信息
                            </span>
                            <br />
                            <img src="http://img3m2.ddimg.cn/81/12/24184692-1_b_3.jpg" alt="店铺图片">
                        </div>
                        <div slot="default">
                            <table style="width: 100%">
                                <tr>
                                    <td>店铺名</td>
                                    <td>
                                        {{shopInfo.name}}
                                    </td>
                                </tr>
                                <tr>
                                    <td>店主</td>
                                    <td>
                                        {{shopInfo.boss.name}}
                                    </td>
                                </tr>
                                <tr>
                                    <td>店铺状态</td>
                                    <td>
                                        <el-tag v-if="shopInfo.code===1" type="primary">审核中</el-tag>
                                        <el-tag v-if="shopInfo.code===2" type="success">正常</el-tag>
                                        <el-tag v-if="shopInfo.code===3" type="danger">封禁</el-tag>
                                    </td>
                                </tr>
                            </table>
                            <el-rate
                                    v-model="shopInfo.mark"
                                    disabled
                                    show-score
                                    text-color="#eb7a67"
                                    :colors="['#eb7a67','#eb7a67','#eb7a67']"
                                    style="margin: 25px auto;">
                            </el-rate>
                            <p style="font-size: 1.6em;color: #EB7A67;">
                                简介
                            </p>
                            <span style="margin: 20px;">
                                {{shopInfo.content}}
                            </span>
                        </div>
                    </el-card>
                </el-col>
                <el-col :span="16">

                </el-col>
            </el-row>
        </template>
    </flow-board>
</template>

<script>
    import FlowBoard from "../../components/board/FlowBoard";
    import MyTitle from "../../components/base/myTitle";
    import API from "../../api";
    export default {
        name: "manage",
        components: {MyTitle, FlowBoard},
        data() {
            return {
                sid: 0,
                shopInfo: {
                    uid: 1,
                    sid: 1,
                    name: '123',
                    boss: {
                        name: 'test'
                    },
                    manager: [],
                    content: 'asd1qaw',
                    code: 2,
                    head: 'http://img3m2.ddimg.cn/81/12/24184692-1_b_3.jpg',
                    mark: 4.7
                },
                shopBookList: []
            }
        },
        mounted(){
            this.sid = this.$route.query.sid;
            this.getShopInfo()
            this.getShopBookInfo()
        },
        methods: {
            getShopInfo: function () {
                let data = new FormData()
                data.append('uid', this.$cookie.get('uid'))
                data.append('token', this.$cookie.get('token'))
                data.append('sid', this.$cookie.get('sid'))
                API.getShopInfo(data).then(res => {
                    if (res.state === 0) {
                        this.shopInfo = res
                    } else this.$message.error('获取商店消息失败')
                }).catch(res => {
                    this.$message.info(res)
                })
            },
            getShopBookInfo: function () {
                let data = new FormData()
                data.append('uid', this.$cookie.get('uid'))
                data.append('token', this.$cookie.get('token'))
                data.append('sid', this.$cookie.get('sid'))
                API.getShopBookInfo().then(res => {
                    if (res.state === 0) {
                        this.shopBookList = res.books
                    } else this.$message.error('获取商店书本信息失败')
                }).catch(res => {
                    this.$message.info(res)
                })
            }
        }
    }
</script>

<style scoped>

</style>