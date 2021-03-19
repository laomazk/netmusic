<template>
    <div class="singer-album">
        <div class="album-slide">
            <div class="singer-img">
                <img :src="singer.pic">
            </div>
            <ul class="info">
                <li v-if="singer.sex==0||singer.sex==1">{{attachSex(singer.sex)}}</li>
                <li>生日：{{singer.birth}}</li>
                <li>故乡：{{singer.location}}</li>
            </ul>
        </div>
        <div class="album-content">
            <div class="intro">
                <h2>{{singer.name}}</h2>
                <span>{{attachIntroduction(singer.introduction)}}</span>
            </div>
            <div class="content">
                <album-content :songList="listOfSongs">
                    <template slot="title">歌曲</template>
                </album-content>
            </div>
        </div>
    </div>
</template>
<script>
import {mixin} from '../mixins';
import {mapGetters} from 'vuex';
import AlbumContent from "../components/AlbumContent";
export default {
  name: 'singer-album',
  mixins: [mixin],
  components:{
    AlbumContent
  },
  data(){
    return {
      singerId: '',       //前面传来的歌手id
      singer: {},         //当前歌手信息
    }
  },
  computed:{
    ...mapGetters([
      'listOfSongs',      //当前播放列表
      'tempList',         //当前歌手对象
      'loginIn',          //用户是否已登录
      'userId',           //当前登录用户id
    ])
  },
  created(){
    this.singerId = this.$route.params.id;
    this.getSingerById();
    this.getSongOfSingerId();
  },
  methods:{
    // 根据歌手id查询歌手详细信息
    getSingerById(){
      this.getRequest('/user/singer/id/'+this.singerId).then(res=>{
        this.singer=res
      })
    },
    //根据歌手id查询歌曲
    getSongOfSingerId(){
      this.getRequest('/user/song/id/'+this.singerId)
        .then(res => {
          this.$store.commit('setListOfSongs',res);
        })
        .catch(err =>{
          console.log(err)
        })
    },
    //获取性别
    attachSex(value){
      if(value==0){
        return '女'
      }else if(value==1){
        return '男'
      }
      return ''
    },
    //裁剪简介
    attachIntroduction(value){
      if(value!=undefined && value.length > 150){
        value = value.slice(0,150)
        value +='...'
        return value
      }
      return value
    }
  }
}
</script>

<style lang="scss" scoped>
    @import '../assets/css/singer-album.scss';
</style>
