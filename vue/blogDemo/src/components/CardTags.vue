<template>
  <div class="card margindown" >
    <div class="card-header">
      <h4 class="card-title">{{info.title}}</h4>
    </div>
    <div slot="header" class="card-body">
<!--      <ul>-->
        <span v-for="(item,index) in info.list" :key="index">
          <button  @click="gotoArticle(item.id)" type="button" style="margin-left: 10px" class="badge badge-light">{{item.name}}</button>
        </span>
<!--      </ul>-->
    </div>
  </div>
</template>

<script>
export default {
  name: 'Card',
  props: {
    info: {
      title: '',
      list: []
    }
  },
  data () {
    return {}
  },
  methods: {
    gotoArticle (id) {
      this.$router.push('/tags/' + id)
    }
  }
}
</script>

<style scoped>

</style>
