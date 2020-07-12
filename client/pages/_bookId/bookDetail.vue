<template>
    <FlowBoard>
        <template v-slot:header>
            <my-title></my-title>
        </template>

        <el-row>
            <el-col :span="18">
                <el-container style="margin-top: 30px;margin-bottom: 20px;">
                    <el-aside style="margin-left: 8%;width: 43%">
                        <img :src="bookItem.pic" style="border: solid 1px #ef9585;width: 470px;margin-left: 20px;margin-top: 25px"/>
                    </el-aside>
                    <el-main>
                        <div style="font-size: 36px;font-weight: 700">
                            {{bookItem.bname}}
                        </div>
                        <div style="color: gray;font-size: 18px;line-height: 40px;margin-top: 10px;margin-bottom: 10px">
                            <span style="font-weight: 600">作者：</span>{{bookItem.author}}
                        </div>

                        <div style="font-size: 18px;line-height: 40px;width: 90%;margin-bottom: 20px">
                            <span style="font-weight: 600;font-size: 20px">内容简介：</span>{{bookItem.content | ellipsis}}
                        </div>

                        <div style="overflow: hidden;margin-bottom: 20px">
                            <div style="color: #eb7a67;font-weight: 600;float: left">综合评分</div>
                            <el-rate
                                    v-model="bookItem.mark"
                                    disabled
                                    show-score
                                    text-color="#eb7a67"
                                    :colors="['#eb7a67','#eb7a67','#eb7a67']"
                                    style="margin-left: 10px;float: left">
                            </el-rate>
                            <span style="margin-left: 25px;color: gray;font-weight: 500;">月销 {{bookItem.sales}} 笔</span>
                        </div>

                        <el-container style="margin-bottom: 15px;color: #B5B386;">
                            <i class="el-icon-s-shop" style="font-size: 22px;margin-top: 3px"></i>
                            <span style="font-size: 20px;margin-left: 5px;text-decoration: underline;
                            cursor: pointer" @click="toShop">{{bookItem.sname}}</span>
                        </el-container>

                        <div style="line-height: 40px;margin-bottom: 20px">
                            <span style="font-size: 30px;color: red">￥{{(bookItem.price*rate).toFixed(2)}}</span>
                            <span style="margin-left: 15px;color: gray;text-decoration:line-through;font-size: 20px">
                                定价:￥{{bookItem.price.toFixed(2)}}</span>
                        </div>

                        <div>
                            <el-input-number v-model="buyNum" :min="1" :max="bookItem.remain" label="购买数量"></el-input-number>
                            <span style="font-size: 18px;margin-left: 20px;color: gray;">剩余库存 {{bookItem.remain}} 本</span>
                        </div>

                        <div style="margin-top: 20px">
                            <el-button icon="el-icon-shopping-cart-2" style="background-color: #eb7a67;color: white;border: solid 1px #ef9585;font-size: 20px" @click="addToCar">加入购物车</el-button>
                            <el-button style="color: #eb7a67;border: solid 1px #ef9585;font-size: 18px" @click="chooseAddress=true">立即购买</el-button>
                        </div>
                    </el-main>
                </el-container>

                <el-container style="margin-bottom: 20px;margin-left: 10%;padding-top: 40px">
                    <el-tabs v-model="activeName" type="card" style="margin-bottom: 20px">
                        <el-tab-pane label="图书详情" name="first" style="min-width: 700px">
                            <div style="font-size: 20px;margin-left: 10px;margin-top: 20px;">
                                图书信息
                            </div>
                            <el-divider></el-divider>
                            <el-row style="margin-left: 20px;font-size: 16px">
                                <el-col :span="9">
                                    <div class="bookMsg">
                                        ISBN编号：{{bookItem.isbn}}<span v-if="bookItem.isbn===undefined">暂无信息</span>
                                    </div>
                                    <div class="bookMsg">
                                        图书名称：{{bookItem.bname}}<span v-if="bookItem.bname===undefined">暂无信息</span>
                                    </div>
                                    <div class="bookMsg">
                                        作者：{{bookItem.author}}<span v-if="bookItem.author===undefined">暂无信息</span>
                                    </div>
                                    <div class="bookMsg">
                                        版本：{{bookItem.banben}}<span v-if="bookItem.banben===undefined">暂无信息</span>
                                    </div>
                                </el-col>
                                <el-col :span="9">
                                    <div class="bookMsg">
                                        装帧：{{bookItem.kind}}<span v-if="bookItem.kind===undefined">暂无信息</span>
                                    </div>
                                    <div class="bookMsg">
                                        开本：{{bookItem.size}}<span v-if="bookItem.size===undefined">暂无信息</span>
                                    </div>
                                    <div class="bookMsg">
                                        纸张：{{bookItem.paperKind}}<span v-if="bookItem.paperKind===undefined">暂无信息</span>
                                    </div>
                                    <div class="bookMsg">
                                        是否套装：{{bookItem.isSuit}}<span v-if="bookItem.isSuit===undefined">暂无信息</span>
                                    </div>
                                </el-col>
                            </el-row>
                            <div style="font-size: 20px;margin-left: 10px;margin-top: 20px">
                                内容介绍
                            </div>
                            <el-divider></el-divider>
                            <div style="width: 90%;margin-left: 10px;text-indent: 2em;line-height: 30px">
                                {{bookItem.content}}
                            </div>
                        </el-tab-pane>
                        <el-tab-pane label="商品评价" name="second">
                            <evalu-list :eval-list="evaluateList"></evalu-list>
                        </el-tab-pane>
                    </el-tabs>
                </el-container>

            </el-col>
            <el-col :span="6">
                <AlsoLike :book-list="thisBookList" style="width: 50%;margin-top: 40px;margin-left: 40px"></AlsoLike>
            </el-col>
        </el-row>

        <el-dialog
                title="请选择收货地址"
                :visible.sync="chooseAddress"
                width="60%">
            <address-list :is-shopping="1" ref="getAddress"></address-list>
            <span slot="footer" class="dialog-footer">
                <el-button @click="chooseAddress = false">取 消</el-button>
                <el-button type="primary" @click="buyOneBook">确 定</el-button>
            </span>
        </el-dialog>

    </FlowBoard>
