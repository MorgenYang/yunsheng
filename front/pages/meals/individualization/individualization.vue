<template>
	<view class="bg">
	
	   <view class="type-head">
			<image class="title-img"  src="../../../static/icon/settings/icon.png"></image>
			<view class="type-title">生理信息</view>
	   </view> 
	  
	   <view v-for="(item,index) in itemList1" style="width: 90%; margin: auto; margin-bottom: 20upx;" 
			v-bind:key="item.id">
			<xfl-select 
				:list="list1[index]"
				:clearable="true"
				:isCanInput="false"  
				:selectHideType="'hideAll'"
				:style_Container="listBoxStyle"
				:placeholder = "item">
			</xfl-select>
	   </view>
	   <!-- <view class="divider"/> -->
	   <view class="type-head other">
			<image class="title-img"  src="../../../static/icon/settings/icon.png"></image>
			<view class="type-title">医疗相关数据</view>
	    </view> 
		<view class="medical">
			<view>糖尿病<checkbox value="r1"/></view>
			<view>高血压<checkbox value="r1"/></view>
			<view>冠心病<checkbox value="r1"/></view>
			<view>肥胖病<checkbox value="r1"/></view>
			<view>痛风<checkbox value="r1"/></view>
			<view>癌症<checkbox value="r1"/></view>
			<view>免疫性疾病<checkbox value="r1"/></view>
		</view>
		<!-- <view class="divider"/> -->
		<view class="type-head">
			<image class="title-img"  src="../../../static/icon/settings/icon.png"></image>
			<view class="type-title">体力活动水平</view>
		</view>
	    <view v-for="(item,index) in itemList2" style="width: 90%; margin: auto; margin-bottom: 20upx;"
	  			v-bind:key="item.id">
	  			<xfl-select 
	  				:list="list2[index]"
	  				:clearable="true"
	  				:isCanInput="false"  
	  				:selectHideType="'hideAll'"
	  				:style_Container="listBoxStyle"
	  				:placeholder = "item">
	  			</xfl-select>
	    </view>
		<view class="sp-title">医疗相关数据</view>
		<view class="medical sp-item">
			<view>有氧耐力运动<checkbox value="r1"/></view>
			<view>肌肉力量运动<checkbox value="r1"/></view>
			<view>关节柔韧性运动<checkbox value="r1"/></view>
		</view>  
	</view>
</template>

<script>
	import xflSelect from '@/components/xfl-select/xfl-select.vue'; 
	export default {
		components: { xflSelect},  //注册为子组件
		data() {
			return {
				itemList1:["性别","年龄","身高","体重","腰围","孕期(月份)",
						"哺乳(月份)"],
				listBoxStyle: `height: 70upx; font-size: 14px;`,
				list1: [
					[ '男','女'],
					[ '20以下','20-30','30-40','40-50','50以上'],
					[ '150cm以下','150-160cm','160-170cm','170-180cm','190cm以上'],
					[ '50kg以下','50kg-60kg','60kg-70kg','70kg以上'],
					[ '50以下','50-60','60-70','70-80','80以上'],
					[ '1','2','3','4','5','6','7','8','9','10'],
					[ '1-3','4-6','7-9','9-12']
				],
				itemList2:["平均每日步数","每周运动次数","每次运动时间"],
				list2: [
					[ '500以下','500-1000','1000以上'],
					[ '5次以下','5-10次','10次以上'],
					[ '10分钟以下','10-60分钟','60分钟以上']
				],
			}
		},
		   
		methods: {
			 bindPickerChange: function(e) {
				let index = e.target.id;
				this.setting[index].choose=e.target.value==1?'是':'否'; 
			 },
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
	.divider{
		 background: #3AA9D1;
		 width: 100%;
		 margin-top: 30upx;
		 height: 1upx;
		}
	.other{
		padding:30upx 30upx 0upx 30upx;
	}
	.medical > view{
		padding-left: 70upx;
		font-size: 14px;
		padding-top: 20upx;
	}
	.medical > view >checkbox{
		padding-left: 20upx;
	}
	.sp-title{
		font-size: 14px;
		padding-left: 40upx;
	}
	.sp-item{
		padding-bottom: 60upx;
	}
	.sp-item> view{
		color: #A9ABA5;
		padding-left: 60upx;
	}
</style>
