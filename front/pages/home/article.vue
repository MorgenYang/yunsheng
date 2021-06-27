<template>
	<view>
		<u-navbar :is-back="true" :is-fixed="true" :background="background" :z-index="100">
			<view class="u-back-wrap" v-if="isBack" @tap="goBack" style="margin-left: -30upx;">
				<view class="u-icon-wrap">
					<image style="width: 52upx;height: 52upx;" src="../../static/icon/img/back_icon.png"></image>
				</view>
			</view>
			<view class="slot-wrap" v-if="useSlot">
				<view class="search-wrap" v-if="search">
					<!-- 如果使用u-search组件，必须要给v-model绑定一个变量 -->
					<u-search v-model="keyword" :show-action="showAction" height="56" @search="searchFun" :action-style="{color: '#fff'}"></u-search>
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
					<image @click="history" style="width: 40upx; height: 40upx;" src="../../static/icon/img/history_icon.png"></image>
				</view>
			
			</view>
		</u-navbar>
		<!-- 下拉刷新组件 -->
		<mix-pulldown-refresh ref="mixPulldownRefresh" class="panel-content" :top="0" @refresh="onPulldownReresh" @setEnableScroll="setEnableScroll">
			<scroll-view class="panel-scroll-box" :scroll-y="enableScroll" @scrolltolower="loadMore">
				<view v-for="(item, index) in list" :key="index" class="item news-item"
					@click="navToDetails(item)" hover-class="setting-click">						
					<view class="item-t">
						<view class="item-title">{{item.wzbt}}</view>
						<view class="u-tag u-size">{{item.wzlxdesc}}</view>
					</view>
					<view class="item-right">
						<view class="item-content">
							<view class="item-author">{{'作者：'+item.wzzz}}</view>
							<view class="item-body">{{item.wzjj}}</view>
							<view class="item-other">
								<view>{{item.dzsl+'赞同 · '+item.plsl+'评论'}}</view>
							</view>
							
						</view>
						<image class="img-list" v-if="item.wzfm!=null && item.wzfm!=''"
							mode="aspectFill" :src="item.wzfm">
						</image>
						
					</view>
				</view>						
				<mix-load-more :status="loadMoreStatus"></mix-load-more>
			</scroll-view> 
		</mix-pulldown-refresh>
	</view>
</template>


