<template>
	<view class="content">
		<!-- 顶部选项卡 -->
		<scroll-view id="nav-bar" class="nav-bar" scroll-x scroll-with-animation :scroll-left="scrollLeft">
			<view 
				v-for="(item,index) in tabBars" :key="item.id"
				class="nav-item"
				:class="{current: index === tabCurrentIndex}"
				:id="'tab'+index"
				@click="changeTab(index)"
			>{{item.name}}</view>
		</scroll-view>
		
		<!-- 下拉刷新组件 -->
		<mix-pulldown-refresh ref="mixPulldownRefresh" class="panel-content" :top="90" @refresh="onPulldownReresh" @setEnableScroll="setEnableScroll">
			<!-- 内容部分 -->
			<swiper id="swiper" class="swiper-box" :duration="300" :current="tabCurrentIndex" @change="changeTab">
				<swiper-item v-for="tabItem in tabBars" :key="tabItem.id">
					<scroll-view class="panel-scroll-box" v-if="tabItem.id==1" :scroll-y="enableScroll" @scrolltolower="loadMore">
						<view v-for="(item, index) in tabItem.newsList" :key="index" class="item news-item" @click="navToDetails(item)">						
							<image class="img-list" mode="aspectFill" :src="item.image"></image>
							<view class="item-right">
								<view class="item-title">{{item.title}}</view>
								<view class="item-body">{{item.content}}</view>
								<view class="bot">
									<text class="author">{{item.author}}</text>
									<text class="time">{{item.time}}</text>
								</view>
							</view>
						</view>						
						<mix-load-more :status="tabItem.loadMoreStatus"></mix-load-more>
					</scroll-view>
					
					<scroll-view class="panel-scroll-box" v-if="tabItem.id==2" :scroll-y="enableScroll" @scrolltolower="loadMore">
						<view v-for="(item, index) in tabItem.videoList" :key="index" class="item-video" @tap="palyVideo(item)">
							<view class="video-head">
								<image class="title-img"  src="../../static/icon/settings/icon.png"></image>
								<view class="video-title"> {{item.title}} </view>
							</view>
							<image class="img-list-single" mode="aspectFill" :src="item.image"></image>
							<image class="play-btn" src="../../static/icon/settings/play.png"></image>
							<!-- <video :id="'video_'+index" class="img-list-single" ref="video" @play="playVideo"  @pause="pauseVideo($event,item)"
								src="http://baobab.kaiyanapp.com/api/v1/playUrl?vid=164016&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss"
								 poster="http://t8.baidu.com/it/u=2247852322,986532796&fm=79&app=86&f=JPEG?w=1280&h=853"></video> -->		
							</view>			
						<mix-load-more :status="tabItem.loadMoreStatus"></mix-load-more>
					</scroll-view>
					
					<scroll-view class="panel-scroll-box" v-if="tabItem.id==3" :scroll-y="enableScroll" @scrolltolower="loadMore">
						<view v-for="(item, index) in tabItem.onlineClassList" :key="index" class="item online-item" @click="navToDetails(item)">													
							<view class="online-head">
								<image class="photo" src="http://inews.gtimg.com/newsapp_bt/0/10901275116/1000.jpg"></image>
								<view class="online-head-mid">
									<view class="online-head-name">{{item.name}}</view>
									<text class="online-head-other">
										<text>{{item.time}}</text>
										<text class="onlie-head-space">|</text>
										<text style="color:#42A3C4">{{item.division}}</text>
									</text>
								</view>
								<view @tap="cancelAttention(item)" v-if="item.isAttention==0" class="online-head-attention">关注</view>
								<view @tap="attention(item)" v-if="item.isAttention!=0" class="online-head-attention-cancel">已关注</view>
							</view>
							<view class="online-title text-one-line">{{item.title}}</view>
							<view class="online-context text-one-line">{{item.content}}</view>
							<image class="online-video-image" mode="aspectFill" :src="item.image"></image>
							<image class="online-video-play-btn" src="../../static/icon/settings/play.png"></image>
						
							<view class="online-bottom">
								<view class="online-icon-layout">
									<image class="online-icon" src="../../static/icon/settings/look.png"></image>
									<text>{{item.lookCount}}</text>
								</view>
								<view class="online-icon-layout">
									<image class="online-icon" src="../../static/icon/settings/msg.png"></image>
									<text>{{item.msgCount}}</text>
								</view>
								<view class="online-icon-layout-right">
									<image class="online-icon" src="../../static/icon/settings/attention.png"></image>
									<text>{{item.attentionCount}}</text>
								</view>
							</view>
						</view>						
						<mix-load-more :status="tabItem.loadMoreStatus"></mix-load-more>
					</scroll-view> 
					
				</swiper-item>
			</swiper>
		</mix-pulldown-refresh>
		
	</view>
