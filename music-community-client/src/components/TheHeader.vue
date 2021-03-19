<template>
    <div class="home">
        <el-header class="homeHeader">
            <div class="homeLeft">
                <div class="title">
                    <span class="collapse-btn">
                        <i class="fa fa-apple"></i>
                    </span>
                    NetMusic
                </div>
                <div style="margin-left: 30px;height: 60px">
                    <!--                    :default-active="0"-->
                    <el-menu
                            :default-active="midx"
                            class="el-menu-demo"
                            mode="horizontal"
                            background-color="#409eff"
                            text-color="#fff1fd"
                            active-text-color="#4e72b8"
                    >
                        <!--                        :class="{active: item.name == activeName}"  :index="(idx+1)+''"-->
                        <el-menu-item v-for="(item,idx) in navMsg" :key="item.path" :index="item.idx"
                                      @click="goPage(item.path,item.name,idx)"
                        >{{item.name}}
                        </el-menu-item>
                    </el-menu>
                </div>
                <div style="margin-top: 17px;padding-left: 30px">
                    <el-input placeholder="搜索音乐" size="small" @keydown.enter.native="goSearch()" v-model="keywords"
                              style="width: 270px"
                              suffix-icon="el-icon-search"
                    />
                    <el-button size="small" style="margin-left: 5px" @click="goSearch()">搜索</el-button>
                </div>
            </div>
            <div>
                <div style="padding-right: 50px">
                    <el-button plain size="small" @click="showLoginView" v-if="!user">登录</el-button>
                    <el-button icon="el-icon-bell" type="text" style="margin-right: 8px;color: #000000;" size="normal"
                               v-if="user">
                    </el-button>
                    <el-dropdown class="userInfo" @command="commandHandler" v-if="user">
                          <span class="el-dropdown-link">
                            {{ user.username }}
                            <i><img :src="user.avator" alt=""></i>
                          </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
                            <br>
                            <el-dropdown-item divided command="logout">注销登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </div>
        </el-header>

        <el-dialog
                :visible.sync="dialogVisible"
                width="500px">
            <el-tabs v-model="tabName" @tab-click="handleClick" style="margin-top: -45px;width:450px ">
                <el-tab-pane label="普通登录" name="normalLogin">
                    <div style="margin-top: 20px; text-align: center">
                        <el-form :model="loginForm" ref="loginForm" label-width="80px" :rules="rules"
                                 style="margin-left: 50px">
                            <el-form-item prop="username" label="用户名" style="width: 300px">
                                <el-input v-model="loginForm.username" placeholder="用户名"></el-input>
                            </el-form-item>
                            <el-form-item prop="password" label="密码" style="width: 300px">
                                <el-input type="password" v-model="loginForm.password" placeholder="密码"
                                          @keydown.enter.native="handleLogin"></el-input>
                            </el-form-item>
                            <br/>
                            <el-button size="small" @click="toRegister" style="margin-left: -30px">注 册</el-button>
                            <el-button size="small" type="primary" @click="handleLogin">登 录</el-button>
                        </el-form>
                    </div>

                </el-tab-pane>
                <el-tab-pane label="QQ登录" name="QQlLogin">敬请期待</el-tab-pane>
            </el-tabs>
        </el-dialog>

        <el-dialog
                title="注册"
                :visible.sync="regdialogVisible"
                width="500px">
            <div style="margin-top: 20px; text-align: center">
                <el-form :model="registerForm" ref="registerForm" label-width="80px" :rules="regrules"
                         style="margin-left: 50px">
                    <el-form-item prop="username" label="用户名" style="width: 300px">
                        <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
                    </el-form-item>
                    <el-form-item prop="password" label="密码" style="width: 300px">
                        <el-input type="password" v-model="registerForm.password" placeholder="密码"></el-input>
                    </el-form-item>
                    <el-form-item prop="repassword" label="确认密码" style="width: 300px">
                        <el-input type="password" v-model="registerForm.repassword" placeholder="再次输入密码"></el-input>
                    </el-form-item>
                    <el-form-item prop="sex" label="性别" style="width: 300px">
                        <el-radio-group v-model="registerForm.sex">
                            <el-radio :label="0">女</el-radio>
                            <el-radio :label="1">男</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item prop="phoneNum" label="手机" style="width: 300px">
                        <el-input v-model="registerForm.phoneNum" placeholder="手机"></el-input>
                    </el-form-item>
                    <el-form-item prop="email" label="邮箱" style="width: 300px">
                        <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
                    </el-form-item>
                    <el-form-item prop="birth" label="生日" style="width: 300px">
                        <el-date-picker v-model="registerForm.birth" placeholder="选择日期"
                                        style="width: 100%;"></el-date-picker>
                    </el-form-item>
                    <el-form-item prop="introduction" label="签名" style="width: 300px">
                        <el-input v-model="registerForm.introduction" placeholder="签名"></el-input>
                    </el-form-item>
                    <el-form-item prop="location" label="地区" style="width: 300px">
                        <el-select v-model="registerForm.location" placeholder="地区" style="width: 100%;">
                            <el-option v-for=" item in cities" :key="item.value" :label="item.label"
                                       :value="item.value"></el-option>
                        </el-select>
                    </el-form-item>

                    <br/>
                    <el-button size="small" @click="regdialogVisible=false" style="margin-left: -30px">取 消</el-button>
                    <el-button size="small" type="primary" @click="handleRegister">注 册</el-button>
                </el-form>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import {navMsg} from "../assets/data/header";
import {mapGetters} from 'vuex';
import {cities} from "../assets/data/form";
import {Message} from "element-ui";

