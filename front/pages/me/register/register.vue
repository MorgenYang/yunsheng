<template>
	<view class="container">
	    <view class="form">
		  <view class="row">
			  <text>手机号：</text>
			  <input type="text" placeholder="请输入手机号" v-model="user.phone" />
		  </view>
		  <view class="row">
			  <text>性别：</text>
			<view class="gender">
				 <radio-group @change="radioChange">
					<label class="radio"><radio value="0" checked="true"/>男</label>
					<label class="radio"><radio value="1" />女</label>
				</radio-group>
			</view>
		  </view>
	     <view class="row">
			  <text>密  码：</text>
			  <input type="text" placeholder="请输入密码" password="true" v-model="user.password" />
	     </view>
	     <view class="row">
			  <text>确认密码：</text>
			  <input type="text" placeholder="请再次输入密码" password="true" v-model="user.rePassword" />
	     </view> 
		  <view class="img-code">
			  <image class="img" :src="user.image"></image>
			  <input class="txt-size img-code-input" type="text" placeholder="请输入验证码" v-model="user.imgCode"/>
			  
		  </view>
		  <view class="img-code">
			  <button class="txt-size sms-btn" type="primary" @tap="getSms()">获取短信验证码</button>
			  <input class="txt-size img-code-input" type="text" placeholder="请输入短信验证码" v-model="user.smsCode"/>
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
				user:{
					phone:"13439048677",
					password:"",
					rePassword:"",
					image:"",
					gender:0,
					imgCode:"",
					smsCode:"",
					verifyToken:"",
					smsVerifyToken:""
				},
				items: [{
					value: '0',
					name: '男'
				},
				{
					value: '1',
					name: '女'
				}]
			}
		},
		onLoad() {
			_self = this;
			this.loadVaildCode();
		},
		methods: {
			 radioChange(evt){
				for (let i = 0; i < this.items.length; i++) {
					if (this.items[i].value === evt.target.value) {
						this.current = i;
						break;
					}
				}
			},
			loadVaildCode(){
				uni.request({
				    url: 'http://120.24.53.84/ysapi/verificationCode/getBase64Image',
				    success: (res) => {
						var data = res.data;
						if(data.code==200 && data.data!=null){
							_self.user.image = data.data.image;
							_self.user.verifyToken = data.data.verifyToken;
							//_self.user.imgCode = data.data.code;
						}
				    }
				});
			},
			getSms(){
				//发送短信前提验证(手机号和验证码验证)
				if(!_self.validateSendMsg()){
					return;
				}
			
				
				uni.request({
				    url: 'http://120.24.53.84/ysapi/verificationCode/sendmsg',
					method:"POST",
					data: {
					  "code":_self.user.imgCode,
					  "msgtype": "2",//注册发短信类型
					  "phone": _self.user.phone,
					  "verifyToken":_self.user.verifyToken
					},
				    success: (res) => {
						console.log("====发送短信=====");
						console.log(res);
						var data = res.data;
						if(data.code==200){
							_self.user.smsVerifyToken = data.data.requestId;
						}
				    }
				});
			},
			signIn(){
				if(!_self.validateRegisterInfo()){
					return;
				}
				console.log("+=================smsVerifyToken");
				console.log(_self.user.smsVerifyToken);
				uni.request({
				    url: 'http://120.24.53.84/ysapi/register',
					method:"POST",
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
						if(data.code==200){
							msg = "注册成功";
						}
						uni.showToast({
						    title: msg,
							icon:"none",
						    duration: 800
						});
				    }
				});
			},
			validateSendMsg(){
				if(_self.user.imgCode == 'undefined' || !_self.user.imgCode || !/[^\s]/.test(_self.user.imgCode)){
					uni.showToast({
					    title: "请输入验证码",
						icon:"none",
					    duration: 800
					});
					return false;
				}
				if(!(/^1[3456789]\d{9}$/.test(_self.user.phone))){ 
					uni.showToast({
					    title: "手机号为空或格式错误",
						icon:"none",
					    duration: 800
					});
					return false;;
				}
				return true;
			},
			validateRegisterInfo(){
				_self.validateSendMsg();
				if(!(/^[0-9A-Za-z]{6,10}$/.test(_self.user.password))){ 
					uni.showToast({
					    title: "请输入6到10位(数字或者字母)",
						icon:"none",
					    duration: 800
					});
					return false;
				}
				if(_self.user.password!=_self.user.rePassword){ 
					uni.showToast({
					    title: "两次输入的密码不一致",
						icon:"none",
					    duration: 800
					});
					return false;;
				}
				if(_self.user.smsCode == 'undefined' || !_self.user.smsCode || !/[^\s]/.test(_self.user.smsCode)){
					uni.showToast({
					    title: "请输入短信验证码",
						icon:"none",
					    duration: 800
					});
					return false;
				}
				return true;
			}
		}
	}
</script>
<style>
  .container {
  	padding: 80upx;
  }
  .txt-size{
	font-size:12px;
	height: 70upx;
  }
  .btn{
	 margin-top: 0upx;
  }
  .img{
  	  height: 70upx;
  	  width: 160upx;
	  margin-right: 20upx;
  }
  .img-code{
  	  display: flex;
  	  flex-direction: row;
  }
  .row{
	  display: flex;
	  flex-direction: row;
	  width: 100%;
	  align-self:center;
  }
  .row text{
	  height: 70upx;
	  line-height:70upx;
	  font-size: 16px;
	  text-align: left;
	  width: 30%;
  }
  .gender{
	 display: flex;
	 justify-content:center;
	 align-items:center;
	 height: 70upx; 
	 margin-bottom: 20upx;
  }
  .radio{
	  margin-right: 40upx;
  }
  .row input  {
  	height: 70upx;
  	border: solid 2upx #DDDDDD;
  	text-indent: 20upx;
	margin-bottom: 20upx;
	width: 70%;
  }
  .btn-register{
	 width: 100%;
	 margin-top: 40upx;
	 margin-bottom: 20upx; 
  }
  .img-code-input{
	 width:480upx;
	 padding-left: 10upx;
	 margin-left: 20upx;
	 border: solid 2upx #DDDDDD;
	 margin-bottom: 20upx;
  }
  .sms-btn{
	 width:300upx; 
	 margin-left: 0upx;
  }
 /* .sms-code-input{
  	 width:280upx;
  	 padding-left: 10upx;
  	 margin-left: 20upx;
  	 border: solid 2upx #DDDDDD;
  	 margin-bottom: 20upx;
  } */
  
  .register{
	  font-size: 16px;
	  padding: 20upx;
  }
</style>