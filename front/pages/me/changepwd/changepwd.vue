<template>
	<view>
		<input @blur="checkPwd(0)" class="pwd" v-model="user.oldPassword" password="true" type="text" placeholder="旧密码" />
		<input @blur="checkPwd(0)" class="pwd" v-model="user.newPassword" password="true" type="text" placeholder="新密码" />
		<input @blur="checkPwd(1)" class="pwd" v-model="user.confirmPassword" password="true" type="text" placeholder="确认密码" />
		<button class="submit" @tap="change()">提交</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user:{
					confirmPassword:"",
					newPassword:"",
					oldPassword:"",
					userId:""
				}
			}
		},
		methods: {
			showMsg(msg) {
				uni.showToast({
					title: msg,
					icon: "none",
					duration: 1000
				});
			},
			checkPwd(val) {
				var ret = true;
				if (val == 0) {
					if (!(/^[0-9A-Za-z]{6,10}$/.test(this.user.newPassword))) {
						this.showMsg("请输入6到10位(数字或者字母)");
						return ret;
					}
				} else {
					if (!(/^[0-9A-Za-z]{6,10}$/.test(this.user.confirmPassword))) {
						this.showMsg("请输入6到10位(数字或者字母)");
						return ret;
					}
					if (this.user.newPassword != this.user.confirmPassword) {
						this.showMsg("两次输入的密码不一致");
						return ret;
					}
				}
				return !ret;
			},
			change(){
				//POST /ClientUser/updatePassword
				var uid = "";
				var token = "";
				if (this.checkPwd(0) || this.checkPwd(1)) {
					this.showMsg("密码不符合要求");
					return false;
				}
				uni.getStorage({
				    key: 'ys_user_id',
				    success: function (res) {
						uid = res.data;
				    }
				});
				uni.getStorage({
				    key: 'ys_user_token',
				    success: function (res) {
						token = res.data;
				    }
				});
				uni.request({
				    url: 'http://120.24.53.84/ysapi/ClientUser/updatePassword',
					method:"POST",
					header: {
						"token": token
					},
					data: {
					  "oldPassword": this.user.oldPassword,
					  "newPassword": this.user.newPassword,
					  "confirmPassword": this.user.confirmPassword,
					  "userId": uid
					},
				    success: (res) => {
						this.showMsg(res.data.msg);
				    }
				});
			}
			
		}
	}
</script>

<style>
.pwd{
	width: 100%;
	height: 100upx;
	background-color: #A8A8A8;
	margin-top: 20upx;
}
.submit{
	background-color: #A2DEFA;
	margin-top: 20upx;
	color: #FFFFFF;
}

</style>
