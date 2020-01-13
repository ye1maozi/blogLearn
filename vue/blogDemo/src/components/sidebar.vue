<script src="../api/user.js"></script>
<script src="../api/siderInfo.js"></script>
<script src="../router/index.js"></script>
<template>
  <div>
    <CardMe></CardMe>
    <CardArticles :info="articles"></CardArticles>
    <CardTags :info="tags"></CardTags>
    <CardComments :info="comments"></CardComments>
  </div>
</template>

<script>
import { getUserInfo } from '@/api/siderInfo'

import CardMe from '@/components/CardMe'
import CardTags from '@/components/CardTags'
import CardArticles from '@/components/CardArticles'
import CardComments from '@/components/CardComments'
export default {
  name: 'sidebar',
  components: {
    'CardTags':CardTags,
    'CardArticles':CardArticles,
    'CardComments': CardComments,
    'CardMe': CardMe
  },
  data () {
    return {
      articles: {
        title: '热门文章',
        list: []
      },
      tags: {
        title: '标签',
        list: []
      },
      comments: {
        title: '最新评论',
        list: []
      }
    }
  },
  methods: {
    getInfo () {
      const info = this.$store.getters.getInfo
      if (info == null) {
        getUserInfo().then( response => {
          const data = response.result
          this.$store.dispatch('checkGetInfo', data)
          console.log(data)
          if (response.code === 0) {
            this.tags.list = data.tagsList
            this.articles.list = data.articles

            this.comments.list = data.comments || []
          }
        })
      }
      // console.log('get count ' + this.$store.getters.getCount)
    }
  },
  mounted () {
    this.getInfo()
    // this.getUserInfo();
  }
}
</script>

<style scoped>
.margindown {
  margin-bottom: 10px;
}
</style>
