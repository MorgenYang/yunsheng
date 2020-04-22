import Vue from 'vue'
import App from './App'

Vue.config.productionTip = false
Vue.prototype.appid = ''
Vue.prototype.domainName = 'http://www.healthycloudsci.com';


App.mpType = 'app'

import mimc from '@/components/mimc-uniapp-sdk/mimc.js';
import Base64 from '@/components/mimc-uniapp-sdk/Base64.js';
Vue.prototype.mimc = mimc
Vue.prototype.Base64 = Base64

const app = new Vue({
    ...App
})
app.$mount()


