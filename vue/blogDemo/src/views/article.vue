<script src="../../build/webpack.prod.conf.js"></script>
<template>
  <div>
    <div class="jumbotron" style="background-color: #f8f8f8">
      <div>
        <h1>{{article.title}}</h1>
        <div>
          <span>{{article.author}} |</span>
          <span>{{article.createtime}} |</span>
          <span>浏览:{{article.lookCount}} |</span>
          <span>评论:{{article.commentCount}}</span>
        </div>
        <div style="margin-top: 2px">
          <span >分类:
            <button type="button" class="badge badge-info " @click="gotoArticle(article.category.id,1)">{{article.category.desction}} </button>
          </span>
          <span>标签:
            <button   @click="gotoArticle(item.id,2)" style="margin-left: 5px" v-for="(item,index) in article.tagsList" :key="index" type="button" class="badge badge-light">{{item.name}} </button>
          </span>
        </div>
        <div class="card" style="margin-top: 15px">
          <mavon-editor style="z-index: 0"
                        ref="mautowire d" v-bind="editor" :value="article.contentmd"></mavon-editor>
        </div>
        <br>
        <div class="alert alert-success" style="margin-top: 10px">
          文章End...
        </div>
      </div>
    </div>
    <div class="jumbotron" style="background-color: #f8f8f8 ; margin-top: 10px">
      <div style="border-bottom: 1px solid">
        输入评论
        <form style="margin-top: 5px;margin-bottom: 10px;overflow: hidden">
          <div class="form-group col-sm-4 pad nonePadding">
            <input v-model="comment.nickname" type="text" class="form-control" placeholder="输入昵称...">
          </div>
          <div class="form-group">
            <textarea v-model="comment.content" rows="3" class="form-control" placeholder="输入评论..."></textarea>
          </div>
          <div class="buttonLeft">
            <input type="submit" @click="upComment" class="btn btn-success" value="评论">
          </div>
        </form>
      </div>

      <div style="margin-top: 40px">
        评论区域
        <commentList :query="query"></commentList>
      </div>
    </div>
  </div>
</template>

<script>
import { mavonEditor } from 'mavon-editor'
import commentList from './commentList'
import 'mavon-editor/dist/css/index.css'
import { getArticle, commentArticle } from '@/api/articles'
export default {
  name: 'articles',
  data () {
    return {
      editor: {
        toolbarsFlag: false,
        subfield: false,
        defaultOpen: 'preview'
      },
      query: {
        articleId: null,
        index: 0
      },
      article: {
        contentmd: 'asdasd\nasdasd\n',
        title: '123',
        author: 'admin',
        looks: 0,
        comments: 0,
        createtime: '2019-09-28T14:59:04.000+0000',
        category: {},
        tagsList: []
      },
      comment: {
        nickname: '',
        ip: '',
        articleid: 0,
        content: ''
      },
      commentList: []
    }
  },
  components: {
    mavonEditor,
    commentList
  },
  created () {
    console.log('artiel create')
    // this.query.articleId = this.$route.params.id
    this.getCurArticle()
  },
  methods: {
    getCurArticle () {
      getArticle(this.$route.params.id).then(res => {
        // eslint-disable-next-line eqeqeq
        if (res.code == 0) {
          const data = res.result
          // console.log('-----')
          Object.assign(this.article, data)
          // console.log(this.article)
          this.getComments()
        }
      })
    },
    gotoArticle (id, index) {
      if (index === 1) {
        this.$router.push('/categories/' + id)
      } else {
        this.$router.push('/tags/' + id)
      }
    },
    upComment () {
      // console.log(this.comment)
      this.comment.articleid = this.$route.params.id
      // eslint-disable-next-line eqeqeq
      if (this.comment.content === '' || this.comment.nickname === '') {
        event.preventDefault()
        return 0
      }
      commentArticle(this.comment).then(res => {
        // eslint-disable-next-line eqeqeq
        if (res.code == 0) {
          this.comment.content = ''
          this.comment.nickname = ''
          this.query.index += 1
          this.getComments()
        }
      })
      event.preventDefault()
    },
    getComments () {
      console.log('getComments change')
      // this.query.index += 1
      // getComment(this.$route.params.id).then(res => {
      //   // eslint-disable-next-line eqeqeq
      //   if (res.code == 0) {
      //     this.commentList = res.result
      //   }
      // })
    }
  },
  watch: {
    // 利用watch方法检测路由变化：
    '$route': function (to, from) {
      // 拿到目标参数 to.query.id 去再次请求数据接口
      console.log('$route change')
      // this.query.articleId = this.$route.params.id
      this.getCurArticle()
    }
  }
}
</script>

<style scoped>
.nonePadding {
  padding-left: 0px;
}
.buttonLeft {
  float: right;
}
</style>
