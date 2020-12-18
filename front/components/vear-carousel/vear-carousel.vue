<template>
	<swiper class="image-container" previous-margin="165rpx" next-margin="165rpx" circular autoplay @change="swiperChange">
		<swiper-item :class="currentIndex == index ? 'swiper-item' : 'swiper-item-side'" v-for="(item, index) in imgList" :key="item[urlKey]">
			<image @click="clickImg(item)" :class="currentIndex == index ? 'item-img' : 'item-img-side'" :src="item[urlKey]" lazy-load :style="dontFirstAnimation ? 'animation: none;' : ''" mode="aspectFill"></image>
		</swiper-item>
	</swiper>
</template>
<script>
	export default {
		props: {
			imgList: {
				type: Array,
				default() {
					return []
				}
			},
			urlKey: {
				type: String,
				default() {
					return ''
				}
			},
		},
		data() {
			return {
				currentIndex: 0,
				dontFirstAnimation: true
			}
		},
		methods: {
			swiperChange(e) {
				this.dontFirstAnimation = false
				this.currentIndex = e.detail.current
			},
			clickImg(item) {
				this.$emit('selected', item, this.currentIndex)
			}
		}
	}
</script>
<style scoped>
	.image-container {
		width: 750rpx;
		height: 350rpx;
	}
	.item-img {
		width: 330rpx;
		height: 300rpx;
		border-radius: 14rpx;
		animation: to-big .3s;
		/* border: 1upx solid gray; */
		/* box-shadow: 2upx 2upx 2upx 2upx #D3D3D3; */
	}
	.swiper-item {
		width: 330rpx;
		height: 200rpx;
		display: flex;
		justify-content: center;
		align-items: center;
		
	}
	.item-img-side {
		width: 330rpx;
		height: 200rpx;
		border-radius: 14rpx;
		animation: to-mini .3s;
	}
	.swiper-item-side {
		width: 630rpx;
		height: 260rpx;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	@keyframes to-mini
	{
		from {
			height: 300rpx;
		}
		to {
			height: 200rpx;
		}
	}
	@keyframes to-big
	{
		from {
			height: 200rpx;
		}
		to {
			height: 300rpx;
		}
	}
</style>
