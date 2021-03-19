<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-button type="danger" size="mini" @click="delVisible=true" icon="el-icon-delete">批量删除</el-button> &nbsp;
                <el-input size="mini" v-model="select_word" placeholder="筛选相关用户" class="handle-input"></el-input> &nbsp;
                <el-button type="primary" size="mini" @click="showAddListenerView" icon="el-icon-circle-plus-outline">添加新用户</el-button> &nbsp;
            </div>
        </div>
        <el-table size="mini" ref="multipleTable" border style="width: 100%" height="680px" :data="data"
                  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="40px"></el-table-column>
            <el-table-column label="用户图片" width="110" align="center">
                <template slot-scope="scope">
                    <div class="siger-img">
                        <img :src="scope.row.avator" style="width: 100%"/>
                    </div>
                    <el-upload
                            :show-file-list="false"
                            :before-upload="beforeAvatorUpload"
                            :on-success="handleAvatorSuccess"
                            action="/listener/basic/updatepic"
                            :data="scope.row"
                    >
                        <el-button size="mini">更新图片</el-button>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column prop="username" label="用户名" width="120px" align="center">
            </el-table-column>
            <el-table-column label="性别" width="50" align="center">
                <template slot-scope="scope">
                    {{changeSex(scope.row.sex)}}
                </template>
            </el-table-column>
            <el-table-column prop="phoneNum" label="手机号" width="120px" align="center">
            </el-table-column>
            <el-table-column prop="email" label="电子邮箱" width="240px" align="center">
            </el-table-column>
            <el-table-column label="生日" width="120px" align="center">
                <template slot-scope="scope">
                    {{attachBirth(scope.row.birth)}}
                </template>
            </el-table-column>
            <el-table-column prop="introduction" label="签名" align="center">
            </el-table-column>
            <el-table-column prop="location" label="地区" width="100" align="center">
            </el-table-column>
            <el-table-column label="收藏" width="80" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" @click="getCollect(data[scope.$index].id)">收藏</el-button>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="300" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" @click="showEditListenerView(scope.row)">编辑</el-button>
                    <el-button size="mini" @click="showEditPasswdView(scope.row)">修改密码</el-button>
                    <el-button size="mini" type="danger" @click="deleteRow(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagination">
            <el-pagination
                    background
                    layout="total,prev,pager,next"
                    :current-page="currentPage"
                    :page-size="pageSize"
                    :total="tableData.length"

                    @current-change="handleCurrentChange"
            >

            </el-pagination>
        </div>

        <el-dialog :title="title" :visible.sync="centerDialogVisible" width="400px" center>
            <el-form :model="registerForm" ref="registerForm" label-width="80px">
                <el-form-item prop="username" label="用户名" size="mini">
                    <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item prop="password" label="密码" size="mini" v-if="!registerForm.id">
                    <el-input type="password" v-model="registerForm.password" placeholder="密码"></el-input>
                </el-form-item>
                <el-form-item label="性别" size="mini">
                    <input type="radio" name="sex" value="0" v-model="registerForm.sex">&nbsp;女&nbsp;&nbsp;
                    <input type="radio" name="sex" value="1" v-model="registerForm.sex">&nbsp;男
                </el-form-item>
                <el-form-item prop="phoneNum" label="手机号" size="mini">
                    <el-input v-model="registerForm.phoneNum" placeholder="手机号"></el-input>
                </el-form-item>
                <el-form-item prop="email" label="电子邮箱" size="mini">
                    <el-input v-model="registerForm.email" placeholder="电子邮箱"></el-input>
                </el-form-item>
                <el-form-item prop="birth" label="生日" size="mini">
                    <el-date-picker type="date" placeholder="选择日期" v-model="registerForm.birth"
                                    style="width: 100%"></el-date-picker>
                </el-form-item>
                <el-form-item prop="introduction" label="签名" size="mini">
                    <el-input v-model="registerForm.introduction" placeholder="签名"></el-input>
                </el-form-item>
                <el-form-item prop="location" label="地区" size="mini">
                    <el-input v-model="registerForm.location" placeholder="地区"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="cancelEdit">取消</el-button>
                <el-button size="mini" @click="editListener">确定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="修改密码" :visible.sync="editPasswdDialogVisible" width="400px" center>
            <el-form :model="PasswordForm" ref="PasswordForm" label-width="80px" :rules="editPasswdRules">
                <el-form-item prop="oldPassword" label="原密码" size="mini">
                    <el-input type="password" v-model="PasswordForm.oldPassword" placeholder="原密码"></el-input>
                </el-form-item>
                <el-form-item prop="newPassword" label="新密码" size="mini">
                    <el-input type="password" v-model="PasswordForm.newPassword" placeholder="新密码"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="cancelEditPassword">取消</el-button>
                <el-button size="mini" @click="editListenerPassword">确定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="删除用户" :visible.sync="delVisible" width="300px" center>
            <div align="center">删除不可恢复，是否确定删除？</div>
            <span slot="footer">
                <el-button size="mini" @click="delVisible = false">取消</el-button>
                <el-button size="mini" type="danger" @click="delAll">确定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import {mixin} from "../../utils/mixins";

