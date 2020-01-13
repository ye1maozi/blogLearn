<template>
  <div class="card comment">
    <div class="head">
      <h4 class="card-title">{{nickname}}</h4>
    </div>
    <div class="card-text content">
     {{content}}
    </div>
  </div>
</template>

<script>
export default {
  name: 'commentItem',
  props: {
    nickname: '',
    content: ''
  }
}
</script>

<style scoped>
.comment {
  margin-top: 10px;
}
.head {
  margin-left: 10px;
  margin-top: 10px;
}
.content {
  margin-left: 10px;
}
</style>
