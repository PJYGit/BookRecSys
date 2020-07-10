export default {

    //baseURL: 'http://134.175.237.140:9000/',//后端地址
    //baseURL: 'http://127.0.0.1:8080/',//后端地址
    baseURL: '39.106.160.119:9000/',
    
    method: 'post',
    //登录模块接口
    userLogin: {url: 'user/login'},
    userRegister: {url: 'user/register'},
    userGetInfo: {url: 'user/getinfo'},
    userGetPhoneCode: {url: 'user/phone'},

    // 购物车模块接口
    getCarList: {url: '/car/getlist'},
    modifyCarBookCnt: {url: '/car/addbook'},
    editCarOrder: {url: '/car/setlist'},
    submitCarOrder: {url: '/car/submit'},

    //个人中心模块
    changeMsg:{url:'user/setinfo'},
}
