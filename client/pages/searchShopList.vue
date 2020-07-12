<template>
    <flow-board v-loading="loading">
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
                myShop:[],
                loading:false,
            }
        },

        mounted(){
            this.searchContent = this.$route.query.word;
            this.searchShop();
        },

        methods:{
            searchShop(){
                this.loading=true;
                let fd = new FormData();
                fd.append('uid',this.uid);
                fd.append('token',this.token);
                fd.append('word',this.searchContent);

                API.SShop(fd).then(res=>{
                    console.log(res);
                    if (res.state) {
                        alert("获取店铺列表失败");
                        this.loading=false;
                        return;
                    }

                    this.myShop = res.shops;
                    this.loading=false;
                }).catch(msg => {
                    alert(msg)
                })
            }
        }
    }
</script>

<style scoped>

</style>