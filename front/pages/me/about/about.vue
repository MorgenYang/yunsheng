<template>
	<view class="content">
		<image class="img" src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1577015160967&di=e05e5db70f325d30f7567e43058e80f6&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn%2Fw1000h1000%2F20180202%2F2ee3-fyrcsrx0017170.jpg"></image>
		<view class="all-intro">
			<image class="feedback-title-img"  src="../../../static/icon/settings/icon.png"></image>
			<text class="feedback-title">云生健康管理(深圳)有限公司</text>
			<text class="intro">
				云生健康管理（深圳）有限公司云生健康管理（深圳）有限公司（简称云生健康）于 2018年9月成立于深圳市，是一家致力于构建线上医疗及健康大数据储存及交易的技术型企业。公司结合医疗大数据及AI人工智能技术搭建起完善的健康管理平台“健康云”。结合国内外临床应用经验与场景应用，以再生医学、精准医学、临床医学、中医学、心理学、运动医学、基因组学、功能医学、营养学和食品科学为理论基础；使用专家系统、人工智能和大数据等技术手段，融合打造三位一体的“健康云”平台。为客户提供从短期辅助治疗过渡到长期医疗保健的全程健康管理服务及支持，整合全领域资源推动医养项目落地实施。
			</text>
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
	.content{
		padding-top: 40upx;
		margin: 0 40upx 0 40upx;
	}
	.img{
		width: 100%;
		height: 450upx;
	}
	.all-intro{
		margin-top: 20upx;
	}
	.feedback-title{
		font-size: 14px;
		color:#A2DEFA;
		letter-spacing:2upx;
		
	}
	.feedback-title-img{
		width: 30upx;
		height: 30upx;
		margin-right: 10upx;
	}
	.intro{
		display: flex;
		flex-flow: row;
		justify-content: center;
		color: #808080;
		font-size:12px ;
		text-indent:1.5em;
		letter-spacing:2upx;
		margin-top: 20upx;
		line-height: 1.5em;
		padding-bottom: 40upx;
	}
</style>
