<template>
  <scroll-page :loading="loading" :offset="offset" :no-data="noData" v-on:load="load">
    <article-item v-for="a in articles" :key="a.id" v-bind="a"></article-item>
  </scroll-page>
</template>

<script>
import { getArticles } from '@/api/articles'
import ScrollPage from '@/components/ScrollPage'
import ArticleItem from '@/components/ArticleItem'
export default {
  name: 'ArticleScrollPage',
  props: {
    offset: {
      type: Number,
      default: 100
    },
    page: {
      type: Object,
      default () {
        return {}
      }
    },
    query: {
      type: Object,
      default () {
        return {}
      }
    }
  },
  data () {
    return {
      loading: false,
      noData: false,
      articles: [],
      innerPage: {
        pageSize: 10,
        pageNo: 1,
        name: 'create_time',
        sort: 'desc'
      }
    }
  },
  components: {
    'scroll-page': ScrollPage,
    'article-item': ArticleItem
  },
  methods: {
    load () {
      this.getArticles()
    },
    getArticles () {
      console.log('load next')
      this.loading = true
      getArticles(this.query, this.innerPage).then(res => {
        const data = res.result
        this.loading = false
        if (res.code === 0) {
          if (data && data.length > 0) {
            this.innerPage.pageNo += 1
            this.articles = this.articles.concat(data)
          } else {
            this.noData = true
          }
        }
      })
    }
  },
  created () {
    this.getArticles()
  },
  watch: {
    'query': {
      handler () {
        this.noData = false
        this.articles = []
        this.innerPage.pageNo = 1
        this.getArticles()
      },
      deep: true
    },
    'page': {
      handler () {
        this.noData = false
        this.articles = []
        this.innerPage = this.page
        this.getArticles()
      },
      deep: true
    }
  }
}
</script>

<style scoped>

</style>
