<template>
  <div class="card margindown" >
    <div class="card-header">
      <h4 class="card-title">{{title}}</h4>
    </div>
    <div slot="header" class="card-body">
      <div class="row rounded ">
        <div class="col-4 p-4">
          <img src="@/../static/img/header.jpg" style="border-radius:50%" width="60px"/>
        </div>
        <div class="col-auto">
          <p  style="text-align: center;line-height: 100px;margin-left: -15px">
           快乐的胖子
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Card',
  data () {
    return {
      title: 'hi'
    }
  }
}
</script>

<style scoped>

</style>
