<template>
	<view class="content">
		<view>
			<image class="feedback-title-img"  src="../../../static/icon/settings/icon.png"></image>
			<text class="feedback-title">反馈信息</text>
		</view>
		<textarea class="feedback-border textarea" v-model="inputContext" placeholder="请留下您的反馈内容"  placeholder-style='color:#ccc;font-size:13px'  maxlength="200" @input="limitNum" />
		<text class="text-num">{{inputLength}}/200字</text>
		
		<input class="feedback-border contact-input" placeholder="您的联系方式(手机号、微信号、邮箱......)" placeholder-class="hint-color" />
	
		<text class="feedback-hint top">欢迎为我们提出宝贵的意见和建议,您的</text>
		<text class="feedback-hint bottom" >每一次反馈是我们前进的动力!</text>
	
		<button class="btn" @click="suggest()">提交</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				inputContext:"",
				inputLength:0
			}
		},
		methods: {
			limitNum(e){
				let num = e.detail.value.length;
				this.inputLength = num;
			},
			suggest(){
				this.inputContext = this.inputContext.trim();
				if(typeof this.inputContext == "undefined" || 
					this.inputContext == null ||
					this.inputContext ==""){
					uni.showToast({
					    title: "反馈内容为空",
						icon:"none",
					    duration: 300
					});
					return;
				}
				//意见反馈
				uni.request({
					url: this.reqAddress +'/tjYjfk/add',
					method:"POST",
					header: {
						'token':'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ3ZWIiLCJpc3MiOiJ5c2FwaSIsImV4cCI6MTYwODA4MDkyMywiaWF0IjoxNjA3ODI4OTIzLCJqdGkiOiI5ZTNlOWQ0OTMyNzM0NmM2OTk0ZjI2YTA4ZjBiZWVjNyIsInVzZXJuYW1lIjoiby03LUk1TnBRUHJULUhqNmN4UXdMLVFxS2U5ZyJ9.sMzFrKb9NuQYs-LJCB8sW0P1D4GLKGXV48ZNH-GMot0'
					},
					data:{
						"id":"0" ,
						"modId":"193", //默认为意见反馈模块
						//"createUser":""
						"yjfk":this.inputContext
					},
					success: (res) => {
						var data = res.data;
						if(data.code==200){
							this.inputContext ="";
							uni.showToast({
							    title: "反馈成功,谢谢您的建议!",
								icon:"none",
							    duration: 2000
							});
						}
					}
				});
			}
		}
	}
</script>

<style>
	@import url("./feedback.css");
</style>
