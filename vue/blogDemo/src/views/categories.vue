<script src="../api/articles.js"></script>
<template>
  <div>
    <articleList :title="title" :flag="flag"></articleList>
  </div>
</template>

<script>
import articleList from './articleList'
export default {
  name: 'categories',
  data () {
    return {
      title: '所有分类',
      flag: 'categoryId'
    }
  },
  components: {
    'articleList': articleList
  }
}
</script>

<style scoped>

</style>
