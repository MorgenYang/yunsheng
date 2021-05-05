<template>
	<view >
		<view v-if="index==0">
			<view class="head">
				<image class="bg" :src="imgUrl"></image>
				<carousel class="carousel" :img-list="imgList" url-key="url" @selected="selectedBanner"/>
				<view class="sView">
					<view class="star">
						<image v-for="(level, index3) in detailBean.tjxj" :key="index3"
							src="../../static/icon/img/star.png" class="star-img">
						</image>
					</view>
				</view>
			</view>	
			<view class="mid">
				<text class="t-center">《{{detailBean.cpinfo.cpmc}}》</text>
				<view class="context">
					<text class="t1">材料:</text>
					<!-- <text class="t2 m10">猪肉，姜米，蒜米，酱油，精盐，豌豆尖（可选），醋，泡辣椒，鲜汤，白糖，水豆粉，黑木耳，（可选）玉兰片，（可选）莴笋丝，胡萝卜丝，竹笋丝，蚝油(可选)，干辣椒，（可选）土豆丝，豆瓣酱</text> -->
					<text class="t2 m10">{{detailBean.cpinfo.cpcl}}</text>
					<text class="t1 m30">做法:</text>
					<text class="t2 m10">{{detailBean.cpinfo.cpzf}}</text>
					<!-- <text class="t2 m10">1.里脊肉洗净切成长条，加入料酒、鸡蛋清、水淀粉、盐搅拌均匀，腌制20分钟。冬笋、木耳切丝。</text>
					<text class="t2">2.碗中放入酱油、白糖、醋、葱末、水淀粉调匀成鱼香汁备用。</text>
					<text class="t2">3.锅中放油，油温五成热时放入肉丝，用铲子划散，肉丝变白后出锅备用。</text>
					<text class="t2">4.油温烧到五成热时转小火倒入鱼香调料、姜末、木耳、冬笋丝，倒入鱼香汁、盐，出锅装盘即可。</text> -->
				</view>
				
			</view>
		</view>
		<view v-if="index==1"></view>
		
	</view>
</template>

<script>
	import carousel from '@/components/vear-carousel/vear-carousel.vue';
	let $this;
	export default {
		components: {
			carousel
		},
		data() {
			return {
				index:1,
				msg:"",
				imgList: [],
				imgUrl:"",
				detailBean:{}
			}
		},
		onLoad(param) {
			$this =this;
			this.loadDetail(param);
		},
		methods: {
			loadDetail(param){
				let type = param.type;
				if(type==1){
					$this.loadTodayTuiJian(param.id)
				}else if(type==2){
					$this.index = 0;
					//$this.detailBean = JSON.parse(decodeURIComponent(param.data));
					let data = JSON.parse(decodeURIComponent(param.data));
					$this.handleDetailData(data);
				}
			},
			loadTodayTuiJian(pId){
				let url = $this.reqAddress+'/tjSsgl/info/'+pId;
				$this.$api.get(url).then((res)=>{
					let data = res.data;
					if(data.code==200 && data.data!=null){
						$this.index = 0;
						//$this.detailBean  = data.data;
						$this.handleDetailData(data.data);
					}
				}).catch((err)=>{
					console.log('request fail', err);
				})
			},
			handleDetailData(data){
				let domainName = $this.domainName;
				if(data.cpfm){
					let imageObj = JSON.parse(data.cpfm.replace(/&quot;/g,"\""));
					if(imageObj.length>0){
						$this.imgUrl = domainName+imageObj[0].url;
					}else{
						$this.imgUrl = "../../static/icon/img/image-error.png";
					}
				}else{
					$this.imgUrl = "../../static/icon/img/image-error.png";
				}
				if(data.cpinfo && data.cpinfo.cpfm){
					let imgArr = JSON.parse(data.cpinfo.cpfm.replace(/&quot;/g,"\""));
					if(imgArr.length>0){
						imgArr.forEach(function(item, index) {
							let img = domainName+item.url;
							let imgBean = {url:img}
							$this.imgList.push(imgBean);
						});
					}else{
						let imgBean = {url:"../../static/icon/img/image-error.png"}
						$this.imgList.push(imgBean);
					} 
				}else{
					$this.imgUrl = "../../static/icon/img/image-error.png";
				}
				if(data.cpinfo && data.cpinfo.cpcl==null){
					data.cpinfo.cpcl ="暂无";
				}
				if(data.cpinfo && data.cpinfo.cpzf==null){
					data.cpinfo.cpzf ="暂无";
				}
				$this.detailBean  = data;
			},
			selectedBanner(item, index) {
				
			}
		}
	}
</script>

<style>
	page{
		background: #EFF8FF;
	}
	.bg{
		height: 360upx;
		width: 100%;
		opacity:0.5;	
	}
	.carousel{
		position: relative;
		top: -180upx;	
	}
	.sView{
		position: relative;
		top: -200upx;	
	}
	.star{
		display: flex;
		flex-direction: row;
		justify-content: center;
	}
	.star > image{
		height: 60upx;
		width: 60upx;
	}
	.mid{
		position: relative;
		top: -150upx;	
	}
	.context{
		display: flex;
		flex-direction: column;
		margin-left: 80upx;
		margin-right: 80upx;	
	}
	.t-center{
		display: flex;
		flex-direction: row;
		justify-content: center;
		color: #2183CE;
		font-size: 46upx;
	}
	.t1{
		color: #007AFF;
	}
	.t2{
		font-size: 26upx;
		letter-spacing:2upx;
		line-height:1.6
	}
	.m30{
		margin-top: 30upx;
	}
	.m10{
		margin-top: 10upx;
	}
</style>
