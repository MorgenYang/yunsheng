<template>
	<view>
		<button @tap="change" v-if='value'>点我</button>
		<button @tap="change" v-else>点我1</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title: 'meals',
				value:true
			}
		},
		onLoad() {

		},
		methods: {
			change(){
				this.value = !this.value;
			}
		}
	}
</script>

<style>

</style>
