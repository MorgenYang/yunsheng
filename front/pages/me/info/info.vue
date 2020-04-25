<template>
	<view>
		<view class="setting-body">
			<button @getuserinfo="getUserInfo" open-type="getUserInfo">获取微信信息</button>
			<button @getphonenumber="getPhoneNumber" open-type="getPhoneNumber">绑定手机号</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			getUserInfo(){
				console.log("getUserInfo");
				uni.getUserInfo({
					provider: 'weixin',
					success: function (infoRes) {
						uni.setStorageSync('avatarUrl', infoRes.userInfo.avatarUrl);
						uni.setStorageSync('gender', infoRes.userInfo.gender);
						uni.setStorageSync('nickName', infoRes.userInfo.nickName);
					},
					fail:function(e){
						console.log('获取用户信息失败');
					}
				});
			},
			getPhoneNumber(e){
				let that = this;
				var edata = e.detail.encryptedData;
				var iv = e.detail.iv;
				uni.login({
					provider: 'weixin',
					success: function(loginRes) {
						var logincode = loginRes.code;
						var openid = uni.getStorageSync('openid');
						uni.request({
							url: that.domainName + '/wechatPhone',
							method: "POST",
							data: {
								"code": logincode,
								"encryptedData": edata,
								"iv": iv,
								"openid": openid
							},
							success: (res) => {
								if (res.data.code == 200) {
									console.log(res.data);
									try {
										console.log(res.data.data.phoneNumber);
										uni.setStorageSync('phone', res.data.data.phoneNumber);
									} catch (e) {
										console.log(e);
									}
								}
							}
						});
					}
				});
			}
		}
	}
</script>

<style>
	.nickname{
		margin-left: 75%;
	}
	.logout{
		margin-top: 20upx;
		width: 60%;
		transform: scale(0.99);
		transform-origin: 0 0;
		background: #76D2F7;
		color: white;
		letter-spacing:30upx;
		text-indent: 30upx;
		font-size: 20px;
	}
	.line{
		background-color: #E4E4E4; 
		height: 2upx;
		/*解决:ios5不显示*/
		transform: scale(0.99);
		transform-origin: 0 0;
	}
	.setting-body{
		margin-top: 30upx;
		color: #A8A8A8;
		padding-left: 40upx;
		padding-right: 40upx;
		margin-bottom: 100upx;
		font-size: 16px;
	}
	.setting-line{
		display: flex;
		flex-flow: row;
		align-items:center;
		height: 100upx;
		margin-bottom: 2upx;
	}
	.user-img{
		height: 100upx;
		width: 100upx;
		border-radius:50%;
		border:6upx solid white;
		left: 70%;
		/* box-shadow:20upx 20upx 10upx #a2a2a3 ; */
	}
</style>
