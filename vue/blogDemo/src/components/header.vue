<template>
  <div id="header">
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
      <router-link to="/"><img src="../assets/melogo.png" width="45px"></router-link>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item active">
            <router-link class="nav-link" to="/">首页</router-link>
<!--            <a class="nav-link" href="/">首页 <span class="sr-only">(current)</span></a>-->
          </li>

          <li class="nav-item">
            <router-link class="nav-link" to="/history">归档</router-link>
<!--            <a class="nav-link" href="#">归档</a>-->
          </li>
          <li class="nav-item">

<!--            <router-link class="nav-link" to="/">后台管理</router-link>-->
            <a class="nav-link" href="javascript:void(0)" @click="gotoBack">后台管理</a>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/comments">留言区</router-link>
<!--            <a class="nav-link" href="#">留言区</a>-->
          </li>
<!--          <li class="nav-item dropdown">-->
<!--            <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Dropdown</a>-->
<!--            <div class="dropdown-menu" aria-labelledby="dropdown01">-->
<!--              <a class="dropdown-item" href="#">Action</a>-->
<!--              <a class="dropdown-item" href="#">Another action</a>-->
<!--              <a class="dropdown-item" href="#">Something else here</a>-->
<!--            </div>-->
<!--          </li>-->
        </ul>
        <form class="form-inline my-2 my-lg-0">
          <input class="form-control mr-sm-2" type="text" placeholder="..." aria-label="Search">
          <button class="btn btn-outline-success my-2 my-sm-0" type="submit">搜索</button>
        </form>
        <span style="margin-left: 3px" v-show="showLogin">
           <button class="btn btn-outline-success" @click="onClickLogin">登录</button>
        </span>

      </div>
    </nav>
  </div>
</template>

<script>
export default {
  name: 'app-header',
  data () {
    return {
      navlist: ['a', 'b', 'c'],
      msg: 'this is nav',
      showLogin: true
    }
  },
  methods: {
    onClickLogin () {
      console.log('show')
      this.$router.push('/login')
    },
    gotoBack () {
      return false
    }
  },
  mounted () {
    console.log('nav end')
  }
}
</script>

<style scoped>
  #header {
   background-color: #b3b7bb;
  }
</style>
