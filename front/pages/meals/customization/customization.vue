<template>
	<view class="bg">
	
	   <view class="type-head">
			<image class="title-img"  src="../../../static/icon/settings/icon.png"></image>
			<view class="type-title">深入订制</view>
	   </view> 
	  
	   <view v-for="item in itemList" style="width: 90%; margin: auto; margin-bottom: 20upx;" 
			v-bind:key="item.id">
			<xfl-select 
				:list="list"
				:clearable="true"
				:isCanInput="false"  
				:selectHideType="'hideAll'"
				:style_Container="listBoxStyle"
				:placeholder = "item">
			</xfl-select>
	   </view>
 
		<view class="type-head">
			<image class="title-img"  src="../../../static/icon/settings/icon.png"></image>
			<view class="type-title">饮食习惯</view>
		</view>
		<view v-for="item1 in itemList1" style="width: 90%; margin: auto; margin-bottom: 20upx;"
			v-bind:key="item1.id">
			<xfl-select 
				:list="list1"
				:clearable="true"
				:isCanInput="false"  
				:selectHideType="'hideAll'"
				:style_Container="listBoxStyle"
				:placeholder = "item1">
			</xfl-select>
		</view>
	 <view class="bottom-view">
		  <button hover-class='none'  @tap="individualization()" class="custom-btn">个性化定制</button>
	 </view>
	</view>
</template>

<script>
	import xflSelect from '@/components/xfl-select/xfl-select.vue'; 
	export default {
		components: { xflSelect},  //注册为子组件
		data() {
			return {
				itemList:["每日餐次","每日主食种类","早餐主食种类","早餐蔬菜种类","午餐主食种类","午餐菜品种类",
						"晚餐菜品种类","每日最低蔬菜种类","每日水果种类"],
				listBoxStyle: `height: 70upx; font-size: 14px;`,
				list: [       //要展示的数据
					'苹果',
					'香蕉',
					'葡萄',
					'芒果',
					'大白菜',
				],
				itemList1:["是否素食","回避食物","是否包含薯类","是否包含豆制品","是否喝豆浆","是否喝牛奶"],
				list1:['否','是']
			}
		},
		methods: {	
			 individualization:function(e){
				 uni.navigateTo({
					 url: "/pages/meals/individualization/individualization"
				 });
			 }
		}
	}
</script>

<style>
	@import url("../item.css");
</style>
