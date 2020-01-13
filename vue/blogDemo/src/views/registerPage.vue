<script src="../api/articles.js"></script>
<template>
  <div id="register" >
    <div class="form-top  bg-light thumbnail">
      <div class="m-5">
        <h3>注册</h3>
      </div>
      <div class="bg-white p-1">
        <form class="p-3">
          <div class="form-group ">
            <input type="text" id="name" class="form-control" v-model="user.name" placeholder="Username..." >
          </div>
          <div class="form-group">
            <input type="password" id="password" class="form-control" v-model="user.password" placeholder="password...">
          </div>
          <div class="form-group">
            <input type="password" id="password2" class="form-control" v-model="user.password2" placeholder="again password...">
          </div>
          <div>
            <input type="submit" @click="register" class="btn btn-success btn-lg" value="register">
          </div>
        </form>
      </div>
    </div>
    <br>
    <div id="errorMsg" class="alert alert-danger " v-show="errMsg" role="alert">
      <p>{{errMsg}}</p>
    </div>
  </div>
</template>

<script>
import { registerUser } from '@/api/user'
export default {
  name: 'register',
  methods: {
    register () {
      if (!this.check()) {
        event.preventDefault()
        return
      }
      console.log('click')
      // var url = 'http://localhost:8888/register'
      var user = this.user
      var that = this
      registerUser(user).then(function (response) {
        var data = response.data
        if (data.code === 0) {
          that.errMsg = null
          location.href = '/'
        } else {
          that.errorShow(data)
        }
      })
      event.preventDefault()
    },
    errorShow (data) {
      this.errMsg = data.message
    },
    check () {
      var user = this.user
      if (user.name == null) {
        // eslint-disable-next-line no-undef
        $('#name').css('border', '1px solid red')
        this.errMsg = '用户名为空'
        return false
      }
      // eslint-disable-next-line no-undef
      $('#name').css('border', '')
      if (user.password == null || user.password2 == null) {
        if (user.password == null) {
          // eslint-disable-next-line no-undef
          $('#password').css('border', '1px solid red')
        }
        if (user.password2 == null) {
          // eslint-disable-next-line no-undef
          $('#password2').css('border', '1px solid red')
        }
        this.errMsg = '密码为空'
        return false
      }
      if (user.password !== user.password2) {
        // eslint-disable-next-line no-undef
        $('#password').css('border', '1px solid red')
        // eslint-disable-next-line no-undef
        $('#password2').css('border', '1px solid red')
        this.errMsg = '2次密碼不相同'
        return false
      }
      return true
    }
  },
  data () {
    return {
      errMsg: null,
      user: {
        name: null,
        password: null,
        password2: null
      }
    }
  }
}
</script>

<style scoped>

</style>
