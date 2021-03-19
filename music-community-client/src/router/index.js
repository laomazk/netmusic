import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Singer from "../views/Singer";
import PlayList from "../views/PlayList";
import MyList from "../views/MyList";
import My from "../views/My";
import Search from "../views/Search";
import Lyric from "../views/Lyric";
import SingerAlbum from "../views/SingerAlbum";
import PlayListAlbum from "../views/PlayListAlbum";
import ListenerInfo from "../views/ListenerInfo";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,

  },{
    path:'/listenerinfo',
    name:'个人中心',
    component:ListenerInfo,
  },{
    path: '/singer',
    name: 'singer',
    component: Singer
  },{
    path: '/play-list',
    name: 'PlayList',
    component: PlayList
  },{
    path: '/my',
    name: 'My',
    component: My
  },{
    path: '/my-list',
    name: 'MyList',
    component: MyList
  },{
    path: '/search',
    name: 'Search',
    component: Search
  },{
    path:'/lyric',
    name: 'lyric',
    component:Lyric
  },{
    path:'/singer-album/:id',
    name: 'singerAlbum',
    component:SingerAlbum
  },{
    path:'/play-list-album/:id',
    name: 'playListAlbum',
    component:PlayListAlbum
  }
]

const router = new VueRouter({
  routes
})

export default router
