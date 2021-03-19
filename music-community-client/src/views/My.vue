<template>
    <div class="my-music">
        <div class="album-slide">
            <div class="album-img">
                <img :src="avator">
            </div>
            <ul class="album-info">
                <li>昵称:{{username}}</li>
                <li>性别:{{userSex}}</li>
                <li>生日：{{birth}}</li>
                <li>故乡：{{location}}</li>
            </ul>
        </div>
        <div class="album-content">
            <div class="album-title">
                签名：{{introduction}}
            </div>
            <div class="songs-body">
                <album-content :songList="collectList">
                    <template slot="title">我的歌曲</template>
                </album-content>
                <br>
                <br>
                <br>
                <div style="background-color: white;text-align: center;">
                    <h1 class="title">
                        我的歌单
                    </h1>
                    <hr>
                <content-list :contentList="collectListOfPlayList"></content-list>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import {mixin} from '../mixins';
import {mapGetters} from 'vuex';
import AlbumContent from "../components/AlbumContent";
import ContentList from '../components/ContentList';

export default {
  name: 'my',
  mixins: [mixin],
  components: {
    AlbumContent,
    ContentList
  },
  data() {
    return {
      avator: '',       //用户头像
      username: '',     //昵称
      userSex: '',      //性别
      birth: '',        //生日
      location: '',     //故乡
      introduction: '', //签名
      collection: [],     //收藏的歌曲列表
      collectList: [],    //收藏的歌曲列表（带歌曲详情）
      collectionOfPlayList: [],     //收藏的歌曲列表
      collectListOfPlayList: [],    //收藏的歌曲列表（带歌曲详情）
    }
  },
  computed: {
    ...mapGetters([
      'listOfSongs',      //当前播放列表
      'userId',           //当前登录用户id
    ])
  },
  mounted() {
    this.getMsg(this.userId);
    this.getCollection(this.userId);
  },
  methods: {
    getMsg(userId) {
      this.getRequest('/user/listener/id/' + userId)
        .then(res => {
          this.avator = res.avator;
          this.username = res.username;
          if (res.sex == 0) {
            this.userSex = '女';
          } else if (res.sex == 1) {
            this.userSex = '男';
          }
          this.birth = res.birth
          this.location = res.location;
          this.introduction = res.introduction;
        })
        .catch(err => {
          console.log(err);
        })
    },
    //获取我的收藏列表
    getCollection(userId) {
      this.getRequest('/user/star/' + userId)
        .then(res => {
          this.collection = res;
          //通过歌曲id获取歌曲信息
          for (let item of this.collection) {
            if (item.songId != null) {
              this.getSongsOfIds(item.songId);
            }
            if(item.playListId != null){
              this.getPlayListOfIds(item.playListId);
            }
          }
        })
        .catch(err => {
          console.log(err);
        })
    },
    //通过歌曲id获取歌曲信息
    getSongsOfIds(id) {
      this.getRequest('/user/song/detail/' + id)
        .then(res => {
          this.collectList.push(res);
        })
        .catch(err => {
          console.log(err);
        })
    },
    // 通过歌单id获取歌单信息
    getPlayListOfIds(id){
      this.getRequest('/user/playlist/id/'+id).then(
        res=>{
          this.collectListOfPlayList.push(res)
        }
      )
        .catch(err => {
          console.log(err);
        })
    }
  }
}
</script>

<style lang="scss" scoped>
    @import '../assets/css/my-music.scss';
    @import '../assets/css/album-content.scss';
</style>
