<template>
	<view class="content">	
		<view class="week-scroll-box" >
			<view class="date-sel" >
				<view style="line-height:70upx;text-align: center;padding-left:30upx;">当前日期：{{weekDay}}</view>	
				<view style="width: 200upx;margin-left: 20upx;margin-top: 10upx;margin-bottom: 10upx;">
					<button size="mini"  @click="showDrawer" type="primary" style="background-color: #3AA9D1;">选择日期</button>
				</view>
			</view>
			
			<uni-drawer ref="showLeft" mode="left" :mask-click="true" width="160">
				<scroll-view style="height: 100%;" scroll-y="true" class="left-drawer">
					<view class="drawer-item w-left w-right" v-for="(week, index) in weekList" :key="index" >
						<view class="date-size" :class="week.class" :data-index="index" :data-id="week.id" @tap="weekSelect">
							<view class="date-col">
								<text>{{week.chinaName}}</text>
								<text class="w-enlish">{{week.englishName}}</text>
							</view>
						</view>	
						<image src="../../static/icon/settings/icon.png"  class="date-icon"
							:class="weekCurrent==index?(0==index?'pointer-one':'pointer'):'pointer pointer-hiden'">
						</image>
					</view>
				</scroll-view>
			</uni-drawer>		

			<!-- 内容部分 <view class="item-line">-->
			<swiper :current="weekCurrent" style="flex:1;" @change="ontabchange">
				<swiper-item v-for="(week,weekIndex) in weekList" :key="weekIndex">
					<scroll-view scroll-y="true" style="height: 100%;">
						<view class="food-bottom">
						 <block v-for="(food,foodIndex) in (weeksFoodList[week.id-1]).recommendList" 
								v-if="(weeksFoodList[week.id-1]).recommendList!=undefined"  :key="foodIndex">
							<!-- <image mode="scaleToFill" class="week-inline-img" :src="food.image" :class="foodIndex>0?'week-inline-top':''"></image> -->
							<view class="type-head" style="margin-bottom: 10upx;margin-top: 10upx;">
								<image class="title-img" style="margin-left: 30upx;" src="../../static/icon/settings/icon.png"></image>
								<view class="type-title">{{food.cslxdesc}} </view>
							</view>
							<block v-for="(item,itemIndex) in food.dzcs">
								<!-- <uni-card class="card-item" isShadow="true" > -->
								<view class="cu-card " v-if="item.cpinfo!=null"  :data-item="item.cpinfo.id"  @click="itemDetail">
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
											<!-- <image src="../../static/icon/img/del-btn.png" class="del" @tap="delItem(weekIndex,foodIndex,itemIndex)"></image> -->
										</view>
									</view> 
								</view>
							</block>
						 </block>
						 </view>
					</scroll-view>
				</swiper-item>
			</swiper>
		</view>
	</view>
</template>

