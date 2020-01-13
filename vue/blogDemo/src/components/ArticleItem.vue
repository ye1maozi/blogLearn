<template>
  <div class="border border-dark article media">
     <div class="media-left">
       <a>
         <img :src="topimg" width="100px"/>
       </a>
     </div>
    <div class="media-body">
      <h3 >
         <router-link :to="{path:'/articles/' + id}">{{title}}</router-link>
       </h3>
       <p> {{contentmd}}</p>
        <div style="margin-top: 2px">
          <span >分类:
            <button type="button" class="badge badge-info" @click="gotoArticle(category.id,1)">{{category.desction}} </button>
          </span>
      <span>标签:
            <button   @click="gotoArticle(item.id,2)" style="margin-left: 5px" v-for="(item,index) in tagsList" :key="index" type="button" class="badge badge-light">{{item.name}} </button>
          </span>
    </div>
       <div>
      <span>{{author}} |</span>
      <span>{{createtime}} |</span>
      <span>浏览:{{lookCount}} |</span>
      <span>评论:{{commentCount}}</span>
    </div>
     </div>
  </div>
</template>

<script>
export default {
  name: 'ArticleItem',
  props: {
    title: '',
    contentmd: '',
    topimg: '',
    id: 0,
    author: '',
    createtime: '',
    lookCount: 0,
    commentCount: 0,
    category: {},
    tagsList: {}
  },
  date () {
    return {}
  },
  methods: {
    gotoArticle (id, index) {
      if (index === 1) {
        this.$router.push('/categories/' + id)
      } else {
        this.$router.push('/tags/' + id)
      }
    }
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
</style>
