<template>
	<view>
		<video class="video" autoplay="true" :src="item.url" :poster="item.image"></video>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				item:null
			}
		},
		onLoad: function (option) {
			var data =  JSON.parse(decodeURIComponent(option.item));
			//console.log(data);
			this.item = data;
		},
		methods: {
			
		}
	}
</script>

<style>
	.video{
		width: 100%;
		height: 420upx;
	}
</style>
