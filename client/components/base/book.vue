<template>
    <div style="width: 180px;cursor: pointer" @click="toDetail">
        <img :src="bookItem.src" style="width: 180px;">
        <div style="font-size: 18px;line-height: 25px;margin-left: 21px;margin-top: 2px;">
            {{bookItem.bname}}
        </div>
        <div style="color: #8c939d;font-size: 15px;line-height: 25px;margin-left: 22px;">
            {{bookItem.author}}
        </div>
        <div style="margin-left: 20px">
            <span style="font-size: 18px;color: red">{{bookItem.price}}</span>
            <span style="margin-left: 5px;color: gray;text-decoration:line-through;margin-top: -1px">
                {{bookItem.oldPrice}}</span>
        </div>
    </div>
</template>

<script>
    export default {
        name: "book",
        props:{
            bookItem:{
                type:Object,
            }
        },
        data(){
            return{
                src:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                bname:"九年级上册历史书",
                author:"作者1 作者2等",
                price:'￥24.00',
                oldPrice:'￥30.00 ',
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