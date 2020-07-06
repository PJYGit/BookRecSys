<template>
    <div>
        <img :src="src" style="width: 180px;">
        <div style="font-size: 18px;line-height: 25px;margin-left: 21px;margin-top: 2px">
            {{this.name}}
        </div>
        <div style="color: #8c939d;font-size: 15px;line-height: 25px;margin-left: 22px;">
            {{this.author}}
        </div>
        <div style="margin-left: 20px">
            <span style="font-size: 18px;color: red">{{this.price}}</span>
            <span style="margin-left: 5px;color: gray;text-decoration:line-through;margin-top: -1px">
                {{this.oldPrice}}</span>
        </div>
    </div>
</template>

<script>
    export default {
        name: "book",
        data(){
            return{
                src:"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=127724150,3260846456&fm=26&gp=0.jpg",
                name:"九年级上册历史书",
                author:"作者1 作者2等",
                price:'￥24.00',
                oldPrice:'￥30.00 ',
            }
        }
    }
</script>

<style scoped>

</style>