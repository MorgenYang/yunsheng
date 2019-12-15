<template>
	<view>
		<view class="setting-body">
			<view  v-for="(item,index) in setting"  hover-class="setting-click" v-bind:key="item.id" 
				 @tap="itemClick" v-bind:id="'id_'+item.id">
				<view class="setting-line" >
					<text class="setting-text">{{item.title}}</text>
					<switch name="recive" v-if="item.id==0" class="setting-switch" :checked="item.onoff"></switch>
					<text v-if="item.id==3" class="setting-cache">22M</text>
					<image v-if="item.id == 2" class="arrows-right-setting" src="../../../static/icon/settings/arrows-right-setting.png"></image>
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
				setting:[
						{
							id:0,
							title:"新消息通知",
							info:"接收消息",
							onoff:true
						},
						{
							id:1,
							title:"图片加载",
						},
						{
							id:2,
							title:"修改密码",
							linkUrl:"../changepwd/changepwd"
						},
						{
							id:3,
							title:"清除缓存",
							info:"清除成功"
						},
						{
							id:4,
							title:"退出登录",
						}				
					]	
				}
		},
		onLoad() {
			
		},
		methods: {
			itemClick(e){
				let id = e.currentTarget.id.toString().substr(3);
				if (id == 0 || id == 3) {
					if (id == 0) {
						
					}
					uni.showToast({
						title: this.setting[id].info,
						icon:"none",
						duration: 500
					});
				}
				if (id == 2) {
					uni.navigateTo({
					    url: this.setting[id].linkUrl
					});
				}
			}
		}
	}
</script>

<style>
@import url("setting.css");
</style>
