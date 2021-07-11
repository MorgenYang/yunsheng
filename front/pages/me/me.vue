<template>
	<view class="content">
		<view class="header" v-if="user.openid != ''">
			<image class="user-img" :src="user.avatarUrl"></image>
			<view class="user-info">
				<text class="user-name">{{user.nickName}}</text>
				<view class="user-other">
					<button class="user-phone" @getphonenumber="getPhoneNumber" open-type="getPhoneNumber" v-if="user.phone == '' || user.phone == null">请绑定手机号</button>
					<text class="user-age" v-else>手机号：{{user.phone}}</text>
				</view>
			</view>
		</view>
		<view class="header-for-login" v-else>
			<button class="header-for-login-btn" @tap="getUserProfile" >登录注册</button>
			<!-- <button class="header-for-login-btn" @getuserinfo="getUserInfo" open-type="getUserInfo">登录注册</button> -->
		</view>

		<!-- body -->
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
					id: '',
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
					/* {
						id: 3,
						title: "个人设置",
						imageSrc: "../../static/icon/settings/settings.png",
						linkUrl: "./setting/setting"
					}, */
					{
						id: 3,
						title: "深入定制",
						imageSrc: "../../static/icon/settings/settings.png",
						linkUrl: "/pages/meals/customization/customization"
					},
					{
						id: 4,
						title: "我的收藏",
						imageSrc: "../../static/icon/settings/attention.png",
						linkUrl: "./collecting/collecting"
					},
					{
						id: 5,
						title: "意见反馈",
						imageSrc: "../../static/icon/settings/feedback.png",
						linkUrl: "./feedback/feedback"
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
			this.user.avatarUrl = uni.getStorageSync('avatarUrl');
			this.user.nickName = uni.getStorageSync('nickName');
			this.user.openid = uni.getStorageSync('openid');
			this.user.id = uni.getStorageSync('id');
			this.user.phone = uni.getStorageSync('phone');
			this.user.gender = uni.getStorageSync('gender');
			this.user.token = uni.getStorageSync('token');
			
		},
		methods: {
			getUserProfile(e) {
				let that = this;
				uni.getUserProfile({
					provider: 'weixin',
					desc: '获取你的昵称、头像、地区及性别',
					success: function(info) {
						that.user.avatarUrl = info.userInfo.avatarUrl;
						that.user.nickName = info.userInfo.nickName;
						uni.setStorageSync('avatarUrl', that.user.avatarUrl);
						uni.setStorageSync('nickName', that.user.nickName);
						uni.login({
							provider: 'weixin',
							success: (res) => {
								//这里需要做登录验证操作，如果用户存在直接登录，不存在就注册
								uni.request({
									url: that.reqAddress + '/wechatlogin',
									method: "POST",
									data: {
										"code": res.code
									},
									success: (res) => {
										// console.log(res);
										if (200 == res.data.code) {
											//var userClient = res.data.data.loginClientUserVo;
											that.saveUserInfo(res);
											uni.showToast({
												title: '授权成功',
												duration: 2000
											});
										} else {
											// 注册
											uni.login({
												provider: 'weixin',
												success: (res) => {
													// console.log(res);
													uni.request({
														url: that.reqAddress + '/wechatRegister',
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
															if (200 == res.data.code) {
																that.user.openid = res.data.data.openid;
																uni.setStorageSync('openid', that.user.openid); 
																uni.login({
																	provider: 'weixin',
																	success: function(loginRes) {
																		uni.request({
																			url:  that.reqAddress+'/wechatlogin',
																			method: "POST",
																			data: {
																				"code": loginRes.code
																			},
																			success: (res) => {
																				if (res.data.code == 200 && res.data.data) {
																					that.saveUserInfo(res);
																				} 
																			}
																		});
																	}
																});
																
																uni.showToast({
																	title: '注册成功，请绑定手机号',
																	duration: 2000,
																	icon: 'none'
																});
															}
														},
														fail: (res) => {
															uni.showToast({
																title: '注册失败，请重新注册',
																duration: 2000
															});
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
					fail: function(e) {
						uni.showToast({
							title: '获取信息失败',
							duration: 2000,
							icon: 'none'
						});
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
							url: that.reqAddress + '/wechatPhone',
							method: "POST",
							data: {
								"code": loginRes.code,
								"encryptedData": edata,
								"iv": iv
							},
							success: (res) => {
								if (res.data.code == 200) {
								/* 	that.user.phone = res.data.data.loginClientUserVo.phone;
									uni.setStorageSync('phone', that.user.phone); */
									that.saveUserInfo(res);
									uni.showToast({
										title: '绑定成功',
										duration: 2000,
										icon: 'none'
									});
								} else {
									uni.showToast({
										title: '获取手机号失败',
										duration: 2000,
										icon: 'none'
									});
								}
							}
						});
					}
				});
			},
			itemClick(e) {
				//console.log(this.user.id);
				//this.user.id = 1340247104929828865;
				if(this.user.id==null || this.user.id=="" ){
					uni.showToast({
						title: '请先登录',
						duration: 2000,
						icon: 'none'
					});
					return;
				};
				
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
			saveUserInfo(res){
				let userClient = res.data.data.loginClientUserVo;						
				this.user.nickName = userClient.nickname;
				this.user.avatarUrl = userClient.avatarurl;
				this.user.openid = userClient.openid;
				this.user.id = userClient.id;
				this.user.phone = userClient.phone;
				this.user.gender = userClient.gender;
				this.user.token = res.data.data.token;
				uni.setStorageSync('nickName', this.user.nickName);
				uni.setStorageSync('avatarUrl', this.user.avatarUrl);
				uni.setStorageSync('openid', this.user.openid);
				uni.setStorageSync('id', this.user.id);
				uni.setStorageSync('phone', this.user.phone);
				uni.setStorageSync('gender', this.user.gender);
				uni.setStorageSync('token', res.data.data.token);
			}
		}
	}
</script>

<style>
	@import url("./me.css");
</style>
