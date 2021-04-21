<template>
	<view>
	       <view class="top-view">
			   <view :class="current===0?'p-view choose-view':'p-view'" @tap="clickTitle(0)">深入定制</view>
			   <image src="../../../static/icon/settings/arrows-right-setting.png"></image>
			   <view :class="current===1?'p-view choose-view':'p-view'" @tap="clickTitle(1)">生理信息</view>
			   <image src="../../../static/icon/settings/arrows-right-setting.png"></image>
			   <view :class="current===2?'p-view choose-view':'p-view'" @tap="clickTitle(2)">医疗相关</view>
			   <image src="../../../static/icon/settings/arrows-right-setting.png"></image>
			   <view :class="current===3?'p-view choose-view':'p-view'" @tap="clickTitle(3)">体力活动</view>
		   </view>
	        <scroll-view class="content" scroll-y="true" :style="{height:navHeight+'px'}">
	            <view class="s-item" v-show="current === 0">
					<view class="row-item">
						<view class="d-item">每日主食种类：</view>
						<ld-select :list="spdzdList"
							label-key="label" value-key="value"
							placeholder="请选择"
							clearable
							v-model="spdzdValue"
							@change="selectSpdzdChange">
						</ld-select>
					</view>
					<view class="row-item">
						<view class="d-item">每日餐次：</view>
						<ld-select :list="mrccList"
							label-key="label" value-key="value"
							placeholder="请选择"
							clearable
							v-model="mrccValue"
							@change="selectMrccChange">
						</ld-select>
					</view>
					
					<button class="btn" @click="next1()">下一步</button>
				</view>
	            <view class="s-item" v-show="current === 1">
	                <view class="row-item">
	                	<view class="d-item">性别：</view>
	                	<ld-select :list="genderList"
	                		label-key="label" value-key="value"
	                		placeholder="请选择"
	                		v-model="genderValue"
	                		@change="selectGenderChange">
	                	</ld-select>
	                </view>
					<view class="row-item">
						<view class="d-item">年龄(岁)：</view>
						<view @tap="selectAge" class="input-border input-h" disabled="true">{{ageValue}}</view>
					</view>
					<view class="row-item">
						<view class="d-item">身高(cm)：</view>
						<view @tap="selectHeight" class="input-border input-h">{{heightValue}}</view>
					</view>
					<view class="row-item">
						<view class="d-item">体重(kg)：</view>
						<view @tap="selectWeight" class="input-border input-h">{{weightValue}}</view>
					</view>
					<view class="row-item">
						<view class="d-item">腰围(尺)：</view>
						<view @tap="selectYaowei" class="input-border input-h">{{yaoweiValue}}</view>
					</view>
					<view class="row-item">
						<view class="d-item">孕期(月)：</view>
						<view @tap="selectYunqi" class="input-border input-h">{{yunqiValue}}</view>
					</view>
					<view class="row-item">
						<view class="d-item">哺乳(月)：</view>
						<view @tap="selectburu" class="input-border input-h">{{buruValue}}</view>
					</view>
					<button class="btn" @click="next2()">下一步</button>
				</view>
				<view class="s-item" v-show="current === 2">
				  <!-- <view class="medical"> -->
						<!-- <checkbox-group>
						   <label class="uni-list-cell uni-list-cell-pd" v-for="(item,index) in items" 
						      @tap="change(index)" :key="index+'aaa'">
							   <view>
								   <checkbox :checked="item.value!=0" />
							   </view>
							   <view>{{item.name}}</view>
						   </label>
					   </checkbox-group> -->
					    <view class="row-item row-item-left" v-for="(item,index) in items">
						   <view class="d-item">{{'是否有'+item.name}}：</view>
						   <radio-group @change="radioChange(index,$event)" class="radio-class">
								<view><radio :checked="item.value=='1'" value="1"/></view>
								<view class="radio-text">否</view>
								<view class="radio-space"><radio :checked="item.value=='0'" value="0"/></view>
								<view class="radio-text">是</view>
						   </radio-group>
					   </view>
				  <!-- </view> -->
				   <button class="btn" @click="next3()">下一步</button>
				</view>
				<view class="s-item" v-show="current === 3">
				   <view class="row-item">
				   	 <view class="d-item">平均每日步数(步):</view>
				   	 <input class="input-border" type="number" v-model="pjmrbs" maxlength="5" />
				   </view>
				   <view class="row-item">
				   	 <view class="d-item">每周运动次数(次):</view>
				   	 <input class="input-border" type="number" v-model="mzydcs"  maxlength="3"/>
				   </view>

				   <view class="row-item">
				   	 <view class="d-item">每次运动时间(分钟):</view>
				   	 <input class="input-border" type="number" v-model="mcydsj" maxlength="5"/>
				   </view>
			  
				   <button class="btn" @click="next4()">完成</button>
				</view>
				<uni-popup ref="popupAge" type="bottom" :animation="isShowAnimation" :maskClick="outerClose">
					<view style="background-color:#F5F5F5;height: 360upx;">
						<view class="select-bar bg-white">
							<view class="action text-blue" @tap="ageCancelClick">取消</view>
							<view class="hint">年龄选择(岁)</view>
							<view class="action text-green" @tap="ageConfirmClick">确定</view>
						</view>
						<view class="bp">
							<view  class="hint">范围：0-100(默认值18)</view>
							<slider class="progress m-space"  @change="ageChange" activeColor="#00FCFE" show-value
								block-color="#ffffff" block-size="28" :value="ageDefaultValue"/>			
						</view>
					</view>
				</uni-popup>
				<uni-popup ref="popupHeight" type="bottom" :animation="isShowAnimation" :maskClick="outerClose">
					<view style="background-color:#F5F5F5;height: 360upx;">
						<view class="select-bar bg-white">
							<view class="action text-blue" @tap="heightCancelClick">取消</view>
							<view class="hint">身高选择(cm)</view>
							<view class="action text-green" @tap="heightConfirmClick">确定</view>
						</view>
						<view class="bp">
							<view class="hint">范围：30-220(默认值150)</view>		
							<slider class="progress m-space" @change="heightChange" min="30" max="220" activeColor="#00FCFE"
								 show-value block-color="#ffffff" block-size="28" :value="heightDefaultValue"/>					
						</view>
					</view>
				</uni-popup>
				<uni-popup ref="popupWeight" type="bottom" :animation="isShowAnimation" :maskClick="outerClose">
					<view style="background-color:#F5F5F5;height: 360upx;">
						<view class="select-bar bg-white">
							<view class="action text-blue" @tap="weightCancelClick">取消</view>
							<view class="hint">体重选择(kg)</view>
							<view class="action text-green" @tap="weightConfirmClick">确定</view>
						</view>
						<view class="bp">
							<view class="hint">范围：0-200(默认值50)</view>
							<slider class="progress m-space" @change="weightChange" min="0" max="200" activeColor="#00FCFE"
								 show-value block-color="#ffffff" block-size="28" :value="weightDefaultValue"/>						
						</view>
					</view>
				</uni-popup>
				<uni-popup ref="popupYaowei" type="bottom" :animation="isShowAnimation" :maskClick="outerClose">
					<view style="background-color:#F5F5F5;height: 360upx;">
						<view class="select-bar bg-white">
							<view class="action text-blue" @tap="yaoweiCancelClick">取消</view>
							<view class="hint">腰围选择(尺)</view>
							<view class="action text-green" @tap="yaoweiConfirmClick">确定</view>
						</view>
						<view class="bp">
							<view class="hint">范围：18.5-24.9(默认值22)</view>
							<slider class="progress m-space" @change="yaoweiChange" min="18.5" max="24.9" activeColor="#00FCFE"
								 show-value block-color="#ffffff" block-size="28" step="0.1" :value="yaoweiDefaultValue"/>				
						</view>
					</view>
				</uni-popup>
				<uni-popup ref="popupYunqi" type="bottom" :animation="isShowAnimation" :maskClick="outerClose">
					<view style="background-color:#F5F5F5;height: 360upx;">
						<view class="select-bar bg-white">
							<view class="action text-blue" @tap="yunqiCancelClick">取消</view>
							<view class="hint">孕期选择(月)</view>
							<view class="action text-green" @tap="yunqiConfirmClick">确定</view>
						</view>
						<view class="bp">
							<view class="hint">范围：0-10(默认值1)</view>
							<slider class="progress m-space" @change="yunqiChange" min="0" max="10" activeColor="#00FCFE"
								 show-value block-color="#ffffff" block-size="28" step="1" :value="yunqiDefaultValue"/>	
						</view>
					</view>
				</uni-popup>
				<uni-popup ref="popupburu" type="bottom" :animation="isShowAnimation" :maskClick="outerClose">
					<view style="background-color:#F5F5F5;height: 360upx;">
						<view class="select-bar bg-white">
							<view class="action text-blue" @tap="buruCancelClick">取消</view>
							<view class="hint">哺乳选择(月)</view>
							<view class="action text-green" @tap="buruConfirmClick">确定</view>
						</view>
						<view class="bp">
							<view class="hint">范围：0-15(默认值1)</view>
							<slider class="progress m-space" @change="buruChange" min="0" max="15" activeColor="#00FCFE"
								 show-value block-color="#ffffff" block-size="28" step="1" :value="buruDefaultValue"/>	
						</view>
					</view>
				</uni-popup>
			</scroll-view>
			
	    </view>
	</template>

