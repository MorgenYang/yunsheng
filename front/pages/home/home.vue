<template>
	<view>
		<view>{{loginCode}}</view>
		<button @tap="login()">微信登录</button>
		<button @getphonenumber="getPhoneNumber1" open-type="getPhoneNumber">phone</button>
		<button open-type="getUserInfo" @getuserinfo="getUserInfo">获取信息</button>
	</view>
</template>

<script>
	var _self;
	export default {
		data() {
			return {
				title: 'home',
				loginCode: ''
			}
		},
		onLoad() {
			_self = this;
			uni.login({
			  provider: 'weixin',
			  success: function (loginRes) {
			    console.log(loginRes.code);
				_self.loginCode = loginRes.code;
			  }
			});

		},
		methods: {
			login(){
				uni.request({
				    url: this.domainName + '/ysapi/wechatlogin',
					method:"POST",
					data: {
					  "code": _self.loginCode,
					},
				    success: (res) => {
						var data = res.data;
						console.log(data);
				    }
				});
			},
			getUserInfo(e){
				console.log(e);
			},
			getPhoneNumber1(e){
				console.log(e);
			}
			
		}
	}
</script>

<style>

</style>
