<template>
	<view class="content">
		<view class="header" v-if="user.phone != '' && user.openid != ''">
			<image class="user-img" :src="user.avatarUrl"></image>
			<view class="user-info">
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
		<view class="header-for-login" v-else-if="user.openid != '' && user.phone == ''">
			<button class="header-for-login-btn" @getphonenumber="getPhoneNumber" open-type="getPhoneNumber">获取手机号</button>
		</view>
		<view class="header-for-login" v-else>
			<button class="header-for-login-btn" @getuserinfo="getUserInfo" open-type="getUserInfo">微信登录</button>
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
					nickName: '',
					gender: ''
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
			
			console.log(this.user.phone);
			console.log(this.user.openid);
			console.log(this.user.phone);
		},
		methods: {
			getUserInfo(e) {
				let that = this;
				uni.getUserInfo({
					provider: 'weixin',
					success: function (infoRes) {
						uni.setStorageSync('avatarUrl', infoRes.userInfo.avatarUrl);
						uni.setStorageSync('gender', infoRes.userInfo.gender);
						uni.setStorageSync('nickName', infoRes.userInfo.nickName);
						that.user.avatarUrl = infoRes.userInfo.avatarUrl;
						that.user.gender = infoRes.userInfo.gender;
						that.user.nickName = infoRes.userInfo.nickName;
						uni.login({
							provider:'weixin',
							success: (res) => {
								console.log(res.code);
								//这里需要做登录验证操作，如果用户存在直接登录，不存在就注册
								uni.request({
									url: that.domainName + '/wechatlogin',
									method: "POST",
									data: {
										  "code": res.code
									},
									success: (res) => {
										console.log(res);
										if (200 == res.data.code) {
											that.user.openid = res.data.data.loginClientUserVo.openid;
											that.user.phone = res.data.data.loginClientUserVo.phone;
										} else {
											// 注册
											uni.login({
												provider:'weixin',
												success: (res) => {
													console.log(res.code);
													uni.request({
														url: that.domainName + '/wechatRegister',
														method: "POST",
														data: {
															"avatarUrl": that.user.avatarUrl,
															  "city": "",
															  "code": res.code,
															  "country": "",
															  "gender": that.user.gender,
															  "nickName": that.user.nickName,
															  "province": ""
														},
														success: (res) => {
															console.log(res.data);
															if (res.data.code == 200) {
																console.log(res.data.data.openid);
																that.user.openid = res.data.data.openid;
																uni.setStorageSync('openid', that.user.openid);
															
															}
														}
													});
												}
											})
											
										}
									}
								})
								
								
								
							}
						});
					},
					fail:function(e){
						console.log('获取用户信息失败');
					}
				});
			},
			getPhoneNumber(e) {
				//拿到edata和iv
				let that = this;
				var edata = e.detail.encryptedData;
				var iv = e.detail.iv;

				//请求后台拿到openid
				uni.login({
					provider: 'weixin',
					success: function(loginRes) {
						uni.request({
							url: that.domainName + '/wechatPhone',
							method: "POST",
							data: {
								  "code": loginRes.code,
								  "encryptedData": edata,
								  "iv": iv
							},
							success: (res) => {
								if (res.data.code == 200) {
									that.user.phone = res.data.data.loginClientUserVo.phone;
									console.log(that.user.phone);
									uni.setStorageSync('phone', that.user.phone);
								} else {
									console.log(res.data.msg);
								}
							}
						});
					}
				});
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
			}

		}
	}
</script>

<style>
	@import url("./me.css");
</style>
