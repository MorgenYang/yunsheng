<template>
	<view class="page">
		<view class="header">
			demo
			
		</view>
		<view>
			<view>个人信息</view>
			<scroll-view class="scroll-view-x" scroll-x="true">
				<view class="scroll-view-item-x red">red</view>
				<view class="scroll-view-item-x green">green</view>
				<view class="scroll-view-item-x blue">blue</view>
			</scroll-view>
		</view>
		
		<view>
			<view>设置</view>
			<view>
				<image class="personal-settings" src="../../static/icon/settings/settings.png"></image>
				<text>个人设置</text>
			</view>
			<view>
				<image class="personal-settings" src="../../static/icon/settings/feedback.png"></image>
				<text>意见反馈</text>
			</view>
			<view>
				<image class="personal-settings" src="../../static/icon/settings/update.png"></image>
				<text>检查更新</text>
			</view>
			<view>
				<image class="personal-settings" src="../../static/icon/settings/us.png"></image>
				<text>关于我们</text>
			</view>
			<view>
				<image class="personal-settings" src="../../static/icon/settings/connection.png"></image>
				<text>联系我们</text>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title: 'me'
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
