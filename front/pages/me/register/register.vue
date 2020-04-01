<template>
	<view class="container">
		<view class="form">
			<view class="row">
				<text>手机号：</text>
				<input @blur="quaryPhone" type="text" placeholder="请输入手机号" v-model.lazy="user.phone" />
			</view>
			<view class="hintdefcolor" :style='{color: user.hintColor}'>
			<text>{{user.phoneHint}}</text>
			</view>
			<view class="row">
				<text>密 码：</text>
				<input @blur="checkPwd(0)" type="text" placeholder="请输入密码" password="true" v-model="user.password" />
			</view>
			<view class="row">
				<text>确认密码：</text>
				<input @blur="checkPwd(1)" type="text" placeholder="请再次输入密码" password="true" v-model="user.rePassword" />
			</view>
			<!-- <view class="hintdefcolor"> -->
			<!-- <text>{{user.pwdHint}}</text> -->
			<!-- </view> -->
			<view class="img-code">
				<image @tap="loadVaildCode" class="img" :src="user.image"></image>
				<input class="txt-size img-code-input" type="text" placeholder="请输入验证码" v-model="user.imgCode" />

			</view>
			<view class="img-code">
				<button :disabled="info.flag" class="txt-size sms-btn" type="primary" @tap="getSms()">{{info.getsmsbtn}}</button>
				<input class="txt-size img-code-input" type="text" placeholder="请输入短信验证码" v-model="user.smsCode" />
			</view>
			<button class="btn-register" type="primary" @tap="signIn">注册</button>

		</view>
	</view>
</template>

