<template>
    <div style="text-align: center;">
        <el-upload
                class="avatar-uploader"
                action="http://39.106.160.119:9000/pic/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                disabled style="">
            <img v-if="userInfo.head" :src="userInfo.head" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
        <el-form label-width="150px" :model="userInfo" style="text-align: left;margin-top: 20px">
            <el-form-item label="账号">
                {{userInfo.urn}}
            </el-form-item>
            <el-form-item label="昵称">
                <el-input v-model="userInfo.nickname"></el-input>
            </el-form-item>
            <el-form-item label="注册时间">
                {{new Date(userInfo.regtime*1000).toLocaleDateString()}}
            </el-form-item>
            <el-form-item label="VIP折扣">
                <div v-if="userInfo.vipRate===1">无折扣</div>
                <div v-if="userInfo.vipRate < 1">{{userInfo.vipRate * 10}}折</div>
            </el-form-item>
            <el-form-item label="账户余额">
                {{userInfo.money}}元
            </el-form-item>
        </el-form>
        <el-button style="background-color: #ef9585;color: white;margin-top: 20px" @click="changePersonMsg">
            提交修改</el-button>
    </div>
</template>

<script>
    import API from "../../api";
    import Cookies from 'js-cookie';
    export default {
        name: "userInfo",
        props:{
            userInfo:{
                type:Object,
            }
        },
        data(){
            return{
                uid:Cookies.get('uid'),
                token:Cookies.get('token'),
            }
        },

        methods:{
            handleAvatarSuccess(res) {
                this.userInfo.head = res;
            },

            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';

                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 或 PNG 格式!');
                }

                return isJPG;
            },

            changePersonMsg(){
                let data = new FormData();
                data.append('uid',this.uid);
                data.append('token',this.token);
                data.append('head',this.userInfo.head);
                data.append('nickname',this.userInfo.nickname);

                API.changeMsg(data).then(res=>{
                    if (res.state) {
                        alert("修改失败");
                        return;
                    }
                    alert("修改成功");
                }).catch(msg => {
                    alert(msg)
                });
            },
        }
    }
</script>

<style scoped>
    >>> .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }

    >>> .el-form-item__label{
        font-size: 18px;
        padding-right: 40px;
    }
    >>> .el-form-item__content{
        font-size: 18px;
    }
</style>