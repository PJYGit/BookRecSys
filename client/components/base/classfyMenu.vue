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
            <el-submenu v-if="item.subMenu.length!==0" :index="item.classfyId">
                <template slot="title">
                    <span style="font-size: 18px;margin-left: 10px">{{item.name}}</span>
                </template>
                <el-menu-item v-for="item1 in item.subMenu" :key="item1.classfyId" :index="item1.classfyId">
                    <span slot="title" style="font-size: 16px;margin-left: 25px">{{item1.name}}</span>
                </el-menu-item>
            </el-submenu>
            <el-menu-item v-if="item.subMenu.length===0" :index="item.classfyId">
                <span slot="title" style="font-size: 18px;margin-left: 10px">{{item.name}}</span>
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
                    classfyId:'1',
                    name:"有书推荐",
                    subMenu:[],
                },{
                    classfyId:'2',
                    name:"文学",
                    subMenu:[{
                        classfyId:'2-1',
                        name:"纪实文学",
                    },{
                        classfyId:'2-2',
                        name:"中国古典文学",
                    },{
                        classfyId:'2-3',
                        name:"中国现当代文学",
                    },{
                        classfyId:'2-4',
                        name:"国外诗歌",
                    },{
                        classfyId:'2-5',
                        name:"国外散文",
                    },],
                },{
                    classfyId:'3',
                    name:"教辅",
                    subMenu:[{
                        classfyId:'3-1',
                        name:"中小学教辅",
                    },{
                        classfyId:'3-2',
                        name:"高中教辅",
                    },],
                },{
                    classfyId:'4',
                    name:"社会科学",
                    subMenu:[{
                        classfyId:'3-1',
                        name:"中小学教辅",
                    },{
                        classfyId:'3-2',
                        name:"高中教辅",
                    },],
                },{
                    classfyId:'5',
                    name:"童书",
                    subMenu:[{
                        classfyId:'3-1',
                        name:"中小学教辅",
                    },{
                        classfyId:'3-2',
                        name:"高中教辅",
                    },],
                },{
                    classfyId:'1',
                    name:"艺术",
                    subMenu:[{
                        classfyId:'3-1',
                        name:"中小学教辅",
                    },{
                        classfyId:'3-2',
                        name:"高中教辅",
                    },],
                },{
                    classfyId:'1',
                    name:"生活",
                    subMenu:[{
                        classfyId:'3-1',
                        name:"中小学教辅",
                    },{
                        classfyId:'3-2',
                        name:"高中教辅",
                    },],
                },]
            }
        }
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