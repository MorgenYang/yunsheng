<template>
	<view class="content">
		<view>
			<image class="feedback-title-img"  src="../../../static/icon/settings/icon.png"></image>
			<text class="feedback-title">反馈信息</text>
		</view>
		<textarea class="feedback-border textarea" placeholder="请留下您的反馈内容"  placeholder-style='color:#ccc;font-size:13px'  maxlength="200" @input="limitNum" />
		<text class="text-num">{{inputLength}}/200字</text>
		
		<input class="feedback-border contact-input" placeholder="您的联系方式(手机号、微信号、邮箱......)" placeholder-class="hint-color" />
	
		<text class="feedback-hint top">欢迎为我们提出宝贵的意见和建议,您的</text>
		<text class="feedback-hint bottom" >每一次反馈是我们前进的动力!</text>
	
		<button class="btn">提交</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				inputLength:0
			}
		},
		methods: {
			limitNum(e){
				let num = e.detail.value.length;
				this.inputLength = num;
			}
		}
	}
</script>

<style>
	@import url("./feedback.css");
</style>
