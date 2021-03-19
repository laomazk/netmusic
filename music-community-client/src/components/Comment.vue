<template>
    <div>
        <div class="comment">
            <h2>评论</h2>
            <div class="comment-msg">
                <div class="comment-img">
                    <img :src="avator">
                </div>
                <el-input class="comment-input" type="textarea" :rows="4" placeholder="请输入内容" v-model="textarea">
                </el-input>
            </div>
            <el-button type="primary" class="sub-btn" @click="postComment">评论</el-button>
        </div>
        <p>精彩评论：共{{commentList.length}}条评论</p>
        <ul class="popular" v-for="(item,index) in commentList" :key="index">
            <li>
                <div class="popular-img">
                    <img :src="userPic[index]">
                </div>
                <div class="popular-msg">
                    <ul>
                        <li class="name">{{userName[index]}}</li>
                        <li class="time">{{item.createTime}}</li>
                        <li class="content">{{item.content}}</li>
                    </ul>
                </div>
                <div class="up" ref="up" @click="postUp(item.id,item.up,index)">
                    <svg class="icon">
                        <use xlink:href="#icon-zan"></use>
                    </svg>
                    {{item.up}}
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
import {mixin} from '../mixins';
import {mapGetters} from 'vuex';
import {Message} from "element-ui";

export default {
  name: 'comment',
  mixins: [mixin],
  props: [
    'playId',       //歌曲或歌单id
    'type'          //0歌曲、1歌单
  ],
  computed:{
    ...mapGetters([
      'id',               //歌曲或歌单id
      'loginIn',          //用户是否已登录
      'userId',           //当前登录用户id
      'avator',           //当前登录用户头像
    ])
  },
  data(){
    return{
      textarea: '',        //存放输入的评论内容
      commentList: [],    //存放评论列表
      userPic: [],        //用户的头像
      userName: [],       //用户的昵称
    }
  },
  mounted(){
    this.getComment();
  },
  methods: {
    //提交评论
    postComment(){
      // let params = {}
      // params['songListId'] = this.songListId;
      // params['consumerId'] = this.userId;
      // params['score'] = this.rank * 2;
      if(this.loginIn){
        let params = {}
        if(this.type == 0){
          params['songId'] = this.playId;
        }else{
          params['songListId'] = this.playId;
        }
        params['userId'] = this.userId;
        params['type'] = this.type;
        params['content'] = this.textarea;
        console.log(params)
        this.postRequest('/user/comment/',params)
          .then(res => {
            if(res){
              this.textarea='';
              this.getComment();
            }
          })
          .catch(err =>{
          })
      }else{
        this.rank = null;
        Message.warning("请先登录")
      }
    },
    //评论列表
    getComment(){
      if(this.type==0){
        //歌曲
        this.getRequest('/user/comment/song/'+this.playId).then(res=>{
          this.commentList = res;
          for(let item of res){
            this.getUsers(item.userId);
          }
        }).catch(err=>{
          Message.error("评论加载失败")
        })
      }else{
        //歌单
        this.getRequest('/user/comment/songList/'+this.playId).then(res=>{
          this.commentList = res;
          for(let item of res){
            this.getUsers(item.userId);
          }
        }).catch(err=>{
          Message.error("评论加载失败")
        })
      }
    },
    //获取用户的头像和昵称
    getUsers(id){
      this.getRequest('/user/listener/id/'+id)
        .then(res => {
          this.userPic.push(res.avator);
          this.userName.push(res.username);
        })
        .catch(err =>{
          Message.error("出错了")
        })
    },
    //给某一个评论点赞
    postUp(id,up,index){
      if(this.loginIn){
        let params = {};
        params['id'] = id;
        params['up'] = up+1;
        this.putRequest('/user/comment/like/',params)
          .then(res => {
            if(res){
              this.$refs.up[index].children[0].style.color = '#2796cd';
              this.getComment();
            }else{
              Message.error('点赞失败');
            }
          })
          .catch(err =>{
            Message.error('点赞失败');
          })
      }else{
        this.rank = null;
        Message.warning('请先登录');
      }
    }
  }
}
</script>
<style lang="scss" scoped>
    @import '../assets/css/comment.scss';
</style>