<script>
	let $this;
	import uniPopupMessage from '@/components/uni-popup/uni-popup-message.vue'
	import uniPopupDialog from '@/components/uni-popup/uni-popup-dialog.vue'
	export default {
		components: {
			uniPopupMessage,
			uniPopupDialog
		},
		data() {
			return {
				user:{},
				isShowAnimation:true,
				outerClose:false,
				isLoadData:[0,0,0,0],
				current: 0,
				mrccValue:"",
				mrccList:[],
				spdzdValue:"",
				spdzdList:[],
				genderValue:1,
				genderList:[{"value":0,"label":"男"},{"value":1,"label":"女"}],
				ageDefaultValue:"",
				heightDefaultValue:"",
				weightDefaultValue:"",
				yaoweiDefaultValue:"",
				ageValue:"",
				heightValue:"",
				weightValue:"",
				yaoweiValue:"",
				yunqiValue:"",
				yunqiDefaultValue:"",
				buruValue:"",
				buruDefaultValue:"",
				pH:0, //窗口高度
				navHeight:0, //元素的所需高度
				items: [{value:0,name:'糖尿病',index:'1'},{value:0,name:'高血压',index:'2'},{value:0,name:'冠心病',index:'3'},
				{value:0,name:'肥胖病',index:'4'},{value:0,name:'痛风',index:'5'},{value:0,name:'癌症',index:'6'}],
				mcydsj:null,
				mzydcs:null,
				pjmrbs:null
			}
		},
		onReady() {
			let that=this;
			uni.getSystemInfo({ //调用uni-app接口获取屏幕高度
				success(res) { //成功回调函数
					that._data.pH=res.windowHeight //windoHeight为窗口高度，主要使用的是这个
					let titleH=uni.createSelectorQuery().select(".content"); //想要获取高度的元素名（class/id）
					titleH.boundingClientRect(data=>{
						let pH=that._data.pH; 
						that._data.navHeight=pH-data.top-10  //计算高度：元素高度=窗口高度-元素距离顶部的距离（data.top）
					}).exec()
				}
			})
		},
		onLoad() {
			$this = this;
			this.user = $this.getLoginUser();
			$this.loadCustomizationData();
		},
	
		methods: {
			inInput1(e){
				setTimeout(() => {  this.mcydsj = e.detail }, 0)
			},
			inInput2(e){
				setTimeout(() => {  this.mzydcs = e.detail }, 0)
			},
			inInput3(e){
				setTimeout(() => {  this.pjmrbs = e.detail }, 0)
			},
			change(index){
			    let currentValue = this.items[index].value;		
				if(currentValue==0){
					this.items[index].value =1;
				}else{
					this.items[index].value=0;
				}
			},
			ageConfirmClick(){
				this.ageValue = this.ageDefaultValue;
				this.$refs.popupAge.close();
			},
			ageCancelClick(){
				this.ageDefaultValue = this.ageValue;
				if(this.ageDefaultValue==""){
					this.ageDefaultValue =18;
				}
				this.$refs.popupAge.close();
			},
			heightConfirmClick(){
				this.heightValue =this.heightDefaultValue;
				this.$refs.popupHeight.close();
			},
			heightCancelClick(){
				this.heightDefaultValue = this.heightValue;
				if(this.heightDefaultValue==""){
					this.heightDefaultValue =150;
				}
				this.$refs.popupHeight.close();
			},
			weightConfirmClick(){
				this.weightValue =this.weightDefaultValue;
				this.$refs.popupWeight.close();
			},
			weightCancelClick(){
				this.weightDefaultValue = this.weightValue;
				if(this.weightDefaultValue==""){
					this.weightDefaultValue =50;
				}
				this.$refs.popupWeight.close();
			},
			yaoweiConfirmClick(){
				this.yaoweiValue = this.yaoweiDefaultValue;
				this.$refs.popupYaowei.close();
			},
			yaoweiCancelClick(){
				this.yaoweiDefaultValue = this.yaoweiValue;
				if(this.yaoweiDefaultValue==""){
					this.yaoweiDefaultValue =22;
				}
				this.$refs.popupYaowei.close();
			},
			
			yunqiConfirmClick(){
				this.yunqiValue = this.yunqiDefaultValue;
				this.$refs.popupYunqi.close();
			},
			yunqiCancelClick(){
				this.yunqiDefaultValue = this.yunqiValue;
				if(this.yunqiDefaultValue==""){
					this.yunqiDefaultValue =1;
				}
				this.$refs.popupYunqi.close();
			},
			buruConfirmClick(){
				this.buruValue = this.buruDefaultValue;
				this.$refs.popupburu.close();
			},
			buruCancelClick(){
				this.buruDefaultValue = this.buruValue;
				if(this.buruDefaultValue==""){
					this.buruDefaultValue =1;
				}
				this.$refs.popupburu.close();
			},
			yunqiChange(e){
				this.yunqiDefaultValue =e.detail.value;
			},
			selectYunqi(){
				if(this.yunqiDefaultValue==""){
					this.yunqiDefaultValue =1;
				}
				this.$refs.popupYunqi.open();
			},
			buruChange(e){
				this.buruDefaultValue =e.detail.value;
			},
			selectburu(){
				if(this.buruDefaultValue==""){
					this.buruDefaultValue =1;
				}
				this.$refs.popupburu.open();
			},
			yaoweiChange(e){
				this.yaoweiDefaultValue =e.detail.value;
			},
			selectYaowei(){
				if(this.yaoweiDefaultValue==""){
					this.yaoweiDefaultValue =22;
				}
				this.$refs.popupYaowei.open();
			},
			weightChange(e){
				this.weightDefaultValue =e.detail.value;
			},
			selectWeight(){
				if(this.weightDefaultValue==""){
					this.weightDefaultValue =50;
				}
				this.$refs.popupWeight.open();
			},
			heightChange(e){
				this.heightDefaultValue =e.detail.value;
			},
			selectHeight(){
				if(this.heightDefaultValue==""){
					this.heightDefaultValue =150;
				}
				this.$refs.popupHeight.open();
			},
			ageChange(e){
				this.ageDefaultValue =e.detail.value;
			},
			selectAge(){
				if(this.ageDefaultValue==""){
					this.ageDefaultValue =18;
				}
				this.$refs.popupAge.open();
			},
			radioChange1: function(evt) {
				this.sfhdjValue = evt.target.value
			},
			radioChange2: function(evt) {
				this.sfhtzrValue = evt.target.value
			},
			radioChange3: function(evt) {
				this.sfhbtzValue = evt.target.value
			},
			radioChange4: function(evt) {
				this.sfhqzrValue = evt.target.value
			},
			radioChange5: function(evt) {
				this.sfcglValue = evt.target.value
			},
			radioChange: function(index,evt) {
				this.items[index].value = evt.target.value;
			},
			clickTitle(index){
				if($this.current != index ){
					$this.current = index;
					if(index==0 && $this.isLoadData[0]==0){
						//加载深入定制数据--在读取用户个人深入定制信息
						$this.loadCustomizationData();
					}else if(index==1 && $this.isLoadData[1]==0){
						//读取用户个人生理信息
						$this.loadShengLiForUserData();
					}else if(index==2 && $this.isLoadData[2]==0){
						//读取用户个人医疗相关
						$this.loadYiLiaoUserData();
					}else if(index==3 && $this.isLoadData[3]==0){
						//读取用户个人体力活动
						$this.loadTiLiHuoDongForUserData();
					}
				}
			},
			next1(){
				//先保存当前数据,在跳转下一页
				let url = $this.reqAddress+'/tjSrdz/add';
				var formData = {
					"userid":$this.user.id,
					"typeid":1,
					"spdzd":$this.spdzdValue,
					"mrcc":$this.mrccValue
				};
				let options ={};
				options.header = {'Content-Type':'application/x-www-form-urlencoded'};
				$this.$api.post(url,formData,options).then((res)=>{
					let data = res.data;
					if(data.code==200){
						$this.current=1;
						$this.loadShengLiForUserData();
					}
				}).catch((err)=>{
					console.log('request fail', err);
				}) 
			},
			next2(){
				//先生理信息保存当前数据,在跳转下一页
				let url = $this.reqAddress+'/tjYhslxx/addorupdate';
				var formData = {
				   "userid":$this.user.id,
				   "xb": $this.genderValue,
				   "nl": $this.ageValue,
				   "sg": $this.heightValue,
				   "tz": $this.weightValue,
				   "yw": $this.yaoweiValue,
				   "yq": $this.yunqiValue,
				   "br": $this.buruValue,
				   //"id": $this.genderValue,
				};
				/* let options ={};
				options.header = {'Content-Type':'application/x-www-form-urlencoded'}; */
				$this.$api.post(url,formData).then((res)=>{
					let data = res.data;
					if(data.code==200){
						this.current=2;
						this.loadYiLiaoUserData();
						
					}
				}).catch((err)=>{
					console.log('request fail', err);
				}) 
			},
			next3(){
				//先保存医疗相关数据,在跳转下一页
				let url = $this.reqAddress+'/tjYlxgsj/addorupdate';
				var formData = {
				   "userid": $this.user.id,
				   "sftnb": $this.items[0].value,  //是否糖尿病
				   "sfgxy": $this.items[1].value,  //是否高血压
				   "sfgxb": $this.items[2].value,  //是否冠心病
				   "sffpb": $this.items[3].value,  //是否肥胖病
				   "sftf": $this.items[4].value,   //是否痛风
				   "sfaz": $this.items[5].value,   //是否癌症
				   //"id": $this.genderValue,
				};
				/* let options ={};
				options.header = {'Content-Type':'application/x-www-form-urlencoded'}; */
				$this.$api.post(url,formData).then((res)=>{
					let data = res.data;
					if(data.code==200){
						this.current=3;
						$this.loadTiLiHuoDongForUserData();
					}
				}).catch((err)=>{
					console.log('request fail', err);
				}) 
			},
			next4(){
				//体力相关数据
				let url = $this.reqAddress+'/tjTlhdsp/addorupdate';
				var formData = {
				   "userid": $this.user.id,
				   "mcydsj": $this.mcydsj,  //每次运动时间
				   "mzydcs": $this.mzydcs,  //每周运动次数
				   "pjmrbs": $this.pjmrbs,  //平均每日步数
				};
				/* let options ={};
				options.header = {'Content-Type':'application/x-www-form-urlencoded'}; */
				$this.$api.post(url,formData).then((res)=>{
					let data = res.data;
					if(data.code==200){
						//this.current=4;
						uni.showToast({
						    title: "保存成功",
						    duration: 2000,
							icon:"none"
						});
						setTimeout(function(){uni.navigateBack();},200);
					}
				}).catch((err)=>{
					console.log('request fail', err);
				}) 
			},
			selectYunqiChange(val){
				this.yunqiValue = val;
			},
			selectburuChange(val){
				this.buruList = val;
			},
			selectGenderChange(val){
				console.log("gender:"+val);
				this.genderValue = val;
			},
			selectSpdzdChange(val){
				this.spdzdValue = val
			},
			selectMrccChange(val){
				this.mrccValue = val
			},
			
			individualization:function(e){
				uni.navigateTo({
					url: "/pages/meals/individualization/individualization"
				});
			},
			//深入定制
			loadCustomizationData(){
				uni.showLoading({ title:"加载中..."});
				let url = $this.reqAddress+'/tjSrdz/getItems/1';
				$this.$api.post(url).then((res)=>{
					let data = res.data;
					if(data.code==200 && data.data!=null){
						$this.processCusData(data.data);
						$this.isLoadData[0]=1;
						$this.loadCustomizationForUserData();
					}
				}).catch((err)=>{
					console.log('request fail', err);
					setTimeout(function(){uni.hideLoading();},100);
				})
				 
			 },
			 processCusData(data){
				 data.forEach(function(item, index){
					 let obj = item.propsitem;
					 for(let key in obj){
						 if(item.props=="mrcc"){
						 	$this.mrccList.push({value:key,label:obj[key]});	
						 }else if(item.props=="spdzd"){
							$this.spdzdList.push({value:key,label:obj[key]});
						 }
					 }
				 })
			 },
			
			 loadCustomizationForUserData(){ 
				 //获取用户深入定制数据
				 let url = $this.reqAddress+'/tjSrdz/info/'+$this.user.id+'/1';
				 $this.$api.get(url).then((res)=>{
					let data = res.data;
					if(data.code==200 && data.data!=null){
						data.data.forEach(function(item, index){
							if(item.selectedval!=null){
								if(item.props =="mrcc"){
									$this.mrccValue = item.selectedval+"";
								}else if(item.props =="spdzd"){
									$this.spdzdValue = item.selectedval+"";
								}
							}
						})
					}
					setTimeout(function(){uni.hideLoading();},100);
				 }).catch((err)=>{
					setTimeout(function(){uni.hideLoading();},100);
					console.log('request fail', err);
				 })
			 },
			 loadShengLiForUserData(){
				 //读取用户个人生理信息
				 uni.showLoading({ title:"加载中..."});
				 let url = $this.reqAddress+'/tjYhslxx/info/'+$this.user.id;
				 $this.$api.get(url).then((res)=>{
					let data = res.data;
					if(data.code==200 && data.data!=null){
					   $this.isLoadData[2]=1;
					   $this.genderDefaultValue=$this.genderValue = data.data.xb!=null?data.data.xb:"";
					   $this.ageDefaultValue=$this.ageValue = data.data.nl!=null?data.data.nl:"";
					   $this.heightDefaultValue=$this.heightValue = data.data.sg!=null?data.data.sg:"";
					   $this.weightDefaultValue=$this.weightValue = data.data.tz!=null?data.data.tz:"";
					   $this.yaoweiDefaultValue= $this.yaoweiValue = data.data.yw!=null?data.data.yw:"";
					   $this.yunqiDefaultValue= $this.yunqiValue = data.data.yq!=null?data.data.yq:"";
					   $this.buruDefaultValue= $this.buruValue = data.data.br!=null?data.data.br:"";
					}
					setTimeout(function(){uni.hideLoading();},100);
				 }).catch((err)=>{
					console.log('request fail', err);
					setTimeout(function(){uni.hideLoading();},100);
				 })
			 },
			 loadYiLiaoUserData(){
				 //读取用户个人医疗相关
				 uni.showLoading({ title:"加载中..."});
				 let url = $this.reqAddress+'/tjYlxgsj/info/'+$this.user.id;
				 $this.$api.get(url).then((res)=>{
					let data = res.data;
					if(data.code==200 && data.data!=null){
						$this.isLoadData[3]=1;
						$this.items[0].value = data.data.sftnb;
						$this.items[1].value = data.data.sfgxy;
						$this.items[2].value = data.data.sfgxb;
						$this.items[3].value = data.data.sffpb;
						$this.items[4].value = data.data.sftf;
						$this.items[5].value = data.data.sfaz;
					}
					setTimeout(function(){uni.hideLoading();},100);
				 }).catch((err)=>{
					console.log('request fail', err);
					setTimeout(function(){uni.hideLoading();},100);
				 })
			 },
			 loadTiLiHuoDongForUserData(){
				 //读取用户体力活动相关
				 uni.showLoading({ title:"加载中..."});
				 let url = $this.reqAddress+'/tjTlhdsp/info/'+$this.user.id;
				 $this.$api.get(url).then((res)=>{
					let data = res.data;
					if(data.code==200 && data.data!=null){
						$this.isLoadData[4]=1;
						$this.pjmrbs = data.data.pjmrbs;
						$this.mzydcs = data.data.mzydcs;
						$this.mcydsj = data.data.mcydsj;
					}
					setTimeout(function(){uni.hideLoading();},100);
					
				 }).catch((err)=>{
					console.log('request fail', err);
					setTimeout(function(){uni.hideLoading();},100);
				 })
			 },
		}
	}
