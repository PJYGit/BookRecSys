<template>
    <div>
        <el-tabs v-model="activeName" tab-position="left" >
            <el-tab-pane label="用户管理" name="user">
                <manage-user style="margin-left: 40px"></manage-user>
            </el-tab-pane>
            <el-tab-pane label="店铺管理" name="shop">
                <manage-shop style="margin-left: 40px"></manage-shop>
            </el-tab-pane>
            <el-tab-pane v-if="role===2" label="系统管理员管理" name="manager">

            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
    import ManageUser from "./manageUser";
    import ShopList from "../shop/shopList";
    import ManageShop from "./manageShop";
    export default {
        name: "manageBlock",
        components: {ManageShop, ManageUser},
        data(){
            return{
                activeName:'user',
                role:1,
            }
        }
    }
</script>

<style scoped>
    >>> .el-tabs__content{
        border-left: 0;
        min-height: 600px;
        padding-left: 0;
    }

    >>> .el-tabs__item:hover{
        color: #EB7A67;
    }

    >>> .el-tabs__item.is-active{
        color: #EB7A67;
    }

    >>> .el-tabs__item{
        font-size: 16px;
        height: 45px;
        line-height: 45px;
        padding: 0 20px 0 10px;
    }

    >>> .el-tabs__header{
        margin-top: 180px;
    }

    >>> .el-tabs__active-bar{
        background-color:#ef9585;
    }
</style>