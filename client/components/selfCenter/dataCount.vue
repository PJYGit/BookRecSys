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

        methods:{

        }

    }
</script>

<style scoped>

</style>