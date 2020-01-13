<script src="../api/user.js"></script>
<script src="../api/siderInfo.js"></script>
<template>
  <div ref="scroll" id="scroll" style="overflow:hidden;">
    <slot></slot>
    <div
      style="height: 40px;margin-top: 10px;z-index: 1"></div>
  </div>
</template>

<script>
export default {
  name: 'ScrollPage',
  props: {
    noData: Boolean,
    offset: Number
  },
  data () {
    return {
      scrollAction: {
        x: 'undefined',
        y: 'undefined'
      }
    }
  },
  mounted () {
    window.addEventListener('scroll', this.handlerScroll, false)
  },
  beforeDestroy () {
    window.removeEventListener('scroll', this.handlerScroll)
  },
  methods: {
    handlerScroll (e) {
      var that = this
      if (!that.noData) {
        let curHeight = document.documentElement.scrollTop || document.body.scrollTop
        // var scrollPage = document.getElementById('scroll-page')
        // console.log("height")
        // const a = curHeight + window.innerHeight >= (that.$refs.scroll.offsetHeight + that.offset)
        // console.log(a,curHeight,window.innerHeight,that.$refs.scroll.offsetHeight , that.offset )
        if (curHeight + window.innerHeight >= (that.$refs.scroll.offsetHeight + that.offset) &&
          that.isDownDirection()) {
          if (!that.loading) {
            that.$emit('load')
          }
        }
      }
    },
    isDownDirection () {
      if (typeof this.scrollAction.x === 'undefined') {
        this.scrollAction.x = window.pageXOffset
        this.scrollAction.y = window.pageYOffset
      }
      // var diffX = this.scrollAction.x - window.pageXOffset;
      var diffY = this.scrollAction.y - window.pageYOffset

      this.scrollAction.x = window.pageXOffset
      this.scrollAction.y = window.pageYOffset
      console.log(diffY)
      if (diffY < 0) {
        // Scroll down
        return true
      }
      return false
    }
  }
}
</script>

<style scoped>

</style>
