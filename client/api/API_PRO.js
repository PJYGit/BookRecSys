export default {

    //baseURL: 'http://134.175.237.140:9000/',//后端地址
    //baseURL: 'http://127.0.0.1:8080/',//后端地址
    baseURL: 'http://39.106.160.119:9000/',

    method: 'post',
    // 主页模块
    getTopBook: {url: '/home/top'},
    getRecommendBook: {url: '/home/person'},
    getNew:{url:'home/new'},
    getTag:{url:'shop/tags'},

    //搜索模块
    SShop:{url:'shop/search'},//搜索店铺
    SBook:{url:'home/search'},//图书搜索


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
    addToShoppingCar:{url:'car/addbook'},

    // 店铺模块
    getShopBaseInfo:{url:'shop/getinfo'},
    getShopBookInfo: {url:'shop/booklist'},
    getTagsList: {url: 'shop/tags'},

    //个人中心模块
    changeMsg: {url: 'user/setinfo'},

    // 个人管理模块
    getUSList: {url: 'user/manage/list'},//获取用户列表
    setUserInfo: {url: 'user/manage/setinfo'},//修改用户信息
    getSList:{url:'shop/manage/list'},//获取全部店铺列表

    // 用户管理
    searchTargetUserInfo: {url: '/user/manage/search'},

    // 店铺管理模块
    getShopInfo: {url: '/shop/manage/getinfo'},
    setShopInfo: {url: '/shop/manage/setinfo'},
    addNewBookIntoShop: {url: '/shop/manage/addbook'},
    setBookInfo: {url: '/shop/manage/setbookinfo'},
    delBookInfo: {url: '/shop/manage/delbook'},
    getShopOrder: {url: '/order/manage/getlist'},
    operateOrder: {url: '/order/manage/operate'},

    // 图片上传
    uploadImage: {url: '/pic/upload'},

    //订单模块
    getOList:{url:'order/getlist'},
    getOrderDetail:{url:'order/getinfo'},
    setOState:{url:'order/operate'},
    CMOrder:{url:'order/comment'},

    //图书模块
    getBookDetail:{url:'shop/getbookinfo'},
    buyOnlyBook:{url:'order/create'},

}