</template>

<script>
	import json from '@/json'
	import mixPulldownRefresh from '@/components/mix-pulldown-refresh/mix-pulldown-refresh';
	import mixLoadMore from '@/components/mix-load-more/mix-load-more';
	
	let windowWidth = 0, scrollTimer = false, tabBar;
	export default {
		components: {
			mixPulldownRefresh,
			mixLoadMore
		},
		data() {
			return {
				tabCurrentIndex: 0, //当前选项卡索引
				scrollLeft: 0, //顶部选项卡左滑距离
				enableScroll: true,
				tabBars: [],
				videoCur:0,
				video: null,
			}
		},
		
		computed: {
			advertNavUrl(){
				let data = {
					title: '测试跳转新闻详情',
					author: '测试作者',
					time: '2019-04-26 21:21'
				}
				return `/pages/details/details?data=${JSON.stringify(data)}`;
			} 
		},
		async onLoad() {
			// 获取屏幕宽度
			windowWidth = uni.getSystemInfoSync().windowWidth;
			this.loadTabbars();
		},
		methods: {
			//关注
			attention(item){
				item.isAttention = 0;
				item.attentionCount = item.attentionCount-1;
			},
			//取消关注
			cancelAttention(item){
				item.isAttention = 1;
				item.attentionCount = item.attentionCount+1;
			},
			//跳转页面播放视频
			palyVideo(item){
				var item = JSON.stringify(item); // 这里转换成 字符串
				//console.log(item);
				uni.navigateTo({
				    url: './videoDetail?item='+encodeURIComponent(item)
				}) 
			},
			//加载tabbar,顶部导航栏
			loadTabbars(){
				let tabList = json.tabList;
				tabList.forEach(item=>{
					item.newsList = [];
					item.videoList = [];
					item.onlineClassList = [];
					item.loadMoreStatus = 0;  //加载更多 0加载前，1加载中，2没有更多了
					item.refreshing = 0;
				})
				this.tabBars = tabList;
				this.loadDataList('add');
			},
			//加载数据
			loadDataList(type){
				let tabItem = this.tabBars[this.tabCurrentIndex];
				
				//type add 加载更多 refresh下拉刷新
				if(type === 'add'){
					if(tabItem.loadMoreStatus === 2){
						return;
					}
					tabItem.loadMoreStatus = 1;
				}
				// #ifdef APP-PLUS
				else if(type === 'refresh'){
					tabItem.refreshing = true;
				}
				// #endif
				
				//setTimeout模拟异步请求数据
				setTimeout(()=>{
					let newsList = [];
					let videoList = [];
					let onlineClassList = [];
					console.log(tabItem);
					if(tabItem.id==1){
						newsList = json.newsList;
						newsList.sort((a,b)=>{
							return Math.random() > .5 ? -1 : 1; //静态数据打乱顺序
						})
						if(type === 'refresh'){
							tabItem.newsList = []; //刷新前清空数组
						}
						newsList.forEach(item=>{
							item.id = parseInt(Math.random() * 10000);
							tabItem.newsList.push(item);
						})
						//下拉刷新 关闭刷新动画
						if(type === 'refresh'){
							this.$refs.mixPulldownRefresh && this.$refs.mixPulldownRefresh.endPulldownRefresh();
							// #ifdef APP-PLUS
							tabItem.refreshing = false;
							// #endif
							tabItem.loadMoreStatus = 0;
						}
						//上滑加载 处理状态
						if(type === 'add'){
							tabItem.loadMoreStatus = tabItem.newsList.length > 40 ? 2: 0;
						}
					}else if(tabItem.id==2){
						videoList = json.videoList;
						videoList.sort((a,b)=>{
							return Math.random() > .5 ? -1 : 1; //静态数据打乱顺序
						}) 
						if(type === 'refresh'){
							tabItem.videoList = []; //刷新前清空数组
							this.video = 0;
						}
						videoList.forEach(item=>{
							item.id = parseInt(Math.random() * 10000);
							tabItem.videoList.push(item);
						}) 
						//下拉刷新 关闭刷新动画
						if(type === 'refresh'){
							this.$refs.mixPulldownRefresh && this.$refs.mixPulldownRefresh.endPulldownRefresh();
							// #ifdef APP-PLUS
							tabItem.refreshing = false;
							// #endif
							tabItem.loadMoreStatus = 0;
						}
						//上滑加载 处理状态
						if(type === 'add'){
							tabItem.loadMoreStatus = tabItem.videoList.length > 40 ? 2: 0;
						}
					}else{
						onlineClassList = json.onlineClassList;
						onlineClassList.sort((a,b)=>{
							return Math.random() > .5 ? -1 : 1; //静态数据打乱顺序
						})
						if(type === 'refresh'){
							tabItem.onlineClassList = []; //刷新前清空数组
						}
						onlineClassList.forEach(item=>{
							item.id = parseInt(Math.random() * 10000);
							tabItem.onlineClassList.push(item);
						})
						//下拉刷新 关闭刷新动画
						if(type === 'refresh'){
							this.$refs.mixPulldownRefresh && this.$refs.mixPulldownRefresh.endPulldownRefresh();
							// #ifdef APP-PLUS
							tabItem.refreshing = false;
							// #endif
							tabItem.loadMoreStatus = 0;
						}
						//上滑加载 处理状态
						if(type === 'add'){
							tabItem.loadMoreStatus = tabItem.onlineClassList.length > 40 ? 2: 0;
						}
					}
				}, 600)
			},
			//新闻详情
			navToDetails(item){
				let data = {
					id: item.id,
					title: item.title,
					author: item.author,
					time: item.time
				}
				let url = item.videoSrc ? 'videoDetails' : 'details'; 

				uni.navigateTo({
					url: `/pages/details/${url}?data=${JSON.stringify(data)}`
				})
			},
			
			//下拉刷新
			onPulldownReresh(){
				this.loadDataList('refresh');
			},
			//上滑加载
			loadMore(){
				this.loadDataList('add');
			},
			//设置scroll-view是否允许滚动，在小程序里下拉刷新时避免列表可以滑动
			setEnableScroll(enable){
				if(this.enableScroll !== enable){
					this.enableScroll = enable;
				}
			},

			//tab切换
			async changeTab(e){
				if(scrollTimer){
					//多次切换只执行最后一次
					clearTimeout(scrollTimer);
					scrollTimer = false;
				}
				let index = e;
				//e=number为点击切换，e=object为swiper滑动切换
				if(typeof e === 'object'){
					index = e.detail.current
				}
				if(typeof tabBar !== 'object'){
					tabBar = await this.getElSize("nav-bar")
				}
				//计算宽度相关
				let tabBarScrollLeft = tabBar.scrollLeft;
				let width = 0; 
				let nowWidth = 0;
				//获取可滑动总宽度
				for (let i = 0; i <= index; i++) {
					let result = await this.getElSize('tab' + i);
					width += result.width;
					if(i === index){
						nowWidth = result.width;
					}
				}
				if(typeof e === 'number'){
					//点击切换时先切换再滚动tabbar，避免同时切换视觉错位
					this.tabCurrentIndex = index; 
				}
				//延迟300ms,等待swiper动画结束再修改tabbar
				scrollTimer = setTimeout(()=>{
					if (width - nowWidth/2 > windowWidth / 2) {
						//如果当前项越过中心点，将其放在屏幕中心
						this.scrollLeft = width - nowWidth/2 - windowWidth / 2;
					}else{
						this.scrollLeft = 0;
					}
					if(typeof e === 'object'){
						this.tabCurrentIndex = index; 
					}
					this.tabCurrentIndex = index; 
					//第一次切换tab，动画结束后需要加载数据
					let tabItem = this.tabBars[this.tabCurrentIndex];
					if(this.tabCurrentIndex !== 0 && tabItem.loaded !== true){
						this.loadDataList('add');
						tabItem.loaded = true;
					}
				}, 300)
				
			},
			//获得元素的size
			getElSize(id) { 
				return new Promise((res, rej) => {
					let el = uni.createSelectorQuery().select('#' + id);
					el.fields({
						size: true,
						scrollOffset: true,
						rect: true
					}, (data) => {
						res(data);
					}).exec();
				});
			},
		}
	}
