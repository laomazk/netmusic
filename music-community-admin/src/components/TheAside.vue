<template>
    <div >
        <el-menu router unique-opened class="sidebar-el-menu">
            <el-submenu :index="index+''" v-for="(item,index) in routes" v-if="!item.hidden" :key="index">
                <template slot="title">
                    <i style="margin-right: 5px" :class="item.iconCls"></i>
                    <span>{{ item.name }}</span>
                </template>
                <el-menu-item :index="children.path" v-for="(children,indexj) in item.children" :key="indexj">
                    {{ children.name }}
                </el-menu-item>
            </el-submenu>
        </el-menu>

        <!--        <el-menu class="sidebar-el-menu"-->
        <!--                 :default-active="onRoutes"-->
        <!--                 background-color="#334256"-->
        <!--                 text-color="#ffffff"-->
        <!--                 active-text-color="#20a0ff"-->
        <!--                 router-->
        <!--        >-->
        <!--            <template v-for="item in items">-->
        <!--                <template>-->
        <!--                    <el-menu-item :index="item.index" :key="item.index">-->
        <!--                        <i :class="item.icon"></i>-->
        <!--                        {{item.title}}-->
        <!--                    </el-menu-item>-->
        <!--                </template>-->
        <!--            </template>-->

        <!--        </el-menu>-->
    </div>
</template>

<script>
import bus from "../utils/bus";

export default {
  data() {
    return {
      user: JSON.parse(window.sessionStorage.getItem("user")),
      collapse: false,
      items: [
        {
          icon: 'el-icon-s-home',
          index: 'info',
          title: '系统首页'
        },
        {
          icon: 'el-icon-user-solid',
          index: 'listener',
          title: '用户管理'
        },
        {
          icon: 'el-icon-s-custom',
          index: 'singer',
          title: '歌手管理'
        },
        {
          icon: 'el-icon-s-management',
          index: 'playlist',
          title: '歌单管理'
        }
      ],
    }
  },
  computed: {
    // onRoutes () {
    //   return this.$route.path.replace('/admin/', '')
    // }
    routes() {
      return this.$store.state.routes;
    }
  },
  created() {
    // 通过Bus进行组件间的通信，来折叠侧边栏
    // bus.$on('collapse', msg =>{
    //   this.collapse = msg
    // })
  }
}
</script>

<style scoped>
    .sidebar {
        font-family: "Microsoft YaHei";
        display: block;
        position: absolute;
        left: 0;
        top: 70px;
        bottom: 0px;
        background-color: #334256;
        overflow-y: scroll;
        scrollbar-width: none;
    }

    .sidebar::-webkit-scrollbar {
        width: 0;
    }

    .sidebar-el-menu:not(.el-menu--collapse) {
        width: 150px;
    }

    .sidebar > ul {
        height: 100%;
    }


</style>

