<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-button type="danger" size="mini" @click="delVisible=true" icon="el-icon-delete">批量删除</el-button> &nbsp;
                <el-input size="mini" v-model="select_word" placeholder="请输入歌曲名" class="handle-input"></el-input> &nbsp;
                <el-button type="primary" size="mini" @click="centerDialogVisible = true" :icon="uploadIcon" :disabled="addSongDisable" v-if="singerId">
                    {{addSongFileMsg}}
                </el-button> &nbsp;
            </div>
        </div>
        <el-table size="mini" ref="multipleTable" border style="width: 100%" height="680px" :data="data"
                  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="40px"></el-table-column>
            <el-table-column label="歌曲图片" width="110" align="center">
                <template slot-scope="scope">
                    <div class="song-img">
                        <img :src="scope.row.pic" style="width: 100%"/>
                    </div>
                    <div class="play" @click="setSongUrl(scope.row.url,scope.row.name)">
                        <div v-if="toggle == scope.row.name ">
                            <svg class="icon">
                                <use xlink:href="#icon-zanting"></use>
                            </svg>
                        </div>
                        <div v-if="toggle != scope.row.name">
                            <svg class="icon">
                                <use xlink:href="#icon-bofanganniu"></use>
                            </svg>
                        </div>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="歌手-歌名" width="120px" align="center">
            </el-table-column>
            <el-table-column prop="introduction" label="专辑" width="150px" align="center">
            </el-table-column>
            <el-table-column label="歌词" align="center">
                <template slot-scope="scope">
                    <ul style="height:100px;overflow:scroll;">
                        <li v-for="(item,index) in parseLyric(scope.row.lyric)" :key="index">
                            {{item}}
                        </li>
                    </ul>
                </template>
            </el-table-column>

            <el-table-column label="资源更新" align="center" width="100">
                <template slot-scope="scope">
                    <el-upload
                            :show-file-list="false"
                            :before-upload="beforeAvatorUpload"
                            :on-success="handleAvatorSuccess"
                            action="/source/song/updatepic"
                            :data="scope.row"
                    >
                        <el-button size="mini">更新图片</el-button>
                    </el-upload>
                    <br>
                    <el-upload
                            :show-file-list="false"
                            :on-success="handleAvatorSuccess"
                            :on-error="handleUpdateSongErr"
                            action="/source/song/updateurl"
                            :data="scope.row"
                    >
                        <el-button size="mini">更新歌曲</el-button>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="150" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
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
                <div>
                    <label>歌名</label>
                    <el-input type="text" name="name" v-model="registerForm.name"></el-input>
                </div>
                <div>
                    <label>专辑</label>
                    <el-input type="text" name="introduction" v-model="registerForm.introduction"></el-input>
                </div>
                <div>
                    <label>歌词</label>
                    <el-input type="textarea" name="lyric" v-model="registerForm.lyric"></el-input>
                </div>
                <div>
                    <label>歌曲上传</label>
                    <input type="file" ref="myfile">
                </div>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="centerDialogVisible = false">取消</el-button>
                <el-button size="mini" @click="addSong">确定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="修改歌曲" :visible.sync="editVisible" width="400px" center>
            <el-form :model="form" ref="form" label-width="80px">
                <el-form-item prop="name" label="歌手-歌名" size="mini">
                    <el-input v-model="form.name" placeholder="歌手-歌名"></el-input>
                </el-form-item>
                <el-form-item prop="introduction" label="专辑" size="mini">
                    <el-input v-model="form.introduction" placeholder="专辑"></el-input>
                </el-form-item>
                <el-form-item prop="lyric" label="歌词" size="mini">
                    <el-input v-model="form.lyric" placeholder="歌词" type="textarea"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="editVisible = false">取消</el-button>
                <el-button size="mini" @click="editSave">确定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="删除歌曲" :visible.sync="delVisible" width="300px" center>
            <div align="center">删除不可恢复，是否确定删除？</div>
            <span slot="footer">
                <el-button size="mini" @click="delVisible = false">取消</el-button>
                <el-button size="mini" @click="delAll">确定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import {mixin} from "../../utils/mixins";
import {mapGetters} from 'vuex';
import '../../utils/iconfont'

