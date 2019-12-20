<template>
	<view>
		<view class="setting-body">
			<view hover-class="setting-click" >
				<view class="setting-line" >
					<image class="setting-img" src="../../../static/icon/settings/feedback.png"></image>
					<text class="setting-text">电子病历上传</text>
					<image class="arrows-right-setting" src="../../../static/icon/settings/arrows-right-setting.png"></image>
				</view>
				<view class="line"></view>
			</view>
			<view hover-class="setting-click" >
				<view class="setting-line" >
					<image class="setting-img" src="../../../static/icon/tabbar/tabbar/medical.png"></image>
					<text class="setting-text">手动录入个人档案</text>
					<image class="arrows-right-setting" src="../../../static/icon/settings/arrows-right-setting.png"></image>
				</view>
			</view>
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
			
		}
	}
</script>

<style>
.setting-body{
	margin-top: 30upx;
	color: #A8A8A8;
	padding-left: 40upx;
	padding-right: 40upx;
	margin-bottom: 100upx;
	font-size: 16px;
}
.setting-img{
	width: 40upx;
	height: 40upx;
}
.setting-line{
	display: flex;
	flex-flow: row;
	align-items:center;
	height: 100upx;
	margin-bottom: 2upx;
}
.setting-text{
	margin-left: 30upx;
}
.line{
	background-color: #E4E4E4; 
	height: 2upx;
	/*解决:ios5不显示*/
	transform: scale(0.99);
	transform-origin: 0 0;
}
.arrows-right-setting{
	width: 50upx;
	height: 50upx;
	position:absolute;
	right:40upx;
}
.setting-click{
	background-color:#F5F5F5;
}
</style>
