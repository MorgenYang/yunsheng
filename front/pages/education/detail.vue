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
			@clickPraise="clickPraiseComment"
			@clickDelete="clickDeleteComment"
			@clickDeleteChild="clickDeleteCommentChild"
			@clickRecomment="clickRecomment"
			@clickRecommentChild="clickRecommentChild"
		></five-mul-commentlist>
		<view class="view-conmment-send-bottom">
			<view class=" view-comment-textarea" @click="clickComment()"><view class="textarea-comment">写评论…</view></view>
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
				topParent:{},
				isTopParent:true
			}
		},
		onLoad: function (param) {
			/* 
			let data  = JSON.parse(decodeURIComponent(param.data));
			console.log(data.fwbnrPC);
			this.id = data.id;
			this.fwbnrPC = data.fwbnrPC;
			 */
			$this =this;
			this.wzid =param.id;
			this.loadWzDetail(param.id);
			this.loadPlList(param.id);
			
		},
		methods: {
			imageError(e, index) {  
			    this.list[index]['tpnr'] = 'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2315821891,1911640403&fm=26&gp=0.jpg';  
			},
			//加载文章详情
			loadWzDetail(id){
				let url = this.reqAddress;
				let domainName = this.domainName;
				uni.request({
					url: url +'/wzbaseinfo/info/'+id,
					method:"GET",
					header: {
						'token':'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ3ZWIiLCJpc3MiOiJ5c2FwaSIsImV4cCI6MTYwODA4MDkyMywiaWF0IjoxNjA3ODI4OTIzLCJqdGkiOiI5ZTNlOWQ0OTMyNzM0NmM2OTk0ZjI2YTA4ZjBiZWVjNyIsInVzZXJuYW1lIjoiby03LUk1TnBRUHJULUhqNmN4UXdMLVFxS2U5ZyJ9.sMzFrKb9NuQYs-LJCB8sW0P1D4GLKGXV48ZNH-GMot0'
					},
					success: (res) => {
						var data = res.data;
						if(data.code==200 && data.data!=null){
							let result = data.data;
							if(result!=null &&  !(typeof(result)==="undefined") && result!=""){
								let base =  base64.Base64;
								this.$refs.article.setContent(base.decode(result.fwbnrPC));							
							}	
						}
					}
				});
			},
			//加载评论列表
			loadPlList(id){
				let url = $this.reqAddress;
				uni.request({
					url: url +'/tjWzpl/getPageList',
					method:"POST",
					header: {
						'token':'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ3ZWIiLCJpc3MiOiJ5c2FwaSIsImV4cCI6MTYwODA4MDkyMywiaWF0IjoxNjA3ODI4OTIzLCJqdGkiOiI5ZTNlOWQ0OTMyNzM0NmM2OTk0ZjI2YTA4ZjBiZWVjNyIsInVzZXJuYW1lIjoiby03LUk1TnBRUHJULUhqNmN4UXdMLVFxS2U5ZyJ9.sMzFrKb9NuQYs-LJCB8sW0P1D4GLKGXV48ZNH-GMot0'
					},
					data:{
					  "current": 1,
					  "keyword": "",
					  "orders": [
					    {
					      "asc": false,
					      "column":"plsj"
					    }
					  ],
					  "size": 10000,
					  "plwz":id
					  //"sjpl":""
					},
					success: (res) => {
						var data = res.data;
						if(data.code==200 && data.data!=null){
							let result = [] ;
							let childsArr =[];
							data.data.records.forEach(function(item, index) {	
								if(item.plcj==0){
									item.childs=[];
									result.push(item);
								}else{
									childsArr.push(item);
								}
							});
							//循环找到所有子评价的直接父级
							childsArr.forEach(function(childItem, index) {
								data.data.records.forEach(function(allItem, index) {
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
						}
					}
				});
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
		  		this.commentParam = {
		  			avatarurl: 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=107619675,3445212730&fm=26&gp=0.jpg',
		  			nickname: 'AAA',
		  			plsj: '2020-12-18 12:12:12',
		  			childs: []
		  		};
				this.placeholder="请输入评论内容…";
				this.$refs.ygcComment.toggleMask('show',null,null);
		  	},
		  	/**
		  	 * 详情点赞
		  	 * @param {Object} isPraise
		  	 */
		  	clickPraise(isPraise) {
		  		let praiseParam = '1';
		  		let praiseNum = parseInt(this.detailInfo.PRAISE_NUM);
		  		switch (isPraise) {
		  			case '0':
		  				praiseParam = '1';
		  				praiseNum++;
		  				break;
		  			case '1':
		  				praiseParam = '0';
		  				praiseNum--;
		  				break;
		  		}
		  		this.detailInfo.IS_PRAISE = praiseParam;
		  		this.detailInfo.PRAISE_NUM = praiseNum--;
		  	},
		  
		  	/**
		  	 *  查看图片大图
		  	 * @param {Object} images
		  	 * @param {Object} index
		  	 */
		  	cliclImage(images, index) {
		  		uni.previewImage({
		  			urls: images,
		  			current: index,
		  			longPressActions: {
		  				itemColor: '#12B368',
		  				success: function(data) {},
		  				fail: function(err) {}
		  			}
		  		});
		  	},
		  	/**
		  	 * 删除详情
		  	 */
		  	clickDeleteDetail() {
		  		uni.showModal({
		  			title: '提示',
		  			content: '确定要删除吗？',
		  			confirmColor: '#12B368',
		  			success: function(res) {
		  				/* 调用接口删除 */
		  			}
		  		});
		  	},
		  	/**
		  	 * 点赞多级评论
		  	 * @param {Object} item
		  	 */
		  	clickPraiseComment(item) {
		  		//只能点赞不能取消
		  		if (item.IS_PRAISE == '1') {
		  			return;
		  		}
		  		item.IS_PRAISE = '1';
		  		item.PRAISE_NUM++;
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
		  				/* 调用接口删除 */
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
		  				/* 调用接口删除 */
		  			}
		  		});
		  	},
		  
		  	/**
		  	 * 回复 评论
		  	 * @param {Object} item
		  	 */
		  	clickRecomment(item) {		  	 		
				this.commentParam = {};
				this.commentParam = {
					avatarurl: 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=107619675,3445212730&fm=26&gp=0.jpg',
					nickname: 'AAA',
					plsj: '2020-12-18 12:12:12',
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
				this.commentParam = {};
				this.commentParam = {
					avatarurl: 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=107619675,3445212730&fm=26&gp=0.jpg',
					nickname: 'AAA',
					plsj: '2020-12-18 12:12:12',
					parent: item
				};
				this.placeholder = '回复' + item.nickname + ':';
				this.$refs.ygcComment.toggleMask('show',item);
		 
		  	},
		  	/**
		  	 * 删除单级评论
		  	 */
		  	clickDeleteSig(item) {
		  		uni.showModal({
		  			title: '提示',
		  			content: '确定要删除评论吗？',
		  			confirmColor: '#12B368',
		  			success: function(res) {
		  				/* 调用接口删除 */
		  			}
		  		});
		  	},
		  	/**
		  	 * 点赞单级评论
		  	 */
		  	clickPraiseSig(item) {
		  		//只能点赞不能取消
		  		if (item.IS_PRAISE == '1') {
		  			return;
		  		}
		  		item.IS_PRAISE = '1';
		  		item.PRAISE_NUM++;
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
				let url = this.reqAddress;
				uni.request({
					url: url +'/tjWzpl/add',
					method:"POST",
					header: {
						'token':'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ3ZWIiLCJpc3MiOiJ5c2FwaSIsImV4cCI6MTYwODA4MDkyMywiaWF0IjoxNjA3ODI4OTIzLCJqdGkiOiI5ZTNlOWQ0OTMyNzM0NmM2OTk0ZjI2YTA4ZjBiZWVjNyIsInVzZXJuYW1lIjoiby03LUk1TnBRUHJULUhqNmN4UXdMLVFxS2U5ZyJ9.sMzFrKb9NuQYs-LJCB8sW0P1D4GLKGXV48ZNH-GMot0'
					},
					data:{
					  "plcj": item==null?0:1,
					  "plnr": result,
					  "sjplid": (item!=null && item.id!=null)?item.id:0,
					  "userid":"1258758167997136897",
					  "wzid": $this.wzid
					},
					success: (res) => {
						var data = res.data;
						if(data.code==200){
							$this.$refs.ygcComment.toggleMask();
							$this.$options.methods.loadPlList($this.wzid); 
						}else{
							
						}
					}
				});
			
				/* $this.$refs.ygcComment.toggleMask();
				$this.commentParam.plnr = result;
				if(item!=null && item.topParent!=null){
					$this.commentList.forEach(function(itemChild, index) {
						if(itemChild.id==item.topParent.id){
							itemChild.childs.push($this.commentParam);
							return;
						}
					});
				}else{
					$this.commentList.push($this.commentParam);	
				} */
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
	.title{
		text-align: center;
		font-weight: bold;
		font-size: 18px;
		padding:30upx;
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
		border-top: 1px solid $view-bg-color;
	}
	
	.view-comment-textarea {
		background-color: $uni-bg-color-grey;
		border-radius: 40rpx;
		padding: 15rpx 20rpx;
		margin: 25rpx 20rpx;
		width: 80%;
		flex: 1;
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
</style>
