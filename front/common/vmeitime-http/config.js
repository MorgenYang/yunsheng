
let req_domain = 'https://www.healthycloudsci.com';
let req_address = req_domain+'/ysapi';

if(process.env.NODE_ENV === 'development'){
    // 开发环境
    //req_address = 'https://*****.com/'
}else{
    // 生产环境
    //req_address = 'https://*****.com/'
}

export default {
	req_domain,
	req_address
}