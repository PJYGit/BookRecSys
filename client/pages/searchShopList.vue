<template>
    <flow-board>
        <template v-slot:header>
            <my-title></my-title>
        </template>
        <my-search style="width: 80%;margin-left: 10%;margin-top: 20px"></my-search>
        <shop-list :my-shop="myShop" style="width: 80%;margin-left: 10%;margin-top: 20px"></shop-list>

    </flow-board>
</template>

<script>
    import FlowBoard from "../components/board/FlowBoard";
    import MyTitle from "../components/base/myTitle";
    import ShopList from "../components/shop/shopList";
    import MySearch from "../components/base/mySearch";
    import API from "../api"
    import Cookies from 'js-cookie';
    export default {
        name: "searchShopList",
        components: {MySearch, ShopList, MyTitle, FlowBoard},
        data(){
            return{
                searchContent:'',
                uid:Cookies.get('uid'),
                token:Cookies.get('token'),
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

        mounted(){
            this.searchContent = this.$route.query.word;
            this.searchShop();
        },

        methods:{
            searchShop(){
                let fd = new FormData();
                fd.append('uid',this.uid);
                fd.append('token',this.token);
                fd.append('word',this.searchContent);

                API.SShop(fd).then(res=>{
                    console.log(res);
                    if (res.state) {
                        alert("获取店铺列表失败");
                        return;
                    }

                    this.myShop = res.shops;

                }).catch(msg => {
                    alert(msg)
                })
            }
        }
    }
</script>

<style scoped>

</style>