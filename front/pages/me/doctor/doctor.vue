<template>
	<view>
		<view class="header">
			<view class="nurse">
				<image :src="doctors[0].img"></image>
				<text class="post">{{doctors[0].post}}</text>
				<text class="dname">{{doctors[0].name}}</text>
			</view>
			<view class="doctor">
				<image :src="doctors[1].img"></image>
				<text class="post">{{doctors[1].post}}</text>
				<text class="dname">{{doctors[1].name}}</text>
			</view>
			<view class="nurse">
				<image :src="doctors[2].img"></image>
				<text class="post">{{doctors[2].post}}</text>
				<text class="dname">{{doctors[2].name}}</text>
			</view>
		</view>
		<view class="content">
			<view>
				<image class="title-img"  src="../../../static/icon/settings/icon.png"></image>
				<text class="title">团队介绍</text>
			</view>
			<view class="doctor-introduce">
				<text class="name">{{doctors[1].name}}：</text>
				<text class="introduce">{{doctors[1].introduce}}</text>
			</view>
			<view>
				<text class="name">{{doctors[2].name}}：</text>
				<text class="introduce">{{doctors[2].introduce}}</text>
			</view>
			<text class="introduce">......</text>
			<view class="service">
				<image class="title-img"  src="../../../static/icon/settings/icon.png"></image>
				<text class="title">服务内容</text>
			</view>
			<view class="service-dispaly">
				<view class="service-btn service-btn-left" @tap="onlineVisits" hover-class="setting-click">
					<image class="icon" src="../../../static/icon/settings/online-icon.png"></image>
					在线问诊
				</view>
				<view class="service-btn service-btn-right">
					<image class="icon" src="../../../static/icon/settings/movie-icon.png"></image>
					视频问诊
				</view>
			</view>
			<view class="service-dispaly service-dispaly-bottom">
				<view class="service-btn service-btn-left">
					<image class="icon" src="../../../static/icon/settings/medicine-icon.png"></image>
					用药指导
				</view>
				<view class="service-btn service-btn-right">
					<image class="icon" src="../../../static/icon/settings/service-icon.png"></image>
					预约服务
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				doctors:[
					{
						name:"胡静",
						post:"护士",
						img:"http://inews.gtimg.com/newsapp_bt/0/10901275116/1000.jpg",
						introduce:""
					},
					{
						name:"张天宏",
						post:"主任医师",
						img:"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1647278778,916698284&fm=15&gp=0.jpg",
						introduce:"医学博士、教授、博士生导师,天津心脏病学研究所所长、心脏科主任、"+
							"中华医学会心电生理与起搏分会常委、中国医师协会心血管内科医师分会常委、中华医学会心血管病分会委员、"+
							"中国心力衰竭学会主委、中国心血管杂志副主编。擅长复杂心血管疾病的诊治"+
							"及冠心病和心律失常的介入治疗、心脏起搏器安置术等。",
					},
					{
						name:"林林",
						post:"助理医师",
						img:"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=26588126,1713270113&fm=15&gp=0.jpg",
						introduce:"首都医科大学医学博士,长期从事心血管疾病诊疗"+
							"工作,对高血压、冠心病、心律失常等常见心血管疾病有"+
							"丰富的临床工作经验,且擅长诊治各种疑难复杂心血管疾"+
							"病,在复杂心律失常的诊断及治疗、起搏与电生理治疗及"+
							"随访方面具有丰富经验。"
					}
					
				]	
			}
		},
		methods: {
			onlineVisits(e){
				uni.showToast({
				    title: "在线问诊",
					icon:"none",
				    duration: 300
				});
				uni.navigateTo({
				    url: "./chat"
				});
			}
			
		}
	}
</script>

<style>
@import url("doctor.css");
</style>
