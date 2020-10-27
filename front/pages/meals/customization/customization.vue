<template>
	<view class="bg">
		<view class="type-head">
			<image class="title-img"  src="../../../static/icon/settings/icon.png"></image>
			<view class="type-title">深入订制</view>
		</view>
		<view style="width: 80%;margin-bottom: 60px;">
		         <xfl-select 
		             :list="list"
		             :clearable="false"
		             :showItemNum="5" 
		             :listShow="false"
		             :isCanInput="false"  
		             :style_Container="'height: 40px; font-size: 16px;'"
		             :placeholder = "'placeholder'"
		             :initValue="'苹果'"
		             :selectHideType="'hideAll'"
		         >
		         </xfl-select>
		 </view>		
	</view>
</template>

<script>
	import xflSelect from '@/components/xfl-select/xfl-select.vue'; 
	export default {
		components: { xflSelect },  //注册为子组件
		data() {
			return {
				list: [       //要展示的数据
					'苹果',
					'香蕉',
					'葡萄',
					'芒果',
					'大白菜',
				],
			}
		},
		   
		methods: {
			
		}
	}
</script>

<style>
	@import url("./customization.css");
</style>
