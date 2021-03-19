<template>
    <div class="header">
        <!--  折叠图片-->
        <div class="collapse-btn" >
            <i class="el-icon-headset">
            </i>
        </div>
        <div class="logo">NetMusic 后台管理</div>
        <div class="header-right">
            <div class="btn-fullscreen" @click="handleFullScreen">
                <el-tooltip :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
                    <i class="el-icon-rank"></i>
                </el-tooltip>
            </div>
            <div class="user-avator">
                <img :src="user.avator" alt="">
            </div>
            <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                <span class="el-dropdown-link">
                    {{user.username}}
                    <i class="el-icon-caret-bottom"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>

        </div>
    </div>
</template>

<script>


export default {
  data () {
    return {
      fullscreen: false,
      user: JSON.parse(window.sessionStorage.getItem("user"))
    }
  },
  computed: {
    userName () {
      return localStorage.getItem('userName')
    }
  },
  methods: {
    // 侧边栏折叠
    // collapseChange () {
    //   this.collapse = !this.collapse
    //   bus.$emit('collapse', this.collapse)
    // },
    // 全屏事件
    handleFullScreen () {
      if (this.fullscreen) {
        if (document.exitFullscreen) {
          document.exitFullscreen()
        } else if (document.webkitCancelFullScreen) { // safari 、Chrome
          document.webkitCancelFullScreen()
        } else if (document.mozCancelFullScreen) { // firefox
          document.mozCancelFullScreen()
        } else if (document.msExitFullScreen) { // ie
          document.msExitFullScreen()
        }
      } else {
        let element = document.documentElement
        if (element.requestFullscreen) {
          element.requestFullscreen()
        } else if (element.webkitRequestFullScreen) {// safari 、Chrome
          element.webkitRequestFullScreen()
        } else if (element.mozRequestFullScreen) { // firefox
          element.mozRequestFullScreen()
        } else if (element.msRequestFullScreen) { // ie
          element.msRequestFullScreen()
        }
      }
      this.fullscreen = !this.fullscreen
    },
    handleCommand (cmd) {
      if (cmd == 'logout') {
        this.$confirm('此操作将注销登录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.getRequest("/logout");
          window.sessionStorage.removeItem("user")
          this.$store.commit('initRoutes', []);
          this.$router.replace("/")
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }
    }

  }
}
</script>

<style>
    .header {
        font-family: "Microsoft YaHei";
        padding: 0;
        position: relative;
        background-color: #c20c0c;
        box-sizing: border-box;
        width: 100%;
        height: 70px;
        font-size: 22px;
        color: #ffffff;
    }

    .collapse-btn {
        float: left;
        padding: 0 21px;
        cursor: pointer;
        line-height: 70px;
    }

    .header .logo {
        float: left;
        line-height: 70px;
    }

    .header-right {
        float: right;
        padding-right: 50px;
        display: flex;
        height: 70px;
        align-items: center;
    }

    .btn-fullscreen {
        transform: rotate(45deg);
        margin-right: 50px;
        font-size: 24px;
    }

    .user-avator {
        margin-left: 20px;
    }

    .user-avator img {
        display: block;
        width: 40px;
        height: 40px;
        border-radius: 50%;
    }

    .user-name {
        margin-left: 10px;
    }

    .el-dropdown-link {
        color: #ffffff;
        cursor: pointer;
    }
</style>