<script>
	var _self;
	export default {
		data() {
			return {
				user: {
					phone: "18924679759",
					phoneHint:"",
					gender: "",
					password: "",
					rePassword: "",
					// pwdHint:"",
					hintColor:"#87DC49",
					image: "",
					imgCode: "",
					smsCode: "",
					verifyToken: "",
					smsVerifyToken: ""
				},
				items: [{
						value: '0',
						name: '男'
					},
					{
						value: '1',
						name: '女'
					}
				],
				info: {
					getsmsbtn: "获取短信验证码",
					flag: false,
					count: 1
				}
			}
		},
		onLoad() {
			_self = this;
			_self.loadVaildCode();
		},
		methods: {
			/**
			 * function: 
			 * return 0: success
			 * return !0: fail
			 * 
			 * */
			loadVaildCode() {
				uni.request({
					url: 'http://120.24.53.84/ysapi/verificationCode/getBase64Image',
					success: (res) => {
						var data = res.data;
						if (data.code == 200 && data.data != null) {
							// console.log(data.code)
							_self.user.image = data.data.image;
							_self.user.verifyToken = data.data.verifyToken;
						}
					}
				});

			},
			setSmsBtnStatus(count) {
				var time = 60 * count;

				var timer = setInterval(function() {
					if (time == 0) {
						_self.info.getsmsbtn = "获取短信验证码";
						_self.info.flag = false;
						_self.loadVaildCode();
						clearInterval(timer);
						return;
					}
					_self.info.flag = true;
					_self.info.getsmsbtn = time-- + "s";

				}, 1000);
			},
			showMsg(msg) {
				uni.showToast({
					title: msg,
					icon: "none",
					duration: 1000
				});
			},
			checkPhoneNumber(val) {
				var reg = /^1[3456789]\d{9}$/;
				var ret = true;
				if (reg.test(val)) {
					ret = !ret;
				} else {
					_self.showMsg("手机号不合法");
				}
				return ret;
			},
			quaryPhone() {
				var ret = true;
				if (this.checkPhoneNumber(_self.user.phone)) {
					_self.user.phoneHint = '手机号不合法,请重新填写';
					_self.user.hintColor = '#ff0000';
					return ret;
				}
				// 做用户是否存在校验
				uni.request({
					url: 'http://120.24.53.84/ysapi/checkuser',
					method: "POST",
					data: {
						"phone": _self.user.phone
					},
					success: (res) => {
						var data = res.data;
						if (data.code == 200) {
							if (!data.data) {
								// _self.showMsg("手机号未注册，可以使用");
								_self.user.phoneHint = '手机号未注册，可以使用';
								_self.user.hintColor = '#87DC49';
								return false;
							} else {
								// _self.showMsg("手机号已注册，请直接登录");
								_self.user.phoneHint = '手机号已注册，请直接登录';
								_self.user.hintColor = '#ff0000';
								return true;
							}
						}
					}
				});
			},
			checkPwd(val) {
				var ret = true;
				if (val == 0) {
					if (!(/^[0-9A-Za-z]{6,10}$/.test(_self.user.password))) {
						_self.showMsg("请输入6到10位(数字或者字母)");
						return ret;
					}
				} else {
					if (!(/^[0-9A-Za-z]{6,10}$/.test(_self.user.rePassword))) {
						_self.showMsg("请输入6到10位(数字或者字母)");
						return ret;
					}
					if (_self.user.password != _self.user.rePassword) {
						_self.showMsg("两次输入的密码不一致");
						return ret;
					}
				}
				return !ret;
			},
			validateSendMsg() {
				var ret = true;
				if (_self.user.imgCode == 'undefined' || !_self.user.imgCode || !/[^\s]/.test(_self.user.imgCode)) {
					_self.showMsg("请输入验证码");
					return ret;
				}
				ret = this.checkPhoneNumber(_self.user.phone);
				return !ret;
			},
			getSms() {
				//发送短信前提验证(手机号和验证码验证)
				if (!_self.validateSendMsg()) {
					return;
				}
				uni.request({
					url: 'http://120.24.53.84/ysapi/verificationCode/sendmsg',
					method: "POST",
					data: {
						"code": _self.user.imgCode,
						"msgtype": "2", //注册发短信类型
						"phone": _self.user.phone,
						"verifyToken": _self.user.verifyToken
					},
					success: (res) => {
						var data = res.data;
						var time = 60;
						if (data.code == 200) {
							_self.user.smsVerifyToken = data.data.requestId;
							this.setSmsBtnStatus(_self.info.count);
							_self.info.count += 5;
						} else if (data.code == 5103) {
							_self.showMsg("验证码过期或不正确");

						}
					}
				});
			},
			checkImgCode(){
				var ret = true;
				if (_self.user.imgCode.length != 4) {
					_self.showMsg("验证码格式不对");
					return ret;
				}
				return !ret;
			},
			validateRegisterInfo() {
				var ret = true;
				_self.validateSendMsg();
				if (_self.user.smsCode == 'undefined' || !_self.user.smsCode || !/[^\s]/.test(_self.user.smsCode)) {
					_self.showMsg("请输入短信验证码");
					return ret;
				}
				return !ret;
			},
			signIn() {
				if (this.quaryPhone()
					||this.checkPhoneNumber(_self.user.phone)
					|| this.checkPwd(0)
					|| this.checkPwd(1)
					|| this.checkImgCode()
					|| this.validateRegisterInfo()) {
					return;
				}

				uni.request({
					url: 'http://120.24.53.84/ysapi/register',
					method: "POST",
					data: {
						"phone": _self.user.phone,
						"gender": _self.user.gender,
						"password": _self.user.password,
						"smscode": _self.user.smsCode,
						"smsVerifyToken": _self.user.smsVerifyToken
					},
					success: (res) => {
						console.log(res);
						var data = res.data;
						var msg = data.msg;
						if (data.code == 200) {
							_self.showMsg(msg);
						} else {
							_self.showMsg("验证信息填写错误");
						}
					}
				});
			}
		}
	}
</script>
<style>
	.container {
		padding: 80upx;
	}

	.txt-size {
		font-size: 12px;
		height: 70upx;
	}

	.btn {
		margin-top: 0upx;
	}

	.img {
		height: 70upx;
		width: 160upx;
		margin-right: 20upx;
	}

	.img-code {
		display: flex;
		flex-direction: row;
	}

	.row {
		display: flex;
		flex-direction: row;
		width: 100%;
		align-self: center;
	}

	.row text {
		height: 70upx;
		line-height: 70upx;
		font-size: 16px;
		text-align: left;
		width: 30%;
	}

	.radio {
		margin-right: 40upx;
	}

	.row input {
		height: 70upx;
		border: solid 2upx #DDDDDD;
		text-indent: 20upx;
		margin-bottom: 20upx;
		width: 70%;
	}

	.btn-register {
		width: 100%;
		margin-top: 40upx;
		margin-bottom: 20upx;
	}

	.img-code-input {
		width: 480upx;
		padding-left: 10upx;
		margin-left: 20upx;
		border: solid 2upx #DDDDDD;
		margin-bottom: 20upx;
	}

	.sms-btn {
		width: 300upx;
		margin-left: 0upx;
	}

	.register {
		font-size: 16px;
		padding: 20upx;
	}

	.hintdefcolor {
		font-size: 8upx;
		color: #ff0000;
		margin-left: 30%;
		margin-bottom: 10upx;
	}
</style>
