<template>
	<view class="content">
		<!-- 下拉刷新组件 -->
		<mix-pulldown-refresh ref="mixPulldownRefresh" class="panel-content" :top="0" @refresh="onPulldownReresh" @setEnableScroll="setEnableScroll">
			<scroll-view class="panel-scroll-box" :scroll-y="enableScroll" @scrolltolower="loadMore">
				<view v-for="(item, index) in list" :key="index" class="item news-item"
					@click="navToDetails(item)" hover-class="setting-click">						
				<!-- 	<image class="img-list" mode="aspectFill" :src="item.image"></image> -->
					<!--  -->
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
				scrollLeft: 0, //顶部选项卡左滑距离
				enableScroll: true,
				loadMoreStatus:0,
				refreshing:true,
				current:1,
				pageSize:10,
				list:[]
			}
		},
		async onLoad() {
			// 获取屏幕宽度
			windowWidth = uni.getSystemInfoSync().windowWidth;
			$this =this;
			this.loadDataList("add");
		},
		methods: {
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
					  "keyword": "",
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
			}
		}
	}
</script>

<style lang='scss'>
page, .content{
	background-color: #F1F1F1;
	height: 100%;
	overflow: hidden;
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
