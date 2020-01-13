<template>
  <div class="container  " id="footer">
    <div @click="toTop" class="float-right">
      <p>top</p>
<!--      <span class="glyphicon glyphicon-arrow-up"></span>-->
    </div>
    <p>
      copy right
    </p>
  </div>
</template>

<script>
export default {
  name: 'app-footer',
  data () {
    return {}
  },
  methods: {
    toTop () {
      document.body.scrollTop = 0
      document.documentElement.scrollTop = 0
      this.$emit('gotoTop')
    }
  }
}
</script>

<style scoped>
#footer{
  /*padding-bottom:70px;*/
  /*position:relative;*/
  height: 25px;
  margin-top: -25px;
  opacity: 0.5;
}
</style>
