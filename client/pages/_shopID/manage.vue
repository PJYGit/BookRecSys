<template>
    <flow-board>
        <template slot="header">
            <my-title></my-title>
        </template>
        <template slot="default">
            <el-tabs type="border-card" style="margin: 30px;">
                <el-tab-pane label="店铺信息管理">
                    <div style="text-align: center">
                        <div>
                            <span style="font-size: 34px;color: #EB7A67;">
                                店铺信息
                            </span>
                            <div style="float: right">
                                <p style="font-size: 1.1rem; cursor: pointer;" @click="isEditShopInfo=!isEditShopInfo">
                                    <u>
                                        修改
                                    </u>
                                </p>
                                <el-dialog
                                        title="修改店铺信息"
                                        center
                                        :visible.sync="isEditShopInfo">
                                    <div>
                                        <el-form label-width="80px">
                                            <el-form-item label="店铺名">
                                                <el-input v-model="editShopInfo.name" size="mini" style="width: 300px;" />
                                            </el-form-item>
                                            <el-form-item label="店铺简介">
                                                <el-input v-model="editShopInfo.content" type="textarea" :autosize="{ minRows: 4}" />
                                            </el-form-item>
                                            <el-form-item label="店铺图片">
                                                TODO 上传图片获得返回地址
                                            </el-form-item>
                                        </el-form>
                                    </div>
                                    <span slot="footer">
                                        <el-button @click="modifyShopInfo" type="primary" size="mini">保存修改</el-button>
                                        <el-button @click="cancelEditShopInfo" size="mini">取消</el-button>
                                    </span>
                                </el-dialog>
                            </div>
                            <br/>
                            <img src="http://img3m2.ddimg.cn/81/12/24184692-1_b_3.jpg" alt="店铺图片">
                        </div>
                        <div>
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
                    </div>
                </el-tab-pane>
                <el-tab-pane label="店铺书籍管理">
                    <div>
                        <el-button size="mini" type="primary">添加新图书</el-button>
                    </div>
                    <div style="margin: 30px;display: flex;justify-content: space-between;">
                        <el-card shadow="hover" style="width: 30%; text-align: center">
                            <div slot="header">
                                book name
                                <br/>
                                <img src="http://img3m2.ddimg.cn/81/12/24184692-1_b_3.jpg" alt="bookImage">
                            </div>
                            <div slot="default">
                                <table style="width: 100%">
                                    <tr>
                                        <td>author</td>
                                        <td>
                                            author
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>content</td>
                                        <td>
                                            content
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>sales</td>
                                        <td>
                                            sales
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>remain</td>
                                        <td>
                                            remain
                                        </td>
                                    </tr>
                                </table>
                                <p>
                                    ￥price
                                </p>
                                <el-rate
                                        v-model="shopInfo.mark"
                                        disabled
                                        show-score
                                        text-color="#eb7a67"
                                        :colors="['#eb7a67','#eb7a67','#eb7a67']"
                                        style="margin: 25px auto;">
                                </el-rate>
                                <span style="font-size: 1.1rem; cursor: pointer; color: #EB7A67">
                                    <u>
                                        修改
                                    </u>
                                </span>
                                <span style="font-size: 1.1rem; cursor: pointer;margin-left: 15px;color: #EB7A67">
                                    <u>
                                        删除
                                    </u>
                                </span>
                            </div>
                        </el-card>
                    </div>
                </el-tab-pane>
                <el-tab-pane v-if="parseInt(this.uid)===shopInfo.boss.uid" label="店铺管理员管理">

                </el-tab-pane>
                <el-tab-pane label="店铺订单管理">
                    <el-table :data="orderList">
                        <el-table-column label="订单号" prop="cid"></el-table-column>
                        <el-table-column label="订单内容">
                            <template slot-scope="scope">
                                <div v-for="(item, index) in scope.row.items" :key="index">
                                    <img :src="item.pic" alt="图书图片" style="width: 40%"/>
                                    <div style="display: inline-block;">
                                        <p style="font-size: 1.2rem;color: #EB7A67;margin: 10px;">书名：{{item.name}}</p>
                                        <p style="font-size: 1rem;color: #EB7A67;margin: 10px;">数量：{{item.cnt}}</p>
                                        <p style="font-size: 1.2rem;color: #EB7A67;margin: 10px;">￥{{item.money}}</p>
                                    </div>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="状态">
                            <template slot-scope="scope">
                                <el-tag v-if="scope.row.type===0" type="warning">待付款</el-tag>
                                <el-tag v-if="scope.row.type===1" type="info">待发货</el-tag>
                                <el-tag v-if="scope.row.type===2" type="info">待确认发货</el-tag>
                                <el-tag v-if="scope.row.type===3">待评价</el-tag>
                                <el-tag v-if="scope.row.type===4" type="success">已完成</el-tag>
                                <el-tag v-if="scope.row.type===-1" type="danger">已取消</el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作">
                            <template slot-scope="scope">
                                <el-button size="mini" type="primary">发货</el-button>
                                <el-button size="mini" type="danger">取消订单</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-tab-pane>
            </el-tabs>
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
                uid: this.$cookie.get('uid'),
                isEditShopInfo: false,
                shopInfo: {
                    uid: 0,
                    sid: 0,
                    name: '123',
                    boss: {
                        uid: 7,
                        name: 'test'
                    },
                    manager: [],
                    content: 'asd1qaw',
                    code: 2,
                    head: 'http://img3m2.ddimg.cn/81/12/24184692-1_b_3.jpg',
                    mark: 4.7
                },
                editShopInfo: {
                    uid: 0,
                    sid: 0,
                    name: '123',
                    boss: {
                        uid: 7,
                        name: 'test'
                    },
                    manager: [],
                    content: 'asd1qaw',
                    code: 2,
                    head: 'http://img3m2.ddimg.cn/81/12/24184692-1_b_3.jpg',
                    mark: 4.7
                },
                shopBookList: [],
                orderList: [
                    {
                        cid: 1,
                        type: 0,
                        items: [
                            {
                                bid: 1,
                                name: 'test',
                                cnt: 5,
                                pic: 'http://img3m2.ddimg.cn/81/12/24184692-1_b_3.jpg',
                                money: 123
                            },
                            {
                                bid: 2,
                                name: 'asdada',
                                cnt: 3,
                                pic: 'http://img3m2.ddimg.cn/81/12/24184692-1_b_3.jpg',
                                money: 11223
                            }
                        ]
                    },
                ]
            }
        },
        mounted() {
            this.sid = this.$route.query.sid;
            //this.getShopInfo()
            //this.getShopBookInfo()
        },
        methods: {
            getShopInfo: function () {
                let data = new FormData()
                data.append('uid', this.$cookie.get('uid'))
                data.append('token', this.$cookie.get('token'))
                data.append('sid', this.sid)
                API.getShopInfo(data).then(res => {
                    if (res.state === 0) {
                        this.shopInfo = res
                        this.editShopInfo = this.shopInfo
                    } else this.$message.error('获取商店消息失败')
                }).catch(res => {
                    this.$message.info(res)
                })
            },
            getShopBookInfo: function () {
                let data = new FormData()
                data.append('uid', this.$cookie.get('uid'))
                data.append('token', this.$cookie.get('token'))
                data.append('sid', this.sid)
                API.getShopBookInfo().then(res => {
                    if (res.state === 0) {
                        this.shopBookList = res.books
                    } else this.$message.error('获取商店书本信息失败')
                }).catch(res => {
                    this.$message.info(res)
                })
            },
            modifyShopInfo: function () {
                let data = new FormData()
                data.append('uid', this.$cookie.get('uid'))
                data.append('token', this.$cookie.get('token'))
                data.append('sid', this.sid)
                data.append('manager', JSON.stringify(this.shopInfo.manager))
                data.append('content', this.shopInfo.content)
                data.append('head', this.shopInfo.head)
                API.setShopInfo(data).then(res => {
                    if (res.state === 0) {
                        this.$message.success('修改成功')
                    } else this.$message.error('')
                }).catch(res => {
                    this.$message.info(res)
                })
            },
            addNewBook: function () {
                let data = new FormData()
                data.append('uid', this.$cookie.get('uid'))
                data.append('token', this.$cookie.get('token'))
                data.append('sid', this.sid)
                data.append('tid', '')
                data.append('bname', '')
                data.append('author', '')
                data.append('content', '')
                data.append('pic', '')
                data.append('remain', '')
                data.append('price', '')
                API.addNewBookIntoShop().then(res => {
                    if (res.state === 0) {
                        this.$message.success('添加成功')
                    } else this.$message.error('')
                }).catch(res => {
                    this.$message.info(res)
                })
            },
            modifyBookInfo: function (bid) {
                let data = new FormData()
                data.append('uid', this.$cookie.get('uid'))
                data.append('token', this.$cookie.get('token'))
                data.append('sid', this.sid)
                data.append('bid', bid)
                data.append('tid', '')
                data.append('bname', '')
                data.append('author', '')
                data.append('content', '')
                data.append('pic', '')
                data.append('remain', '')
                data.append('price', '')
                API.setBookInfo().then(res => {
                    if (res.state === 0) {
                        this.$message.success('修改成功')
                    } else this.$message.error('')
                }).catch(res => {
                    this.$message.info(res)
                })
            },
            delShopBook: function (bid) {
                let data = new FormData()
                data.append('uid', this.$cookie.get('uid'))
                data.append('token', this.$cookie.get('token'))
                data.append('bid', bid)
                API.delBookInfo().then(res => {
                    if (res.state === 0) {
                        this.$message.success('删除成功')
                    } else this.$message.error('')
                }).catch(res => {
                    this.$message.info(res)
                })
            },
            getOrderList: function () {
                let data = new FormData()
                data.append('uid', this.$cookie.get('uid'))
                data.append('token', this.$cookie.get('token'))
                data.append('sid', this.$cookie.get('sid'))
                API.addNewBookIntoShop().then(res => {
                    if (res.state === 0) {

                    } else this.$message.error('')
                }).catch(res => {
                    this.$message.info(res)
                })
            },
            getShopOrder: function () {

                API.getShopOrder().then(res => {
                    if (res.state === 0) {

                    } else this.$message.error('')
                }).catch(res => {
                    this.$message.info(res)
                })
            },
            operateOrder: function () {
                let data = new FormData()
                data.append('uid', this.$cookie.get('uid'))
                data.append('token', this.$cookie.get('token'))
                data.append('sid', this.$cookie.get('sid'))
                API.operateOrder().then(res => {
                    if (res.state === 0) {

                    } else this.$message.error('')
                }).catch(res => {
                    this.$message.info(res)
                })
            },
            modifyManager: function () {
                let data = new FormData()
                data.append('uid', this.$cookie.get('uid'))
                data.append('token', this.$cookie.get('token'))
                data.append('sid', this.sid)
                data.append('manager', JSON.stringify(this.shopInfo.manager))
                data.append('content', this.shopInfo.content)
                data.append('head', this.shopInfo.head)
                API.setShopInfo(data).then(res => {
                    if (res.state === 0) {
                        this.$message.success('修改成功')
                    } else this.$message.error('')
                }).catch(res => {
                    this.$message.info(res)
                })
            },
            cancelEditShopInfo: function () {
                this.isEditShopInfo = !this.isEditShopInfo
                this.editShopInfo = this.shopInfo
            }
        }
    }
</script>

<style scoped>

</style>