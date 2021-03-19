<template>
    <div class="singer">
        <ul class="singer-header">
            <li v-for="(item,index) in singerStyle" :key="index" @click="handleChangeView(item)"
                :class="{active:item.name==activeName}">
                {{item.name}}
            </li>
        </ul>
        <div>
            <content-list :contentList="data"></content-list>
            <div class="pagination" style="display: flex;justify-content: flex-end">
                <el-pagination @current-change="handleCurrentChange" background layout="total,prev,pager,next"
                               :current-page="currentPage" :page-size="pageSize" :total="total">
                </el-pagination>
            </div>
        </div>
    </div>
</template>
<script>
import ContentList from '../components/ContentList';
import {getAllSinger,getSingerOfSex} from '../api/index';
import {mixin} from "../mixins";
import {singerStyle} from '../assets/data/singer';

export default {
  name: 'singer',
  components:{
    ContentList
  },
  data(){
    return{
      albumDatas: [],      //歌手数据
      pageSize: 15,        //页面大小，一页有15条数据
      currentPage: 1,      //当前页，默认第一页
      singerStyle: [],           //风格
      activeName: '全部歌手',   //当前风格
      total:0,
      type:'-1'
    }
  },
  computed:{
    //计算当前表格中的数据
    data(){
      return this.albumDatas
    }
  },
  mounted(){
    this.singerStyle = singerStyle;
    this.getSingerList();
  },

  methods:{
    getSingerList(){
     this.getRequest('/user/singer/?page='+this.currentPage+'&size='+this.pageSize+'&type='+this.type)
      .then(res=>{
        this.albumDatas = res.data
        this.total = res.total
      }).catch(err=>{
       console.log(err)
     })
    },
    //获取当前页
    handleCurrentChange(val){
      this.currentPage = val;
      this.getSingerList()
    },
    // 根据性别查询
    handleChangeView(item){
      this.activeName = item.name;
      this.currentPage = 1;
      this.albumDatas = [];
      this.type = item.type;
      this.getSingerList();
    }

  }
}
</script>

<style lang="scss" scoped>
    @import '../assets/css/singer.scss';
</style>
