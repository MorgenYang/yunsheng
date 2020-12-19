import Vue from 'vue'
import App from './App'

Vue.config.productionTip = false;
Vue.prototype.domainName = 'https://www.healthycloudsci.com';
Vue.prototype.reqAddress = Vue.prototype.domainName+'/ysapi';
Vue.prototype.openid = '';
				
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


