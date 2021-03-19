import Axios from "axios";


//下载音乐
export const download = (url)  => Axios({
  headers: {
    'Content-Type':'application/x-www-form-urlencoded'
  },
  method: 'get',
  url: url,
  responseType: 'blob'
});

//===============收藏===================
//新增收藏
export const setCollect =(params) => post(`/collect/add`,params);
//指定用户的收藏列表
export const getCollectOfUserId = (userId) => get(`/collect/collectOfUserId?userId=${userId}`);
