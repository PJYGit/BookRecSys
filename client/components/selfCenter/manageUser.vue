<template>
    <div style="min-height: 500px">
        <div style="display: table;width: 100%">
            <div style="display: table-row">
                <div class="tableTitle">
                    序号
                </div>
                <div class="tableTitle">
                    手机号
                </div>
                <div class="tableTitle">
                    昵称
                </div>
                <div class="tableTitle">
                    注册时间
                </div>
                <div class="tableTitle">
                    折扣
                </div>
                <div class="tableTitle">
                    账户余额
                </div>
                <div class="tableTitle">
                    身份
                </div>
                <div class="tableTitle">
                    账户状态
                </div>
                <div class="tableTitle">
                </div>
                <div class="tableTitle">
                </div>
            </div>
            <div :class="{'grayLine': (index % 2 === 0)}" style="display: table-row"
                 v-for="(item,index) in userList" :key="item.uid">
                <div class="tableCell">
                    {{index+1}}
                </div>
                <div class="tableCell">
                    {{item.urn}}
                </div>
                <div class="tableCell">
                    {{item.nickname}}
                </div>
                <div class="tableCell">
                    {{new Date(item.regtime*1000).toLocaleDateString()}}
                </div>
                <div class="tableCell">
                    {{item.vipRate}}
                </div>
                <div class="tableCell">
                    {{item.money}}
                </div>
                <div class="tableCell">
                    <div v-if="item.role===0">
                        普通用户
                    </div>
                    <div v-if="item.role===1">
                        系统管理员
                    </div>
                    <div v-if="item.role===2">
                        超级管理员
                    </div>
                </div>
                <div class="tableCell">
                    <div v-if="item.baned===0">
                        <el-tag type="success">正常</el-tag>
                    </div>
                    <div v-if="item.baned===1">
                        <el-tag type="danger">封禁</el-tag>
                    </div>
                </div>
                <div class="tableCell">
                    <el-button v-if="item.baned===0" style="font-size: 16px;" type="text" @click="lockUser(item)">封禁</el-button>
                    <el-button v-if="item.baned!==0" style="font-size: 16px;" type="text" @click="lockUser(item)">解封</el-button>
                </div>
                <div class="tableCell">
                    <el-button style="font-size: 16px;" type="text" @click="delAddr(index,item)">修改</el-button>
                </div>
            </div>

        </div>
        <el-pagination
                layout="prev, pager, next"
                :current-page.sync="currentPage"
                :page-count="pagesize"
                @current-change="getUserList" style="margin-top: 20px;float: right;margin-right: 20px">
        </el-pagination>
    </div>
</template>

<script>
    import API from "../../api";
    import Cookies from 'js-cookie';
    export default {
        name: "manageUser",
        data(){
            return{
                userList:[],
                pagesize:0,
                currentPage:1,
                uid:Cookies.get('uid'),
                token:Cookies.get('token'),
            }
        },

        mounted(){
            this.getUserList();
        },

        methods:{
            getUserList(){
                let data = new FormData();
                data.append('uid',this.uid);
                data.append('token',this.token);
                data.append('page',this.currentPage);

                API.getUSList(data).then(res=>{
                    console.log(res);
                    if (res.state) {
                        alert("获取用户列表失败");
                        return;
                    }
                    this.userList = res.list;
                    this.pagesize=res.pagesize;

                }).catch(msg => {
                    alert(msg)
                })
            },

            lockUser(item){
                let data = new FormData();
                data.append('uid',this.uid);
                data.append('token',this.token);
                data.append('target',item.uid);

                if(item.baned===0){
                    item.baned=1;
                }else{
                    item.baned=0;
                }

                data.append('baned',item.baned);

                API.setUserInfo(data).then(res=>{
                    console.log(res);
                    if (res.state) {
                        alert("修改账户状态失败");
                        return;
                    }
                    alert("修改账户状态成功");
                }).catch(msg => {
                    alert(msg)
                })
            }

        }
    }
</script>

<style scoped>
    @import "../../assets/tableStyle.css";
</style>