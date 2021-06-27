<template>
	<view>
		<view v-for="(item, index) in list" :key="index" class="item setting-line"
			 hover-class="setting-click" @click="searchH(item)">
			{{item}}
		</view>
	</view>
	
</template>

<script>
	export default {
		data() {
			return {
				list:[]
			}
		},
		async onLoad() {
			let searchContent = uni.getStorageSync("searchHistory");
			if(searchContent!=null && searchContent!=undefined && searchContent!=""){
				let strArr = searchContent.split("###");
				for(let i=strArr.length;i>0;i--){
					if(strArr[i]!=null ){
						this.list.push(strArr[i])
					}
				}
			}
		},
		methods: {
			searchH(item){
				uni.setStorageSync("loadHistory","1");
				uni.setStorageSync("loadHistoryContext",item);
				uni.navigateBack();
			}
		}
	}
</script>

<style>
	page, .content{
		background-color: #F1F1F1;
	}
	.item{
		padding-left: 30upx;
		padding-top: 10upx;
		background-color: #FFFFFF;
	}
	.setting-line{
		display: flex;
		flex-flow: row;
		align-items:center;
		height: 100upx;
		margin-bottom: 2upx;
	}
	.setting-click{
		background-color:#F5F5F5;
	}
	
</style>