</template>

<script>
    import MyTitle from "../../components/base/myTitle";
    import MyFooter from "../../components/base/myFooter";
    import FlowBoard from "../../components/board/FlowBoard";
    import AlsoLike from "../../components/AlsoLike";
    import EvaluList from "../../components/evaluList";
    import Cookies from 'js-cookie';
    import API from '../../api';
    import AddressList from "../../components/addressList";
    export default {
        name: "bookDetail",
        components: {AddressList, EvaluList, AlsoLike, FlowBoard, MyFooter, MyTitle},
        filters: {
            ellipsis (value) {
                if (!value) return '';
                if (value.length > 57) {
                    return value.slice(0,57) + '...'
                }
                return value;
            }
        },
        data(){
            return{
                rate:Cookies.get('vipRate'),
                uid:Cookies.get('uid'),
                token:Cookies.get('token'),
                buyNum:1,
                activeName:'first',
                chooseAddress:false,
                bookId:'',
                bookItem: {
                    price:5,
                },

                thisBookList:[],

                evaluateList:[],
            }
        },

        mounted(){
            this.bookId = this.$route.query.bid;
            this.setBookMsg();
            this.getRecoBook();
        },

        methods:{
            toShop(){
                let link = this.$router.resolve({ path: `/shopBookList`,
                    query: { sid: this.bookItem.sid }});
                window.open(link.href, '_blank');
            },

            setBookMsg(){
                let data = new FormData();
                data.append('uid',this.uid);
                data.append('token',this.token);
                data.append('bid',this.bookId);

                API.getBookDetail(data).then(res=>{
                    console.log(res);
                    if (res.state) {
                        alert("获取图书详情失败");
                        return;
                    }
                    this.bookItem=res;
                    this.evaluateList=[];
                    console.log(res);
                }).catch(msg => {
                    alert(msg)
                })
            },

            addToCar(){
                let data = new FormData();
                data.append('uid',this.uid);
                data.append('token',this.token);
                data.append('bid',this.bookItem.bid);
                data.append('cnt',this.buyNum);
                data.append('sid',this.bookItem.sid);

                API.addToShoppingCar(data).then(res=>{
                    if (res.state) {
                        alert("加购失败");
                        return;
                    }
                    alert("加购成功");

                }).catch(msg => {
                    alert(msg)
                })
            },

            buyOneBook(){
                let data = new FormData();
                data.append('uid',this.uid);
                data.append('token',this.token);
                data.append('bid',this.bookItem.bid);
                data.append('cnt',this.buyNum);
                data.append('address',this.$refs.getAddress.returnAddressContent());

                API.buyOnlyBook(data).then(res=>{
                    if (res.state) {
                        alert("加购失败");
                        return;
                    }
                    alert("加购成功");
                    this.buyNum=1;

                }).catch(msg => {
                    alert(msg)
                })
            },

            getRecoBook(){
                let data = new FormData();
                data.append('uid',this.uid);
                data.append('token',this.token);
                data.append('bid',this.bookId);

                API.getBookDetail(data).then(res=>{
                    console.log(res);

                    if (res.state) {
                        alert("获取推荐信息失败");
                        return;
                    }
                    this.thisBookList.push(res);

                }).catch(msg => {
                    alert(msg)
                })
            }
        },


    }
</script>

<style scoped>
    .bookMsg{
        line-height: 40px;
        margin-bottom: 10px;
    }

    >>> .el-rate__text{
        font-size: 18px;
    }

    >>> .el-tabs__item:hover{
        color: #EB7A67;
    }

    >>> .el-tabs__item.is-active{
        color: #EB7A67;
    }

    >>> .el-tabs__item{
        font-size: 16px;
    }
</style>