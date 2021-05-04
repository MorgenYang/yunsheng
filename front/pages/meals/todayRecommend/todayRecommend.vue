<template>
	<view class="content">
		<scroll-view class="panel-scroll-box" :scroll-y="enableScroll">
			<view class="uni-margin-wrap" >
				<swiper v-if="false" class="swiper" circular :indicator-dots="indicatorDots" :autoplay="autoplay" :interval="interval" :duration="duration">
					<swiper-item v-for="(item, index) in picList" :key="index">
						<image class="swiper-item" mode="scaleToFill" :src="item.url" @tap="itemDetail(item)"></image>
					</swiper-item>	
				</swiper>
				<image  class="not_load_class" src="../../../static/icon/img/image-error.png" mode="aspectFit"></image>
			</view>				
			<view class="item-food" >
				<view class="item-line" v-for="(menu, index1) in todayRecommendList" :key="index1">
					<view class="type-head">
						<image class="title-img"  src="../../../static/icon/settings/icon.png"></image>
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
									src="../../../static/icon/img/star.png" class="star-img">
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
	</view>
</template>

<script>

	let windowWidth = 0, scrollTimer = false, tabBar;
	let nowDate = new Date(); 
	let $this;
	export default {
		data() {
			return {
				scrollLeft:0, //顶部选项卡左滑距离
				enableScroll: true,
				indicatorDots: true,
				autoplay: true,
				interval: 2000,
				duration: 500,
				placeholderImage:'/static/icon/img/image-error.png',//占位图
				picList:[],
				todayRecommendList:[]
				
			}
		},
		
		computed: {
			
		},
		onLoad() {
			// 获取屏幕宽度
			windowWidth = uni.getSystemInfoSync().windowWidth;
			$this = this;
			this.loadTodayRecommend();
		},
		onShow() {
		
		},
		methods: {
			onImageError(index1,index2){
				console.log("图片显示失败,显示占位图");
				this.todayRecommendList[index1].tjcp[index2].image = this.placeholderImage;
			},
			itemDetail(itemMenu){
				uni.navigateTo({
					url: '../foodDetails?id='+itemMenu.id
				});
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
								}else{
									item.image = "../../../static/icon/img/image-error.png";
								}
							}	
							if(item.cpinfo.jj==null){
								item.cpinfo.jj="暂无";
							}
						});
						//注意*** 不自动刷新--需要强制刷新
						this.$forceUpdate();
					}
				}).catch((err)=>{
					console.log('request fail', err);
				})
			},
			
			//设置scroll-view是否允许滚动，在小程序里下拉刷新时避免列表可以滑动
			setEnableScroll(enable){
				if(this.enableScroll !== enable){
					this.enableScroll = enable;
				}
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

.panel-scroll-box{
	height: 100%;
	margin-bottom: 40upx;
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
.item-line{
	display:flex;
	flex-direction: column;
	margin: 20upx 20upx;
}	

.item-top{
	background-color: #fff;
	height: 20upx;
}
.item-bottom{
	display:flex;
	flex-direction: row;
}
.item-column{
	display:flex;
	flex-direction: column;
}
.img{
	width: 100%;
	height: 100%;
}
.item{
	width:100%;
	margin-top: 20upx;
	/* margin-bottom: 20upx; */
	border-bottom-width: 1px;
	border-color: #eee;
	background-color: #FFFFFF;
	display: flex;
}

.news-item{
	/* height: 282upx; */
	flex-direction: row;
	/* margin-bottom: 20upx; */
}
.item-right{
	padding-left: 40upx;
	overflow: hidden;
	margin-top: 15upx;
	margin-bottom: 15upx;
}
.item-title{
	margin-top:0upx;
	font-size: 36upx;
	color: #197EC6;
	display: block;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
.item-level{
	flex-direction: row;
	margin-top: 10upx;
}
.item-tip{
	font-size: 26upx;
	color: #197EC6;
}
.star-title{
	padding-right: 10upx;
}
.item-function{
	margin-top: 4upx;
	flex-direction: row;
}
.function-context{
	overflow: hidden;
	word-break: break-all;  /* break-all(允许在单词内换行。) */
	text-overflow: ellipsis;  /* 超出部分省略号 */
	display: -webkit-box; /** 对象作为伸缩盒子模型显示 **/
	-webkit-box-orient: vertical; /** 设置或检索伸缩盒对象的子元素的排列方式 **/
	-webkit-line-clamp: 3; /** 显示的行数 **/
}
.function-context-text{
	color:#858585;
	padding-left: 8upx;
}
.star-img{
	padding-top: 3upx;
	width:30upx;
	height:30upx;
}
.item-body{
	font-size: 26upx;
	height: 106upx;
	margin-top: 10upx;
	overflow: hidden;
	display: -webkit-box;  
	-webkit-box-orient: vertical; 
	-webkit-line-clamp: 3; 
	color: #aaa;
}
.img-list{
	flex-shrink: 0;
	background-color: #fff;
	width: 260upx;
	height: 190upx;
	margin-top: 20upx;
}
.type-head{
	display: flex;
	flex-direction: row;
}
.type-title{
	font-size: 28upx;
}
.title-img{
	width: 30upx;
	height: 30upx;
	margin-top: 6upx;
	margin-right: 10upx;
}
.setting-click{
	background-color:#F5F5F5;
}
.not_load_class{
	width: 100%;
	height: 340upx; 
	border-bottom:4upx solid #eee;
}
</style>

