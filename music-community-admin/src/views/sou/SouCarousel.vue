<template>
    <div class="table">
        <el-table size="mini" border style="width: 100%" height="830" :data="tableData"
                  @selection-change="handleSelectionChange">
            <el-table-column label="轮播图" width="150" align="center">
                <template slot-scope="scope">
                    <div class="carousel-img">
                        <img :src="scope.row.pic" style="width: 100%"/>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="title" label="标题" width="180px" align="center">
            </el-table-column>
            <el-table-column prop="url" label="链接地址" align="center">
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="150px" align="center">
            </el-table-column>
            <el-table-column prop="updateTime" label="更新时间" width="150px" align="center">
            </el-table-column>
            <el-table-column label="操作" width="200" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" @click="handleEdit(scope.row)">更新内容</el-button>
                    <br>
                    <br>
                    <el-upload
                            :show-file-list="false"
                            :before-upload="beforeAvatorUpload"
                            :on-success="handleAvatorSuccess"
                            action="/sou/carousel/updatepic"
                            :data="scope.row"
                    >
                        <el-button size="mini">更新图片</el-button>
                    </el-upload>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog title="编辑轮播图信息" :visible.sync="editVisible" width="400px" center>
            <el-form :model="form" ref="form" label-width="80px">
                <el-form-item prop="name" label="标题" size="mini">
                    <el-input v-model="form.title" placeholder="标题"></el-input>
                </el-form-item>
                <el-form-item prop="url" label="链接地址" size="mini">
                    <el-input v-model="form.url" placeholder="链接地址"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="editVisible = false">取消</el-button>
                <el-button size="mini" @click="editSave">确定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import {mixin} from "../../utils/mixins";

export default {
  mixins: [mixin],
  name: "SouCarousel",

  data(){
    return{
      tableData: [],
      tempData: [],
      editVisible: false, // 编辑弹窗是否显示
      form: { // 编辑框
        id: '',
        title: '',
        url: ''
      },
    }
  },
  created() {
    this.getData()
  },
  methods:{
    editSave() {
      this.putRequest('/sou/carousel/put/', this.form).then(resp => {
        if (resp) {
          this.editVisible = false
          this.getData();
        }
      })

    },
    // 弹出编辑页面
    handleEdit(row) {
      this.editVisible = true
      this.form = {
        id: row.id,
        title: row.title,
        url: row.url
      }
    },
    getData(){
      this.tempData = []
      this.tableData = []
      this.getRequest("/sou/carousel/").then(resp=>{
        if(resp){
          this.tempData = resp
          this.tableData = resp
        }
      })
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

    .carousel-img {
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
