<template>
    <div class="song-list">
        <ul class="song-list-header">
            <li v-for="(item,index) in songStyle" :key="index" @click="handleChangeView(item.name)"
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
import {getAllSongList, getSongListOfLikeStyle} from '../api/index';
import {mixin} from "../mixins";
import {songStyle} from '../assets/data/songList';

export default {
  name: 'play-list',
  components: {
    ContentList
  },
  data() {
    return {
      albumDatas: [],      //歌单数据
      pageSize: 15,        //页面大小，一页有15条数据
      currentPage: 1,      //当前页，默认第一页
      songStyle: [],           //风格
      activeName: '全部歌单',    //当前风格
      total: 0,
      style: ''
    }
  },
  computed: {
    //计算当前表格中的数据
    data() {
      return this.albumDatas
    }
  },
  mounted() {
    this.songStyle = songStyle;
    this.getSongList();
  },

  methods: {
    getSongList() {
      let url = '/user/playlist/?page=' + this.currentPage + '&size=' + this.pageSize
      if (this.style != '' && this.style != '全部歌单') {
        url += ('&style=' + this.style)
      }
      this.getRequest(url).then(res => {
        this.albumDatas = res.data
        this.total = res.total
      }).catch(err => {
        console.log(err)
      })
    },
    //获取当前页
    handleCurrentChange(val) {
      this.currentPage = val;
      this.getSongList();
    },
    //根据style显示对应的歌单
    handleChangeView(name) {
      this.activeName = name;
      this.albumDatas = [];
      this.style = name
      this.currentPage = 1
      this.getSongList()
    }

  }
}
</script>

<style lang="scss" scoped>
    @import '../assets/css/song-list.scss';
</style>
