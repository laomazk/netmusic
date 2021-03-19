<template>
    <div>
        <span v-title data-title="后台管理"></span>
        <the-header></the-header>
        <the-aside></the-aside>
        <div class="content-box">
            <el-breadcrumb separator-class="el-icon-arrow-right" v-if="this.$router.currentRoute.path!='/home'">
                <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>{{ this.$router.currentRoute.name }}</el-breadcrumb-item>
            </el-breadcrumb>
            <div class="homeWelcome" v-if="this.$router.currentRoute.path=='/home'">
                NetMusic 后台管理
            </div>
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
import TheHeader from '@/components/TheHeader'
import TheAside from '@/components/TheAside'
import bus from "../utils/bus";

export default {
  components: {
    TheHeader,
    TheAside
  },
  data () {
    return {
      collapse: false
    }
  },
  created () {
    //  通过Bus进行组件间的通信，来折叠侧边栏

  }

}
</script>

<style>
    .homeWelcome {
        text-align: center;
        font-size: 30px;
        font-family: 微软雅黑;
        color: #ff392b;
        padding-top: 50px;
    }
    .content-box{
        padding: 15px;
    }
    /*.content-box{*/
    /*    position: absolute;*/
    /*    left: 150px;*/
    /*    right: 0;*/
    /*    top: 70px;*/
    /*    bottom: 0;*/
    /*    overflow-y: scroll;*/
    /*    -webkit-transition: left .3s ease-in-out;*/
    /*    transition: left .3s ease-in-out;*/
    /*    background: #f0f0f0;*/
    /*}*/
</style>
