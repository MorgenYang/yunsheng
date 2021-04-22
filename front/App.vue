<script>
	let $this;
	export default {
		onLaunch: function() {
			$this = this;
			if($this.getGlobalUser!=null){
				uni.login({
					provider: 'weixin',
					success: function(loginRes) {
						uni.request({
							url:  $this.reqAddress+'/wechatlogin',
							method: "POST",
							data: {
								"code": loginRes.code
							},
							success: (res) => {
								if (res.data.code == 200 && res.data.data) {
									uni.setStorageSync('token', res.data.data.token);
									uni.setStorageSync('nickName', res.data.data.loginClientUserVo.nickname);
									uni.setStorageSync('avatarUrl', res.data.data.loginClientUserVo.avatarurl);
									uni.setStorageSync('openid', res.data.data.loginClientUserVo.openid);
									uni.setStorageSync('phone', res.data.data.loginClientUserVo.phone);
									uni.setStorageSync('gender', res.data.data.loginClientUserVo.gender);
									uni.setStorageSync('id', res.data.data.loginClientUserVo.id);
									uni.setStorageSync('username', res.data.data.loginClientUserVo.username);
								} 
							}
						});
					}
				});
			}
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		},
		methods:{
			
		}
	}
</script>

<style>
	/*每个页面公共css */
	
	/*app-手机端-状态栏样式*/
	.titleNview-placing {
		height: var(--status-bar-height);
		box-sizing: content-box;
		background-color:#A2DEFA ;
	 }
</style>
