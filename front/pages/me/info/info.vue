<template>
	<view>
		<view class="setting-body">
			<view >
				<view @tap="selectHead" class="setting-line" >
					<text>头像</text>
					<image class="user-img" :src="user.userImg"></image>
				</view>
				<view class="line"></view>
				<view @tap="modifyNickname" class="setting-line" >
					<text>昵称</text>
					<text class="nickname">{{user.nickname}}</text>
				</view>
				<view class="line"></view>
				<!-- <view class="setting-line">
					<text>出生日期</text>
					<text class="birth"></text>
				</view>
				<view class="line"></view> -->
			</view>
			<button @click="logout" class="logout" type="default">退出登录</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user: {
					nickname:"morgen",
					birthday:"",
					userImg: "http://img0.imgtn.bdimg.com/it/u=1787676231,2721036559&fm=26&gp=0.jpg"
				}
			}
		},
		methods: {
			showMsg(msg) {
				uni.showToast({
					title: msg,
					icon: "none",
					duration: 1000
				});
			},
			selectHead(){
				var uImg = "";
				uni.chooseImage({
				    count: 1,
				    sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
				    sourceType: ['album'], //从相册选择
				    success: function (res) {
						uImg = res.tempFilePaths[0];
						
				    }
				});
				this.showMsg(uImg + "morge")
			},
			modifyNickname(){
				
			},
			logout(){
				var flag = false;
				var token = "";
				uni.getStorage({
				    key: 'ys_user_token',
				    success: function (res) {
						token = res.data;
				    }
				});
				uni.request({
				    url: 'http://120.24.53.84/ysapi/logout',
					method:"POST",
					header: {
						"token": token
					},
				    success: (res) => {
						if (res.data.code==200) {
							flag = true;
							this.showMsg(res.data.msg);
						}
				    }
				});
				
				uni.navigateTo({
				    url: "../login/login"
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
