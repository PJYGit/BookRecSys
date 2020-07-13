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
                                                <el-input v-model="editShopInfo.name" size="mini"
                                                          style="width: 300px;"/>
                                            </el-form-item>
                                            <el-form-item label="店铺简介">
                                                <el-input v-model="editShopInfo.content" type="textarea"
                                                          :autosize="{ minRows: 4}"/>
                                            </el-form-item>
                                            <el-form-item label="店铺图片">
                                                <input type="file" id="shopImage" @change="imageUpload('shopImage')"/>
                                                <el-input v-model="uploadImageURL" disabled size="mini"></el-input>
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
                            <img :src="shopInfo.head" alt="店铺图片">
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
                        <el-button @click="isAddNewBook = !isAddNewBook" size="mini" type="primary">添加新图书</el-button>
                        <el-dialog title="添加图书" center :visible.sync="isAddNewBook">
                            <div>
                                <el-form label-width="80px">
                                    <el-form-item label="书名">
                                        <el-input v-model="newBookInfo.bname" size="mini"
                                                  style="width: 300px;"/>
                                    </el-form-item>
                                    <el-form-item label="作者">
                                        <el-input v-model="newBookInfo.author" size="mini"
                                                  style="width: 300px;"></el-input>
                                    </el-form-item>
                                    <el-form-item label="图书简介">
                                        <el-input v-model="newBookInfo.content" type="textarea"
                                                  :autosize="{ minRows: 4}"/>
                                    </el-form-item>
                                    <el-form-item label="图书价钱">
                                        <el-input-number v-model="newBookInfo.price" size="small"></el-input-number>
                                    </el-form-item>
                                    <el-form-item label="图书库存">
                                        <el-input-number v-model="newBookInfo.remain" size="small"></el-input-number>
                                    </el-form-item>
                                    <el-form-item label="图书类别">
                                        <el-select v-model="newBookInfo.tid" multiple size="mini">
                                            <el-option v-for="item in tagList" :key="item.tid" :value="item.tid"
                                                       :label="item.name"/>
                                        </el-select>
                                    </el-form-item>
                                    <el-form-item label="图书图片">
                                        <input type="file" id="newBookInfo" @change="imageUpload('newBookInfo')"/>
                                        <el-input v-model="uploadImageURL" disabled size="mini"></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                            <span slot="footer">
                                <el-button @click="addNewBook" type="primary" size="mini">确认添加</el-button>
                                <el-button @click="handleCancelAddNewBook" size="mini">取消</el-button>
                            </span>
                        </el-dialog>
                    </div>
                    <div
                            style="margin: 30px;display: flex;justify-content: space-around;">
                        <el-card v-for="(item) in shopBookList" :key="item.bid" shadow="hover"
                                 style="width: 30%; text-align: center">
                            <div slot="header">
                                {{item.bname}}
                                <br/>
                                <img :src="item.pic" alt="bookImage">
                            </div>
                            <div slot="default">
                                <table style="width: 100%">
                                    <tr>
                                        <td>作者</td>
                                        <td>
                                            {{item.author}}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>标签</td>
                                        <td>
                                            <span v-for="each in item.tid" :key="each">
                                                {{tagList.filter(i => i.tid===each)[0] == null ?
                                                "加载中" : tagList.filter(i => i.tid===each)[0].name}}
                                            </span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>简介</td>
                                        <td>
                                            {{item.content}}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>销量</td>
                                        <td>
                                            {{item.sales}}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>库存</td>
                                        <td>
                                            {{item.remain}}
                                        </td>
                                    </tr>
                                </table>
                                <p>
                                    ￥{{item.price}}
                                </p>
                                <el-rate
                                        v-model="item.mark"
                                        disabled
                                        show-score
                                        text-color="#eb7a67"
                                        :colors="['#eb7a67','#eb7a67','#eb7a67']"
                                        style="margin: 25px auto;">
                                </el-rate>
                                <span
                                        style="font-size: 1.1rem; cursor: pointer; color: #EB7A67">
                                    <u @click="beforeEditBookInfo(item)">
                                        修改
                                    </u>
                                    <el-dialog center title="修改图书信息" :visible.sync="isEditBookInfo">
                                        <div>
                                            <el-form label-width="80px">
                                                <el-form-item label="书名">
                                            <el-input v-model="editBookInfo.bname" size="mini"
                                                      style="width: 300px;"/>
                                            </el-form-item>
                                            <el-form-item label="作者">
                                                <el-input v-model="editBookInfo.author" size="mini"
                                                          style="width: 300px;"></el-input>
                                            </el-form-item>
                                            <el-form-item label="图书简介">
                                                <el-input v-model="editBookInfo.content" type="textarea"
                                                          :autosize="{ minRows: 4}"/>
                                            </el-form-item>
                                            <el-form-item label="图书价钱">
                                                <el-input-number v-model="editBookInfo.price"
                                                                 size="small"></el-input-number>
                                            </el-form-item>
                                            <el-form-item label="图书库存">
                                                <el-input-number v-model="editBookInfo.remain"
                                                                 size="small"></el-input-number>
                                            </el-form-item>
                                            <el-form-item label="图书类别">
                                                <el-select v-model="editBookInfo.tid" multiple size="mini">
                                                    <el-option v-for="item in tagList" :key="item.tid" :value="item.tid"
                                                               :label="item.name"/>
                                                </el-select>
                                            </el-form-item>
                                            <el-form-item label="图书图片">
                                                <input type="file" id="editBookInfo" @change="imageUpload('editBookInfo')"/>
                                                <el-input v-model="uploadImageURL" disabled size="mini"></el-input>
                                            </el-form-item>
                                            </el-form>
                                        </div>
                                        <span slot="footer">
                                            <el-button @click="modifyBookInfo(item.bid)" type="primary"
                                                       size="mini">确认修改</el-button>
                                            <el-button @click="cancelEditBookInfo" size="mini">取消</el-button>
                                        </span>
                                    </el-dialog>
                                </span>
                                <span @click="delShopBook(item.bid)"
                                      style="font-size: 1.1rem; cursor: pointer;margin-left: 15px;color: #EB7A67">
                                    <u>
                                        删除
                                    </u>
                                </span>
                            </div>
                        </el-card>
                    </div>
                </el-tab-pane>
                <el-tab-pane v-if="parseInt(this.uid)===shopInfo.boss.uid" label="店铺管理员管理">
                    <el-button @click="isAddNewManager=!isAddNewManager" size="mini" type="primary">添加新管理员</el-button>
                    <el-dialog title="添加管理员" center :visible.sync="isAddNewManager">
                        <el-form label-width="80px">
                            <el-form-item label="手机号">
                                <el-input v-model="newManagerUrn" size="mini" style="width: 300px;"></el-input>
                                <el-button @click="getUserInfo(newManagerUrn)" type="primary" size="mini"
                                           style="margin-left: 10px;">确认成员
                                </el-button>
                            </el-form-item>
                            <el-form-item label="用户名">
                                <el-input v-model="newManagerInfo.name" disabled size="mini"
                                          style="width: 300px;"></el-input>
                            </el-form-item>
                        </el-form>
                        <span slot="footer">
                            <el-button @click="modifyManager" type="primary" size="mini">确认添加</el-button>
                            <el-button @click="isAddNewManager=!isAddNewManager" size="mini">取消</el-button>
                        </span>
                    </el-dialog>
                    <el-table :data="shopInfo.manager">
                        <el-table-column label="管理员ID" prop="uid"></el-table-column>
                        <el-table-column label="管理员名字" prop="name"></el-table-column>
                        <el-table-column label="操作">
                            <template slot-scope="scope">
                                <el-button @click="delManager(scope.row.uid)" size="mini" type="danger">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
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
                                <el-tag v-if="scope.row.type===2" type="info">待确认收货</el-tag>
                                <el-tag v-if="scope.row.type===3">待评价</el-tag>
                                <el-tag v-if="scope.row.type===4" type="success">已完成</el-tag>
                                <el-tag v-if="scope.row.type===-1" type="danger">已取消</el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作">
                            <template slot-scope="scope">
                                <el-button @click="operateOrder(true, scope.row.cid)" size="mini" type="primary">确认发货</el-button>
                                <el-button @click="operateOrder(false, scope.row.cid)" size="mini" type="danger">取消</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-tab-pane>
            </el-tabs>
        </template>
    </flow-board>
