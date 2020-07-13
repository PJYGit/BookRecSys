<template>
    <div style="width: 180px;cursor: pointer;" @click="toDetail">
        <div v-if="bookItem.bname===null" style="width: 180px;height: 180px;text-align: center;margin-top: 70px">图书已下架</div>
        <el-image
                v-if="bookItem.bname!==null"
                style="width: 180px; height: 180px"
                :src="bookItem.pic"
                fit="contain">
        </el-image>
        <div style="font-size: 18px;line-height: 25px;margin-left: 21px;margin-top: 2px;height: 75px;overflow: hidden">
            {{bookItem.bname | ellipsis}}
        </div>
        <div style="color: #8c939d;font-size: 15px;line-height: 25px;margin-left: 22px;height: 25px;overflow: hidden">
            {{bookItem.author}}
        </div>
        <div style="margin-left: 20px">
            <span style="font-size: 18px;color: red">￥{{(bookItem.price*rate).toFixed(2)}}</span>
            <span style="margin-left: 5px;color: gray;text-decoration:line-through;margin-top: -1px">
                ￥{{bookItem.price.toFixed(2)}}</span>
        </div>
    </div>
</template>

<script>
    import Cookies from 'js-cookie'
    export default {
        name: "book",
        props:{
            bookItem:{
                type:Object,
            }
        },
        filters: {
            ellipsis (value) {
                if (!value) return '';
                if (value.length > 50) {
                    return value.slice(0,50) + '...'
                }
                return value;
            },

            nameFilter (value) {
                if (!value) return '';
                if (value.length > 15) {
                    return value.slice(0,15) + '...'
                }
                return value;
            }
        },
        data(){
            return{
                rate:Cookies.get('vipRate'),
                pic:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                bname:"九年级上册历史书",
                author:"作者1 作者2等",
                price:24,
            }
        },

        methods:{
            toDetail(){
                let link = this.$router.resolve({ path: `/`+this.bookItem.bid+`/bookDetail`,
                    query: { bid: this.bookItem.bid }});
                window.open(link.href, '_blank');
                /*this.$router.push({path: `/`+this.bookItem.bid+`/bookDetail`})*/
            }
        }
    }
</script>

<style scoped>

</style>