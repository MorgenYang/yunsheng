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
					<u-search placeholder="搜标题/标签/内容" v-model="keyword" :show-action="showAction" height="56" @search="searchFun" :action-style="{color: '#fff'}"></u-search>
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
			</view>
		</u-navbar>
		<view class="top_his">
			<view style="padding-top: 20upx;">历史搜索</view>
			<image class="del_icon" src="../../static/icon/img/del_all.png" @click="clearAll()"></image>
		</view>
		<view class="no_content" v-if="tagList.length==0"><view class="u-line1"></view>暂无历史搜索记录<view class="u-line2"></view></view>
		<robby-tags v-if="tagList.length!=0" v-model="tagList" @add="addTag" @delete="delTag" @click="clickTag" :enable-del="enableDel"></robby-tags>
	</view>
</template>


<script>
	let $this;
	export default {
		data() {
			return {
				enableDel: true,
				colorType: 'primary',
				tagList: [],
				right: true,
				showAction: false,
				rightSlot: false,
				useSlot: true,
				background: {
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
			$this =this;
		},
		onShow() {
			let loadHistory = uni.getStorageSync("searchHistory");
			if(loadHistory!="" && loadHistory!=undefined && loadHistory!=null){
				$this.tagList = loadHistory;
			}
		},
		methods: {
			clickTag: function(e){
				uni.navigateTo({
					url: './article?key='+e
				});
			},
			delTag: function(e){
				let loadHistory = uni.getStorageSync("searchHistory");
				if(loadHistory!="" && loadHistory!=undefined && loadHistory!=null){
					let arr = [];
					for(let i=0;i<loadHistory.length;i++){
						if(e.currentTag != loadHistory[i]){
							arr.push(loadHistory[i])
						}
					}
					uni.setStorageSync("searchHistory",arr);
				}
			},
			goBack(){
				uni.navigateBack();
			},
			clearAll(){
				uni.setStorageSync("searchHistory",null);
				this.tagList =[];
			},
			goBack(){
				uni.navigateBack();
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
				uni.navigateTo({
					url: './article?key='+$this.keyword
				});
				$this.keyword="";
			}
		}
	}
</script>

<style lang='scss'>
 page, .content{
	background-color: #FFFFFF;
}
.top_his{
	display:flex;
	justify-content:space-between;
	margin-top: 10upx;
	margin-bottom: 30upx;
	padding-left: 30upx;
	padding-right: 20upx;
}
.del_icon{
	padding: 20upx;
	height: 40upx;
	width: 40upx;
}
.item{
	padding-left: 30upx;
	padding-top: 10upx;
	background-color: #FFFFFF;
}
.no_content{
	text-align: center;	
	color: gray;
	font-size: 28upx;
	display: flex;
	flex-direction:row;
	height: 60upx;
	align-items: center;
	justify-content: center;
}
.navbar-right {
	margin-right: 24rpx;
	display: flex;
}
.u-line1 {
	height: 2upx;
	background-color: gray;
	width: 140upx;
	margin-right: 20upx;
}
.u-line2 {
	height: 2upx;
	background-color: gray;
	width: 140upx;
	margin-left: 20upx;
}

.search-wrap {
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

</style>
