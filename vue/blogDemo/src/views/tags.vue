<template>
  <div>
    <articleList :title="title" :flag="flag"></articleList>
  </div>
</template>

<script>
import articleList from './articleList'
export default {
  name: 'tags',
  data () {
    return {
      title: '所有标签',
      flag: 'tagId'
    }
  },
  components: {
    'articleList': articleList
  }
}
</script>

<style scoped>

</style>
