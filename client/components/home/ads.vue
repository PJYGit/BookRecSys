<template>
    <div>
        <el-carousel height="500px">
            <el-carousel-item v-for="item in 3" :key="item">
            </el-carousel-item>
        </el-carousel>
    </div>
</template>

<script>
    export default {
        name: "ads"
    }
</script>

<style scoped>
    .el-carousel__item:nth-child(3n) {
        background: url("../../assets/image/lunbo1.png") no-repeat;
        background-size: 100%;
    }

    .el-carousel__item:nth-child(3n+1) {
        background: url("../../assets/image/lunbo2.png") no-repeat;
        background-size: 100%;
    }

    .el-carousel__item:nth-child(3n+2) {
        background: url("../../assets/image/lunbo3.png") no-repeat;
        background-size: 100%;
    }
</style>