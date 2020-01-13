<script src="../router/index.js"></script>
<template>
  <div>
    <div>
      <p style="border-bottom: 1px solid">{{title}}</p>
      <button style="margin-right: 5px" type="button" @click="gotoArticle(item.id)"
              v-for="(item,index) in list" :key="index"
              :class="['badge', ( item.id == query.index) ? 'badge-info':' badge-success']">
      <span v-if="flag=='tagId'">
        {{item.name}}
      </span>
      <span v-else>
        {{item.desction}}
      </span>
      </button>
    </div>
    <div>
      <articleScrollPage :query="query"></articleScrollPage>
    </div>
  </div>
</template>

<script>
import { getAllCateogry } from '@/api/articles'
import articleScrollPage from './ArticleScrollPage'
export default {
  name: 'categories',
  props: {
    title: '',
    flag: ''
  },
  data () {
    return {
      query: {
        categoryId: null,
        tagId: null,
        index: 0
      },
      list: [],
      allCategory: {
        id: 0,
        desction: '',
        name:''
      }
    }
  },
  components: {
    'articleScrollPage': articleScrollPage
  },
  created () {
    this.allCategory.desction = this.title
    this.allCategory.name = this.title
    this.checkQueryId()
    this.getCategoryList()
  },
  methods: {
    getCategoryList () {
      getAllCateogry(this.flag).then(res => {
        if (res.code === 0) {
          this.list = res.result
          this.list.unshift(this.allCategory)
        }
      })
    },
    gotoArticle (id) {
      if (this.flag === 'tagId') {
        this.$router.push('/tags/' + id)
      } else {
        this.$router.push('/categories/' + id)
      }
    },
    checkQueryId () {
      this.query[this.flag] = (this.$route.params.id)
      this.query.index = this.query[this.flag]
      // eslint-disable-next-line eqeqeq
      if (this.query[this.flag] == 0) {
        this.query[this.flag] = null
      }
    }
  },
  watch: {
    // 利用watch方法检测路由变化：
    '$route': function (to, from) {
      // 拿到目标参数 to.query.id 去再次请求数据接口
      this.checkQueryId()
    },
    flag (newVal) {
      this.getCategoryList()
    }
  }
}
</script>

<style scoped>

</style>
