<template>
	<view>
		<view>{{loginCode}}</view>
		<button @tap="userlogin()">微信登录</button>
		<button @getphonenumber="getPhoneNumber" open-type="getPhoneNumber">phone</button>
		<button open-type="getUserInfo" @getuserinfo="getUserInfo">获取信息</button>
		<button @tap="update">更新</button>
		<button @tap="register">注册</button>
		<button @tap="logout">推出</button>
	</view>
</template>

<script>
	var _self;
	export default {
		data() {
			return {
				title: 'home',
				loginCode: '',
				openid: '',
				token: '',
				user: {
					avatarUrl: "",
					city: "",
					code: "",
					country: "",
					gender: "",
					nickName: "",
					phone: "",
					province: ""
				}
			}
		},
		onLoad() {
			_self = this;
			console.log(uni.getStorageSync("phone"));
			uni.login({
			  provider: 'weixin',
			  success: function (loginRes) {
			    console.log(loginRes.code);
				_self.loginCode = loginRes.code;
			  }
			});

		},
		methods: {
			userlogin(){
				let that = this;
				uni.request({
				    url: this.domainName + '/wechatlogin',
					method:"POST",
					data: {
					  "code": _self.loginCode,
					},
				    success: (res) => {
						if (res.data.code == 200) {
							console.log(res);
							_self.openid = res.data.data.loginClientUserVo.id;
							// console.log(res.data);
							that.token = res.data.data.token;
							// console.log(that.token);
						} else {
							console.log(res.data.msg);
						}
				    }
				});
			},
			getUserInfo(e){
				console.log(e);
				console.log(e.detail.rawData);
				_self.parseRawdata(e.detail.rawData);
			},
			getPhoneNumber(e){
				let that=this;
				var edata = e.detail.encryptedData;
				var iv = e.detail.iv;
				uni.login({
				  provider: 'weixin',
				  success: function (loginRes) {
				    console.log(loginRes.code);
					uni.request({
					    url: that.domainName + '/wechatPhone',
						method:"POST",
						data: {
						  "code":  loginRes.code,
						  "encryptedData": edata,
						  "iv": iv,
						  "openid":_self.openid
						},
					    success: (res) => {
							var data = res.data.data;
							_self.phone=data.phoneNumber;
							uni.setStorageSync("phone",_self.phone);
							console.log(res);
					    }
					});
				  }
				});				
			},
			register(e){
				let that = this;
				uni.login({
				  provider: 'weixin',
				  success: function (loginRes) {
					console.log(loginRes.code);
					uni.request({
						url: that.domainName + '/wechatRegisterOrBind',
						method:"POST",
						data: {
							"avatarUrl": _self.avatarUrl,
							"city": _self.city,
							"code": loginRes.code,
							"country": _self.country,
							"gender": _self.gender,
							"nickName": _self.nickName,
							"phone": _self.phone,
							"province": _self.province
						},
						success: (res) => {
							console.log(res);
						}
					});
					}
				});	
			},
			parseRawdata(rawdata){
				var raw = rawdata.split(',');
				var tmp;
				for (var i = 0; i < raw.length; i++) {
					console.log(raw[i]);
				}
				tmp = raw[0].split(':')[1];
				_self.nickName = tmp.substring(1, tmp.length-1);
				tmp = raw[1].split(':')[1];
				_self.gender = tmp;
				tmp = raw[2].split(':')[1];
				_self.language = tmp.substring(1, tmp.length-1);
				tmp = raw[3].split(':')[1];
				_self.city = tmp.substring(1, tmp.length-1);
				tmp = raw[4].split(':')[1];
				_self.province = tmp.substring(1, tmp.length-1);
				tmp = raw[5].split(':')[1];
				_self.country = tmp.substring(1, tmp.length -1);
				tmp = raw[6];
				tmp = tmp.substring(tmp.indexOf(':') + 2, tmp.length -2)
				_self.avatarUrl = tmp;
			},
			update(e){
				//POST /wechatUpdate
				uni.request({
					url: this.domainName + '/wechatUpdate',
					method:"POST",
					data: {
						"avatarUrl": _self.avatarUrl,
						"city": _self.city,
						// "openId": '',
						"openId": _self.openid,
						"country": _self.country,
						"gender": _self.gender,
						"nickName": _self.nickName,
						"phone": '',
						"phone": _self.phone,
						"province": _self.province
					},
					success: (res) => {
						console.log(res);
					}
				});
			},
			logout(e){
				uni.request({
					url: this.domainName + '/wechatlogout',
					method:"POST",
					header:{
						'token': this.token, 
					},
					success: (res) => {
						console.log(res);
						if(res.data.code == 200)
							console.log(res);
						// else
						// 	console.log(res.data.data.msg);
					}
				});
			}
		}
	}
</script>

<style>

</style>
