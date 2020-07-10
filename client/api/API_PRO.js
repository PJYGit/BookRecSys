export default {

    baseURL: 'http://134.175.237.140:9000/',//后端地址
    //baseURL: 'http://127.0.0.1:8080/',//后端地址
    
    method: 'post',
    //登录模块接口
    userLogin: {url: 'user/login'},
    userRegister: {url: 'user/register'},
    userGetInfo: {url: 'user/getinfo'},
    userGetPhoneCode: {url: 'user/phone'},

    // 购物车模块接口
    getCarList: {url: '/car/getinfo'},
    modifyCarBookCnt: {url: '/car/addbook'},
    editCarOrder: {url: '/car/setlist'},
    submitCarOrder: {url: '/car/submit'}
}
