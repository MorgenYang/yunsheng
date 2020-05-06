<template>
	<view class="content">
		<!-- 顶部选项卡 -->
		<scroll-view id="nav-bar" class="nav-bar" scroll-x scroll-with-animation>
			<view 
				v-for="(item,index) in tabBars" :key="item.id"
				class="nav-item"
				:class="{current: index === tabCurrentIndex}"
				:id="'tab'+index"
				@click="changeTab(index)"
			>{{item.name}}</view>
		</scroll-view>
		
	
	
			<!-- 内容部分 -->
		<swiper id="swiper" class="swiper-box " :duration="300" :current="tabCurrentIndex" @change="changeTab">
			<swiper-item v-for="tabItem in tabBars" :key="tabItem.id" class="week-swiper">
				<scroll-view class="panel-scroll-box" v-if="tabItem.id==1" :scroll-y="enableScroll" @scrolltolower="loadMore">
					<view class="uni-margin-wrap">
						<swiper class="swiper" circular :indicator-dots="indicatorDots" :autoplay="autoplay" :interval="interval" :duration="duration">
							<swiper-item v-for="(item, index) in picList">
								<image class="swiper-item" mode="scaleToFill" :src="item.url"></image>
							</swiper-item>	
						</swiper>
					</view>
											
					<view v-for="(menu, index) in todayRecommendList" :key="index">
						<view class="item-line">
							<view class="type-head">
								<image class="title-img"  src="../../static/icon/settings/icon.png"></image>
								<view class="type-title"> {{menu.title}} </view>
							</view>
							<view v-for="(itemMenu, index) in menu.menus" :key="index" class="item news-item">
								<image class="img-list" mode="scaleToFill" :src="itemMenu.image"></image>
								<view class="item-right">
									<view class="item-title">{{itemMenu.name}}</view>
									<view class="item-tip item-level">
										<view class="star-title">推荐指数:</view>
										<image v-for="(level, index) in itemMenu.level" :key="index" 
											src="../../static/icon/img/star.png" class="star-img">
										</image>
									</view>
									<view class="item-tip item-function">
										<text class="function-context">
											功效:<text class="function-context-text">{{itemMenu.function}}</text>
										</text>
									</view>
								</view>
							</view>	
						</view>
					</view>
				</scroll-view>
				
				<view class="week-scroll-box"  v-if="tabItem.id==2">
					<view class="food-head">
						<scroll-view scroll-x class="nav" scroll-with-animation :scroll-left="weekScrollLeft">
							<view class="cu-item" v-for="(week, index) in weekList"  >
								<view class="date-size"   :class="week.class" :data-id="week.id" @tap="weekSelect">
									<view class="date-col">
										<text>{{week.chinaName}}</text>
										<text class="w-enlish">{{week.englishName}}</text>
									</view>
								</view>	
								<image src="../../static/icon/img/arrows-down.png"  
									:class="weekCurrent==index?(0==index?'pointer-one':'pointer'):'pointer pointer-hiden'">
								</image>
							</view>
						</scroll-view>
					</view>
					<!-- 内容部分 <view class="item-line">-->
					<swiper :current="weekCurrent" style="height: 100%;" >
						<swiper-item v-for="week in weekList" :key="week.id" @touchmove.stop="">
							<scroll-view scroll-y="true" style="height: 100%;">
								 <block v-for="food in (weeksFoodList[week.id].recommendList)">
									<image mode="scaleToFill" class="week-inline-img" :src="food.image"></image>
									<block v-for="item in food.foodList">
										<uni-card class="card-item" isShadow="true">
											<view class="item-bottom">
												<image class="card-left-img" :src="item.image"></image>
												<view class="card-context">
													 <text class="card-title">{{item.name}}</text>
													 <text class="card-des">{{item.description}}</text>
												</view>
												<view class="item-bottom card-center">
													<image v-for="level in item.star" 
														src="../../static/icon/img/star.png" class="star-img">
													</image>
												</view>
											</view>
										</uni-card> 
									</block>
								 </block>
							</scroll-view>
					
						</swiper-item>
					</swiper>			
				</view>
						
			</swiper-item>
		</swiper>
	</view>
