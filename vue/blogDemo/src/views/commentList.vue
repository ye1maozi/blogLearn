<template>
  <scroll-page :loading="loading" :offset="offset" :no-data="noData" v-on:load="load">
    <comment-item v-for="a in comments" :key="a.id" v-bind="a"></comment-item>
  </scroll-page>
</template>

<script>
import { getComment } from '@/api/articles'
import ScrollPage from '@/components/ScrollPage'
import commentItem from '@/components/commentItem'
export default {
  name: 'commentList',
  props: {
    offset: {
      type: Number,
      default: 1000
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
      comments: [],
      innerPage: {
        pageSize: 5,
        pageNo: 1,
        name: 'create_time',
        sort: 'desc'
      }
    }
  },
  components: {
    'scroll-page': ScrollPage,
    'comment-item': commentItem
  },
  methods: {
    load () {
      console.log('load getArticles ')
      this.getArticles()
    },
    getArticles () {
      console.log('load next')
      this.loading = true
      getComment(this.query, this.innerPage).then(res => {
        const data = res.result
        this.loading = false
        if (res.code === 0) {
          if (data && data.length > 0) {
            this.innerPage.pageNo += 1
            this.comments = this.comments.concat(data)
          } else {
            this.noData = true
          }
        }
      })
    }
  },
  created () {
    this.query.articleId = this.$route.params.id
    console.log('created getArticles ')
    this.getArticles()
  },
  watch: {
    'query': {
      handler () {
        // this.noData = false
        // this.comments = []
        // this.innerPage.pageNo = 1
        // console.log('query getArticles ')
        // this.getArticles()
      },
      deep: true
    },
    'page': {
      handler () {
        this.noData = false
        this.comments = []
        this.innerPage = this.page
        console.log('page getArticles ')
        this.getArticles()
      },
      deep: true
    },
    '$route': function (to, from) {
      // 拿到目标参数 to.query.id 去再次请求数据接口
      this.noData = false
      this.comments = []
      this.innerPage.pageNo = 1
      console.log('$route getArticles ')
      this.query.articleId = this.$route.params.id
      this.getArticles()
    }
  }
}
</script>

<style scoped>

</style>
