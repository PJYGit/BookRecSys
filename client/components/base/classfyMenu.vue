<template>
    <div style="border: solid 2px #ef9585;">
        <div style="background-color: #ef9585;color: white;font-size: 28px;font-weight: 700;
        height: 60px;text-align: center;padding-top: 10px">图书分类</div>
        <el-menu
                :default-active="activeIndex"
                class="el-menu-vertical-demo"
                background-color="#ffffff"
                text-color="#000000"
                active-text-color="#EB7A67" v-for="item in menuList" :key="item.tid">
            <!--<el-submenu v-if="item.subMenu.length!==0" :index="item.classfyId">
                <template slot="title">
                    <span style="font-size: 18px;margin-left: 10px">{{item.name}}</span>
                </template>
                <el-menu-item v-for="item1 in item.subMenu" :key="item1.classfyId" :index="item1.classfyId">
                    <span slot="title" style="font-size: 16px;margin-left: 25px">{{item1.name}}</span>
                </el-menu-item>
            </el-submenu>-->
            <el-menu-item :index="item.tid.toString()" @click="toClassfyList(item)">
                <span slot="title" style="font-size: 18px;margin-left: 10px">{{item.name}}</span>
            </el-menu-item>
        </el-menu>
    </div>
</template>

<script>
    import API from "../../api";
    export default {
        name: "classfyMenu",
        props:{
            activeIndex:{
                type:String,
            },
            isMain:{
                type: Number,
            }
        },
        data(){
            return{
                menuList:[{
                    tid:1,
                    name:"有书推荐",
                    cnt:300,
                }]
            }
        },

        mounted(){
            this.getClassfyMenu();
        },

        methods:{
            toClassfyList(item){
                this.$router.push({path: `/classfyBookList`,query: { tid: item.tid }});
                if(this.isMain!==0){
                    console.log(item);
                    this.$emit('MenuMethod',item);
                }
            },

            getClassfyMenu(){
                API.getTag().then(res => {
                    console.log(res);
                    if (res.state) {
                        alert("获取图书类别失败");
                        return;
                    }
                    this.menuList = res.tags;
                }).catch(msg=>{
                    alert(msg);
                })
            }
        },
    }
</script>

<style scoped>
    >>> .el-menu{
        border-top: solid 1px #ef9585;
    }

    >>> .el-menu-item{
        border-bottom: solid 1px #ef9585;
    }

    >>> .el-submenu__icon-arrow{
        font-size: 20px;
        margin-top: -11px;
        font-weight: bold;
    }

</style>