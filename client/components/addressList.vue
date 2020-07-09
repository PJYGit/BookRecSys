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
                    姓名
                </div>
                <div class="tableTitle">
                    联系方式
                </div>
                <div class="tableTitle">
                    地址
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
                    {{item.conName}}
                </div>
                <div class="tableCell">
                    {{item.phone}}
                </div>
                <div class="tableCell">
                    {{item.address}}
                </div>
                <div class="tableCell">
                    <el-button type="text" @click="changeAddr(item)">修改</el-button>
                </div>
                <div class="tableCell" v-if="isShopping===0">
                    <el-button type="text" @click="delAddr(item)">删除</el-button>
                </div>

            </div>
        </div>

        <el-dialog title="修改收货地址" :visible.sync="changeAddress">
            <el-form :model="updateAddress">
                <el-form-item label="收货人姓名">
                    <el-input v-model="updateAddress.conName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="收货人电话">
                    <el-input v-model="updateAddress.phone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="收货人地址">
                    <el-input v-model="updateAddress.address" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="changeAddress = false">取 消</el-button>
                <el-button type="primary" @click="updateAddr">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="新增收货地址" :visible.sync="addAddress">
            <el-form :model="newAddress">
                <el-form-item label="收货人姓名">
                    <el-input v-model="newAddress.conName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="收货人电话">
                    <el-input v-model="newAddress.phone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="收货人地址">
                    <el-input v-model="newAddress.address" autocomplete="off"></el-input>
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
    export default {
        name: "addressList",
        props:{
            isShopping:{
                type:Number,
                default:0,
            }
        },
        data(){
            return{
                addressId:'1',
                changeAddress:false,
                addAddress:false,
                deleteAddress:false,
                deleId:0,
                newAddress:{
                },
                updateAddress:{},
                addressList:[{
                    uid:1,
                    aid:1,
                    conName:"用户1",
                    phone:"12340000000",
                    address:"北京市海淀区北下关街道北京交通大学南门"
                },{
                    uid:1,
                    aid:2,
                    conName:"用户2",
                    phone:"12340000000",
                    address:"北京市海淀区北下关街道北京交通大学南门"
                },{
                    uid:1,
                    aid:3,
                    conName:"用户1",
                    phone:"12340000000",
                    address:"北京市海淀区北下关街道北京交通大学南门"
                },{
                    uid:1,
                    aid:4,
                    conName:"用户1",
                    phone:"12340000000",
                    address:"北京市海淀区北下关街道北京交通大学南门"
                },],
            }
        },

        methods:{
            changeAddr(item){
                this.updateAddress=item;
                this.changeAddress=true;

            },
            updateAddr(){
                this.changeAddress=false;
            },
            addAddr(){
                console.log(this.newAddress);
                this.addAddress=false;
                this.newAddress={};
            },

            delAddr(item){
                this.deleId=item.aid;
                this.deleteAddress=true;

            },

            sureDelete(){
                console.log(this.deleId);
                this.deleteAddress=false;
            }
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