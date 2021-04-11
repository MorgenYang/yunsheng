<template>
	<view>
		<!-- <view v-for="(item, index) in wzdtList" v-bind:key="item.id" class="v-body">
			<view decode="true" class="txt" v-if="item.nrlx==0">{{item.wbnr}}</view>
			<image class="img" v-if="item.nrlx==1" :src="item.tpnr" @error="imageError($event,index)"></image>
			<uni-link v-if="item.nrlx==3" :href="item.clj" :text="item.wbnr"></uni-link>
			<video v-if="item.nrlx==2" class="video" :data-id="item.id+''" :id="item.id+''"  @play="play" :src="item.spnr"></video>
		</view> -->
		<!-- <view class="content_box">
			<wxparser :rich-text="fwbnrPC" />
		</view> -->
		<view class="wz-title">
			{{wzbt}}
		</view>
		<view class="wz-other">
			<text>发布时间: {{time}}</text>
			<text class="author">作者: {{wzzz}}</text>
			
		</view>
		<jyf-parser ref="article" class="article"></jyf-parser>
		<!-- 
		@param: commentList展示的评论列表数据
		@method: clickPraise 点赞评论
		@method: clickDelete 删除父级评论
		@method: clickRecommentChild 点赞子评论
		@method: clickDeleteChild 删除子评论
		 -->
		<five-mul-commentlist
			:commentList="commentList"
			@clickDelete="clickDeleteComment"
			@clickDeleteChild="clickDeleteCommentChild"
			@clickRecomment="clickRecomment"
			@clickRecommentChild="clickRecommentChild"
		></five-mul-commentlist>
		<view class="view-conmment-send-bottom">
			<view class="view-comment-textarea" @click="clickComment()"><view class="textarea-comment">写评论…</view></view>
			<!-- <image
				class="praise-icon"
				src="data:image/png;base64,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"
				mode="aspectFill"
				
			></image> -->
			<!-- #ifdef MP-WEIXIN-->
			<button class="share-btn" open-type="share" ></button>
			<!-- #endif -->
			<image
				class="praise-icon dianzan-icon"
				v-if="isDianzan==false"
				@click="addDianzan()"
				src="../../static/icon/img/dianzan1.png"
				mode="aspectFill"
			></image>
			 <image
			 	class="praise-icon dianzan-icon"
			 	v-if="isDianzan==true"
				@click="cancelDianzan()"
				src="../../static/icon/img/dianzan2.png"
				mode="aspectFill"
			 ></image>
			<image
				class="praise-icon"
				v-if="isCollect==false"
				@click="addCollect()"
				src="../../static/icon/img/shoucang1.png"
				mode="aspectFill"
			></image>
			 <image
			 	class="praise-icon"
			 	v-if="isCollect==true"
				@click="cancelCollect()"
				src="../../static/icon/img/shoucang2.png"
				mode="aspectFill"
			 ></image>
		</view>
		<ygc-comment ref="ygcComment" :placeholder="placeholder" @pubComment="sendComment"></ygc-comment>
		<!-- <five-comment ref="detailComment" @sendComment="sendComment" :placeholder="placeholder"></five-comment> -->
	</view>
</template>