</script>

<style>
	@import url("../item.css");
	.top-view{
		height: 80upx;
		background-color: #F5F5F5;
		display: flex;
		flex-direction: row;
		align-items: center;
		font-size: 26upx;
		padding-left: 20upx;
	}
	.choose-view{
		color:#007AFF;
	}
	.radio-class{
		display: flex;
		flex-direction: row;
		align-items: center;
	}
	.radio-space{
		margin-left: 60upx;
	}
	.main{
		width: 413upx;
	}
	ld-select{
		width: 413upx;
	}
	.top-view >image{
		height: 30upx;
		width: 36upx;
	}
	.content{
		/* height: calc(100vh - 10px); */
		margin-top: 20upx;
		margin-right: 0upx;
	}
	.s-item{
		display: flex;
		flex-direction:column;
		font-size: 13px;
	}
	.row-item{
		display: flex;
		flex-direction:row;
		height: 90upx;
		align-items: center;
		justify-content: center;
	}
	.row-item-left{
		justify-content: left;
		margin-left: 40upx;
		
	}
	.radio-text{
		padding-left: 10upx;
	}
	.d-item{
		/* width: 290upx; */
		width: 35%;
	}
	.p-view{
		padding-top: 16upx;
		padding-bottom: 16upx;
	}
	.btn{
		width: 46%;
		transform: scale(0.99);
		transform-origin: 0 0;
		background: #3AA9D1;
		color: white;
		/* letter-spacing:30upx; 
		text-indent: 30upx;*/
		font-size: 16px;
		margin-top: 60upx ;
	}
	.bg-white{
		background-color: #FFFFFF;
	}
	.text-blue{
		color: #0081ff;
	}
	.text-green{
		color: #39b54a;
	}
	.select-bar {
		font-size: 28rpx;
		padding: 0 20rpx;
		display: flex;
		position: relative;
		align-items: center;
		min-height: 80rpx;
		justify-content: space-between;
	}
	.action {
		display: flex;
		align-items: center;
		height: 100%;
		justify-content: center;
		max-width: 100%;
	}
	.bp{
		margin:50upx 0upx 0upx 0upx;
		height: 50upx;
	}
	.input-border {
	    display: -webkit-box;
	    display: -webkit-flex;
	    display: flex;
	    -webkit-box-align: center;
	    -webkit-align-items: center;
	    align-items: center;
		width: 50%;
	    height: 60upx;
		display: flex;
		align-items:center;
		padding: 10upx 20upx;
		border-radius: 10upx;
		border-style: solid;
		border-width: 1upx;
		border-color: rgba(0, 0, 0, 0.1);
	}
	.hint{
		text-align: center;
		color: gray;
		font-size: 30upx;
	}
	.m-space{
		margin-left: 56upx;
		margin-top: 50upx;
	}
	.medical > view{
		padding-left: 50upx;
		font-size: 14px;
		padding-top: 20upx;
	}
	.medical > view >checkbox{
		padding-left: 20upx;
		padding-right: 20upx;
	}
	.input-h{
		width: 372upx;
	}
</style>
