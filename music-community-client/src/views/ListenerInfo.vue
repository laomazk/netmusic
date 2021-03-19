<template>
    <div style="margin:100px">
        <el-card class="box-card" style="width: 400px">
            <div slot="header" class="clearfix">
                <span>{{listener.username}}</span>
            </div>
            <div>
                <div style="display: flex;justify-content: center">
                    <el-upload
                            :show-file-list="false"
                            :on-success="onSuccess"
                            :data="listener"
                            action="/my/updatepic/">
                        <img title="点击修改用户头像" :src="listener.avator"
                             style="width: 100px;height: 100px;border-radius: 50px"/>
                    </el-upload>

                </div>
                <div style="margin-left: 30px">性别：
                    <el-tag>{{attachSex(listener.sex)}}</el-tag>
                </div>
                <div style="margin-left: 30px">手机：
                    <el-tag>{{listener.phoneNum}}</el-tag>
                </div>
                <div style="margin-left: 30px">邮箱：
                    <el-tag>{{listener.email}}</el-tag>
                </div>
                <div style="margin-left: 30px">生日：
                    <el-tag>{{listener.birth}}</el-tag>
                </div>
                <div style="display: flex;justify-content: space-around;margin-top: 10px">
                    <el-button type="primary" @click="showUpdateUserInfo">修改信息</el-button>

                    <el-button type="danger" @click="showUpdatePasswdView">修改密码</el-button>
                </div>
            </div>
        </el-card>
        <el-dialog
                title="修改密码"
                :visible.sync="passwdDialogVisible"
                width="30%">
            <div>
                <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                    <el-form-item label="请输入旧密码" prop="oldPass">
                        <el-input type="password" v-model="ruleForm.oldPass" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="请输入新密码" prop="pass">
                        <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="新密码确认" prop="checkPass">
                        <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
                        <el-button @click="resetForm('ruleForm')">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-dialog>
        <el-dialog
                title="修改用户信息"
                :visible.sync="dialogVisible"
                width="30%">
            <div>
                <table>
                    <tr>
                        <td>
                            <el-tag>用户名：</el-tag>
                        </td>
                        <td>
                            <el-input v-model="listener2.username"></el-input>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <el-tag>性别：</el-tag>
                        </td>
                        <input type="radio" name="sex" value="0" v-model="listener2.sex">&nbsp;女&nbsp;&nbsp;
                        <input type="radio" name="sex" value="1" v-model="listener2.sex">&nbsp;男
                    </tr>
                    <tr>
                        <td>
                            <el-tag>手机：</el-tag>
                        </td>
                        <td>
                            <el-input v-model="listener2.phoneNum"></el-input>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <el-tag>邮箱：</el-tag>
                        </td>
                        <td>
                            <el-input v-model="listener2.email"></el-input>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <el-tag>生日：</el-tag>
                        </td>
                        <td>
                            <el-date-picker type="date" placeholder="选择日期" v-model="listener2.birth"
                                            style="width: 100%"></el-date-picker>
                        </td>
                    </tr>
                </table>
            </div>
            <span slot="footer" class="dialog-footer">
                            <el-button @click="dialogVisible = false">取 消</el-button>
                            <el-button type="primary" @click="updateUserInfo">确 定</el-button>
                          </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
  name: "ListenerInfo",
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        oldPass:'',
        pass: '',
        checkPass: ''
      },
      rules: {
        oldPass: [
          { validator: validatePass, trigger: 'blur' }
        ],
        pass: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'blur' }
        ]
      },
      listener: '',
      listener2: '',
      dialogVisible: false,
      passwdDialogVisible: false,
    }
  },
  mounted() {
    this.initListener();
  },
  methods: {
    onSuccess(){
      this.initListener();
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.ruleForm.lisId = this.listener.id
          this.putRequest('/my/pass',this.ruleForm).then(resp=>{
            if(resp){
              this.getRequest("/logout");
              window.sessionStorage.removeItem("user")
              his.$store.commit('setLoginIn', false);
              this.$store.commit('setUserId', '');
              this.$store.commit('setUsername', '');
              this.$store.commit('setAvator', '');
              this.$store.commit('setIsActive', false);
              this.passwdDialogVisible = false
              this.$router.replace("/");
            }
          })
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    showUpdatePasswdView(){
      this.passwdDialogVisible = true
    },
    updateUserInfo() {
      console.log(this.listener2)
      this.putRequest('/my/info', this.listener2).then(resp => {

        if (resp) {
          this.dialogVisible = false
          this.initListener();
        }
      })
    },
    showUpdateUserInfo() {
      this.dialogVisible = true
    },
    initListener() {
      this.getRequest('/my/info').then(resp => {
        if (resp) {
          this.listener = resp
          this.listener2 = Object.assign({}, this.listener)
          window.sessionStorage.setItem("user", JSON.stringify(resp))
          this.$store.commit('setUsername', resp.username);
          this.$store.commit('setAvator', resp.avator);
        }
      })
    },
    //获取性别
    attachSex(value) {
      if (value == 0) {
        return '女'
      } else if (value == 1) {
        return '男'
      }
      return ''
    },
  }
}
</script>

<style scoped>

</style>
