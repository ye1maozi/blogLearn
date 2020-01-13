<script src="../router/index.js"></script>
<template>
  <div class="card margindown" >
    <div class="card-header">
      <h4 class="card-title">{{info.title}}</h4>
    </div>
    <div slot="header" class="card-body">
      <ul class="list-group list-group-flush">
        <li v-for="(item,index) in info.list" :key="index" class="list-group-item">
          <router-link :to="{path:'/articles/' + item.id}">{{item.title}}</router-link>
        </li>
      </ul>
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
  }
}
</script>

<style scoped>

</style>