</script>

<style lang='scss'>
page, .content{
	background-color: #f8f8f8;
	height: 100%;
	overflow: hidden;
}

/* 顶部tabbar */
.nav-bar{
	position: relative;
	z-index: 10;
	height: 90upx;
	text-align: center;
	white-space: nowrap;
	box-shadow: 0 2upx 8upx rgba(0,0,0,.06);
	background-color: #fff;
	.nav-item{
		display: inline-block;
		width: 150upx;
		height: 90upx;
		text-align: center;
		line-height: 90upx;
		padding-left: 45upx;
		padding-right: 45upx;
		font-size: 30upx;
		color: #303133;
		position: relative;
		&:after{
			content: '';
			width: 0;
			height: 0;
			border-bottom: 4upx solid #007aff;
			position: absolute;
			left: 50%;
			bottom: 0;
			transform: translateX(-50%);
			transition: .3s;
		}
	}
	.current{
		color: #007aff;
		&:after{
			width: 50%;
		}
	}
}

.swiper-box{
	height: 100%;
}

.panel-scroll-box{
	height: 100%;
	.panel-item{
		background: #fff;
		padding: 30px 0;
		border-bottom: 2px solid #000;
	}
}

view{
	display:flex;
	flex-direction: column;
}	
	
@import url("./medical.css");
</style>
