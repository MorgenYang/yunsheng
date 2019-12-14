<template>
	<view class="content">
		<!-- #ifdef APP-PLUS -->
		<view class="titleNview-placing"></view>
		<!-- #endif -->
		<view class="header">
			<image class="user-img" :src="user.userImg"></image>
			<view class="user-info">
				<text class="user-name">{{user.userName}}</text>
				<view class="user-other">
					<text class="user-gender">{{user.userGender}}</text>
					<text class="user-age">{{user.userAge}}岁</text>
				</view>
			</view>
			<view class="user-load">
				<image class="arrows-right-user" src="../../static/icon/settings/arrows-right-user.png"></image>
			</view>
		</view>
		
		<view class="setting-body">
			<view v-for="(item,index) in setting" v-bind:key="index.id">
				<view class="setting-line" >
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
				title: 'me',
				user:{
					userName:"张伟",
					userGender: "男",
					userAge: 18,
					userImg:"http://img0.imgtn.bdimg.com/it/u=1787676231,2721036559&fm=26&gp=0.jpg"
				},
				setting:[
					{
						title:"健康档案",
						imageSrc:"../../static/icon/settings/health-record.png"
					},
					{
						title:"健康商城",
						imageSrc:"../../static/icon/settings/store.png"
					},
					{
						title:"私人医生",
						imageSrc:"../../static/icon/settings/doctor.png"
					},
					{
						title:"个人设置",
						imageSrc:"../../static/icon/settings/feedback.png"
					},
					{
						title:"意见反馈",
						imageSrc:"../../static/icon/settings/health-record.png"
					},
					{
						title:"检查更新",
						imageSrc:"../../static/icon/settings/update.png"
					},
					{
						title:"关于我们",
						imageSrc:"../../static/icon/settings/us.png"
					},
					{
						title:"联系我们",
						imageSrc:"../../static/icon/settings/connection.png"
					}					
				]	
			}
		},
		onLoad() {

		},
		methods: {
			
		}
	}
</script>

<style>
@import url("./me.css");
</style>