</template>

<script>
    import FlowBoard from "../components/board/FlowBoard";
    import MyTitle from "../components/base/myTitle";
    import API from "../api";

    export default {
        name: "manage",
        components: {MyTitle, FlowBoard},
        data() {
            return {
                sid: 0,
                uid: this.$cookie.get('uid'),
                isEditShopInfo: false,
                isAddNewBook: false,
                isEditBookInfo: false,
                isAddNewManager: false,
                tagList: [{tid: 1, name: 'first'}],
                shopInfo: {
                    uid: 0,
                    sid: 0,
                    name: '123',
                    boss: {
                        uid: 7,
                        name: 'test'
                    },
                    manager: [
                        {uid: 18, name: 'testmdf'}
                    ],
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
                shopBookList: [
                    {
                        bid: 1,
                        sid: 1,
                        tid: [1, 2, 3],
                        bname: 'test',
                        sname: '12313',
                        author: '123123131',
                        content: '12313131',
                        pic: 'adasdasda',
                        mark: 4.5,
                        sales: 1,
                        remain: 1,
                        price: 1
                    },
                ],
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
                ],
                newBookInfo: {
                    bid: 1,
                    sid: 1,
                    tid: [],
                    bname: '',
                    author: '',
                    content: '',
                    pic: '',
                    remain: '',
                    price: 1
                },
                editBookInfo: {
                    bid: 1,
                    sid: 1,
                    tid: [],
                    bname: '',
                    author: '',
                    content: '',
                    pic: '',
                    remain: '',
                    price: 1
                },
                newManagerInfo: {},
                newManagerUrn: '',
                uploadImage: {},
                uploadImageURL: '',
            }
        },
        mounted() {
            this.sid = this.$route.query.sid
            this.getTagList()
            this.getShopInfo()
            this.getShopBookInfo()
            this.getOrderList()
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
                API.getShopBookInfo(data).then(res => {
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
                for (let i = 0; i < this.shopInfo.manager.length; i++)
                    data.append('managers', JSON.stringify(this.shopInfo.manager[i].uid))
                data.append('content', this.shopInfo.content)
                if (this.uploadImageURL === '')
                    data.append('head', this.shopInfo.head)
                else
                    data.append('head', this.uploadImageURL)
                API.setShopInfo(data).then(res => {
                    if (res.state === 0) {
                        this.$message.success('添加成功')
                        this.isEditShopInfo = !this.isEditShopInfo
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
                for (let i = 0; i < this.newBookInfo.tid.length; i++)
                    data.append('tid', JSON.stringify(this.newBookInfo.tid[i]))
                data.append('bname', this.newBookInfo.bname)
                data.append('author', this.newBookInfo.author)
                data.append('content', this.newBookInfo.content)
                if (this.uploadImageURL === '')
                    data.append('pic', this.newBookInfo.pic)
                else
                    data.append('pic', this.uploadImageURL)
                data.append('remain', this.newBookInfo.remain)
                data.append('price', this.newBookInfo.price)
                API.addNewBookIntoShop(data).then(res => {
                    if (res.state === 0) {
                        this.$message.success('添加成功')
                        this.getShopBookInfo()
                    } else this.$message.error('添加失败')
                }).catch(res => {
                    this.$message.info(res)
                })
                this.isAddNewBook = !this.isAddNewBook
                this.newBookInfo = {
                    bid: 1,
                    sid: 1,
                    tid: [],
                    bname: '',
                    author: '',
                    content: '',
                    pic: '',
                    remain: '',
                    price: 1
                }
            },
            modifyBookInfo: function (bid) {
                let data = new FormData()
                data.append('uid', this.$cookie.get('uid'))
                data.append('token', this.$cookie.get('token'))
                data.append('sid', this.sid)
                data.append('bid', bid)
                for (let i = 0; i < this.editBookInfo.tid.length; i++)
                    data.append('tid', JSON.stringify(this.editBookInfo.tid[i]))
                data.append('bname', this.editBookInfo.bname)
                data.append('author', this.editBookInfo.author)
                data.append('content', this.editBookInfo.content)
                if (this.uploadImageURL === '')
                    data.append('pic', this.editBookInfo.pic)
                else
                    data.append('pic', this.uploadImageURL)
                data.append('remain', this.editBookInfo.remain)
                data.append('price', this.editBookInfo.price)
                API.setBookInfo(data).then(res => {
                    if (res.state === 0) {
                        this.$message.success('修改成功')
                        this.isEditBookInfo = !this.isEditBookInfo
                    } else this.$message.error('')
                }).catch(res => {
                    this.$message.info(res)
                })
            },
            delShopBook: function (bid) {
                this.$confirm('确认删除？').then(_ => {
                    let data = new FormData()
                    data.append('uid', this.$cookie.get('uid'))
                    data.append('token', this.$cookie.get('token'))
                    data.append('bid', bid)
                    API.delBookInfo(data).then(res => {
                        if (res.state === 0) {
                            this.$message.success('删除成功')
                            this.getShopBookInfo()
                        }
                    }).catch(_ => {
                    })
                }).catch(_ => {
                })
            },
            getOrderList: function () {
                let data = new FormData()
                data.append('uid', this.$cookie.get('uid'))
                data.append('token', this.$cookie.get('token'))
                data.append('sid', this.sid)
                data.append('type', 999)
                API.getShopOrder(data).then(res => {
                    if (res.state === 0) {
                        this.orderList = res.items
                    } else this.$message.error('')
                }).catch(res => {
                    this.$message.info(res)
                })
            },
            operateOrder: function (isSend, cid) {
                let data = new FormData()
                data.append('uid', this.$cookie.get('uid'))
                data.append('token', this.$cookie.get('token'))
                data.append('cid', cid)
                data.append('opcode', isSend ? 2 : 1)
                API.operateOrder(data).then(res => {
                    if (res.state === 0) {
                        this.$message.success(isSend ? '发送成功' : '取消成功')
                    } else this.$message.error('未能完成操作')
                }).catch(_ => {
                })
            },
            modifyManager: function () {
                if (this.newManagerInfo === {} || this.newManagerInfo.name === '') {
                    this.$message.error('未获得用户姓名')
                } else {
                    this.shopInfo.manager.push(this.newManagerInfo)
                    let data = new FormData()
                    data.append('uid', this.$cookie.get('uid'))
                    data.append('token', this.$cookie.get('token'))
                    data.append('sid', this.sid)
                    for (let i = 0; i < this.shopInfo.manager.length; i++)
                        data.append('managers', JSON.stringify(this.shopInfo.manager[i].uid))
                    data.append('content', this.shopInfo.content)
                    data.append('head', this.shopInfo.head)
                    API.setShopInfo(data).then(res => {
                        if (res.state === 0) {
                            this.$message.success('修改成功')
                            this.isAddNewManager = !this.isAddNewManager
                        } else this.$message.error('')
                    }).catch(_ => {
                    })
                }
            },
            cancelEditShopInfo: function () {
                this.isEditShopInfo = !this.isEditShopInfo
                this.uploadImageURL = ''
                this.editShopInfo = this.shopInfo
            },
            beforeEditBookInfo: function (item) {
                this.isEditBookInfo = !this.isEditBookInfo
                // TODO 双向绑定fix
                this.editBookInfo = item
            },
            cancelEditBookInfo: function () {
                this.isEditBookInfo = !this.isEditBookInfo
                this.uploadImageURL = ''
                this.editBookInfo = {
                    bid: 1,
                    sid: 1,
                    tid: [],
                    bname: '',
                    author: '',
                    content: '',
                    pic: '',
                    remain: '',
                    price: 1
                }
                this.getShopBookInfo()
            },
            getTagList: function () {
                let data = new FormData()
                data.append('uid', this.$cookie.get('uid'))
                data.append('token', this.$cookie.get('token'))
                API.getTagsList(data).then(res => {
                    if (res.state === 0) {
                        this.tagList = res.tags
                    }
                }).catch(res => {
                    console.log(res)
                })
            },
            delManager: function (uid) {
                this.editShopInfo.manager = this.shopInfo.manager.filter(item => item.uid !== uid)
                let data = new FormData()
                data.append('uid', this.$cookie.get('uid'))
                data.append('token', this.$cookie.get('token'))
                data.append('sid', this.sid)
                for (let i = 0; i < this.editShopInfo.manager.length; i++)
                    data.append('managers', JSON.stringify(this.editShopInfo.manager[i].uid))
                data.append('content', this.shopInfo.content)
                data.append('head', this.shopInfo.head)
                API.setShopInfo(data).then(res => {
                    if (res.state === 0) {
                        this.$message.success('删除成功')
                        this.getShopBookInfo()
                    } else this.$message.info('未知错误发生')
                }).catch(res => {
                    console.log(res)
                })
                this.editBookInfo = {
                    bid: 1,
                    sid: 1,
                    tid: [],
                    bname: '',
                    author: '',
                    content: '',
                    pic: '',
                    remain: '',
                    price: 1
                }
            },
            getUserInfo: function (urn) {
                let data = new FormData()
                data.append('uid', this.$cookie.get('uid'))
                data.append('token', this.$cookie.get('token'))
                data.append('phone', urn)
                API.searchTargetUserInfo(data).then(res => {
                    if (res.state === 0) {
                        if (res.list !== null) {
                            let data = res.list[0]
                            this.newManagerInfo.name = data.nickname
                            this.newManagerInfo.uid = data.uid
                            // todo 需要点一下才能出现用户姓名
                            this.$message.success('获取用户信息成功')
                        } else this.$message.error('未搜索到用户')
                    } else this.$message.error('获取用户信息失败')
                }).catch(res => {
                    console.log(res)
                })
            },
            imageUpload: function (id) {
                let files = document.getElementById(id).files
                console.log(files)
                console.log(id)
                this.uploadImage = files[0]
                let data = new FormData()
                data.append('uid', this.$cookie.get('uid'))
                data.append('token', this.$cookie.get('token'))
                data.append('pic', this.uploadImage)
                API.uploadImage(data).then(res => {
                    if (res.state === 0) {
                        this.uploadImageURL = "http://39.106.160.119:9000/pictures/"+res.url
                    }
                }).catch(_ => {
                })
            },
            handleCancelAddNewBook: function () {
                this.isAddNewBook = !this.isAddNewBook
                this.uploadImageURL = ''
            },
        }
    }
</script>

<style scoped>

</style>