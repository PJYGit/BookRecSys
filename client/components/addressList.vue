<template>
    <div>
        <el-button style="background-color: #ef9585;color: white;float: right" @click="addAddress=true">
            新增地址</el-button>
        <div style="display: table;width: 100%">
            <div style="display: table-row">
                <div v-if="isShopping===1" class="tableTitle">
                </div>
                <div class="tableTitle">
                    序号
                </div>
                <div class="tableTitle">
                    地址名
                </div>
                <div class="tableTitle">
                    地址
                </div>
                <div class="tableTitle">
                    收货人姓名
                </div>
                <div class="tableTitle">
                    联系方式
                </div>
                <div class="tableTitle" style="width: 80px">
                </div>
                <div class="tableTitle" style="width: 80px">
                </div>
                <div class="tableTitle" v-if="isShopping===0" style="width: 80px">
                </div>
            </div>

            <div :class="{'grayLine': (index % 2 === 0)}" style="display: table-row" v-for="(item,index) in addressList" :key="item.aid">
                <div v-if="isShopping===1" class="tableCell">
                    <el-radio v-model="addressId" :label="item.aid">
                        {{''}}
                    </el-radio>
                </div>
                <div class="tableCell">
                    {{index+1}}
                </div>
                <div class="tableCell">
                    {{item.title}}
                </div>
                <div class="tableCell">
                    {{item.content}}
                </div>
                <div class="tableCell">
                    {{item.name}}
                </div>
                <div class="tableCell">
                    {{item.phone}}
                </div>
                <div class="tableCell">
                    <el-tag type="success" v-if="item.selected===1">默认地址</el-tag>
                </div>
                <div class="tableCell">
                    <el-button type="text" @click="changeAddr(index,item)">修改</el-button>
                </div>
                <div class="tableCell" v-if="isShopping===0">
                    <el-button type="text" @click="delAddr(index,item)">删除</el-button>
                </div>

            </div>
        </div>

        <el-dialog title="修改收货地址" :visible.sync="changeAddress">
            <el-form :model="updateAddress">
                <el-form-item label="地址名">
                    <el-input v-model="updateAddress.title" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="updateAddress.content" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="收货人姓名">
                    <el-input v-model="updateAddress.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="联系方式">
                    <el-input v-model="updateAddress.phone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="">

                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="changeAddress = false">取 消</el-button>
                <el-button type="primary" @click="updateAddr">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="新增收货地址" :visible.sync="addAddress">
            <el-form :model="newAddress">
                <el-form-item label="地址名">
                    <el-input v-model="newAddress.title" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="newAddress.content" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="收货人姓名">
                    <el-input v-model="newAddress.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="联系方式">
                    <el-input v-model="newAddress.phone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="">

                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="changeAddress = false">取 消</el-button>
                <el-button type="primary" @click="addAddr">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog
                title="提示"
                :visible.sync="deleteAddress"
                width="30%">
            <span>确定删除地址吗？</span>
            <span slot="footer" class="dialog-footer">
    <el-button @click="deleteAddress = false">取 消</el-button>
    <el-button type="primary" @click="sureDelete">确 定</el-button>
  </span>
        </el-dialog>
    </div>
</template>

<script>
    import API from "../api";
    import Cookies from 'js-cookie';
    export default {
        name: "addressList",
        props:{
            isShopping:{
                type:Number,
                default:0,
            },
            addressList:{
                type: Array,
            }
        },

        data(){
            return{
                addressId:'1',
                changeAddress:false,
                addAddress:false,
                deleteAddress:false,
                chooseId:0,
                defafultAddress:0,
                newAddress:{},
                updateAddress:{},
            }
        },

        methods:{
            changeAddr(index,item){
                this.updateAddress=item;
                this.chooseId=index;
                this.changeAddress=true;
            },
            updateAddr(){
                this.addressList[this.chooseId]=this.updateAddress;
                let data = new FormData();
                data.append('uid',Cookies.get('uid'));
                data.append('token',Cookies.get('token'));
                this.addressList.forEach(item=>{
                    data.append('address',JSON.stringify({'title':item.title,'content':item.content,
                        'name':item.name,'phone':item.phone,'selected':item.selected}));
                });
                API.changeMsg(data).then(res=>{
                    if (res.state) {
                        alert("修改失败");
                        return;
                    }
                    alert("修改成功");
                }).catch(msg => {
                    alert(msg)
                });
                this.changeAddress=false;
            },

            addAddr(){
                console.log(this.newAddress);
                this.addressList.push(this.newAddress);
                this.addAddress=false;
                this.newAddress={};
            },

            delAddr(index,item){
                this.chooseId=item.index;
                this.deleteAddress=true;
            },

            sureDelete(){
                console.log(this.chooseId);
                this.addressList.splice(this.chooseId,1);
                let data = new FormData();
                data.append('uid',Cookies.get('uid'));
                data.append('token',Cookies.get('token'));
                this.addressList.forEach(item=>{
                    data.append('address',JSON.stringify({'title':item.title,'content':item.content,
                        'name':item.name,'phone':item.phone,'selected':item.selected}));
                });
                API.changeMsg(data).then(res=>{
                    if (res.state) {
                        alert("删除失败");
                        return;
                    }
                    alert("删除成功");
                }).catch(msg => {
                    alert(msg)
                });
                this.deleteAddress=false;
            },
        }
    }
</script>

<style scoped>
    .tableTitle{
        display: table-cell;
        text-align: center;
        font-size: 15px;
        padding: 5px;
        color: gray;
    }

    .tableCell{
        display: table-cell;
        text-align: center;
        font-size: 15px;
        padding: 5px;
    }

    .grayLine{
        background-color: #f5f5f5;
    }

</style>