export default {
  mixins: [mixin],
  name: "Listener",

  data() {
    return {
      editPasswdDialogVisible: false,//编辑密码弹窗是否显示
      centerDialogVisible: false, // 添加弹窗是否显示
      editVisible: false, // 编辑弹窗是否显示
      delVisible: false, // 删除弹窗是否显示
      title: '',
      registerForm: {      //添加框
        username: '',
        password: '',
        sex: '1',
        phoneNum: '',
        email: '',
        birth: '',
        introduction: '',
        location: ''
      },
      form: {      //编辑框
        id: '',
        username: '',
        sex: '',
        phoneNum: '',
        email: '',
        birth: '',
        introduction: '',
        location: ''
      },
      PasswordForm: {      //编辑框
        id: '',
        oldPassword: '',
        newPassword: ''
      },
      tableData: [],
      tempData: [],
      select_word: '',
      pageSize: 5, //  分页每页大小
      currentPage: 1, //  当前页
      idx: -1,// 当前选择项
      mutipleSelection: [], // 哪些项已经打钩
      editPasswdRules: {
        oldPassword: [
          {required: true, message: '请输入旧密码', trigger: 'blur'}
        ],
        newPassword: [
          {required: true, message: '请输入新密码', trigger: 'blur'},
        ]
      }
    }
  },
  computed: {
    // 计算当前搜索结果表里的数据
    data() {
      return this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    }
  },
  watch: {
    // 搜索框里的内容发生变化时候，搜索结果table列表的内容跟着他的内容发生变化
    select_word: function () {
      if (this.select_word == '') {
        this.tableData = this.tempData
      } else {
        this.tableData = []
        for (let item of this.tempData) {
          if (item.username.includes(this.select_word)) {
            this.tableData.push(item)
          }
        }
      }
    }
  },
  created() {
    this.getData()
  },
  methods: {
    // 置空添加框
    emptyListener() {
      this.registerForm = { // 添加框或编辑框
        username: '',
        password: '',
        sex: '',
        phoneNum: '',
        email: '',
        birth: '',
        introduction: '',
        location: ''
      }
    },
    // 删除一名用户
    deleteRow(data) {
      this.$confirm('此操作将永久【' + data.username + '】, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest("/listener/basic/" + data.id).then(resp => {
          if (resp) {
            this.getData()
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    cancelEdit() {
      this.centerDialogVisible = false;
      this.getData()
    },
    cancelEditPassword() {
      this.editPasswdDialogVisible = false;
    },
    showEditPasswdView(data) {
      this.editPasswdDialogVisible = true;
      this.PasswordForm = {
        id: data.id,
        oldPassword: '',
        newPassword: ''
      }
    },
    showEditListenerView(data) {
      this.title = '编辑用户信息'
      this.form = { // 添加框或编辑框
        id: data.id,
        username: data.username,
        sex: data.sex,
        phoneNum: data.phoneNum,
        email: data.email,
        birth: data.birth,
        introduction: data.introduction,
        location: data.location
      }
      this.registerForm = data
      this.centerDialogVisible = true
    },
    showAddListenerView() {
      this.emptyListener();
      this.title = '添加新用户';
      this.centerDialogVisible = true;
    },
    // 获取当前页
    handleCurrentChange(val) {
      this.currentPage = val
    },
    // 查询所有用户
    getData() {
      this.tempData = []
      this.tableData = []
      this.getRequest("/listener/basic/").then(resp => {
        if (resp) {
          this.tempData = resp
          this.tableData = resp
          this.currentPage = 1
        }
      })
    },
    editListenerPassword() {
      this.$refs['PasswordForm'].validate(valid => {
        if (valid) {
          this.postRequest('/listener/basic/updatepd/', this.PasswordForm).then(resp => {
            console.log(resp)
            this.editPasswdDialogVisible = false
          })
        }
      })
    },
    editListener() {
      if (this.registerForm.id) {
        console.log("编辑")
        this.putRequest("/listener/basic/", this.form).then(resp => {
          if (resp) {
            this.centerDialogVisible = false;
            this.getData();
          }
        })
      } else {
        console.log("添加")
        this.postRequest("/listener/basic/", this.registerForm).then(resp => {
          if (resp) {
            this.centerDialogVisible = false;
            this.getData();
          }
        })
      }
    },
    // 弹出删除窗口
    handleDelete(id) {
      this.idx = id
      this.deleteRequest("/listener/basic/" + id).then(resp => {
        if (resp) {
          this.getData()
        }
      })
    },
    // 把已经选择的项赋值给 mutipleSelection
    handleSelectionChange(val) {
      this.mutipleSelection = val
    },
    // 批量删除已经选择的项
    delAll() {
      this.delVisible = true
      for (let item of this.mutipleSelection) {
        this.handleDelete(item.id)
      }
      this.mutipleSelection = []
      this.delVisible = false;
    },
    //转向该用户的收藏列表
    getCollect(id){
      this.$router.push({path: '/sou/collect',query:{id}})
    }
  }
}
</script>

<style scoped>
    .table {
        padding: 15px;
    }

    .handle-box {
        margin-bottom: 20px;
    }

    .siger-img {
        width: 100%;
        height: 80px;
        border-radius: 5px;
        margin-bottom: 5px;
        overflow: hidden;
    }

    .handle-input {
        width: 300px;
        display: inline-block;
    }

    .pagination {
        display: flex;
        justify-content: center;
    }
</style>
