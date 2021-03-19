import Vue from 'vue'
import Vuex from 'vuex'
import fa from "element-ui/src/locale/lang/fa";

Vue.use(Vuex)

let base = '';

export default new Vuex.Store({
  state: {
    routes:[],
    HOST: `${base}`,
    isPlay:false, // 是否播放中
    url: '', //歌曲地址
    id:''    // 歌曲id
  },
  getters: {
    isPlay: state => state.isPlay,
    url: state => state.url,
    id: state => state.id
  },
  mutations: {
    initRoutes(state,data) {
      state.routes = data;
    },
    setIsPlay: (state,isPlay) => {state.isPlay = isPlay},
    setUrl: (state,url) => {state.url = url},
    setId: (state,id) => {state.id = id}
  },
  actions: {
  },
  modules: {
  }
})