<script>

	let windowWidth = 0, scrollTimer = false, tabBar;

	let nowDate = new Date(); 
	var a = [7,1,2,3,4,5,6]; //new Date().getDay()==>0代表周日
	let weekIndex=1;
	let week = a[nowDate.getDay()] ;
	let $this;
	export default {
		components: {
			
		},
		data() {
			return {
				scrollLeft:0, //顶部选项卡左滑距离
				enableScroll: true,
				weekCurrent:weekIndex,
				placeholderImage:'/static/icon/img/image-error.png',//占位图
				picList:[],
				weekDay:'',
				todayRecommendList:[],
				weekList:[
					{"id":1,"chinaName":"周一","chinaName1":"周一","englishName":"Monday",class:"w1"},
					{"id":2,"chinaName":"周二","chinaName1":"周二","englishName":"Tuesday",class:"w2"},
					{"id":3,"chinaName":"周三","chinaName1":"周三","englishName":"Wednesday",class:"w3"},
					{"id":4,"chinaName":"周四","chinaName1":"周四","englishName":"Thursday",class:"w4"},
					{"id":5,"chinaName":"周五","chinaName1":"周五","englishName":"Friday",class:"w5"},
					{"id":6,"chinaName":"周六","chinaName1":"周六","englishName":"Saturday",class:"w6"},
					{"id":7,"chinaName":"周日","chinaName1":"周日","englishName":"Sunday",class:"w7"},
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
			
		},
		onLoad() {
			
		},
		onShow() {
			// 获取屏幕宽度
			windowWidth = uni.getSystemInfoSync().windowWidth;
			$this = this;
			let weekToday = this.weekList[week-1].chinaName1;
			this.weekDay = weekToday;
			this.weekList[week-1].chinaName='今天';
			this.weekCurrent = week-1;
			//this.weekScrollLeft = (week-1-1) *80;
			
			var timesStamp = nowDate.getTime();
			var currenDay = nowDate.getDay();
			this.weekList.forEach(function(item, index) {
				let date = new Date(timesStamp + 24 * 60 * 60 * 1000 * (index - (currenDay + 6) % 7));
				let y = date.getFullYear();
				let MM = date.getMonth() + 1;
				MM = MM < 10 ? ('0' + MM) : MM;//月补0
				let d = date.getDate();
				d = d < 10 ? ('0' + d) : d;//天补0
				let h = date.getHours();
				h = h < 10 ? ('0' + h) : h;//小时补0
				let m = date.getMinutes();
				m = m < 10 ? ('0' + m) : m;//分钟补0
				let s = date.getSeconds();
				s = s < 10 ? ('0' + s) : s;//秒补0
				item.date = y + '-' + MM + '-' + d;
			});
			
			let date = nowDate.getFullYear()+"-"+(nowDate.getMonth()+1)+'-'+nowDate.getDate();
			this.loadCustomizationData(date);
		},
		methods: {
			showDrawer() {
				this.$refs.showLeft.open();
			},
			closeDrawer() {
				this.$refs.showLeft.close();
			},
			ontabchange(e) {
				let index = e.target.current || e.detail.current;
				this.weekCurrent = index;
				this.loadCustomizationData(this.weekList[this.weekCurrent].date);
			},
			onImageError(index1,index2){
				console.log("图片显示失败,显示占位图");
				this.todayRecommendList[index1].tjcp[index2].image = this.placeholderImage;
			},
			itemDetail(e){
				uni.navigateTo({
					url: './foodDetails?id='+e.currentTarget.dataset.item
				});
			},
		
			weekSelect(e) {
				
				this.$refs.showLeft.close();
				this.weekCurrent = e.currentTarget.dataset.index;
				/* if(this.weekCurrent>0){
					this.weekScrollLeft = (e.currentTarget.dataset.index-1) *80;
				} */
				//console.log(this.weekList[this.weekCurrent].date);
				this.loadCustomizationData(this.weekList[this.weekCurrent].date);
			},
			//加载
			loadCustomizationData(dayDate){
				//清空历史数据
				/* this.weeksFoodList=[{"recommendList":[]},{"recommendList":[]},{"recommendList":[]},
				{"recommendList":[]},{"recommendList":[]},{"recommendList":[]},{"recommendList":[]}]; */
				if($this.weeksFoodList[$this.weekCurrent].recommendList.length>0){
					//return;
					$this.weeksFoodList[$this.weekCurrent].recommendList=[];
				}
				uni.showLoading({ title:"加载中..."});
				let url = $this.reqAddress+'/tjDzcs/getPageList?dzrq='+dayDate;
				let domainName = $this.domainName;
				$this.$api.post(url).then((res)=>{
					let data = res.data;
					if(data.code==200 && data.data!=null){
						var result = data.data;
						this.weekDay = this.weekList[$this.weekCurrent].chinaName1;
						result.forEach(function(item, index1) {
							item.dzcs.forEach(function(childItem, index2) {
								if(childItem!=null && childItem.cpinfo!=null){
									if(childItem.cpinfo.cpfm!=null){
										childItem.cpinfo.cpfm=childItem.cpinfo.cpfm.replace(/&quot;/g,"\"");
										let imageObj = JSON.parse(childItem.cpinfo.cpfm);
										if(imageObj.length>0){
											childItem.cpinfo.image = domainName+imageObj[0].url;
										}else{
											childItem.cpinfo.image = "../../static/icon/img/image-error.png";
										}
									}
									if(childItem.cpinfo.jj==null){
										childItem.cpinfo.jj="暂无";
									}
								}
							});								
						});
						$this.weeksFoodList[$this.weekCurrent].recommendList= result;
					}
					setTimeout(function(){uni.hideLoading();},100);
				}).catch((err)=>{
					console.log('request fail', err);
					setTimeout(function(){uni.hideLoading();},100);
				})
			},
			//设置scroll-view是否允许滚动，在小程序里下拉刷新时避免列表可以滑动
			setEnableScroll(enable){
				if(this.enableScroll !== enable){
					this.enableScroll = enable;
				}
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
	background-color: #D0EDFB;
}
.left-drawer{
	margin-top: 20upx;
	display:flex;
	flex-direction: row;
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

