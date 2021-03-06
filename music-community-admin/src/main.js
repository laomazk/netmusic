import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './utils/css/main.css'
import VCharts from 'v-charts'
import {postRequest} from "@/utils/api";
import {postKeyValueRequest} from "@/utils/api";
import {putRequest} from "@/utils/api";
import {deleteRequest} from "@/utils/api";
import {getRequest} from "@/utils/api";
import {uploadFileRequest} from "./utils/api";
import {initMenu} from "./utils/menus";

import 'font-awesome/css/font-awesome.min.css';

Vue.prototype.postRequest = postRequest;
Vue.prototype.postKeyValueRequest = postKeyValueRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.deleteRequest = deleteRequest;
Vue.prototype.getRequest = getRequest;
Vue.prototype.uploadFileRequest = uploadFileRequest;

Vue.config.productionTip = false
Vue.use(ElementUI);
Vue.use(VCharts)
Vue.directive('title', {
  inserted: function (el) {
    document.title = el.dataset.title
  }
})

router.beforeEach((to,from,next) =>{
  if(to.path=="/"){
    next();
  }else{
    if(window.sessionStorage.getItem("user")){
      initMenu(router,store);
      next();
    }else{
      // console.log(to);
      next('/?redirect='+to.path)
    }
  }
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')



