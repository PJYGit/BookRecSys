<template>
    <div style="text-align: center;padding-top: 40px">
        <el-progress type="circle" :percentage="prec" :width="wid"></el-progress>
        <div style="margin-top: 30px;font-size: 18px">购书转化率</div>
    </div>
</template>

<script>
    import API from "../../api"
    import Cookies from 'js-cookie';
    export default {
        name: "dataCount",
        data(){
            return{
                uid:Cookies.get('uid'),
                token:Cookies.get('token'),
                prec:30,
                wid:300,
            }
        },

        mounted(){
            this.totalTrans();
        },

        methods:{
            totalTrans(){
                let data = new FormData();
                data.append('uid',this.uid);
                data.append('token',this.token);

                API.getTrans(data).then(res=>{
                    if (res.state) {
                        alert("获取个人信息失败");
                        return;
                    }
                    this.prec = res.val*100;
                }).catch(msg => {
                    alert(msg)
                })

            },
        }

    }
</script>

<style scoped>

</style>