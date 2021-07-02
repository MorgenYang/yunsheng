<template>
	<view>
		<u-navbar :is-back="false" :is-fixed="true" :background="background" :z-index="100">
			<view class="slot-wrap" v-if="useSlot">
				<view class="search-wrap" v-if="search">
					<!-- 如果使用u-search组件，必须要给v-model绑定一个变量 -->
					<u-search placeholder="搜标题/标签/内容" v-model="keyword" disabled="true" :show-action="showAction" height="56" @click="searchFun" :action-style="{color: '#fff'}"></u-search>
				</view>
				<view class="navbar-right" v-if="rightSlot">
					<view class="message-box right-item">
						
					</view>
					<view class="dot-box right-item">
						
					</view>
				</view>
			</view>
			<view class="navbar-right" slot="right" v-if="slotRight">
				<view class="message-box right-item">
					
				</view>
				<view class="dot-box right-item">
					
				</view>
			</view>
		</u-navbar>
		<swiper class="swiper" circular :indicator-dots="indicatorDots" :autoplay="autoplay" :interval="interval" :duration="duration">
			<swiper-item v-for="(item, index) in picList" :key="index">
				<image class="swiper-item" mode="scaleToFill" :src="item.url"></image>
			</swiper-item>
		</swiper>
		<view class="home-menu m30">
			<view class="item" @tap="todayRecommend()">
				<image src="../../static/icon/img/tuijian.png"></image>
				<text>今日推荐</text>
			</view>
			<view class="item">
				<image src="../../static/icon/img/shipu.png"></image>
				<text>一日食谱</text>
			</view>
			<view class="item">
				<image src="../../static/icon/img/dingzhi.png"></image>
				<text>深入定制</text>
			</view>	
		</view>
		<view class="m30">
			<view class="service">热门服务</view>
			<view class="service-item">
				<view class="m-right30 service-item-card">
					<view class="title">
						<view class="main-title">会议报道</view>
						<view class="sub-title">最新行业咨询</view>
					</view>
					<!-- <image src="https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1320246657,167452742&fm=15&gp=0.jpg"></image> -->
				</view>
				<view class="m-right30 service-item-card">
					<view class="title">
						<view class="main-title">在线课堂</view>
						<view class="sub-title">名师辅导</view>
					</view>
					<image src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1605437234618&di=2ca171ced296b9f892575cfb836fdca6&imgtype=0&src=http%3A%2F%2F2.pic.pc6.com%2Fup%2F2016-1%2F2016113162434.png"></image>
				</view>
			</view>
		</view>
		
	</view>
</template>

<script>
	let $this;
	
	export default {
		data() {
			return {
				right: false,
				showAction: false,
				rightSlot: false,
				useSlot: true,
				background: {
					/* 'background-image': 'linear-gradient(45deg, rgb(28, 187, 180), rgb(141, 198, 63))' */
					'background-color':'#3AA9D1'
				},
				isBack: false,
				search: true,
				custom: false,
				keyword: '',
				// #ifdef MP
				slotRight: false,
				// #endif
				// #ifndef MP
				slotRight: true,
				// #endif
				keyword: '',
				user: {},
				indicatorDots: true,
				autoplay: true,
				interval: 2000,
				duration: 500,
				picList:[
					{"id":1,"title":"bbb","url":"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic19.nipic.com%2F20120225%2F2489200_110712203129_2.jpg&refer=http%3A%2F%2Fpic19.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1622035681&t=79a0c4c8cb2d371dba9fabdbdb9d15f2"},
					{"id":2,"title":"aaa","url":"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg000.hc360.cn%2Fy5%2FM05%2F29%2FB9%2FwKhQUVXURPKEcDiDAAAAADmjueY110.jpg&refer=http%3A%2F%2Fimg000.hc360.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1622036024&t=03d57aea72a5845681c8a51957558474"},
					{"id":3,"title":"bbb","url":"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fspider.ws.126.net%2F995fa76117487b49e6590059aa91365a.jpeg&refer=http%3A%2F%2Fspider.ws.126.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1622035755&t=639d9aa135795d4fc77a1973b90208ae"},
				],
			}
		},
		onLoad() {
			$this = this;
		},
		methods: {
			todayRecommend(){
				uni.navigateTo({
					url: '../meals/todayRecommend/todayRecommend'
				});
			},
			searchFun(){
				uni.navigateTo({
					url:'./historySearch'
				});
			}
		}
	}
</script>

<style>
	.m30{
		margin-left: 30upx;
		margin-right: 30upx;
	}
	.m-right30{
		margin-right: 30upx;
	}
	.search{
		margin-top: 20upx;
		margin-bottom: 20upx;
	}
	.search > input{
		border: 2upx solid #000000;
		height: 60upx;
	
	}
	.swiper {
		height: 340upx;
		margin-bottom:10upx;
	}
	.swiper-item {
		display: block;
		height: 340upx;
		width: 100%;
		line-height: 340upx;
		text-align: center;
	}
	.home-menu{
		display:flex;
		flex-wrap:wrap;
		justify-content:space-between;
		display: -webkit-flex; /* Safari */
		margin-top: 20upx;
	}
	.home-menu>.item{
		flex: 1;
		height:200upx;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}
	.home-menu>.item >image{
		height: 110upx;
		width: 110upx;
	}
	.home-menu>.item >text{
		padding-top: 10upx;
		font-size: 20upx;
	}
	.service{
		margin-top: 40upx;
		margin-bottom: 40upx;
	}
	.service-item{
		display: flex;
		flex-direction: row;
		justify-content: center;
	}
	.service-item > view{
		height: 170upx;
		width: 334upx;
		background:#6FB9F6;
		border-radius: 16upx;
	}
	.service-item-card{
		display: flex;
		flex-direction: row;
		align-items: center;
	}
	.service-item-card > image{
		width: 130upx;
		height:130upx ;
	}
	.title{
		width:176upx;
	}
	.title > view{
		color: #FFFFFF;
		padding-left: 10upx;
	}
	.main-title{
		font-size: 16px;
	}
	.sub-title{
		font-size: 12px;
	}
	
	
	.navbar-right {
		margin-right: 24rpx;
		display: flex;
	}
	
	.search-wrap {
		margin: 0 20rpx;
		flex: 1;
	}
	
	.right-item {
		margin: 0 0rpx;
		position: relative;
		color: #ffffff;
		display: flex;
	}
	
	.slot-wrap {
		margin-left: 20upx;
		display: flex;
		align-items: center;
		flex: 1;
	}
	
</style>
