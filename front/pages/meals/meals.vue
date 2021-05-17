<template>
	<view class="content">	
		<view class="week-scroll-box" >
			<view class="date-sel nav-bar" >
				<view style="height: 100%;text-align: center;padding-left:30upx;padding-top: 12upx;">当前日期：{{weekDay}}</view>	
				<!-- <view style="width: 200upx;margin-left: 20upx;margin-top: 10upx;margin-bottom: 10upx;">
					<button size="mini"  @click="showDrawer" type="primary" style="background-color: #3AA9D1;">选择日期</button>
				</view> -->
			</view>
			
			<uni-drawer ref="showLeft" mode="left" :mask-click="true" :width="160">
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

			<!-- 下拉刷新组件 -->
			<mix-pulldown-refresh ref="mixPulldownRefresh" class="panel-content" :top="90" @refresh="onPulldownReresh" @setEnableScroll="setEnableScroll">
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
									<view class="cu-card " v-if="item.cpinfo!=null"  :data-item="item"  @click="itemDetail">
										<view class="cu-item shadow">
											<view class="item-bottom card">
												<image class="card-left-img" :src="item.cpinfo.image"></image>
												<view class="card-context">
													 <text class="card-title">{{item.cpinfo.cpmc}}</text>
													 <text class="card-des">{{item.cpinfo.jj}}</text>
												</view>
												<!-- <view class="item-bottom card-center">
													<image v-for="(level,levelIndex) in item.star"
														src="../../static/icon/img/star.png" class="star-img">
													</image>
												</view> -->
												<button size="mini" :data-item="item" @click.stop="updateMenu">更换</button>
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
			</mix-pulldown-refresh>
		</view>
		<movable-area class="movableArea">
			<movable-view class="movableView" direction="all" x="600rpx" y="800rpx">
				<image src="../../static/icon/img/date-icon.png" mode="widthFix" @click="showDrawer"></image>
			</movable-view>
		</movable-area>
		<uni-popup ref="popup" type="bottom" :animation="isShowAnimation" :mask-click="false">
			<view style="background-color:#F5F5F5;">
				<view class="select-bar bg-white">
					<view class="action text-blue" @tap="changeCancelClick">取消</view>
					<view class="action text-green" @tap="changeConfirmClick">确定</view>
				</view>
				<view class="select-content">
					<view class="select-item" v-for="(item,index) in changeList" :key="index"@click="select(item)">
						<view style="display: flex;flex-direction: row;background-color: #FFFFFF;">
							<view class="title">{{item.cpmc}}</view>
							<view style="width: 10%;" v-if="item.isSelect==1">
								<image style="width: 50upx; height: 50upx;padding-top: 16upx;" 
										src="../../static/icon/img/select-icon.png"></text>
							</view>
						</view>						
					</view>
				</view>
			</view>
		</uni-popup>
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
				isShowAnimation:true,
				placeholderImage:'/static/icon/img/image-error.png',//占位图
				picList:[],
				user:{},
				changeList:[],
				selectBean:{},
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
					{"recommendList":[],"refreshing":0},
					{"recommendList":[],"refreshing":0},
					{"recommendList":[],"refreshing":0},
					{"recommendList":[],"refreshing":0},
					{"recommendList":[],"refreshing":0},
					{"recommendList":[],"refreshing":0},
					{"recommendList":[],"refreshing":0} 
				]
				
			}
		},
		
		computed: {
			
		},
		onLoad(params) {
			// 获取屏幕宽度
			windowWidth = uni.getSystemInfoSync().windowWidth;
			$this = this;
			this.user = $this.getLoginUser();
			$this.loadFirstData();
		},
		onShow() {
			const reloadMeals = uni.getStorageSync('reloadMeals');
			if (reloadMeals) {
				this.weeksFoodList=[{"recommendList":[]},{"recommendList":[]},{"recommendList":[]},
				{"recommendList":[]},{"recommendList":[]},{"recommendList":[]},{"recommendList":[]} ]
				uni.removeStorageSync('reloadMeals');
				$this.loadFirstData();
			}
		},
		methods: {
			updateMenu(e){
				$this.changeList=[];
				//console.log(e.currentTarget.dataset.item);
				let cplx = e.currentTarget.dataset.item.cpinfo.cplx;
				if(cplx=="" || cplx==null || cplx==undefined){
					uni.showToast({
					    title: "菜品类型为空",
					    duration: 2000,
						icon:"none"
					});
					return;
				}
				uni.showLoading({ title:"加载中..."});
				$this.selectBean.detailid  = e.currentTarget.dataset.item.id;
				$this.selectBean.id  = e.currentTarget.dataset.item.mainId;
				let url = $this.reqAddress+'/tjDzcs/changeList?cplx='+cplx;
				$this.$api.post(url).then((res)=>{
					let data = res.data;
					if(data.code==200 && data.data!=null){
						this.$refs.popup.open('boottom');
						var result = data.data;
						result.forEach(function(item, index) {
							item.isSelect = 0;
						})
						$this.changeList = result;
					}
					setTimeout(function(){uni.hideLoading();},100);
				}).catch((err)=>{
					console.log('request fail', err);
					setTimeout(function(){uni.hideLoading();},100);
				})
			},
			//下拉刷新
			onPulldownReresh(){
				let type = 'refresh';
				// #ifdef APP-PLUS
				if(type === 'refresh'){
					this.weekList[this.weekCurrent].refreshing = true;
				}
				// #endif
				if(type === 'refresh'){
					this.weekList[this.weekCurrent].current = 1;
				}
				setTimeout(function(){
					$this.loadCustomizationData($this.weekList[$this.weekCurrent].date,0,type);
				},500);
			},
			loadFirstData(){
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
				this.loadCustomizationData(date,1);
			},
			showDrawer() {
				this.$refs.showLeft.open();
			},
			closeDrawer() {
				this.$refs.showLeft.close();
			},
			ontabchange(e) {
				let index = e.target.current || e.detail.current;
				this.weekCurrent = index;
				this.loadCustomizationData(this.weekList[this.weekCurrent].date,1);
			},
			onImageError(index1,index2){
				console.log("图片显示失败,显示占位图");
				this.todayRecommendList[index1].tjcp[index2].image = this.placeholderImage;
			},
			itemDetail(e){
				uni.navigateTo({
					url: './foodDetails?type=2&data='+encodeURIComponent(JSON.stringify(e.currentTarget.dataset.item))
				});
			},
			weekSelect(e) {
				this.$refs.showLeft.close();
				this.weekCurrent = e.currentTarget.dataset.index;
				/* if(this.weekCurrent>0){
					this.weekScrollLeft = (e.currentTarget.dataset.index-1) *80;
				} */
				//console.log(this.weekList[this.weekCurrent].date);
				this.loadCustomizationData(this.weekList[this.weekCurrent].date,1);
			},
		//加载
			loadCustomizationData(dayDate,refresh,type){
				this.weekDay = this.weekList[$this.weekCurrent].chinaName1;
				//清空历史数据
				/* this.weeksFoodList=[{"recommendList":[]},{"recommendList":[]},{"recommendList":[]},
				{"recommendList":[]},{"recommendList":[]},{"recommendList":[]},{"recommendList":[]}]; */
				if(refresh!=0){
					if($this.weeksFoodList[$this.weekCurrent].recommendList.length>0){
						return;	
					}
				}else{
					$this.weeksFoodList[$this.weekCurrent].recommendList=[];
				}
				if(type!="refresh"){
					uni.showLoading({ title:"加载中..."});
				}
				let url = $this.reqAddress+'/tjDzcs/getPageList?dzrq='+dayDate;
				let domainName = $this.domainName;
				$this.$api.post(url).then((res)=>{
					let data = res.data;
					if(data.code==200 && data.data!=null){
						var result = data.data;
						$this.handleData(result);
						//下拉刷新 关闭刷新动画
						if(type === 'refresh'){
						
							this.$refs.mixPulldownRefresh && this.$refs.mixPulldownRefresh.endPulldownRefresh();
							// #ifdef APP-PLUS
							this.weeksFoodList[this.weekCurrent].refreshing = false;
							// #endif
							this.weeksFoodList[this.weekCurrent].loadMoreStatus = 0;
						}
					}
					setTimeout(function(){uni.hideLoading();},100);
				}).catch((err)=>{
					console.log('request fail', err);
					setTimeout(function(){uni.hideLoading();},100);
				})
				
			},
			handleData(result){
				let domainName = $this.domainName;
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
			},
			//设置scroll-view是否允许滚动，在小程序里下拉刷新时避免列表可以滑动
			setEnableScroll(enable){
				if(this.enableScroll !== enable){
					this.enableScroll = enable;
				}
			},
			select(item){ // 点击选项
				$this.selectBean.cpid  = item.id;
				$this.changeList.forEach(function(row,index){
					if(row.id == item.id){
						row.isSelect = 1;
					}else{
						row.isSelect = 0;
					}
				})
			},
			changeCancelClick(){
				$this.selectBean = {};
				this.$refs.popup.close();
			},
			changeConfirmClick(){
				if(!$this.selectBean.hasOwnProperty("cpid")){
					uni.showToast({
					    title: "更换菜品为空",
					    duration: 2000,
						icon:"none"
					});
					return;
				}
				if($this.user.id==null || $this.user.id=="" ){
					uni.showToast({
						title: '请先登录',
						duration: 2000,
						icon: 'none'
					});
					return;
				};
				$this.selectBean.userid =  $this.user.id;
				let url = $this.reqAddress+'/tjDzcs/savechange';
				let paramData = { "cpid":$this.selectBean.cpid,"detailid":$this.selectBean.detailid,
					"id":$this.selectBean.id,"userid":$this.selectBean.userid};
				//console.log(paramData)
				$this.$api.post(url,paramData).then((res)=>{
					let data = res.data;
					if(data.code==200 && data.data!=null){
						$this.selectBean = {};
						this.$refs.popup.close();
						$this.loadCustomizationData($this.weekList[$this.weekCurrent].date,0);
						//$this.weeksFoodList[$this.weekCurrent].recommendList=[];
						//$this.handleData(data.data);
					}
					setTimeout(function(){uni.hideLoading();},100);
				}).catch((err)=>{
					console.log('request fail', err);
					setTimeout(function(){uni.hideLoading();},100);
				})
			}
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
	height: 70upx;
	text-align: center;
	white-space: nowrap;
	box-shadow: 0 2upx 8upx rgba(0,0,0,.06);
	background-color: #D0EDFB;
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
.item-bottom  button {
	padding: 0 18rpx 0 18rpx;
	height: 60rpx;
	margin-top: 7%;
	margin-right: 24upx;
}
.select-bar {
	font-size: 28rpx;
	padding: 0 20rpx;
	display: flex;
	flex-direction:row;
	position: relative;
	align-items: center;
	min-height: 80rpx;
	justify-content: space-between;
}
.bg-white{
	background-color: #FFFFFF;
}
.action {
	display: flex;
	align-items: center;
	height: 100%;
	justify-content: center;
	max-width: 100%;
}
.text-blue{
	color: #0081ff;
}
.text-green{
	color: #39b54a;
}
.movableArea {
	position: fixed;
	top: 70upx;
	left: 32upx;
	width: 100%;
	height: 100%;
	pointer-events: none;//设置area元素不可点击，则事件便会下移至页面下层元素
	.movableView {
		pointer-events: auto;//可以点击
		width: 80rpx;
		height: 70rpx;

		image {
			width: 100%;
			height: 100%;
		}
	}
}
/* .select-content{
	height: 100%;
} */
.select-content{
	background-color: #FFFFFF;
	max-height: 420upx;
	min-height: 200upx;
	overflow:auto;
	margin-top: 16upx;
	margin-bottom: 10upx;
	.select-item{
		background-color: #F1F1F1;
		padding: 0rpx 0 2rpx 0;
		display: flex;
		.title{
			flex: 1;
			width:80%;
			padding: 20rpx 0 20rpx 30upx;
			background-color: white;
		}
	}
}
@import url("./meals.css");
</style>