<script>
	import jyfParser from "@/components/jyf-parser/jyf-parser.vue";
	import base64 from '@/common/base64.min.js';
	import ygcComment from '@/components/ygc-comment/ygc-comment.vue';
	
	let $this;
	export default {
		components: {jyfParser,ygcComment},
		data() {
			return {
				placeholder: '请输入评论内容…',
				commentParam: {},
				wzid:"",
				commentList :[],//评论列表
				wzbt:"", //文章标题
				wzzz:"", //文章作者
				time:"" ,//发布时间
				user:{},
				isCollect:false,
				isDianzan:false
			}
		},
		onLoad: function (param) {
			$this =this;
			this.user = $this.getLoginUser();
			this.wzid =param.id;
			if(this.user.id){
				this.checkCollect(this.wzid,this.user.id);
				this.checkDianzan(this.wzid,this.user.id);
			}
			this.loadWzDetail(param.id);
		},
	    // #ifdef MP-WEIXIN
		onShareAppMessage(res) {
			if (res.from === 'button') {// 来自页面内分享按钮
			  console.log(res.target)
			}
			return {
			  title: $this.wzbt,
			  path: '/pages/education/detail?id='+this.wzid
			}
		 },
		// #endif
		methods: {
			addShare(){
				//分享到微信朋友 app端
				/* uni.share({ 
					provider: "weixin",
					scene: "WXSceneSession",
					type: 0,
					href: "https://www.it610.com/article/1279654845837492224.htm",//这地址太长了，就省略了
					title: "你笑起来真好看",
					summary: "唐艺昕，你有火吗？没有,为何你点燃了我的心？",                         
					//imageUrl: "http:*******************",
					success: function(res) {
						console.log("success:" + JSON.stringify(res));
					},
					fail: function(err) {
						console.log("fail:" + JSON.stringify(err));
					}
				}); */
			},
			checkUser(){
				if(this.user.id==null || this.user.id=="" ){
					uni.showToast({
						title: '请先登录',
						duration: 2000,
						icon: 'none'
					});
					return;
				};
			},
			//添加点赞
			addDianzan(){
				$this.checkUser();
				let url = $this.reqAddress+'/tjDz/addorremove';
				var data ={"dzdxid":$this.wzid,"userid":$this.user.id,"dzlx":0};
				$this.$api.post(url,data).then((res)=>{
					let result = res.data;
					if(result!=null && result.code==200){
						$this.isDianzan = true;
						uni.showToast({
						    title: "点赞成功",
						    duration: 1000,
							icon:"none"
						});
					}
				}).catch((err)=>{
					console.log('request fail', err);
				})
			},
			//取消点赞
			cancelDianzan(){
				$this.checkUser();
				let url = $this.reqAddress+'/tjDz/addorremove';
				var data ={"dzdxid":$this.wzid,"userid":$this.user.id,"dzlx":0};
				$this.$api.post(url,data).then((res)=>{
					let result = res.data;
					if(result!=null && result.code==200){
						$this.isDianzan = false;
						uni.showToast({
						    title: "点赞取消成功",
						    duration: 1000,
							icon:"none"
						});
					}
				}).catch((err)=>{
					console.log('request fail', err);
				})
			},
			//检查是否点赞过
			checkDianzan(wzid,userId){
				if(this.user.id==null || this.user.id=="" ){
					return;
				};
				let url = $this.reqAddress+'/tjDz/check';
				var data ={"dzdxid":wzid,"dzlx":0,"userid":$this.user.id};
				$this.$api.post(url,data).then((res)=>{
					if(res.data.code==200){
						$this.isDianzan = true;
					}
				}).catch((err)=>{
					console.log('request fail', err);
				})
			},
			//添加收藏
			addCollect(){
				$this.checkUser();	
				let url = $this.reqAddress+'/tjWzsc/addorremove';
				var data ={"szwzid":$this.wzid,"userid":$this.user.id};
				$this.$api.post(url,data).then((res)=>{
					let result = res.data;
					if(result!=null && result.code==200){
						$this.isCollect = true;
						uni.showToast({
						    title: "收藏成功",
						    duration: 1000,
							icon:"none"
						});
					}
				}).catch((err)=>{
					console.log('request fail', err);
				})
			},
			//取消收藏
			cancelCollect(){
				$this.checkUser();
				let url = $this.reqAddress+'/tjWzsc/addorremove';
				var data ={"szwzid":$this.wzid,"userid":$this.user.id};
				$this.$api.post(url,data).then((res)=>{
					let result = res.data;
					if(result!=null && result.code==200){
						$this.isCollect = false;
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
			//检查是否收藏过
			checkCollect(wzId,userId){
				if(this.user.id==null || this.user.id=="" ){
					return;
				};	
				let url = $this.reqAddress+'/tjWzsc/check';
				var data ={"szwzid":wzId,"userid":userId};
				$this.$api.post(url,data).then((res)=>{
					let result = res.data.data;
					if(result==true){
						$this.isCollect = result;
					}
				}).catch((err)=>{
					console.log('request fail', err);
				})
			},
			imageError(e, index) {  
			    this.list[index]['tpnr'] = 'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2315821891,1911640403&fm=26&gp=0.jpg';  
			},
			//加载文章详情
			loadWzDetail(id){
				uni.showLoading({
					mask:true,
					title:"加载中..."
				})
				let url = $this.reqAddress+'/wzbaseinfo/info/'+id;
				$this.$api.get(url).then((res)=>{
					let result = res.data.data;
					if(result!=null &&  !(typeof(result)==="undefined") && result!=""){
						$this.wzzz = result.wzzz;
						$this.time = res.data.time;
						$this.wzbt = result.wzbt;
						let base =  base64.Base64;
						let detail = base.decode(result.fwbnrPC);
						//let detailStr =detail.replace(/\t/g,"&nbsp;&nbsp;&nbsp;&nbsp;").replace(/\s+/g,"&nbsp;");							
						this.$refs.article.setContent(detail);
					}
					$this.$options.methods.loadPlList(id);
				}).catch((err)=>{
					console.log('request fail', err);
					setTimeout(function () {
					    uni.hideLoading();
					}, 300);
				})
			},
			//加载评论列表
			loadPlList(id){
				let url = $this.reqAddress+'/tjWzpl/getPageList';
				let data ={"current": 1,"keyword": "", "orders": [{"asc": false,"column":"plsj"}],"size": 10000,"plwz":id};
				$this.$api.post(url,data).then((res)=>{	
					let dataJson = res.data;
					if(dataJson!=null &&  !(typeof(dataJson)==="undefined") && dataJson!=""){
						let result = [] ;
						let childsArr =[];
						dataJson.data.records.forEach(function(item, index) {
							//判断评论是否当前登录用户（是否可删除评论条件）
							item.isDelete = 0 ;
							if($this.user.id!="" && $this.user.id!=null
								&& item.plr!="" && item.plr!=null
								&& $this.user.id==item.plr){
								item.isDelete = 1;
							} 
							if(item.plcj==0){
								item.childs=[];
								result.push(item);
							}else{
								childsArr.push(item);
							}
						});					
						//循环找到所有子评价的直接父级
						childsArr.forEach(function(childItem, index) {
							dataJson.data.records.forEach(function(allItem, index) {
								if(childItem.sjpl==allItem.id){
									//直接赋值--在小程序端会导致对象的循环引用问题
									childItem.parent = JSON.parse(JSON.stringify(allItem));
								}
							});
						});
						//循环找到第一级评论下的所有直接和间接子类
						result.forEach(function(item,index) {
							$this.$options.methods.findNextChild(item,item.id,childsArr);
						});					
						$this.commentList = result;
						setTimeout(function () {
							uni.hideLoading();
						}, 300);
					}
					
				}).catch((err)=>{
					console.log('request fail', err);
					setTimeout(function () {
					    uni.hideLoading();
					}, 300);
				})
			},
			//递归获取当前评论的子级评论
			findNextChild(item,pId,childs){
				childs.forEach(function(child, index) {
					if(pId==child.sjpl){
						item.childs.push(child);
						let topParent={"id":item.id,"nikename":item.nickname};
						child.topParent = topParent;
						$this.$options.methods.findNextChild(item,child.id,childs);
					}
				});
			},
		  /**
		  	 * 回复问题
		  	 */
		  	clickComment() {
				if($this.user.id==null || $this.user.id=="" ){
					uni.showToast({
						title: '请先登录',
						duration: 2000,
						icon: 'none'
					});
					return;
				};
		  		this.commentParam = {
		  			avatarurl: $this.user.avatarUrl,
		  			nickname: $this.user.nickName,
		  			//plsj: '2020-12-18 12:12:12',
		  			childs: []
		  		};
				this.placeholder="请输入评论内容…";
				this.$refs.ygcComment.toggleMask('show',null,null);
		  	},
		  	/**
		  	 * 删除详情
		  	 */
		  	clickDeleteDetail(item) {
		  		uni.showModal({
		  			title: '提示',
		  			content: '确定要删除吗？',
		  			confirmColor: '#12B368',
		  			success: function(res) {
						
		  			}
		  		});
		  	},
		  	/**
		  	 * 删除多级评论
		  	 */
		  	clickDeleteComment(item) {
		  		uni.showModal({
		  			title: '提示',
		  			content: '确定要删除评论吗？',
		  			confirmColor: '#12B368',
		  			success: function(res) {
		  				if (res.confirm) {							
							$this.$options.methods.delComment(item); 
		  				} 
		  			}
		  		});
		  	},
		  	/**
		  	 * 删除多级子评论
		  	 */
		  	clickDeleteCommentChild(item) {
		  		uni.showModal({
		  			title: '提示',
		  			content: '确定要删除评论吗？',
		  			confirmColor: '#12B368',
		  			success: function(res) {
		  				if (res.confirm) {
		  					$this.$options.methods.delComment(item); 
		  				} 
		  			}
		  		});
		  	},
			//删除评论
			delComment(item){
				let url = $this.reqAddress +'/tjWzpl/delete/'+item.id;
				$this.$api.post(url).then((res)=>{
					var data = res.data;
					if(data.code==200){
						$this.$options.methods.loadPlList($this.wzid); 
					}
				}).catch((err)=>{
					console.log('request fail', err);
					setTimeout(function () {
					    uni.hideLoading();
					}, 300);
				})
			},
		  	/**
		  	 * 回复 评论
		  	 * @param {Object} item
		  	 */
		  	clickRecomment(item) {	
				if($this.user.id==null || $this.user.id=="" ){
					uni.showToast({
						title: '请先登录',
						duration: 2000,
						icon: 'none'
					});
					return;
				};
		
				this.commentParam = {};
				this.commentParam = {
					avatarurl: $this.user.avatarUrl,
					nickname: $this.user.nickName,
					//plsj: '2020-12-18 12:12:12',
					parent: item
				};
				this.placeholder = '回复' + item.nickname + ':';
				this.$refs.ygcComment.toggleMask('show',item);
		  	},
		  	/**
		  	 * 回复评论的评论
		  	 * @param {Object} item
		  	 */
		  	clickRecommentChild(item) {
				if($this.user.id==null || $this.user.id=="" ){
					uni.showToast({
						title: '请先登录',
						duration: 2000,
						icon: 'none'
					});
					return;
				};
				this.commentParam = {};
				this.commentParam = {
					avatarurl: $this.user.avatarUrl,
					nickname: $this.user.nickName,
					//plsj: '2020-12-18 12:12:12',
					parent: item
				};
				this.placeholder = '回复' + item.nickname + ':';
				this.$refs.ygcComment.toggleMask('show',item);
		  	},
		  	/**
		  	 * 发送评论
		  	 * @param {Object} result
		  	 */
		  	sendComment(result,item) {
				if(result==null || result==""){
					uni.showToast({
						title: '评论内容为空',
						duration: 2000,
						icon: 'none'
					});
					return;
				}
				let url = $this.reqAddress +'/tjWzpl/add';
				var data={
				  "plcj": item==null?0:1,
				  "plnr": result,
				  "sjplid": (item!=null && item.id!=null)?item.id:0,
				  "userid":$this.user.id,
				  "wzid": $this.wzid
				};
				$this.$api.post(url,data).then((res)=>{
					var dataJson = res.data;
					if(dataJson.code==200){
						$this.$refs.ygcComment.toggleMask();
						$this.$options.methods.loadPlList($this.wzid); 
					}
				}).catch((err)=>{
					console.log('request fail', err);
					setTimeout(function () {
					    uni.hideLoading();
					}, 300);
				})
		  	}
		}
	}
</script>

<style lang="scss">
	.article{
		margin: 30upx;
	}
	.uni-link { word-break: break-all; }
	.v-body{
		margin-left:30upx ;
		margin-right:30upx ;	
	}
	.wz-title{
		//text-align: center;
		font-weight: bold;
		font-size: 20px;
		padding-top: 20upx;
		padding-bottom: 10upx;
		margin-left:30upx ;
		margin-right:30upx ;	
	}
	.wz-other{
		margin-left:30upx ;
		margin-right:30upx ;
		display: flex;
		flex-direction: column;
		text{
			font-size: 12px;
		}
		/* .author{
			padding-left: 60upx;
		} */
	}

	.img{
		margin-top: 10upx;
		width: 690upx;
		height: 340upx;
	}
	.txt{
		font-size: 14px;
		padding-top: 15upx;
		padding-bottom: 15upx;
	}
	.video{
		height: 400upx;
		width: 100%;
	}
	
	.view-conmment-send-bottom {
		width: 100%;
		position: fixed;
		bottom: 0;
		display: flex;
		flex-direction: row;
		background-color: white;
		/* border-top: 1upx solid $uni-text-color-disable; */
	}
	
	.view-comment-textarea {
		background-color: $uni-bg-color-grey;
		border-radius: 40upx;
		padding: 15upx 20upx;
		margin: 18upx 0upx 18upx 20upx;
		width: 60%;
		align-items: center;
	}
	.textarea-comment {
		padding: 0;
		// width: 100%;
		height: 100%;
		font-size: $uni-font-size-base;
		// color: $uni-text-color;
		color: $uni-text-sub-color;
	}
	.praise-icon {
		height: 53upx;
		width: 50upx;
		font-size: 36rpx;
		color: #999;
		margin-top:26upx ;
		padding-left: 16upx;
		padding-right: 10upx;
	}
	.dianzan-icon{
		margin-top:26upx ;
	}

	button::after{
	    border: none;
	    border-radius:  0;
	}
	
	.share-btn{
		margin: 0;
		margin-top: 33upx;
		margin-left: 30upx;
		padding-left: 20upx;
		border: none;
		background-color: transparent;
		outline: none;	
		background-size:40upx 40upx;
		background-repeat:no-repeat;
		background-image:url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAABoUlEQVRYR+2WsU7DMBCGz44q9QUYKAzdeAzSh6A7EhMsVKouykSZInvpBCsjEnkIykOAxNaFIsQLVIoio5OcynHTxHE7oWSK4tzdd7/vTsdgj0dK+QIAp5zzq+l0+uHjivkYkY0OfqHt3znnYx8ILwAreJGDF0RrACN4CgCFAsV7a4hWAGZwRBxLKRWlj4jMOGsF4QxgB9d1sAGw6sIZwgmgKngVgA9EI4AQYsYYuwOAlGQ3u8a8Aus7tSfVxwIRR3Wd1giQJEnIOQ+jKJrZjnYB0H9CiFvG2A8iPu8FUGdcB+A6X0oKJEkyDIKA5B7aDpRSb7YKuwD0tZ1XQCzzPL+P43hZnJUApJRfADCooldKPUZRdONSA0KIB8bY9Q4VVoh4sgVAdx0EwSsVDlHaxv1+/3MymXy7AMzn8+P1en1m+9Dqhnmej+I4XtD5RgEToKlyC8dta0BKSQl2AJ0CnQIHVeBJ7wOXLqP34G3oEtQaXIedAx1Ap0CnwP9SgLaYLMtWbbPy+b/X6w2K7aq0E+pdjvb5Ix/HDja/SqnU3C3/AGNUsjCFEZJuAAAAAElFTkSuQmCC')
	}
	.bing-progress{
		justify-content: center;
		align-content: center;
	}
</style>
