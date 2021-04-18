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
		<swiper id="swiper" class="swiper-box" :duration="300" :current="tabCurrentIndex" @change="changeTab">
			<swiper-item v-for="tabItem in tabBars" :key="tabItem.id" class="week-swiper" @touchmove.stop="">
				<scroll-view class="panel-scroll-box" v-if="tabItem.id==1" :scroll-y="enableScroll">
					<view class="uni-margin-wrap">
						<swiper class="swiper" circular :indicator-dots="indicatorDots" :autoplay="autoplay" :interval="interval" :duration="duration">
							<swiper-item v-for="(item, index) in picList" :key="index">
								<image class="swiper-item" mode="scaleToFill" :src="item.url" @tap="itemDetail(item)"></image>
							</swiper-item>	
						</swiper>
					</view>
											
					<view class="item-food" >
						<view class="item-line" v-for="(menu, index1) in todayRecommendList" :key="index1">
							<view class="type-head">
								<image class="title-img"  src="../../static/icon/settings/icon.png"></image>
								<view class="type-title"> {{menu.tjlx}} </view>
							</view>
							<view v-if="menu.tjcp!=undefined && menu.tjcp.length>=0" v-for="(itemMenu, index2) in menu.tjcp" :key="index2" class="item news-item" 
								@tap="itemDetail(itemMenu)" hover-class="setting-click">
								<image class="img-list" mode="scaleToFill" :src="itemMenu.image"
									@error="onImageError(index1,index2)"></image>
								<view class="item-right">
									<view class="item-title">{{itemMenu.cpinfo.cpmc}}</view>
									<view class="item-tip item-level">
										<view class="star-title">推荐指数:</view>
										<image v-for="(level, index3) in itemMenu.tjxj" :key="index3" 
											src="../../static/icon/img/star.png" class="star-img">
										</image>
									</view>
									<view class="item-tip item-function">
										<text class="function-context">
											功效:<text class="function-context-text">{{itemMenu.cpinfo.jj}}</text>
										</text>
									</view>
								</view>
							</view>	
							<view v-if="menu.tjcp==undefined ">暂无数据</view>
						</view>
				   </view> 
				</scroll-view>
				
				<view class="week-scroll-box"  v-if="tabItem.id==2">
					<view class="food-head">
						<scroll-view scroll-x class="nav" scroll-with-animation :scroll-left="weekScrollLeft">
							<view class="cu-item" :class="index==0?'w-left':(index==weekList.length-1?'w-right':'')" v-for="(week, index) in weekList" :key="index" >
								<view class="date-size" :class="week.class" :data-index="index" :data-id="week.id" @tap="weekSelect">
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
					<swiper :current="weekCurrent"  @change="ontabchange" style="flex:1;">
						<swiper-item v-for="(week,weekIndex) in weekList" :key="weekIndex">
							<scroll-view scroll-y="true" style="height: 100%;">
								<view class="food-bottom">
								 <block v-for="(food,foodIndex) in (weeksFoodList[week.id-1]).recommendList" :key="foodIndex">
									<!-- <image mode="scaleToFill" class="week-inline-img" :src="food.image" :class="foodIndex>0?'week-inline-top':''"></image> -->
									<view class="type-head" style="margin-bottom: 6upx;">
										<image class="title-img" style="margin-left: 30upx;" src="../../static/icon/settings/icon.png"></image>
										<view class="type-title">{{food.cslxdesc}} </view>
									</view>
									<block v-for="(item,itemIndex) in food.dzcs">
										<!-- <uni-card class="card-item" isShadow="true" > -->
										<view class="cu-card ">
											<view class="cu-item shadow">
												<view class="item-bottom card">
													<image class="card-left-img" :src="item.cpinfo.image"></image>
													<view class="card-context">
														 <text class="card-title">{{item.cpinfo.cpmc}}</text>
														 <text class="card-des">{{item.cpinfo.jj}}</text>
													</view>
													<view class="item-bottom card-center">
														<image v-for="(level,levelIndex) in item.star"
															src="../../static/icon/img/star.png" class="star-img">
														</image>
													</view>
													<image src="../../static/icon/img/del-btn.png" class="del" @tap="delItem(weekIndex,foodIndex,itemIndex)"></image>
												</view>
											</view> 
										</view>
									</block>
								 </block>
								 </view>
							</scroll-view>
							<!-- <button hover-class='none' class="custom-btn" @tap="customization(week)">深入订制</button> -->
						</swiper-item>
					</swiper>			
				</view>		
			</swiper-item>
		</swiper>
	</view>
