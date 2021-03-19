<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-button type="danger" size="mini" @click="delVisible=true" icon="el-icon-delete">批量删除</el-button> &nbsp;
                <el-input size="mini" v-model="select_word" placeholder="请输入歌手名" class="handle-input"></el-input> &nbsp;
                <el-button type="primary" size="mini" @click="showAddSingerView" icon="el-icon-circle-plus-outline">添加歌手</el-button> &nbsp;
            </div>
        </div>
        <el-table size="mini" ref="multipleTable" border style="width: 100%" height="680px" :data="data"
                  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="40px"></el-table-column>
            <el-table-column label="歌手图片" width="110" align="center">
                <template slot-scope="scope">
                    <div class="siger-img">
                        <img :src="scope.row.pic" style="width: 100%"/>
                    </div>
                    <!--                    <el-upload :action="/source/singer/updatepic" :data="scope.row"-->
                    <!--                               :on-success="onSucess" :show-file-list="false">-->
                    <!--                        <el-button size="mini">更新图片</el-button>-->
                    <!--                    </el-upload>-->
                    <el-upload
                            :show-file-list="false"
                            :before-upload="beforeAvatorUpload"
                            :on-success="handleAvatorSuccess"
                            action="/source/singer/updatepic"
                            :data="scope.row"
                    >
                        <el-button size="mini">更新图片</el-button>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="歌手" width="120px" align="center">
            </el-table-column>
            <el-table-column label="性别" width="50" align="center">
                <template slot-scope="scope">
                    {{changeSex(scope.row.sex)}}
                </template>
            </el-table-column>
            <el-table-column label="生日" width="120px" align="center">
                <template slot-scope="scope">
                    {{attachBirth(scope.row.birth)}}
                </template>
            </el-table-column>
            <el-table-column prop="location" label="地区" width="100" align="center">
            </el-table-column>
            <el-table-column label="简介">
                <template slot-scope="scope">
                    <p style="height: 100px;overflow: scroll">{{scope.row.introduction}}</p>
                </template>
            </el-table-column>
            <el-table-column label="歌曲管理" width="110" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" @click="songEdit(scope.row.id,scope.row.name)">歌曲管理</el-button>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="150" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" @click="showEditSingerView(scope.row)">编辑</el-button>
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
                <el-form-item prop="name" label="歌手名" size="mini">
                    <el-input v-model="registerForm.name" placeholder="歌手名"></el-input>
                </el-form-item>
                <el-form-item label="性别" size="mini">
                    <el-radio-group v-model="registerForm.sex">
                        <el-radio :label="0">女</el-radio>
                        <el-radio :label="1">男</el-radio>
                        <el-radio :label="2">组合</el-radio>
                        <el-radio :label="3">不明</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="birth" label="生日" size="mini">
                    <el-date-picker type="date" placeholder="选择日期" v-model="registerForm.birth"
                                    style="width: 100%"></el-date-picker>
                </el-form-item>
                <el-form-item prop="location" label="地区" size="mini" >
                    <el-select v-model="registerForm.location" placeholder="地区" size="mini" >
                        <el-option
                                v-for="item in locations"
                                :key="item.id"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item prop="introduction" label="简介" size="mini">
                    <el-input v-model="registerForm.introduction" placeholder="简介" type="textarea"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="cancelEdit">取消</el-button>
                <el-button size="mini" @click="editSinger">确定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="删除歌手" :visible.sync="delVisible" width="300px" center>
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
  name: "Singer",

  data() {
    return {
      locations:[{
        id:1,
        value: '中国',
        label: '中国'
      }, {
        id:2,
        value: '韩国',
        label: '韩国'
      }, {
        id:3,
        value: '日本',
        label: '日本'
      }, {
        id:4,
        value: '美国',
        label: '美国'
      }, {
        id:5,
        value: '新加坡',
        label: '新加坡'
      }, {
        id:6,
        value: '意大利',
        label: '意大利'
      }, {
        id:7,
        value: '马来西亚',
        label: '马来西亚'
      }, {
        id:8,
        value: '其他',
        label: '其他'
      }],
      centerDialogVisible: false, // 添加弹窗是否显示
      editVisible: false, // 编辑弹窗是否显示
      delVisible: false, // 删除弹窗是否显示
      title: '',
      registerForm: { // 添加框或编辑框
        id: '',
        name: '',
        sex: '',
        birth: '',
        location: '',
        introduction: ''
      },
      form: { // 编辑框
        id: '',
        name: '',
        sex: '',
        birth: '',
        location: '',
        introduction: ''
      },
      tableData: [],
      tempData: [],
      select_word: '',
      pageSize: 5, //  分页每页大小
      currentPage: 1, //  当前页
      idx: -1,// 当前选择项
      mutipleSelection: [] // 哪些项已经打钩
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
          if (item.name.includes(this.select_word)) {
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
    emptySinger() {
      this.registerForm = { // 添加框或编辑框
        id: '',
        name: '',
        sex: '',
        birth: '',
        location: '',
        introduction: ''
      }
    },
    // 删除一名歌手
    deleteRow(data) {
      this.$confirm('此操作将永久【' + data.name + '】, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest("/source/singer/" + data.id).then(resp => {
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
    showEditSingerView(data) {
      this.title = '编辑歌手信息'
      this.form = data
      this.registerForm = data
      this.centerDialogVisible = true
    },
    showAddSingerView() {
      this.emptySinger();
      this.title = '添加歌手';
      this.centerDialogVisible = true;
    },
    // onSucess(){
    //     this.$message({
    //         type: 'success',
    //         message: '更新成功'
    //     });
    //   this.getData();
    // },
    // 获取当前页
    handleCurrentChange(val) {
      this.currentPage = val
    },
    // 查询所有歌手
    getData() {
      this.tempData = []
      this.tableData = []
      this.getRequest("/source/singer/").then(resp => {
        if (resp) {
          this.tempData = resp
          this.tableData = resp
          this.currentPage = 1
        }
      })
    },
    editSinger() {
      if (this.registerForm.id) {
        console.log("编辑")
        this.putRequest("/source/singer/", this.form).then(resp => {
          if (resp) {
            this.centerDialogVisible = false;
            this.getData();
          }
        })
      } else {
        console.log("添加")
        this.postRequest("/source/singer/", this.registerForm).then(resp => {
          if (resp) {
            this.centerDialogVisible = false;
            this.getData();
          }
        })
      }
    },
    // // 更新图片
    // uploadUrl(id){
    //   this.postRequest("/source/singer/updatepic/",id)
    // },
    // 弹出删除窗口
    handleDelete(id) {
      this.idx = id
      this.deleteRequest("/source/singer/" + id).then(resp => {
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
    // 转向歌曲管理页面
    songEdit(id, name) {
      // this.$router.replace('/sou/song','/')
      this.$router.push({path: `/sou/song`, query: {id, name}})
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
