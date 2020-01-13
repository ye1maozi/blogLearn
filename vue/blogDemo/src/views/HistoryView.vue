<script src="../router/index.js"></script>
<template>
  <div>
    <div>
      <p style="border-bottom: 1px solid">归档</p>
      <div class="dropdown">
        <button type="button" class="btn btn-light dropdown-toggle" data-toggle="dropdown">
          {{current}}
        </button>
        <div class="dropdown-menu">
          <router-link class="dropdown-item" :to="{path:'/history/' + item.year + '/' + item.month}"
             v-for="(item,index) in list" :key="index"
          >{{item.year}}年{{item.month}}月
          <span class="badge badge-danger redBadge">{{item.count}}</span>
          </router-link>
        </div>
      </div>
    </div>
    <div>
      <articleScrollPage :query="query"></articleScrollPage>
    </div>
  </div>
</template>

<script>
  import { getArticleHistory } from '@/api/articles'
  import articleScrollPage from './ArticleScrollPage'
  export default {
    name: 'HistoryView',
    data () {
      return {
        current: '全部',
        query: {
          index: 0,
          month: this.$route.params.month,
          year: this.$route.params.year
        },
        list: [],
      }
    },
    components: {
      'articleScrollPage': articleScrollPage
    },
    created () {
      this.checkQueryId()
      this.getLeft()
    },
    methods: {
      gotoArticle (id) {
          // this.$router.push('/archives/' + id)
      },
      checkQueryId () {
        // this.query.index = this.query[this.flag]
        if (this.$route.params.year && this.$route.params.month) {
          this.query.year = this.$route.params.year
          this.query.month = this.$route.params.month
        }
      },
      getLeft() {
        getArticleHistory().then(res => {
          if (res.code == 0) {
            this.list = res.result
          }
        })
      }
    },
    watch: {
      // 利用watch方法检测路由变化：
      '$route': function (to, from) {
        // 拿到目标参数 to.query.id 去再次请求数据接口
        this.checkQueryId()
        this.getLeft()
      }
    }
  }
</script>
<style scoped>
.redBadge {
  margin-left: 20px;
}
</style>