</template>

<script>
	import uniCard from '@/components/uni-card/uni-card.vue'
	
	let windowWidth = 0, scrollTimer = false, tabBar;
	let tabList = [{name: '今日推荐',id: '1',}, {name: '定制餐食',id: '2'}];
	
	var a = [6, 0, 1, 2, 3, 4, 5]; //日期转换0-原代表周日
	let week = a[new Date().getDay()] ;
	let weekScrollLeft_default = (week-1) *80;
	
	export default {
		components: {
			uniCard
		},
		data() {
			return {
				tabCurrentIndex: 0, //当前选项卡索引
				scrollLeft:0, //顶部选项卡左滑距离
				enableScroll: true,
				tabBars: [],
				indicatorDots: true,
				autoplay: true,
				interval: 2000,
				duration: 500,
				weekCurrent:week,
				TabCur:0,
				weekScrollLeft:weekScrollLeft_default,
				picList:[
					{"id":1,"title":"aaa","url":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg"},
					{"id":2,"title":"bbb","url":"https://thumbs.dreamstime.com/b/%E5%9C%A8%E9%87%8E%E9%A4%90%E6%A1%8C%E4%B8%8A%E7%9A%84%E7%83%A4-%E9%A3%9F%E8%86%B3%E9%A3%9F-55789502.jpg"},
					{"id":3,"title":"bbb","url":"https://thumbs.dreamstime.com/b/%E8%8F%A9%E8%90%A8%E7%A2%97%EF%BC%8C%E5%81%A5%E5%BA%B7%E5%92%8C%E5%B9%B3%E8%A1%A1%E7%9A%84-%E9%A3%9F-%E4%B9%89%E8%80%85%E8%86%B3%E9%A3%9F-87269881.jpg"},
				],
				todayRecommendList:[				
					{
						"title":"菜品推荐",
						"menus":[
							{
								"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
								"name":"豆角烧茄子",
								"level":4,
								"function":"豇豆具有理中益气、健胃补肾、和五脏、调颜养身、生精髓、止消渴的功效。茄子含有丰富的抗氧化成分，它能改善脸上的肌肤问题，同时还能阻止电脑辐射对肌肤的伤害"
							},
							{
								"image":"http://photocdn.sohu.com/20120214/Img334684297.jpg",
								"name":"红烧肉",
								"level":3,
								"function":"红烧肉能开胃消食，红烧肉能调节身体平衡度，红烧肉能补血"
							}
						]
					},
					{
						"title":"主食推荐",
						"menus":[
							{
								"image":"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2603886866,2595760544&fm=26&gp=0.jpg",
								"name":"八宝饭",
								"level":5,
								"function":"八宝饭里面含有丰富的糖类、枣酸、钙、磷、铁、维生素等物质，使得八宝饭具有补血、安心神和增强食欲的作用。平时消化不良的人可以通过进食八宝饭改善下肠胃"
							}
						]
					}
							
				],
				weekList:[
					{"id":0,"chinaName":"周一","englishName":"Monday",class:"w1"},
					{"id":1,"chinaName":"周二","englishName":"Tuesday",class:"w2"},
					{"id":2,"chinaName":"周三","englishName":"Wednesday",class:"w3"},
					{"id":3,"chinaName":"周四","englishName":"Thursday",class:"w4"},
					{"id":4,"chinaName":"周五","englishName":"Friday",class:"w5"},
					{"id":5,"chinaName":"周六","englishName":"Saturday",class:"w6"},
					{"id":6,"chinaName":"周日","englishName":"Sunday",class:"w7"},
				],
				weeksFoodList:[
				{
					"recommendList":
					[
						{
							"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
							"foodList":[
								{
									"name":"小米粥",
									"star":2,
									"description":"开肠胃、补虚损、易丹田",
									"image":"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=545335231,4052753289&fm=26&gp=0.jpg"
								},
								{
									"name":"鸡蛋",
									"star":4,
									"description":"富含高蛋白和大量的维生素",
									"image":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2754723133,3961104611&fm=26&gp=0.jpg"
								}
							]
						
						},
						{
							"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
							"foodList":[
								{
									"name":"小米粥",
									"star":2,
									"description":"开肠胃、补虚损、易丹田",
									"image":"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=545335231,4052753289&fm=26&gp=0.jpg"
								},
								{
									"name":"鸡蛋",
									"star":1,
									"description":"富含高蛋白和大量的维生素",
									"image":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2754723133,3961104611&fm=26&gp=0.jpg"
								}
							]	
						},
						{
		
							"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
							"foodList":[
								{
									"name":"小米粥",
									"star":2,
									"description":"开肠胃、补虚损、易丹田",
									"image":"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=545335231,4052753289&fm=26&gp=0.jpg"
								},
								{
									"name":"鸡蛋",
									"star":3,
									"description":"富含高蛋白和大量的维生素",
									"image":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2754723133,3961104611&fm=26&gp=0.jpg"
								}
							]
						
						}
					]
				},
					
					{
						"recommendList":
						[
								
							{
								"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
								"foodList":[
									{
										"name":"小米粥",
										"star":3,
										"description":"开肠胃、补虚损、易丹田",
										"image":"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=545335231,4052753289&fm=26&gp=0.jpg"
									}
								]
							},	
							{
								"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
								"foodList":[											
									{
										"name":"鸡蛋",
										"star":2,
										"description":"富含高蛋白和大量的维生素",
										"image":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2754723133,3961104611&fm=26&gp=0.jpg"
									}
								]
								
							},
								
							{
								"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
								"foodList":[
									{
										"name":"鸡蛋",
										"star":3,
										"description":"富含高蛋白和大量的维生素",
										"image":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2754723133,3961104611&fm=26&gp=0.jpg"
									}
								]
							}
								
						]
						
					},
					{
						"recommendList":[
							{
								"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
								"foodList":[
									{
										"name":"鸡蛋",
										"star":4,
										"description":"富含高蛋白和大量的维生素",
										"image":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2754723133,3961104611&fm=26&gp=0.jpg"
									}
								]
							
							},
							{
						
								"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
								"foodList":[											
									{
										"name":"小米粥",
										"star":3,
										"description":"开肠胃、补虚损、易丹田",
										"image":"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=545335231,4052753289&fm=26&gp=0.jpg"
									}
								]
								
							},
							{
							
								"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
								"foodList":[
									{
										"name":"鸡蛋",
										"star":5,
										"description":"富含高蛋白和大量的维生素",
										"image":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2754723133,3961104611&fm=26&gp=0.jpg"
									}
								]
							}			
						]
					},
					{
						"recommendList":
						[
									
							{
								"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
								"foodList":[
									{
										"name":"小米粥",
										"star":3,
										"description":"开肠胃、补虚损、易丹田",
										"image":"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=545335231,4052753289&fm=26&gp=0.jpg"
								
									}
								]
							},
						
							{
								"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
								"foodList":[											
									{
										"name":"鸡蛋",
										"star":4,
										"description":"富含高蛋白和大量的维生素",
										"image":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2754723133,3961104611&fm=26&gp=0.jpg"
									}
								]
							
							},
							{
							
								"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
								"foodList":[
									{
										"name":"小米粥",
										"star":3,
										"description":"开肠胃、补虚损、易丹田",
										"image":"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=545335231,4052753289&fm=26&gp=0.jpg"
									}
								]
							}							
						]
							
					},
					{
						"recommendList":
						[
									
							{
								"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
								"foodList":[
									{
										"name":"鸡蛋",
										"star":3,
										"description":"富含高蛋白和大量的维生素",
										"image":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2754723133,3961104611&fm=26&gp=0.jpg"
									}
								]
						
							},
							{
							
								"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
								"foodList":[											
									{
										"name":"小米粥",
										"star":2,
										"description":"开肠胃、补虚损、易丹田",
										"image":"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=545335231,4052753289&fm=26&gp=0.jpg"
									}
								]
						
							},
							{
							
								"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
								"foodList":[
									{
										"name":"鸡蛋",
										"star":4,
										"description":"富含高蛋白和大量的维生素",
										"image":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2754723133,3961104611&fm=26&gp=0.jpg"
									}
								]
							
							}
						]
							
					},
					{
						"recommendList":
						[
								
							{
								"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
								"foodList":[
									{
										"name":"小米粥",
										"star":3,
										"description":"开肠胃、补虚损、易丹田",
										"image":"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=545335231,4052753289&fm=26&gp=0.jpg"
									}
								]
							
							},
							{
							
								"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
								"foodList":[											
									{
										"name":"小米粥",
										"star":3,
										"description":"开肠胃、补虚损、易丹田",
										"image":"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=545335231,4052753289&fm=26&gp=0.jpg"
									}
								]
							
							},
							{
							
								"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
								"foodList":[
									{
										"name":"鸡蛋",
										"star":5,
										"description":"富含高蛋白和大量的维生素",
										"image":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2754723133,3961104611&fm=26&gp=0.jpg"
									}
								]
							}
						]	
					},
					{
						"recommendList":
						[			
							{
								"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
								"foodList":[
									{
										"name":"鸡蛋",
										"star":3,
										"description":"富含高蛋白和大量的维生素",
										"image":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2754723133,3961104611&fm=26&gp=0.jpg"
									}
								]
							},
							{
								"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
								"foodList":[											
									{
										"name":"鸡蛋",
										"star":2,
										"description":"富含高蛋白和大量的维生素",
										"image":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2754723133,3961104611&fm=26&gp=0.jpg"
									}
								]
							},
							{
								"image":"http://dpic.tiankong.com/sn/pi/QJ8841940448.jpg",
								"foodList":[
									{
										"name":"小米粥",
										"star":4,
										"description":"开肠胃、补虚损、易丹田",
										"image":"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=545335231,4052753289&fm=26&gp=0.jpg"
									}
								]
							}	
						]
							
					}	
				]
		
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
		onLoad() {
			// 获取屏幕宽度
			windowWidth = uni.getSystemInfoSync().windowWidth;
			this.loadTabbars();
			
		},
		methods: {
			weekSelect(e) {
				this.weekCurrent = e.currentTarget.dataset.id;
				if(this.weekCurrent>0){
					this.weekScrollLeft = (e.currentTarget.dataset.id-1) *80;
				}
			},
			//加载tabbar,顶部导航栏
			loadTabbars(){
				tabList.forEach(item=>{
					/* item.newsList = [];
					item.videoList = [];
					item.onlineClassList = []; */
					item.loadMoreStatus = 0;  //加载更多 0加载前，1加载中，2没有更多了
					item.refreshing = 0;
				})
				this.tabBars = tabList;
				this.loadDataList('add');
			},
			//加载数据
			loadDataList(type){
				let tabItem = this.tabBars[this.tabCurrentIndex];
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
.uni-margin-wrap {
	width:100%;
	margin:20 0upx;
}
.swiper {
	height: 400upx;
	margin-bottom:10upx;
}
.swiper-item {
	display: block;
	height: 400upx;
	width: 100%;
	margin-top: 20upx;
	line-height: 400upx;
	text-align: center;
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
		padding-left: 100upx;
		padding-right: 100upx;
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
	background-color:#FFFFFF;
}

.panel-scroll-box{
	height: 100%;
	.panel-item{
		background: #fff;
		padding: 30px 0;
		border-bottom: 2px solid #000;
	}
}
.week-scroll-box{
	height: 100%;
}
view{
	display:flex;
	flex-direction: column;
}	
.item-line{
	display:flex;
	flex-direction: column;
	margin: 20upx 20upx;
}	
@import url("./meals.css");
</style>

