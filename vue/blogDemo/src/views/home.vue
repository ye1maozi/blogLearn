<script src="../router/index.js"></script>
<template>
   <div>
<!--     <carouselVue></carouselVue>-->
     <br>
    <articleScrollPage></articleScrollPage>
   </div>
</template>

<script>
  import carouselVue from '@/components/carouselVue'
  import articleScrollPage from './ArticleScrollPage'
  export default {
    name: 'home',
    data () {
      return {
        max: 10
      }
    },
    components: {
      'carouselVue':carouselVue,
      'articleScrollPage': articleScrollPage
    }
  }
</script>

<style scoped>
.article{
  margin-bottom: 20px;
  margin-top: 20px;
  padding: 20px;
  background: white;
}
.spanV{
  background-color: red;
}
</style>
