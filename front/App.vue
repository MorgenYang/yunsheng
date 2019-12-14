<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
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
