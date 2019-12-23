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
			<view  v-for="(item,index) in setting"  hover-class="setting-click" v-bind:key="item.id" 
				 @tap="itemClick" v-bind:id="'id_'+item.id">
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
						id:0,
						title:"健康档案",
						imageSrc:"../../static/icon/settings/health-record.png",
						linkUrl:"./healthRecord/healthRecord"
					},
					{
						id:1,
						title:"健康商城",
						imageSrc:"../../static/icon/settings/store.png",
						linkUrl:""
					},
					{
						id:2,
						title:"私人医生",
						imageSrc:"../../static/icon/settings/doctor.png",
						linkUrl:"./doctor/doctor"
					},
					{
						id:3,
						title:"个人设置",
						imageSrc:"../../static/icon/settings/settings.png",
						linkUrl:"./setting/setting"
					},
					{
						id:4,
						title:"意见反馈",
						imageSrc:"../../static/icon/settings/feedback.png",
						linkUrl:"./feedback/feedback"
					},
					{
						id:5,
						title:"检查更新",
						imageSrc:"../../static/icon/settings/update.png",
						linkUrl:""
					},
					{
						id:6,
						title:"关于我们",
						imageSrc:"../../static/icon/settings/us.png",
						linkUrl:"./about/about"
					},
					{
						id:7,
						title:"联系我们",
						imageSrc:"../../static/icon/settings/connection.png",
						linkUrl:"./contact/contact"
					}					
				]	
			}
		},
		onLoad() {

		},
		methods: {
			itemClick(e){
				let id = e.currentTarget.id.toString().substr(3);
				uni.showToast({
				    title: this.setting[id].title,
					icon:"none",
				    duration: 300
				});
				if(this.setting[id].linkUrl !=""){
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
