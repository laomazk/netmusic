<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-button type="danger" size="mini" @click="delVisible=true" icon="el-icon-delete">批量删除</el-button> &nbsp;
                <el-input size="mini" v-model="select_word" placeholder="请输入歌曲名" class="handle-input"></el-input> &nbsp;
                <el-button type="primary" size="mini" @click="centerDialogVisible = true" icon="el-icon-circle-plus-outline">添加歌曲</el-button> &nbsp;
            </div>
        </div>
        <el-table size="mini" ref="multipleTable" border style="width: 100%" height="680px" :data="data"
                  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="40px"></el-table-column>
            <el-table-column prop="name" label="歌手-歌名" align="center">
            </el-table-column>
            <el-table-column label="操作" width="150" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
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

        <el-dialog title="添加歌曲" :visible.sync="centerDialogVisible" width="400px" center>
            <el-form :model="registerForm" ref="registerForm" label-width="80px" action="" id="tf">
                <el-form-item prop="singerName" label="歌手名字" size="mini">
                    <el-input v-model="registerForm.singerName" placeholder="歌手名字"></el-input>
                </el-form-item>
                <el-form-item prop="songName" label="歌曲名字" size="mini">
                    <el-input v-model="registerForm.songName" placeholder="歌曲名字"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="centerDialogVisible = false">取消</el-button>
                <el-button size="mini" @click="getSongId">确定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="删除歌曲" :visible.sync="delVisible" width="300px" center>
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
  name: "SouPlayListSong",

  data() {
    return {
      centerDialogVisible: false, // 添加弹窗是否显示
      delVisible: false, // 删除弹窗是否显示
      title: '',
      registerForm: { // 添加框
        singerName: '',     //歌手名字
        songName: ''        //歌曲名字
      },
      tableData: [],
      tempData: [],
      select_word: '',
      pageSize: 10, //  分页每页大小
      currentPage: 1, //  当前页
      idx: -1,// 当前选择项
      mutipleSelection: [],// 哪些项已经打钩
      songListId: ''          //歌单id
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
    this.songListId = this.$route.query.id;
    this.getData()
  },
  methods: {
    // 获取当前页
    handleCurrentChange(val) {
      this.currentPage = val
    },
    // 查询所有歌曲
    getData() {
      this.tempData = []
      this.tableData = []
      this.getRequest('/source/playlist/songs/' + this.songListId).then(resp => {
        for (let item of resp) {
          this.getSong(item.songId)
        }
      })

    },
    getSong(id) {
      this.getRequest('/source/song/detail/' + id).then(resp => {
        this.tempData.push(resp);
        this.tableData.push(resp)
      }).catch(err => {
        console.log(err)
      })
    },
    // 添加歌曲前的准备，获取到歌曲对象
    getSongId() {
      let _this = this;
      var songFullName = _this.registerForm.singerName + "-" + _this.registerForm.songName;
      this.getRequest('/source/song/name/' + songFullName).then(resp => {
          _this.addSong(resp[0].id)
      })
    },
    // 添加歌曲
    addSong(songId) {
      let _this = this;
      let url = '/source/playlist/songs/?songId=' + songId + '&songListId=' + this.songListId
      _this.postRequest(url).then(resp => {
        this.getData();
      })
      _this.centerDialogVisible = false;

    },
    // 弹出删除窗口
    handleDelete(id) {
      this.idx = id;
      this.delVisible = true;
      this.deleteRow();
      // this.getData();

    },
    //删除一条歌曲
    deleteRow() {
      let url = '/source/playlist/songs/?songId=' + this.idx + '&songListId=' + this.songListId
      this.deleteRequest(url).then(resp => {
        if (resp) {
          this.getData()
        }
      })
      this.delVisible = false;
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

    .song-img {
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

    .play {
        position: absolute;
        z-index: 100;
        width: 80px;
        height: 80px;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        top: 18px;
        left: 15px;
    }

    .icon {
        width: 2em;
        height: 2em;
        color: white;
        fill: currentColor;
        overflow: hidden;
    }
</style>