</template>

<script>

	let windowWidth = 0, scrollTimer = false, tabBar;
	let tabList = [{name: '今日推荐',id: '1',}, {name: '定制餐食',id: '2'}];
	
	let nowDate = new Date(); 
	var a = [7,1,2,3,4,5,6]; //new Date().getDay()==>0代表周日
	let weekIndex=1;
	let week = a[nowDate.getDay()] ;
	let weekScrollLeft_default;
	let $this;
	export default {
		components: {
			
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
				weekCurrent:weekIndex,
				TabCur:0,
				weekScrollLeft:weekScrollLeft_default,
				placeholderImage:'/static/icon/img/image-error.png',//占位图
				picList:[],
				todayRecommendList:[],
				weekList:[
					{"id":1,"chinaName":"周一","englishName":"Monday",class:"w1"},
					{"id":2,"chinaName":"周二","englishName":"Tuesday",class:"w2"},
					{"id":3,"chinaName":"周三","englishName":"Wednesday",class:"w3"},
					{"id":4,"chinaName":"周四","englishName":"Thursday",class:"w4"},
					{"id":5,"chinaName":"周五","englishName":"Friday",class:"w5"},
					{"id":6,"chinaName":"周六","englishName":"Saturday",class:"w6"},
					{"id":7,"chinaName":"周日","englishName":"Sunday",class:"w7"},
				],
				weeksFoodList:[
					{"recommendList":[]},
					{"recommendList":[]},
					{"recommendList":[]},
					{"recommendList":[]},
					{"recommendList":[]},
					{"recommendList":[]},
					{"recommendList":[]} 
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
			
		},
		onShow() {
			// 获取屏幕宽度
			windowWidth = uni.getSystemInfoSync().windowWidth;
			$this = this;
			this.loadTabbars();
			this.loadTodayRecommend();
			this.weekList[week-1].chinaName='今天';
			this.weekCurrent = week-1;
			this.weekScrollLeft = (week-1-1) *80;
			
			this.weekList.forEach(function(item, index) {
				var day = nowDate.getDate()+(index-$this.weekCurrent);
				let date = nowDate.getFullYear()+"-"+(nowDate.getMonth()+1)+'-'+day;
				item.date = date;							
			});
			//weekScrollLeft_default= (week-1-1) *80;
			//this.weekScrollLeft = (week-1-1) *80;
			
			let date = nowDate.getFullYear()+"-"+(nowDate.getMonth()+1)+'-'+nowDate.getDate();
			this.loadCustomizationData(date);
		},
		methods: {
			onImageError(index1,index2){
				console.log("图片显示失败,显示占位图");
				this.todayRecommendList[index1].tjcp[index2].image = this.placeholderImage;
			},
			itemDetail(itemMenu){
				console.log("============");
				console.log(itemMenu);
				uni.navigateTo({
					url: './foodDetails?id='+itemMenu.id
				});
			},
			customization(week){
				console.log(week.id);
				uni.navigateTo({
					url: `/pages/meals/customization/customization?`
				})
			},
			delItem(weekIndex,foodIndex,itemIndex){
				let weekContextIndex = this.weekList[weekIndex].id-1;
				var foodList =this.weeksFoodList[weekContextIndex].recommendList[foodIndex].foodList;
				foodList.splice(itemIndex,1)
			},
			ontabchange(e) {
			    let index = e.target.current || e.detail.current;
			    this.weekCurrent = index;
			    this.weekScrollLeft = (index-1) *80;			
			},
			weekSelect(e) {
				this.weekCurrent = e.currentTarget.dataset.index;
				if(this.weekCurrent>0){
					this.weekScrollLeft = (e.currentTarget.dataset.index-1) *80;
				}
				console.log(this.weekList[this.weekCurrent].date);
				this.loadCustomizationData(this.weekList[this.weekCurrent].date);
			},
			//加载tabbar,顶部导航栏
			loadTabbars(){
				tabList.forEach(item=>{
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
			loadTodayRecommend(){
				let url = $this.reqAddress+'/tjTjlx/getPageList';
				$this.$api.post(url).then((res)=>{
					let data = res.data;
					if(data.code==200 && data.data!=null && data.data.records!=null){
						let result = data.data.records;
						$this.todayRecommendList = result;
						result.forEach(function(item, index) {
							if(item.id){
								$this.loadTypeContent(item.id,index);
							}							
						});
					}
				}).catch((err)=>{
					console.log('request fail', err);
				})
			},
			loadTypeContent(id,index){
				let url = $this.reqAddress+'/tjSsgl/getSsglList/'+id;
				let domainName = $this.domainName;
				$this.$api.post(url).then((res)=>{
					let data = res.data;
					if(data.code==200 && data.data!=null){
						$this.todayRecommendList[index].tjcp = data.data;
						data.data.forEach(function(item, index) {
							if(item.cpfm){
								item.cpfm=item.cpfm.replace(/&quot;/g,"\"");
								let imageObj = JSON.parse(item.cpfm);
								if(imageObj.length>0){
									item.image = domainName+imageObj[0].url;	
									let imgBean = {id:item.id,url:item.image}
									if($this.picList.length<3){
										$this.picList.push(imgBean);
									}
								}
							}							
						});
						//注意*** 不自动刷新--需要强制刷新
						this.$forceUpdate();
					}
				}).catch((err)=>{
					console.log('request fail', err);
				})
			},
			//加载
			loadCustomizationData(dayDate){
				//清空历史数据
				/* this.weeksFoodList=[{"recommendList":[]},{"recommendList":[]},{"recommendList":[]},
				{"recommendList":[]},{"recommendList":[]},{"recommendList":[]},{"recommendList":[]}]; */
				if($this.weeksFoodList[$this.weekCurrent].recommendList.length>0){
					return;
				}
				let url = $this.reqAddress+'/tjDzcs/getPageList?dzrq='+dayDate;
				let domainName = $this.domainName;
				$this.$api.post(url).then((res)=>{
					let data = res.data;
					if(data.code==200 && data.data!=null){
						var result = data.data;
						result.forEach(function(item, index1) {
							item.dzcs.forEach(function(childItem, index2) {
								if(childItem.cpinfo.cpfm){
									childItem.cpinfo.cpfm=childItem.cpinfo.cpfm.replace(/&quot;/g,"\"");
									let imageObj = JSON.parse(childItem.cpinfo.cpfm);
									if(imageObj.length>0){
										childItem.cpinfo.image = domainName+imageObj[0].url;
									}
								}
							});								
						});
						$this.weeksFoodList[$this.weekCurrent].recommendList= result;
					}
				}).catch((err)=>{
					console.log('request fail', err);
				})
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
	/* height: 100%; */
	flex:1;
	background-color:#FFFFFF;
}

.panel-scroll-box{
	height: 100%;
	margin-bottom: 40upx;
	.panel-item{
		background: #fff;
		padding: 30px 0;
		border-bottom: 2px solid #000;
	}
}
.week-scroll-box{
	height: 100%;
	flex: 1;
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

