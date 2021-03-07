<template>
	<view class="content">	
		<view v-for="(item, index) in list" :key="index" class="item"
			@tap="click(item)" @longtap="longClick(item.szwz)" 
			@touchstart='touchstart' @touchend='touchend'
			hover-class="setting-click">
			<view class="item-left">
				<view class="item-title">{{item.wzbt}}</view>
				<view class="item-time">{{item.szsj}}</view>
			</view>
			<view class="img-view">
				<image class="img-list" v-if="item.wzfm!=null && item.wzfm!=''"
					mode="aspectFill" :src="item.wzfm">
				</image>
			</view>
			
		</view>
		<uni-popup ref="popup" type="center" animation="true" @change="change">
			<view class="btn-view">
				<view class="tip">操作</view>
				<button @tap="cancelCollect()" class="btn-collect mini-btn" size="default">取消收藏</button>
				<button @tap="closeWin()">关闭</button>
			</view>
		</uni-popup>
		
	</view>
</template>

<script>
	import uniPopupMessage from '@/components/uni-popup/uni-popup-message.vue'
	import uniPopupDialog from '@/components/uni-popup/uni-popup-dialog.vue'
	let $this;
	export default {
		components: {
			uniPopupMessage,
			uniPopupDialog
		},
		data() {
			return {
				user:{},
				wzid:'',
				list:[]
			}
		},
		onLoad() {
			$this = this;
			this.user = $this.getLoginUser();
		},
		onShow(){
			this.loadDataList();
		},
		methods: {
			cancelCollect(){
				let url = $this.reqAddress+'/tjWzsc/delete';
				var data ={"szwzid":$this.wzid,"userid":$this.user.id};
				$this.$api.post(url,data).then((res)=>{
					let result = res.data;
					if(result!=null && result.code==200){
						this.$refs.popup.close();
						$this.loadDataList();
						uni.showToast({
						    title: "取消成功",
						    duration: 1000,
							icon:"none"
						});
					}
				}).catch((err)=>{
					console.log('request fail', err);
				})
			},
			closeWin(done){
				this.$refs.popup.close();
			},
			change(e) {
				//console.log('popup ' + e.type + ' 状态', e.show)
			},
			click(item){
				if(this.touchE-this.touchT<350){
					uni.navigateTo({
						url: '/pages/education/detail?id='+item.szwz,
						animationType: 'slide-in-right',
						animationDuration: 200
					});
				}
			},
			longClick(wzid){
				this.wzid = wzid;
				this.$refs.popup.open();
				/* uni.showModal({
					title: '提示',
					content: '确定取消收藏吗？',
					confirmColor: '#12B368',
					success: function(res) {
						/* 调用接口删除 
						if (res.confirm) {
							console.log('用户点击确定');
						} else if (res.cancel) {
							console.log('用户点击取消');
						}
					}
				}); */
			},
			touchstart(){
				this.touchT = new Date().getTime();
			},
			touchend(){
				this.touchE = new Date().getTime();
			},
			//加载数据
			loadDataList(){
				setTimeout(()=>{
					$this.list=[];
					//加载文章列表信息
					let url = $this.reqAddress+'/tjWzsc/getPageList';
					let domainName = $this.domainName;
					var data={
					  "current": $this.current,
					  "keyword": "",
					  "orders": [
						{
						  "asc": false,
						  "column":"szsj"
						}
					  ],
					  "size": $this.size,
					  "userid":$this.user.id
					};
					$this.$api.post(url,data).then((res)=>{
						var data = res.data;
						if(data.code==200 && data.data!=null){
							let result = data.data.records;
							result.forEach(function(item, index) {
								if(item.wzfm){
									item.wzfm=item.wzfm.replace(/&quot;/g,"\"");
									let imageObj = JSON.parse(item.wzfm);
									if(imageObj.length>0){
										item.wzfm = domainName+imageObj[0].url;	
									}
								}
								/* if(item.wzbt){
									item.wzbt = "标题："+item.wzbt;
								}	
								if(item.szsj){
									item.szsj = "收藏时间："+item.szsj;
								} */
							});
							$this.list = $this.list.concat(result);
						}else{
							
						}
					}).catch((err)=>{
						console.log('request fail', err);
					})
				},500);
			}
		}
	}
</script>

<style>
	.item{
		display: flex;
		flex-direction:row;
		margin-top: 10upx;
		height: 150upx;
		border-bottom:10upx solid #F1F1F1
	}
	.item-left{
		display: flex;
		flex-direction:column;
		margin-left: 20upx;
		margin-right: 20upx;
	}
	.item-title{
		font-size: 30upx;
		color: #303133;
		overflow: hidden;
		display: -webkit-box;
		-webkit-box-orient: vertical;
		-webkit-line-clamp: 2;
		height: 75upx;
	}
	.item-time{
		padding-top: 26upx;
		margin-bottom: 10upx;
		font-size: 28upx;
		color: #aaa;
	}
	.img-view{
		height: 100%;
		margin-left: auto;
	}
	.img-list{
		flex-shrink: 0;
		background-color: #fff;
		width: 180upx;
		height: 128upx;
		border: 2upx solid #0000000;
		-moz-border-radius: 12upx;
		-webkit-border-radius: 12upx;
		border-radius:12upx;
		margin-right: 20upx;
		margin-top: 10upx;
	}
	.tip{
		margin-top: -60upx;
		margin-bottom: 100upx;
	}
	.btn-view{
		width: 400upx;
		height: 450upx;
		background-color:white;
		justify-content: center;
		display: flex;
		flex-direction: column;
		align-items: center;
	}
	.btn-view > button{
		width: 255upx;
		font-size: 30upx;
	}
	.btn-collect{
		margin-bottom: 60upx;
	}
	.popup-content {
		background-color: #fff;
		padding: 30upx;
	}
	
	
	
</style>
