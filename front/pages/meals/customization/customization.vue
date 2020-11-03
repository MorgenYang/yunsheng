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
	   <view class="setting-body">
		<block v-for="(item,index) in setting" hover-class="setting-click" v-bind:key="item.id" @tap="itemClick" >
			<picker class="setting-line"  @change="bindPickerChange"  :range="array" v-bind:id="item.id">            
				<!-- <image class="setting-img" src="/static/icon/settings/health-record.png"></image> -->
				<!-- <image class="setting-img" :src="item.imageSrc"></image> -->
				<text class="setting-text">{{item.title}}</text>
				<text class="choose-value">{{item.choose}}</text>
				<image class="arrows-right-setting" src="/static/icon/settings/arrows-right-setting.png"></image>
			</picker>
			<view :class="{line:(index !=setting.length-1)}"></view>
		</block>
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
				array: ['否', '是'],
				setting: [{
						id: 0,
						title: "是否素食",
						imageSrc: "/static/icon/settings/health-record.png",
						choose:''
					},
					{
						id: 1,
						title: "回避食物",
						imageSrc: "/static/icon/settings/store.png",
						choose:''
					},
					{
						id: 2,
						title: "是否包含薯类",
						imageSrc: "/static/icon/settings/doctor.png",
						choose:''
					},
					{
						id: 3,
						title: "是否包含豆制品",
						imageSrc: "/static/icon/settings/settings.png",
						choose:''
					},
					{
						id: 4,
						title: "是否喝豆浆",
						imageSrc: "/static/icon/settings/settings.png",
						choose:''
					},
					{
						id: 5,
						title: "是否喝牛奶",
						imageSrc: "/static/icon/settings/settings.png",
						choose:''
					}
					
				]
			}
		},
		   
		methods: {
			 bindPickerChange: function(e) {
				let index = e.target.id;
				this.setting[index].choose=e.target.value==1?'是':'否'; 
			 }
		}
	}
</script>

<style>
	@import url("./customization.css");
</style>
