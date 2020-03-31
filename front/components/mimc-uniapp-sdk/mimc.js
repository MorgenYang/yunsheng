/**
 * 该js文件是根据 mimc-webjs-sdk/demo/demo.html 修改而成
 */
import Base64 from './Base64.js';
import MIMCUser from './mimc-unipp-sdk-min.1.0.2.js';

var user = null;
export default class mimc {
	// 登录
	static login(config) {
		console.log('进入登录....')
		// 配置登录
		console.log("_window 参数： ", mimc._window())
		if(user == null){
			user = new MIMCUser(config.data.appId, config.data.appAccount, mimc._window());
		}

		if (config.hasOwnProperty('statusChange') && typeof config.statusChange == "function") {
			// 登录结果回调
			// console.log('登录回调设置')
			user.registerStatusChange(config.statusChange);
			
		}

		if (config.hasOwnProperty('serverAck') && typeof config.serverAck == "function") {
			// 发送消息后，服务器接收到消息ack的回调
			user.registerServerAckHandler(config.serverAck);
		}

		if (config.hasOwnProperty('receiveP2PMsg') && typeof config.receiveP2PMsg == "function") {
			// 接收单聊消息回调
			// console.log('接收单聊消息回调')
			user.registerP2PMsgHandler(config.receiveP2PMsg);
		}

		if (config.hasOwnProperty('receiveP2TMsg') && typeof config.receiveP2TMsg == "function") {
			// 接收群聊消息回调
			user.registerGroupMsgHandler(config.receiveP2TMsg);
		}

		if (config.hasOwnProperty('disconnect') && typeof config.disconnect == "function") {
			// 连接断开回调
			user.registerDisconnHandler(config.disconnect);
		}

		if (config.hasOwnProperty('ucDismiss') && typeof config.ucDismiss == "function") {
			// 解散无限大群回调
			user.registerUCDismissHandler(config.ucDismiss);
		}

		if (config.hasOwnProperty('ucJoinResp') && typeof config.ucJoinResp == "function") {
			// 加入无限大群回调
			user.registerUCJoinRespHandler(config.ucJoinResp);
		}
		if (config.hasOwnProperty('ucMessage') && typeof config.ucMessage == "function") {
			// 接收无限大群消息回调
			user.registerUCMsgHandler(config.ucMessage);
		}
		if (config.hasOwnProperty('ucQuitResp') && typeof config.ucQuitResp == "function") {
			// 退出无限大群回调
			user.registerUCQuitRespHandler(config.ucQuitResp);
		}
		// fetchMIMCToken 需要同步访问
		var _data = "";
		if (config.hasOwnProperty('data')) {
			// 使用默认鉴权接口
			_data = config.data;
		}
		uni.request({
			"url": config.url,
			"method": "POST",
			"header": {
				"content-type": "application/json"
			},
			"data": JSON.stringify(_data),
			success: (resp) => {
				// console.log("鉴权返回结果：", resp.data)
				// 鉴权回调
				user.registerFetchToken(function() {
					return resp.data
				});
				console.log('调用登录。。。')
				user.login();
				// console.log("登录结束：")
			}
		})
		
		return user;
	} // end login

	static _window() {
		// 默认
		var _window = {
			navigator: {
				userAgent: "mimc-uniapp/1.0",
				appName: "xiaomi",
				appVersion: "1.0"
			}
		}

		//#ifdef H5
		_window = window
		//#endif

		//#ifdef MP-WEIXIN
		const res = uni.getSystemInfoSync();
		var _navigator = {
			userAgent: "mimc-uniapp/1.0/ID:" + res.model.toString() + res.version.toString() + res.platform.toString(), // 设备唯一信息
			appName: "xiaomi-mimc",
			appVersion: "1.0"
		}
		_window.navigator = _navigator
		//#endif

		//#ifdef APP-PLUS
		var _navigator = {
			userAgent: "mimc-uniapp/1.0/ID:111", //  + plus.device.uuid.toString() 设备唯一信息
			appName: "xiaomi-mimc",
			appVersion: "1.0"
		}
		_window.navigator = _navigator
		//#endif

		return _window
	}


}