export default {
  mixins: [mixin],
  name: "Song",

  data() {
    return {
      uploadIcon: 'el-icon-upload2',
      addSongDisable: false,
      addSongFileMsg: '添加歌曲',
      singerId: '', // 歌手id
      singerName: '', // 歌手名
      centerDialogVisible: false, // 添加弹窗是否显示
      editVisible: false, // 编辑弹窗是否显示
      delVisible: false, // 删除弹窗是否显示
      title: '',
      registerForm: { // 添加框
        name: '',
        singerName: '',
        introduction: '',
        lyric: ''
      },
      form: { // 编辑框
        id: '',
        name: '',
        singerName: '',
        introduction: '',
        lyric: ''
      },
      tableData: [],
      tempData: [],
      select_word: '',
      pageSize: 5, //  分页每页大小
      currentPage: 1, //  当前页
      idx: -1,// 当前选择项
      mutipleSelection: [],// 哪些项已经打钩
      toggle: false,// 播放器的图标状态
      play: true
    }
  },
  computed: {
    ...mapGetters([
      'isPlay'
    ]),

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
    if (this.$route.query.id && this.$route.query.name) {
      this.singerId = this.$route.query.id;
      this.singerName = this.$route.query.name;
    }

    this.getData()
  },
  destroyed() {
    this.$store.commit('setIsPlay', false)
  },
  methods: {
    changeInput(e) {
      this.$forceUpdate()
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
    // 查询所有歌曲
    getData() {
      this.tempData = []
      this.tableData = []
      if (this.singerId) {
        this.getRequest("/source/song/id/" + this.singerId).then(resp => {
          if (resp) {
            this.tempData = resp
            this.tableData = resp
            this.currentPage = 1
          }
        })
      } else {
        this.getRequest("/source/song/").then(resp => {
          if (resp) {
            this.tempData = resp
            this.tableData = resp
            this.currentPage = 1
            this.singerId = ''
            this.singerName = ''
          }
        })
      }

    },
    // 添加歌曲
    addSong() {
      let _this = this;
      _this.addSongFileMsg = '正在上传'
      _this.uploadIcon = 'el-icon-loading'
      _this.addSongDisable = true
      var form = new FormData(document.getElementById('tf'));
      form.append('singerId', this.singerId);
      form.set('name', this.singerName + '-' + form.get('name'));
      if (!form.get('lyric')) {
        form.set('lyric', '[00:00:00]暂无歌词');
      }
      let myfile = this.$refs.myfile;
      let files = myfile.files;
      let file = files[0];
      form.append("file", file)

      this.uploadFileRequest("/source/song/add/", form).then(resp => {
        if (resp) {
          console.log(resp.msg)
          _this.addSongFileMsg = '添加歌曲'
          _this.uploadIcon = 'el-icon-upload2'
          _this.addSongDisable = false
          this.getData();
        }
      }).catch(err => {
        console.log(err)
      })

      this.centerDialogVisible = false;

    },
    // 弹出编辑页面
    handleEdit(row) {
      this.editVisible = true
      this.form = {
        id: row.id,
        name: row.name,
        introduction: row.introduction,
        lyric: row.lyric
      }
    },
    editSave() {
      this.putRequest('/source/song/', this.form).then(resp => {
        if (resp) {
          this.editVisible = false
        }
      })
      this.getData();
    },
    // 解析歌词
    parseLyric(text) {
      let lines = text.split('\n')
      let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g
      let result = []
      for (let item of lines) {
        let value = item.replace(pattern, '')
        result.push(value)
      }
      return result
    },
    // 切换播放歌曲
    setSongUrl(url, name) {
      // this.toggle = name
      // this.$store.commit('setUrl', url)
      // if (this.isPlay) {
      //   this.$store.commit('setIsPlay', false)
      // } else {
      //   this.$store.commit('setIsPlay', true)
      // }
      if (this.toggle == name) {
        this.toggle = false;
        this.$store.commit('setIsPlay', false)
      } else {
        this.toggle = name;
        this.$store.commit('setUrl', url)
        this.$store.commit('setIsPlay', true)
      }
    },
    // 弹出删除窗口
    handleDelete(id) {
      console.log(id)
      this.idx = id
      this.deleteRequest("/source/song/" + id).then(resp => {
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
