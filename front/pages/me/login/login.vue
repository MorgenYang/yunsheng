<template>
	<view class="container">
	    <view class="form">
	      <input type="text" placeholder="手机号" v-model="user.username" />
	      <input type="text" placeholder="密码" password="true" v-model="user.password" />
		  <view class="img-code">
			  <image @tap="loadVaildCode()" class="img" :src="user.image"></image>
			  <input type="text" placeholder="验证码" v-model="user.code"/>
		  </view>
	      <!-- <button type="primary" @click="signUp">注册</button> -->
	      <button type="primary" @tap="signIn">登录</button>
	      <!-- <button type="primary" @click="validateToken">token验证</button> -->
		  <view class="img-code">
			 <view class="register" @tap="registerUser">注册</view>
		  </view>
	    </view>
	  </view>
</template>

<script>
	export default {
		data() {
			return {
				user:{
					username:"",
					password:"",
					image:"",
					code:"",
					verifyToken:""
				}
			}
		},
		onLoad() {
			this.loadVaildCode();
		},
		methods: {
			loadVaildCode(){
				uni.request({
				    url: 'http://120.24.53.84/ysapi/verificationCode/getBase64Image',
				    success: (res) => {
						var data = res.data;
						if(data.code==200 && data.data!=null){
							this.user.image = data.data.image;
							this.user.verifyToken = data.data.verifyToken;
							
						}
				    }
				});
			},
			signIn(){
				uni.request({
				    url: 'http://120.24.53.84/ysapi/login',
					method:"POST",
					data: {
					  "code": this.user.code,
					  "password": this.user.password,
					  "username": this.user.username,
					  "verifyToken": this.user.verifyToken
					},
				    success: (res) => {
						console.log(res);
						var data = res.data;
						if(data.code==200){
							uni.setStorage({
							    key: 'ys_user_id',
							    data: data.data.loginClientUserVo.id,
							    success: function () {
							        console.log('缓存uid成功');
							    },
								fail: function () {
									console.log('缓存uid失败');
								}
							});
							uni.setStorage({
							    key: 'ys_user_token',
							    data: data.data.token,
							    success: function () {
							        console.log('缓存utoken成功');
							    },
								fail: function () {
									console.log('缓存utoken失败');
								}
							});
						}
				    }
				});
			},
			registerUser(){
				uni.navigateTo({
				    url: "../register/register"
				});
			}
		}
	}
</script>

<style>
  .container {
  	padding: 260upx 80upx 80upx 80upx;
  }
  .img{
	  height: 80upx;
	  width: 260upx;
	  margin-right: 40upx;
  }
  .img-code{
	  display: flex;
	  flex-direction: row;
  }
  .title {
  	text-align: center;
  	font-size: 20px;
  	padding: 20px 0px;
  }
  
  .form input {
  	height: 80upx;
  	border: solid 2upx #DDDDDD;
  	text-indent: 20upx;
  }
  
  .form input,
  .form button {
  	width: 100%;
  	margin-bottom: 20upx;
  }
  .register{
	  font-size: 16px;
	  padding: 20upx;
  }
</style>