export default {
  name: "TheHeader",
  data() {
    // 匹配规则
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.registerForm.repassword !== '') {
          this.$refs.registerForm.validateField('repassword');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.registerForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    var validatePhone = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入手机号"));
      } else {
        if (!/^1[3456789]\d{9}$/.test(value)) {
          callback(new Error("请输入正确的手机号"));
        } else {
          callback();
        }
      }
    };
    return {
      navMsg: [],    //左侧导航栏
      keywords: '',  //搜索关键字
      loginMsg: [],  //右侧导航栏
      menuList: [], //用户下拉菜单
      dialogVisible: false,
      regdialogVisible: false,
      loginForm: {
        username: '',       //用户名
        password: '',       //密码
      },
      rules: {
        username: [
          {required: true, trigger: 'blur', message: '请输入用户名'}
        ],
        password: [
          {required: true, trigger: 'blur', message: '请输入密码'}
        ]
      },
      regrules: {
        username: [
          {required: true, trigger: 'blur', message: '请输入用户名'}
        ],
        password: [
          {required: true, validator: validatePass, trigger: 'blur'}
        ],
        repassword: [
          {required: true, validator: validatePass2, trigger: 'blur'}
        ],
        sex: [
          {required: true, message: '请选择性别', trigger: 'change'}
        ],
        phoneNum: [
          {validator: validatePhone, trigger: 'blur',}
        ],
        email: [
          {message: '请输入邮箱地址', trigger: 'blur'},
          {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
        ],
        birth: [
          {required: true, message: '请选择日期', trigger: 'change'}
        ],
        introduction: [
          {message: '请输入介绍', trigger: 'blur'}
        ],
        location: [
          {message: '请输入地区', trigger: 'change'}
        ]
      },
      registerForm: {
        username: '',       //用户名
        password: '',       //密码
        repassword: '',       //确认密码
        sex: '',            //性别
        phoneNum: '',       //手机
        email: '',          //邮箱
        birth: '',          //生日
        introduction: '',   //签名
        location: ''       //地区
      },
      cities: [],            //所有的地区--省
      tabName: 'normalLogin',
      user: JSON.parse(window.sessionStorage.getItem("user")),
      midx: '0'
    }
  },
  computed: {
    ...mapGetters([
      'activeName',
      'loginIn',
      'avator'
    ]),
    user1(){
      return this.$store.state.currentListener;
    }
  },
  created() {
    this.navMsg = navMsg;
    this.cities = cities;
  },
  methods: {
    commandHandler(cmd) {
      if (cmd == 'logout') {
        this.$confirm('此操作将注销登录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.getRequest("/logout");
          window.sessionStorage.removeItem("user")
          this.$store.commit('setLoginIn', false);
          this.$store.commit('setUserId', '');
          this.$store.commit('setUsername', '');
          this.$store.commit('setAvator', '');
          this.$store.commit('setIsActive', false);
          this.user = JSON.parse(window.sessionStorage.getItem("user"))
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          });
        });
      } else if(cmd=='userinfo'){
        this.$router.push({path:'/listenerinfo'})
      }

    },
    // 登录
    handleLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.postKeyValueRequest('/doLogin', this.loginForm).then(resp => {
            if (resp) {
              console.log('登录成功')
              window.sessionStorage.setItem("user", JSON.stringify(resp.obj))
              this.user = resp.obj

              this.$store.commit('setLoginIn', true);
              this.$store.commit('setUserId', this.user.id);
              this.$store.commit('setUsername', this.user.username);
              this.$store.commit('setAvator', this.user.avator);
              this.dialogVisible = false;
            }
          })
        } else {
          this.$message.error('请输入所有字段')
          return false;
        }
      })
    },
    // 注册
    handleRegister() {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          this.postRequest('/user/listener/', this.registerForm).then(resp => {
            if (resp) {
              console.log('注册成功')
              this.regdialogVisible = false;
            }
          })
        } else {
          this.$message.error('请输入所有字段')
          return false;
        }
      })
    },
    // 选中时触发
    handleClick(tab, event) {
      console.log(tab, event);
    },
    // 跳转到注册
    toRegister() {
      this.regdialogVisible = true;
      this.dialogVisible = false;
    },
    // 显示登录页面
    showLoginView() {
      this.dialogVisible = true;
    },
    goHome() {
      this.$router.push({path: "/"})
    },
    goPage(path, name, idx) {
      if (!this.loginIn && path == '/my') {
        Message.warning('请先登录')
      } else {
        this.midx = idx + ''
        this.$store.commit('setActiveName', this.midx);
        this.$router.push({path: path})
      }
    },
    goSearch() {
      this.$router.push({path: '/search', query: {keywords: this.keywords}})
    }
  }
}
</script>

<style lang="scss" scoped>

    .homeRouterView {
        margin-top: 10px;
    }

    .homeWelcome {
        text-align: center;
        font-size: 30px;
        font-family: 微软雅黑;
        color: #409eff;
        padding-top: 50px;
    }

    .homeHeader {
        background-color: #409eff;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 0px 15px;
        box-sizing: border-box;
        margin-bottom: -70px;
        position: relative;
        z-index: 2;
    }

    .homeHeader .homeLeft {
        display: flex;
    }

    .homeHeader .title {
        padding-left: 50px;
        padding-top: 13px;
        font-size: 30px;
        font-family: Calibri;
        color: #fff1fd;
        cursor: pointer;
    }

    .homeHeader .userInfo {
        cursor: pointer;
    }

    .el-dropdown-link img {
        width: 48px;
        height: 48px;
        border-radius: 24px;
        margin-left: 8px;
    }

    .el-dropdown-link {
        display: flex;
        align-items: center;
    }
</style>