<script>
	import mixPulldownRefresh from '@/components/mix-pulldown-refresh/mix-pulldown-refresh';
	import mixLoadMore from '@/components/mix-load-more/mix-load-more';
	
	let $this;
	let windowWidth = 0;
	export default {
		components: {
			mixPulldownRefresh,
			mixLoadMore
		},
		data() {
			return {
				right: true,
				showAction: false,
				rightSlot: false,
				useSlot: true,
				background: {
					/* 'background-image': 'linear-gradient(45deg, rgb(28, 187, 180), rgb(141, 198, 63))' */
					'background-color':'#3AA9D1'
				},
				isBack: true,
				search: true,
				custom: false,
				keyword: '',
				// #ifdef MP
				slotRight: true,
				// #endif
				// #ifndef MP
				slotRight: true,
				// #endif
				keyword: '',
				scrollLeft: 0, //顶部选项卡左滑距离
				enableScroll: true,
				loadMoreStatus:0,
				refreshing:true,
				current:1,
				pageSize:10,
				list:[]
			}
		},
		async onLoad(options) {
			// 获取屏幕宽度
			windowWidth = uni.getSystemInfoSync().windowWidth;
			$this =this;
			$this.keyword =  options.key;
			this.loadDataList("add");
		},
		onShow() {
			let loadHistory = uni.getStorageSync("loadHistory");
			let loadHistoryContext = uni.getStorageSync("loadHistoryContext");
			if(loadHistory=="1" && loadHistoryContext!=null && loadHistoryContext!=""&&loadHistoryContext!=undefined){
				$this.keyword = loadHistoryContext;
				this.loadDataList("refresh");
			}
			
		},
		methods: {
			history(){
				uni.navigateTo({
					url:'./historySearch'
				});
			},
			goBack(){
				uni.navigateBack();
			},
			//加载数据
			loadDataList(type){
				//type add 加载更多 refresh下拉刷新
				if(type === 'add'){
					if($this.loadMoreStatus === 2){
						return;
					}
					$this.loadMoreStatus = 1;
					//注意*** 不自动刷新--需要强制刷新
					$this.$forceUpdate();
				}
				// #ifdef APP-PLUS
				else if(type === 'refresh'){
					$this.refreshing = true;
				}
				// #endif
				if(type === 'refresh'){
					$this.current = 1;
				}
				setTimeout(()=>{
					//加载文章列表信息
					let url = $this.reqAddress+'/wzbaseinfo/getPageList';
					let domainName = $this.domainName;
					var data={
					  "current": $this.current,
					  "keyword": $this.keyword,
					  "orders": [
						{
						  "asc": false,
						  "column":"xssx"
						}
					  ],
					  "size": $this.pageSize,
					};
					$this.$api.post(url,data).then((res)=>{
						var data = res.data;
						if(data.code==200 && data.data!=null){
							$this.current++;
							if(type === 'refresh'){
								$this.list = []; //刷新前清空数组
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
							$this.list = $this.list.concat(result);
							//tabItem.list.concat(data.data.records);
							//下拉刷新 关闭刷新动画
							if(type === 'refresh'){
								$this.$refs.mixPulldownRefresh && $this.$refs.mixPulldownRefresh.endPulldownRefresh();
								// #ifdef APP-PLUS
								$this.refreshing = false;
								// #endif
								$this.loadMoreStatus = 0;
							}
							//上滑加载 处理状态
							if(type === 'add'){
								$this.loadMoreStatus = $this.list.length == data.data.total ? 2: 0;
							}
							//注意*** 不自动刷新--需要强制刷新
							$this.$forceUpdate();
							
						}
						if($this.keyword!=null && $this.keyword!="" && $this.keyword!=undefined && $this.keyword.trim().length>0){
							let searchContent = uni.getStorageSync("searchHistory");
							let loadHistory = uni.getStorageSync("loadHistory");
							if(searchContent!=null && searchContent!=undefined && searchContent!=""){
								if(loadHistory!="1"){
									let strArr = searchContent.split("###");
									strArr.push($this.keyword);
									let newArr = [];
									if(strArr.length>10){
										for(let i=1;i<strArr.length;i++){
											newArr.push(strArr[i]);
										} 
									}else{
										newArr =strArr;
									}
									uni.setStorageSync("searchHistory",newArr.join('###'));
								}
							}else{
								uni.setStorageSync("searchHistory",$this.keyword);	
							}
						}
						uni.setStorageSync("loadHistory","");
						uni.setStorageSync("loadHistoryContext","");
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
			searchFun(){
				let str =  $this.keyword;
				if(str=="" || str==undefined || str==null || str.trim().length==0){
					uni.showToast({
					    title: "请输入搜索内容",
					    duration: 2000,
						icon:"none"
					});
					return;
				}
				$this.onPulldownReresh();
			}
		}
	}
</script>

<style lang='scss'>
 page, .content{
	background-color: #F1F1F1;
	/* height: 100%;
	overflow: hidden; */
}
.navbar-right {
	margin-right: 24rpx;
	display: flex;
}

.search-wrap {
/* 	margin: 0 20rpx; */
	flex: 1;
}

.right-item {
	margin: 0 0rpx;
	position: relative;
	color: #ffffff;
	display: flex;
}

.slot-wrap {
	display: flex;
	flex: 1;
	margin-left: 20upx;
	margin-right: 20upx
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
	
 @import url("../education/education.css");
</style>
