<template>
    <div style="border: solid 2px #ef9585;">
        <div style="background-color: #ef9585;color: white;font-size: 28px;font-weight: 700;
        height: 60px;text-align: center;padding-top: 10px">图书分类</div>
        <el-menu
                default-active="2"
                class="el-menu-vertical-demo"
                background-color="#ffffff"
                text-color="#000000"
                active-text-color="#EB7A67" v-for="item in menuList" :key="item.classfyId">
            <!--<el-submenu v-if="item.subMenu.length!==0" :index="item.classfyId">
                <template slot="title">
                    <span style="font-size: 18px;margin-left: 10px">{{item.name}}</span>
                </template>
                <el-menu-item v-for="item1 in item.subMenu" :key="item1.classfyId" :index="item1.classfyId">
                    <span slot="title" style="font-size: 16px;margin-left: 25px">{{item1.name}}</span>
                </el-menu-item>
            </el-submenu>-->
            <el-menu-item :index="item.classfyId">
                <span slot="title" style="font-size: 18px;margin-left: 10px" @click="toClassfyList">{{item.name}}</span>
            </el-menu-item>
        </el-menu>
    </div>
</template>

<script>
    export default {
        name: "classfyMenu",
        data(){
            return{
                menuList:[{
                    tid:'1',
                    name:"有书推荐",
                    cnt:300,
                },{
                    classfyId:'2',
                    name:"文学",
                    cnt:2000,
                },{
                    classfyId:'3',
                    name:"教辅",
                    cnt:250,
                },{
                    classfyId:'4',
                    name:"社会科学",
                    cnt:250,
                },{
                    classfyId:'5',
                    name:"童书",
                    cnt:250,
                },{
                    classfyId:'6',
                    name:"艺术",
                    cnt:250,
                },{
                    classfyId:'7',
                    name:"生活",
                    cnt:250,
                },]
            }
        },

        methods:{
            toClassfyList(){
                this.$router.push({path: `/classfyBookList`},)
            },
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