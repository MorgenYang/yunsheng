import Vue from 'vue'
import App from './App'

//请求地址配置
import config from '@/common/vmeitime-http/config.js'
Vue.prototype.domainName = config.req_domain;
Vue.prototype.reqAddress = config.req_address;

Vue.config.productionTip = false;
Vue.prototype.openid = '';

import api from '@/common/vmeitime-http/request.js'
Vue.prototype.$api = api
				
Vue.prototype.getGlobalUser = function(key) {
	var userInfo = uni.getStorageSync("globalUser");
	if (userInfo != null && userInfo != "" && userInfo != undefined) {
		return userInfo;
	} else {
		return null;
	}
}
Vue.prototype.getLoginUser = function() {
	var userInfo = {
		"id": uni.getStorageSync("id"),
		"nickName": uni.getStorageSync("nickName"),
		"avatarUrl": uni.getStorageSync("avatarUrl"),
		"openid": uni.getStorageSync("openid"),
		"phone": uni.getStorageSync("phone"),
		"gender": uni.getStorageSync("gender"),
		"token": uni.getStorageSync("token")
	}
	return userInfo;
}


App.mpType = 'app'

/* import mimc from '@/components/mimc-uniapp-sdk/mimc.js';
import Base64 from '@/components/mimc-uniapp-sdk/Base64.js';
Vue.prototype.mimc = mimc
Vue.prototype.Base64 = Base64 */
const app = new Vue({
    ...App
})
app.$mount()


