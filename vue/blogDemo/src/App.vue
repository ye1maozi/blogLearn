<script src="router/index.js"></script>
<template>
  <div id="app" class="app">
    <app-header></app-header>
<!--    <img src="./assets/logo.png">-->
<!--    这里就是路由的root-->
    <div class="container app main">
      <div class="row" id="main">
        <div class="col-md-8">
          <router-view></router-view>
        </div>
        <div class="col-md-4 " id="aside">
        <slibder class="sidler"></slibder>
        </div>
      </div>
    </div>
    <app-footer  v-on:gotoTop="gotoTop"></app-footer>
  </div>
</template>
<script>
import footer from './components/footer'
import header from './components/header'
import sidebar from './components/sidebar'
export default {
  name: 'App',
  data (){
    return {}
  },
  components: {
    'app-footer':footer,
    'app-header':header,
    'slibder': sidebar,
  },
  mounted () {
    console.log('createed app' )
    // console.log(hcSticky)
    this.$nextTick(function() {
      var Sticky = new hcSticky('.sidler', {
        stickTo: '.main',
        followScroll: false,
        bottomEnd: 50,
        queries: {
          768: {
            disable: true,
            stickTo: '.container'
          }
        }
      });
    })
  },
  methods: {
    gotoTop () {
      $(".sidler").css({"position": "static", "left": "auto", "width": "350px"})
    }
  },
  watch: {
    // 利用watch方法检测路由变化：
    '$route': function (to, from) {
      // 拿到目标参数 to.query.id 去再次请求数据接口
      this.gotoTop()
      // $(".sidler").css({"position": "static", "left": "auto", "width": "350px"})
    }
  }
}
</script>

<style>
.app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /*text-align: center;*/
  color: #2c3e50;
  height: 100%;
}

html,body{
  height: 100%;
  margin: 0;
  padding: 0;
}
.main{
  padding-top: 80px;
  height: auto;
  box-sizing: border-box;/*为元素指定的任何内边距和边框都将在已设定的宽度和高度内进行绘制*/
  min-height: 100%;
  padding-bottom: 25px;
}
.container{
}

</style>
