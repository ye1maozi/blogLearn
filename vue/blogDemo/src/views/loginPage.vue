<script src="../api/articles.js"></script>
<script src="../store/index.js"></script>
<script src="../router/index.js"></script>
<template>
    <div id="login" >
      <div class="form-top  bg-light thumbnail">
        <div class="m-5">
          <h3>登录</h3>
        </div>
        <div class="bg-white p-1">
          <form class="p-3">
            <div class="form-group ">
              <input type="text" class="form-control" v-model="user.name" placeholder="Username..." >
            </div>
            <div class="form-group">
              <input type="password" class="form-control" v-model="user.password" placeholder="password...">
            </div>
            <div>
              <input type="submit" @click="login" class="btn btn-success btn-lg" value="login">
            </div>
            <router-link class="float-lg-right" to="register" >register</router-link>
<!--            <a class="float-lg-right" href="register" >register</a>-->
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
import { loginUser } from '@/api/user'
export default {
  name: 'loginPage',
  methods: {
    login () {
      // var url = 'http://localhost:8888/login'
      var user = this.user
      var that = this
      loginUser(user).then(function (response) {
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
    register () {
      event.preventDefault()
    },
    errorShow (data) {
      this.errMsg = data.message
    }
  },
  data () {
    return {
      errMsg: null,
      user: {
        name: null,
        password: null
      },
      msg: 'this is vue message'
    }
  }
}
</script>

<style scoped>

</style>
