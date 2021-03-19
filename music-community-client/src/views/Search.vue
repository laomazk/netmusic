<template>
    <div class="search">
        <nav class="searchList-nav" ref="change">
            <span :class="{isActive: toggle=='Songs'}" @click="handleChangeView('Songs')">歌曲</span>
            <span :class="{isActive: toggle=='SongLists'}" @click="handleChangeView('SongLists')">歌单</span>
        </nav>
        <component :is="currentView"></component>
    </div>
</template>

<script>
import SearchSongLists from "../components/search/SearchSongLists";
import SearchSongs from "../components/search/SearchSongs";
export default {
  name: "Search",
  components: {
    SearchSongs,
    SearchSongLists
  },
  data(){
    return {
      toggle: 'Songs',
      currentView: 'SearchSongs'
    }
  },
  // 监听搜索
  watch:{
    $route(to,from){
      this.$router.go(0)
    }
  },
  methods:{
    //切换组件
    handleChangeView(component){
      this.currentView = 'search'+component;
      this.toggle = component;
    }
  }
}
</script>

<style lang="scss" scoped>
    @import '../assets/css/search.scss';
</style>
