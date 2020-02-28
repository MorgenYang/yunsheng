<template>
	<view class="content">
		<text>{{address}}</text>
		<text>{{phone}}</text>
		<text>{{email}}</text>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				address:"地址：深圳市福田区梅林街道梅河社区梅星路5号梅林二村16栋302",
				phone:"联系电话：159-8951-5659 (赵先生）",
				email:"邮箱：xushengzhao2005@126.com"
			}
		},
		methods: {
			
		}
	}
</script>

<style>
	.content{
		display: flex;
		flex-flow: column;
		padding-top: 300upx;
		padding-left: 40upx;
		padding-right: 40upx;
		color: #8B8B8B;
		font-size: 13px;
		text-align:center
	}
	text{
		padding-top: 14upx;
	
	}
</style>
