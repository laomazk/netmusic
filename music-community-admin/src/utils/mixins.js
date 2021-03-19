export const mixin = {

  methods: {
    // 把已经选择的项赋值给 mutipleSelection
    handleSelectionChange(val) {
      this.mutipleSelection = val
    },
    // 获取性别中文
    changeSex(value) {
      if (value == 0) {
        return '女'
      }
      if (value == 1) {
        return '男'
      }
      if (value == 2) {
        return '组合'
      }
      if (value == 3) {
        return '不明'
      }
      return value
    },
    // 获取生日
    attachBirth(val) {
      if (val == null) {
        return '';
      }
      return String(val).substr(0, 10)
    },
    // 上传歌曲失败回调
    handleUpdateSongErr(res) {
      let _this = this

      _this.getData()
      _this.$message({
        type: 'error',
        message: res.msg
      })
    },
    // 上传图片之前的校验
    beforeAvatorUpload(file) {
      const isJPG = (file.type === 'image/jpeg') || (file.type === 'image/png')
      if (!isJPG) {
        this.$message.error('上传图片格式只能是JPG或者PNG格式')
        return false
      }
      const isLt2M = (file.size / 1024 / 1024) < 10
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过2MB')
        return false
      }
      return true
    },
    // 上传图片成功之后的工作
    handleAvatorSuccess(res) {
      let _this = this
      if (res.status == 200) {
        _this.getData()
        _this.$message({
          type: 'success',
          message: res.msg
        })
      } else {
        _this.$message({
          type: 'error',
          message: res.msg
        })
      }
    },
  }

}
