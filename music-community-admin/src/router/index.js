import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../views/Login";
import Home from "../views/Home";
import SouPlayListSong from "../views/sou/SouPlayListSong";
import Collect from "../views/sou/Collect";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path:'/home',
    name: 'Home',
    component: Home,
    children:[
      {
        path:'/sou/playlistsong',
        name:'歌单歌曲',
        component:SouPlayListSong
      },
      {
        path:'/sou/collect',
        name:'用户收藏',
        component:Collect
      }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
