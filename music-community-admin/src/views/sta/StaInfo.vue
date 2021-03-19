<template>
    <div style="margin-top: 8px">
        <el-row :gutter="20" class="mgb20">
            <el-col :span="6">
                <el-card>
                    <div class="grid-content">
                        <div class="grid-cont-center">
                            <div class="grid-num">{{listenerCount}}</div>
                            <div>用户总数</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card>
                    <div class="grid-content">
                        <div class="grid-cont-center">
                            <div class="grid-num">{{songCount}}</div>
                            <div>歌曲总数</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card>
                    <div class="grid-content">
                        <div class="grid-cont-center">
                            <div class="grid-num">{{singerCount}}</div>
                            <div>歌手数量</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card>
                    <div class="grid-content">
                        <div class="grid-cont-center">
                            <div class="grid-num">{{playListCount}}</div>
                            <div>歌单数量</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
        </el-row>
        <el-row :gutter="20" class="mgb20">
            <el-col :span="12">
                <h3 class="mgb20">用户性别比例</h3>
                <div style="background-color:white">
                    <ve-pie :data="listenerSex" :theme="options"></ve-pie>
                </div>
            </el-col>
            <el-col :span="12">
                <h3 class="mgb20">歌单类型分布</h3>
                <div style="background-color:white">
                    <ve-histogram :data="songStyle"></ve-histogram>
                </div>
            </el-col>
        </el-row>
        <el-row :gutter="20" class="mgb20">
            <el-col :span="12">
                <h3 class="mgb20">歌手性别比例</h3>
                <div style="background-color:white">
                    <ve-pie :data="singerSex"></ve-pie>
                </div>
            </el-col>
            <el-col :span="12">
                <h3 class="mgb20">歌手国籍分布</h3>
                <div style="background-color:white">
                    <ve-histogram :data="country" :theme="options1"></ve-histogram>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
export default {
  name: "Info",
  data() {
    return {
      listenerCount: 0,       //用户总数
      songCount: 0,           //歌曲总数
      singerCount: 0,         //歌手数量
      playListCount: 0,        //歌单数量
      listenerSex: {           //按性别分类的用户数
        columns: ['性别', '总数'],
        rows: [
          {'性别': '男', '总数': 0},
          {'性别': '女', '总数': 0}
        ]
      },
      options: {
        color: ['#87cefa', '#ffc0cb']
      },
      options1: {
        color: ['#f391a9']
      },
      songStyle: {           //按歌单风格分类
        columns: ['风格', '总数'],
        rows: [
          {'风格': '华语', '总数': 0},
          {'风格': '粤语', '总数': 0},
          {'风格': '欧美', '总数': 0},
          {'风格': '日韩', '总数': 0},
          {'风格': 'BGM', '总数': 0},
          {'风格': '轻音乐', '总数': 0},
          {'风格': '乐器', '总数': 0},
          {'风格': '其他', '总数': 0}
        ]
      },
      singerSex: {           //按性别分类的歌手数
        columns: ['性别', '总数'],
        rows: [
          {'性别': '女', '总数': 0},
          {'性别': '男', '总数': 0},
          {'性别': '组合', '总数': 0},
          {'性别': '不明', '总数': 0}
        ]
      },
      country: {
        columns: ['国籍', '总数'],
        rows: [
          {'国籍': '中国', '总数': 0},
          {'国籍': '韩国', '总数': 0},
          {'国籍': '日本', '总数': 0},
          {'国籍': '美国', '总数': 0},
          {'国籍': '新加坡', '总数': 0},
          {'国籍': '意大利', '总数': 0},
          {'国籍': '马来西亚', '总数': 0},
          {'国籍': '其他', '总数': 0}
        ]
      }
    }
  },
  mounted() {
    this.getListenerCnt();
    this.getSongCnt();
    this.getSingerCnt();
    this.getPlayListCnt();
  },
  methods: {
    //用户总数
    getListenerCnt() {
      this.getRequest('/listener/basic/').then(resp => {
        this.listenerCount = resp.length;
        this.listenerSex.rows[0]['总数'] = this.setSex(1,resp);
        this.listenerSex.rows[1]['总数'] = this.setSex(0,resp);
      })
    },
    //根据性别获取用户数
    setSex(sex, val) {
      let count = 0;
      for(let item of val){
        if(sex == item.sex){
          count++;
        }
      }
      return count;
    },
    //歌曲总数
    getSongCnt() {
      this.getRequest('/source/song/').then(resp => {
        this.songCount = resp.length;

      })
    },
    //歌手总数
    getSingerCnt() {
      this.getRequest('/source/singer/').then(resp => {
        this.singerCount = resp.length;
        this.singerSex.rows[0]['总数'] = this.setSex(0,resp);
        this.singerSex.rows[1]['总数'] = this.setSex(1,resp);
        this.singerSex.rows[2]['总数'] = this.setSex(2,resp);
        this.singerSex.rows[3]['总数'] = this.setSex(3,resp);
        for(let item of resp){
          this.getByCountry(item.location);
        }
      })
    },
    //歌单总数
    getPlayListCnt() {
      this.getRequest('/source/playlist/').then(resp => {
        this.playListCount = resp.length;
        for(let item of resp){
          this.getByStyle(item.style);
        }
      })
    },
    getByStyle(style) {              //根据歌单风格获取数量
      for(let item of this.songStyle.rows){
        if(style.includes(item['风格'])){
          item['总数']++;
        }
      }
    },
    getByCountry(location) {              //根据国籍获取数量
      for(let item of this.country.rows){
        if(location.includes(item['国籍'])){
          item['总数']++;
        }
      }
    }
  }
}
</script>

<style scoped>
    .grid-content {
        display: flex;
        align-items: center;
        height: 50px;
    }

    .grid-cont-center {
        flex: 1;
        text-align: center;
        font-size: 14px;
        color: darkgray;
    }

    .grid-num {
        font-size: 30px;
        font-weight: bold;
    }
</style>
