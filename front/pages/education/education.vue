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
			>{{item.wzlx}}</view>
		</scroll-view>
		
		<!-- 下拉刷新组件 -->
		<mix-pulldown-refresh ref="mixPulldownRefresh" class="panel-content" :top="90" @refresh="onPulldownReresh" @setEnableScroll="setEnableScroll">
			<!-- 内容部分 -->
			<swiper id="swiper" class="swiper-box" :duration="300" :current="tabCurrentIndex" @change="changeTab">
				<swiper-item v-for="tabItem in tabBars" :key="tabItem.id">
					<scroll-view class="panel-scroll-box" :scroll-y="enableScroll" @scrolltolower="loadMore">
						<view v-for="(item, index) in tabItem.list" :key="index" class="item news-item"
							@click="navToDetails(item)" hover-class="setting-click">						
						<!-- 	<image class="img-list" mode="aspectFill" :src="item.image"></image> -->
							<!--  -->
							<view class="item-title">{{item.wzbt}}</view>
							<view class="item-right">
								<view class="item-body">{{item.wzjj}}</view>
								<image class="img-list" v-if="item.wzfm!=null && item.wzfm!=''" 
									mode="aspectFill" :src="item.wzfm">
								</image>
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
	
	let $this;
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
				pageSize:10
			}
		},
		async onLoad() {
			// 获取屏幕宽度
			windowWidth = uni.getSystemInfoSync().windowWidth;
			$this =this;
			this.loadTabbars();
			
		},
		methods: {
			//加载tabbar,顶部导航栏
			loadTabbars(){
				let url = $this.reqAddress+'/wzlx/getPageList';
				var data = {
					"current": 1,
					"size": 1000,
					"orders": [
					  {
						"asc": false,
						"column":"xspx"
					  }
					]
				};
				$this.$api.post(url,data).then((res)=>{
					let data = res.data;
					if(data.code==200 && data.data!=null){
						this.tabBars = data.data.records;
						this.tabBars.forEach(item =>{
							item.list = [];
							item.loadMoreStatus = 0;  //加载更多 0加载前，1加载中，2没有更多了
							item.refreshing = 0;
							item.current = 1;
							item.size = $this.pageSize;		
						});
						this.loadDataList('add');					
					}
				}).catch((err)=>{
					console.log('request fail', err);
				})
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
					//注意*** 不自动刷新--需要强制刷新
					this.$forceUpdate();
				}
				// #ifdef APP-PLUS
				else if(type === 'refresh'){
					tabItem.refreshing = true;
				}
				// #endif
				if(type === 'refresh'){
					tabItem.current = 1;
				}
				setTimeout(()=>{
					//加载文章列表信息
					let url = $this.reqAddress+'/wzbaseinfo/getPageList';
					let domainName = this.domainName;
					var data={
					  "current": tabItem.current,
					  "keyword": "",
					  "orders": [
						{
						  "asc": false,
						  "column":"xssx"
						}
					  ],
					  "size": tabItem.size,
					  "wzlx":tabItem.id
					};
					$this.$api.post(url,data).then((res)=>{
						var data = res.data;
						if(data.code==200 && data.data!=null){
							tabItem.current++;
							if(type === 'refresh'){
								tabItem.list = []; //刷新前清空数组
							}
							let result = data.data.records;
							result.forEach(function(item, index) {
								if(item.wzfm){
									item.wzfm=item.wzfm.replace(/&quot;/g,"\"");
									let imageObj = JSON.parse(item.wzfm);
									if(imageObj.length>0){
										item.wzfm = domainName+imageObj[0].url;	
									}
								}							
							});
							tabItem.list = tabItem.list.concat(result);
							//tabItem.list.concat(data.data.records);
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
								tabItem.loadMoreStatus = tabItem.list.length == data.data.total ? 2: 0;
							}
							//注意*** 不自动刷新--需要强制刷新
							this.$forceUpdate();
						}
					}).catch((err)=>{
						console.log('request fail', err);
					})
				},500);
			},
			//详情页面
			navToDetails(item){
				let data = {
					id: item.id,
					fwbnrPC: item.fwbnrPC
				}
				var textObj = JSON.stringify(data)
				uni.navigateTo({
					//url: '/pages/education/detail?data='+encodeURIComponent(textObj),
					url: '/pages/education/detail?id='+item.id,
					animationType: 'slide-in-right',
					animationDuration: 200
				});
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
	background-color: #F1F1F1;
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
		/* width: 36upx; */
		height: 90upx;
		text-align: center;
		line-height: 90upx;
		padding-left: 30upx;
		padding-right: 30upx;
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
			width: 40%;
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
	
@import url("./education.css");
</style>
