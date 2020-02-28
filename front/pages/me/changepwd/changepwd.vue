<template>
	<view>
		<input class="pwd" password="true" type="text" placeholder="旧密码" />
		<input class="pwd" password="true" type="text" placeholder="新密码" />
		<input class="pwd" password="true" type="text" placeholder="确认密码" />
		<button class="submit">提交</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			
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
