export default {

    baseURL: 'http://39.106.160.119:8080/',//后端地址
    //baseURL: 'http://127.0.0.1:8080/',//后端地址
    
    method: 'post',
    //登录模块接口
    userLogin: {url: 'user/login'},

    // 接口
    register: {url: 'user/reg'},
    userInfo: {url: 'user/info'},
    updateUserInfo: {url: 'user/infoupd'},

    // 任务接口
    allTask: {url: 'task/list'},
    taskDetail: {url: 'task/view'},
    addTask: {url: 'task/add'},
    updateTask: {url: 'task/upd'},
    deleteTask: {url: 'task/del'},

    // 记录接口
    allRecord: {url: 'rcd/list'},
    recordDetail: {url: 'rcd/view'},
    updateRecord: {url: 'rcd/upd'},
    addRecord: {url: 'rcd/add'},
    deleteRecord: {url: 'rcd/del'},

    // 上传文件
    uploadFile: {url: 'rcd/picupload'},
}
