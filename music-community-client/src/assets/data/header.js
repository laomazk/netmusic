//左侧导航栏
const navMsg = [
  {name: '首页', path: '/',idx:'0'},
  {name: '歌手', path: '/singer',idx:'1'},
  {name: '歌单', path: '/play-list',idx:'2'},
  {name: '我的', path: '/my',idx:'3'},
]

//右侧导航栏
const loginMsg = [
  {name: '登录', path: '/login-in'},
  {name: '注册', path: '/sign-up'}
]

//用户下拉菜单
const menuList = [
  {name: '设置', path: '/setting'},
  {name: '退出', path: 0}
]


export {
  navMsg,
  loginMsg,
  menuList
}
