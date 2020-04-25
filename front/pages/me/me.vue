<template>
	<view class="content">
		<view class="header" v-if="user.openid != ''">
			<image class="user-img" :src="user.avatarUrl"></image>
			<view class="user-info" @tap="setFunc">
				<text class="user-name">{{user.nickName}}</text>
				<view class="user-other">
					<text class="user-gender" v-if="user.gender=='1'">男</text>
					<text class="user-gender" v-else-if="user.gender=='0'">女</text>
					<text class="user-gender" v-else></text>
					<text class="user-age">手机号：{{user.phone}}</text>
				</view>
			</view>
			<view class="user-load">
				<image  class="arrows-right-user" src="../../static/icon/settings/arrows-right-user.png"></image>
			</view>
		</view>
		<view class="header-for-login" v-else>
			<button class="header-for-login-btn" @getphonenumber="getPhoneNumber" open-type="getPhoneNumber">请登录/注册</button>
		</view>

		<view class="setting-body">
			<view v-for="(item,index) in setting" hover-class="setting-click" v-bind:key="item.id" @tap="itemClick" v-bind:id="'id_'+item.id">
				<view class="setting-line">
					<image class="setting-img" :src="item.imageSrc"></image>
					<text class="setting-text">{{item.title}}</text>
					<image class="arrows-right-setting" src="../../static/icon/settings/arrows-right-setting.png"></image>
				</view>
				<view :class="{line:(index !=setting.length-1)}"></view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user: {
					openid: '',
					token: '',
					phone: '',
					avatarUrl: '',
					nickName: ''
				},
				setting: [{
						id: 0,
						title: "健康档案",
						imageSrc: "../../static/icon/settings/health-record.png",
						linkUrl: "./healthRecord/healthRecord"
					},
					{
						id: 1,
						title: "健康商城",
						imageSrc: "../../static/icon/settings/store.png",
						linkUrl: "./shop/shop"
					},
					{
						id: 2,
						title: "私人医生",
						imageSrc: "../../static/icon/settings/doctor.png",
						linkUrl: "./doctor/doctor"
					},
					{
						id: 3,
						title: "个人设置",
						imageSrc: "../../static/icon/settings/settings.png",
						linkUrl: "./setting/setting"
					},
					{
						id: 4,
						title: "意见反馈",
						imageSrc: "../../static/icon/settings/feedback.png",
						linkUrl: "./feedback/feedback"
					},
					{
						id: 5,
						title: "检查更新",
						imageSrc: "../../static/icon/settings/update.png",
						linkUrl: ""
					},
					{
						id: 6,
						title: "关于我们",
						imageSrc: "../../static/icon/settings/us.png",
						linkUrl: "./about/about"
					},
					{
						id: 7,
						title: "联系我们",
						imageSrc: "../../static/icon/settings/connection.png",
						linkUrl: "./contact/contact"
					}
				]
			}
		},
		onShow() {
			this.user.openid = uni.getStorageSync('openid');
			this.user.token = uni.getStorageSync('token');
			this.user.phone = uni.getStorageSync('phone');
			this.user.avatarUrl = uni.getStorageSync('avatarUrl');
			this.user.nickName = uni.getStorageSync('nickName');
		},
		methods: {
			getPhoneNumber(e) {
				//拿到edata和iv
				let that = this;
				var edata = e.detail.encryptedData;
				var iv = e.detail.iv;

				//请求后台拿到openid
				uni.login({
					provider: 'weixin',
					success: function(loginRes) {
						console.log(loginRes.code);
						uni.request({
							url: that.domainName + '/wechatlogin',
							method: "POST",
							data: {
								"code": loginRes.code,
							},
							success: (res) => {
								if (res.data.code == 200) {
									console.log(res);
									that.user.openid = res.data.data.loginClientUserVo.id;
									uni.setStorageSync('openid', that.user.openid);
									that.user.token = res.data.data.token;
									uni.setStorageSync('token', that.user.token);

									// 			//获取phone params(edata,iv,openid,code)
									// 			uni.login({
									// 				provider: 'weixin',
									// 				success: function(loginRes) {
									// 					console.log(loginRes);
									// 					uni.request({ // TODO: get phone
									// 						url: that.domainName + '/wechatPhone',
									// 						method: "POST",
									// 						data: {
									// 							"code": loginRes.code,
									// 							"encryptedData": edata,
									// 							"iv": iv,
									// 							"openid": that.openid
									// 						},
									// 						success: (res) => {
									// 							var data = res.data.data;
									// 							that.phone = data.phoneNumber;
									// 							console.log(that.phone);
									// 						},
									// 					});
									// 				},
									// 			});			
								} else {
									console.log(res.data.msg);
								}
							}
						});
					}
				});

				// uni.login({
				// 	provider: 'weixin',
				// 	success: function(loginRes) {
				// 		console.log(loginRes);
				// 		// uni.request({ // TODO: get phone
				// 		// 	url: that.domainName + '/wechatPhone',
				// 		// 	method: "POST",
				// 		// 	data: {
				// 		// 		"code": loginRes.code,
				// 		// 		"encryptedData": edata,
				// 		// 		"iv": iv,
				// 		// 		"openid": _self.openid
				// 		// 	},
				// 		// 	success: (res) => {
				// 		// 		var data = res.data.data;
				// 		// 		_self.phone = data.phoneNumber;
				// 		// 		uni.setStorageSync("phone", _self.phone);
				// 		// 		console.log(res);

				// 		// 		// uni.login({
				// 		// 		// 	provider: 'weixin',
				// 		// 		// 	success: function(loginRes) {
				// 		// 		// 		console.log(loginRes.code);
				// 		// 		// 		uni.request({ // login or regiser
				// 		// 		// 			url: that.domainName + '/wechatRegisterOrBind',
				// 		// 		// 			method: "POST",
				// 		// 		// 			data: {
				// 		// 		// 				"avatarUrl": '',
				// 		// 		// 				"city": '',
				// 		// 		// 				"code": loginRes.code,
				// 		// 		// 				"country": '',
				// 		// 		// 				"gender": '',
				// 		// 		// 				"nickName": '',
				// 		// 		// 				"phone": _self.phone,
				// 		// 		// 				"province": ''
				// 		// 		// 			},
				// 		// 		// 			success: (res) => {
				// 		// 		// 				console.log(res);
				// 		// 		// 			}
				// 		// 		// 		});
				// 		// 		// 	}
				// 		// 		// });
				// 		// 	}
				// 		// });
				// 	}
				// });
			},
			itemClick(e) {
				let id = e.currentTarget.id.toString().substr(3);
				uni.showToast({
					title: this.setting[id].title,
					icon: "none",
					duration: 300
				});
				if (this.setting[id].linkUrl != "") {
					uni.navigateTo({
						url: this.setting[id].linkUrl
					});
				}
			},
			setFunc() {
				uni.navigateTo({
					url: 'info/info'
				});
			}

		}
	}
</script>

<style>
	@import url("./me.css");
</style>
