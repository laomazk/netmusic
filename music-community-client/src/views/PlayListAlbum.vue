<template>
    <div class="song-list-album">
        <div class="album-slide">
            <div class="album-img">
                <img :src="playList.pic">
            </div>
            <div class="album-info">
                <h2>简介：</h2>
                <span>{{playList.introduction}}</span>
            </div>
        </div>
        <div class="album-content">
            <div class="album-title" style="display: flex; justify-content: space-between">
                <p>{{playList.title}}</p>
            </div>
            <div class="album-score">
                <div>
                    <h3>歌单评分：</h3>
                    <div>
                        <el-rate v-model="average" disabled></el-rate>
                    </div>
                </div>
                <span>{{average*2}}</span>
                <div>
                    <h3>评价：</h3>
                    <div @click="setRank">
                        <el-rate v-model="rank" allow-half show-text></el-rate>
                    </div>
                </div>
                <div style="font-size: 22px;margin-left: 250px;cursor: pointer" @click="startPlayList">
                    <i class="fa fa-heart" aria-hidden="true"style="color: red"  v-if="isStar">&nbsp;已收藏</i>
                    <i class="fa fa-heart" aria-hidden="true"style="color: black"  v-if="!isStar">&nbsp;收藏歌单</i>

                </div>
            </div>
            <div class="songs-body">
                <album-content :songList="listOfSongs">
                    <template slot="title">歌单</template>
                </album-content>
                <comment :playId="songListId" :type="1"></comment>
            </div>
        </div>
    </div>
</template>

<script>
import {mixin} from '../mixins';
import {mapGetters} from 'vuex';
import AlbumContent from "../components/AlbumContent";
import {Message} from "element-ui";
import Comment from "../components/Comment";

export default {
  name: "PlayListAlbum",
  mixins: [mixin],
  components: {
    AlbumContent,
    Comment
  },
  data() {
    return {
      songLists: [],      //当前页面需要展示的歌曲列表
      songListId: '',    //前面传来的歌单id
      average: 0,         //平均分
      rank: 0,            //提交评价的分数
      playList: '',
      isStar:false
    }
  },
  computed: {
    ...mapGetters([
      'listOfSongs',      //当前播放列表
      'tempList',         //当前歌单对象
      'loginIn',          //用户是否已登录
      'userId',           //当前登录用户id
    ])
  },
  created() {
    this.songListId = this.$route.params.id;
    this.checkStar();
    this.getSongId();
    this.getRank(this.songListId);
    this.getSongListDetail(this.songListId)
  },
  methods: {
    // 检查歌单是否收藏
    checkStar(){
      if(this.loginIn){
        this.getRequest('/user/star/check/?userId='+this.userId+'&playListId='+this.songListId).then(resp=>{
          if(resp){
            this.isStar=true;
          }else{
            this.isStar=false;
          }
        })
      }else {
        this.isStar=false;
      }
    },
    // 收藏歌单
    startPlayList(){
      if (this.loginIn) {
        var params = {}
        // params['']=
        params['userId'] = this.userId
        params['type'] = 1
        params['playListId'] = this.songListId
        console.log(params)
        this.postRequest('/user/star/',params).then(resp=>{
          if(resp){
            this.isStar = true
          }
        })
      } else {
        Message.warning("请先登录")
      }
    },
    // 根据歌单id获取歌单对象
    getSongListDetail(id) {
      this.getRequest('/user/playlist/id/' + id).then(res => {
        this.playList = res
      })
    },
    //获取当前歌单的歌曲列表
    getSongId() {
      this.getRequest('/user/playlist/songs/' + this.songListId).then(res => {
        for (let item of res) {
          this.getSongList(item.songId);
        }
        this.$store.commit('setListOfSongs', this.songLists);
      }).catch(err => {
        console.log(err)
      })
    },
    //根据歌曲id获取歌曲信息
    getSongList(id) {
      this.getRequest('/user/song/detail/' + id)
        .then(res => {
          this.songLists.push(res);
        })
        .catch(err => {
          console.log(err)
        })
    },
    //获取歌单评分
    getRank(id) {
      this.getRequest("/user/rank/" + id).then(res => {
        this.average = res / 2;
      }).catch(err => {
        console.log(err)
      })
    },
    //提交评分
    setRank() {
      if (this.loginIn) {
        // let params = new Object();
        // params.set('songListId', this.songListId);
        // params.set('consumerId', this.userId);
        // params.set('score', this.rank * 2);
        let params = {}
        params['songListId'] = this.songListId;
        params['consumerId'] = this.userId;
        params['score'] = this.rank * 2;
        this.postRequest("/user/rank/", params)
          .then(res => {
            if (res) {
              this.getRank(this.songListId);
            }
          })
          .catch(err => {
            Message.error({message: '你已经评价过了o(╯□╰)o'})
          })
      } else {
        this.rank = null;
        Message.warning('请先登录');
      }
    }
  }
}
</script>

<style lang="scss" scoped>
    @import '../assets/css/song-list-album.scss';
</style>
