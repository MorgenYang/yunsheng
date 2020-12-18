<template>
	<view>
		<view class="head">
			<image class="bg" src="https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1095246231,651284479&fm=26&gp=0.jpg"></image>
			<carousel class="carousel" :img-list="imgList" url-key="url" @selected="selectedBanner"/>
			<view class="sView">
				<view class="star">
					<image src="../../static/icon/img/star.png" ></image>
					<image src="../../static/icon/img/star.png" ></image>
					<image src="../../static/icon/img/star.png" ></image>
				</view>
			</view>
		</view>	
		<view class="mid">
			<text class="t-center">《鱼香肉丝》</text>
			<view class="context">
				<text class="t1">材料:</text>
				<text class="t2 m10">猪肉，姜米，蒜米，酱油，精盐，豌豆尖（可选），醋，泡辣椒，鲜汤，白糖，水豆粉，黑木耳，（可选）玉兰片，（可选）莴笋丝，胡萝卜丝，竹笋丝，蚝油(可选)，干辣椒，（可选）土豆丝，豆瓣酱</text>
				<text class="t1 m30">做法:</text>
				<text class="t2 m10">1.里脊肉洗净切成长条，加入料酒、鸡蛋清、水淀粉、盐搅拌均匀，腌制20分钟。冬笋、木耳切丝。</text>
				<text class="t2">2.碗中放入酱油、白糖、醋、葱末、水淀粉调匀成鱼香汁备用。</text>
				<text class="t2">3.锅中放油，油温五成热时放入肉丝，用铲子划散，肉丝变白后出锅备用。</text>
				<text class="t2">4.油温烧到五成热时转小火倒入鱼香调料、姜末、木耳、冬笋丝，倒入鱼香汁、盐，出锅装盘即可。</text>
			</view>
			
		</view>
	</view>
</template>

<script>
	import carousel from '@/components/vear-carousel/vear-carousel.vue'
	    export default {
	        components: {
	            carousel
	        },
	        data() {
	            return {
	                imgList: [{
	                    url: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1604987854994&di=738dd93caf003a7d8cbc564ca8a0043e&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fbaike%2Fs%253D220%2Fsign%3D82060687349b033b2888fbd825cf3620%2F37d12f2eb9389b5019eefbd18535e5dde7116e1b.jpg',
	                    id: 1
	                },{
	                    url: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1604987931040&di=b3e72ba46fd987e683342ea4b9edd4e1&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fbaike%2Fs%253D220%2Fsign%3D863a78a8a8d3fd1f3209a538004f25ce%2Faa18972bd40735fae27ca53f9e510fb30e2408a3.jpg',
	                    id: 2
	                },{
	                    url: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1604987970296&di=8911913a5fa39d5ded7e02122bf30f72&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fbaike%2Fs%253D220%2Fsign%3D44a6b60ad143ad4ba22e41c2b2035a89%2F060828381f30e924250cd79e4c086e061c95f7f1.jpg',
	                    id: 3
	                },{
	                    url: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=713409442,2578499609&fm=11&gp=0.jpg',
	                    id: 4
	                },]
	            }
	        },
	        methods: {
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
