/**
 * 通用uni-app网络请求
 * 基于 Promise 对象实现更简单的 request 使用方式，支持请求和响应拦截
 */

/*
// 开放的接口
import http from './request'

http.config.baseUrl = "http://localhost:8080/api/"

http.request(url:'user/list',method:'GET').then((res)=>{
	console.log(JSON.stringify(res))
})
http.get('user/list').then((res)=>{
	console.log(JSON.stringify(res))
})
http.get('user/list', {status: 1}).then((res)=>{
	console.log(JSON.stringify(res))
})
http.post('user', {id:1, status: 1}).then((res)=>{
	console.log(JSON.stringify(res))
})
http.put('user/1', {status: 2}).then((res)=>{
	console.log(JSON.stringify(res))
})
http.delete('user/1').then((res)=>{
	console.log(JSON.stringify(res))
}) 

*/

import urlConfig from '@/common/vmeitime-http/config.js'

export default {
	config: {
		baseUrl: "",
		header: {
			'Content-Type':'application/json;charset=UTF-8',
			//'Content-Type':'application/x-www-form-urlencoded'
		},  
		data: {},
		method: "GET",
		dataType: "json",  /* 如设为json，会对返回的数据做一次 JSON.parse */
		responseType: "text",
		success() {},
		fail() {},
		complete() {}
	},
	interceptor: {
		request: null,
		response: null
	},
	request(options) {
		if (!options) {
			options = {}
		}
		options.baseUrl = options.baseUrl || this.config.baseUrl
		options.dataType = options.dataType || this.config.dataType
		options.url = options.baseUrl + options.url
		options.data = options.data || {}
		options.method = options.method || this.config.method
		//TODO 加密数据
		
		//TODO 数据签名

		let _token = {'token': uni.getStorageSync("token") || ''};	
		if(options.url.indexOf("/tjDzcs/getPageList")>-1 || options.url.indexOf("/tjDzcs/changeList")){
				options.header = Object.assign({}, options.header, "");
		}else{
				options.header = Object.assign({}, options.header, _token);
		}
	
		/*_sign = {'sign': sign(JSON.stringify(options.data))}
		options.header = Object.assign({}, options.header, _token,_sign) 
		*/
	   
		return new Promise((resolve, reject) => {
			let _config = null
			options.complete = (response) => {
				let statusCode = response.data.code
				response.config = _config
				if (process.env.NODE_ENV === 'development') {
					if (statusCode === 200) {
						//console.log("【" + _config.requestId + "】 结果：" + JSON.stringify(response.data))
					}
				}
				if (this.interceptor.response) {
					let newResponse = this.interceptor.response(response)
					if (newResponse) {
						response = newResponse
					}
				}
		
				// 统一的响应日志记录
				//_reslog(response)
				if (statusCode === 200) { //成功
				//异常信息提示
					resolve(response);
				} else if(statusCode === 401) {
					//token失效，重新登录
					login(options.baseUrl,options);
					
				}else{
					if(options.url.endsWith("/tjDz/check") ||
						options.url.indexOf("/tjYhslxx/info")>-1){
						
					}else{
						//异常信息提示
						uni.showToast({
						    title: response.data.msg,
						    duration: 2000,
							icon:"none"
						});
					}
					reject(response)
				}
			}

			_config = Object.assign({}, this.config, options)
			_config.requestId = new Date().getTime()

			if (this.interceptor.request) {
				this.interceptor.request(_config)
			}
			
			// 统一的请求日志记录
			//_reqlog(_config)

			/* if (process.env.NODE_ENV === 'development') {
				console.log("【" + _config.requestId + "】 地址：" + _config.url)
				if (_config.data) {
					console.log("【" + _config.requestId + "】 参数：" + JSON.stringify(_config.data))
				}
			} */

			uni.request(_config);
		});
	},
	get(url, data, options) {
		if (!options) {
			options = {}
		}
		options.url = url
		options.data = data
		options.method = 'GET'  
		return this.request(options)
	},
	post(url, data, options) {
		if (!options) {
			options = {}
		}
		options.url = url
		options.data = data
		options.method = 'POST'
		return this.request(options)
	},
	put(url, data, options) {
		if (!options) {
			options = {}
		}
		options.url = url
		options.data = data
		options.method = 'PUT'
		return this.request(options)
	},
	delete(url, data, options) {
		if (!options) {
			options = {}
		}
		options.url = url
		options.data = data
		options.method = 'DELETE'
		return this.request(options)
	}
}


/**
 * 请求接口日志记录
 */
function _reqlog(req) {
	if (process.env.NODE_ENV === 'development') {
		console.log("【" + req.requestId + "】 地址：" + req.url)
		if (req.data) {
			console.log("【" + req.requestId + "】 请求参数：" + JSON.stringify(req.data))
		}
	}
	//TODO 调接口异步写入日志数据库
}

function login(baseUrl,options){
	uni.showToast({
	    title: "用户登录失效,重连中。。。",
	    duration: 2000,
		icon:"none"
	});
	uni.login({
		provider: 'weixin',
		success: function(loginRes) {
			uni.request({
				url:  urlConfig.req_address+'/wechatlogin',
				method: "POST",
				data: {
					"code": loginRes.code
				},
				success: (res) => {
					if (res.data.code == 200 && res.data.data) {
						//$this.request(options,1)
						/* let url ="/"+ options.$this.$mp.page.route;
						uni.redirectTo({url:url}); */ 
						uni.switchTab({
							url: '/pages/me/me'
						}); 
						uni.setStorageSync('token', res.data.data.token);
						uni.setStorageSync('nickName', res.data.data.loginClientUserVo.nickname);
						uni.setStorageSync('avatarUrl', res.data.data.loginClientUserVo.avatarurl);
						uni.setStorageSync('openid', res.data.data.loginClientUserVo.openid);
						uni.setStorageSync('phone', res.data.data.loginClientUserVo.phone);
						uni.setStorageSync('gender', res.data.data.loginClientUserVo.gender);
						uni.setStorageSync('id', res.data.data.loginClientUserVo.id);
						uni.setStorageSync('username', res.data.data.loginClientUserVo.username);
					} else {
						
					}
				}
			});
		}
	});

}

/**
 * 响应接口日志记录
 */
function _reslog(res) {
	let _statusCode = res.statusCode;
	if (process.env.NODE_ENV === 'development') {
		console.log("【" + res.config.requestId + "】 地址：" + res.config.url)
		if (res.config.data) {
			console.log("【" + res.config.requestId + "】 请求参数：" + JSON.stringify(res.config.data))
		}
		console.log("【" + res.config.requestId + "】 响应结果：" + JSON.stringify(res))
	}
	//TODO 除了接口服务错误外，其他日志调接口异步写入日志数据库
	switch(_statusCode){
		case 200:
			break;
		case 401:
			break;
		case 404:
			break;
		default:
			break;
	}
}

