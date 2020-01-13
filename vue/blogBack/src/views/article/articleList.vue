<script src="../../utils/permissions.js"></script>
<script src="../../api/tags.js"></script>
<template>
  <div>
    <a-card title="article">
      <a-spin :spinning="loading">
        <a-table
          style="margin-top: 10px"
          :columns="columns"
          :dataSource="sData"
          rowKey="id"
        >
          <!--              v-action="'/api/comments=get'"-->
          <span slot="action" slot-scope="text, record">
            <a-button
              v-action="'/api/articles=put'"
              type="primary"
              size="small"
              icon="edit"
              @click="edit(record)">
              edit
            </a-button>
          </span>
          <span slot="tags" slot-scope="tags">
            <a-tag v-for="tag in tags" color="blue" :key="tag.id">{{tag.name}}</a-tag>
          </span>
          <span slot="topimg" slot-scope="url">
            <img v-if="url" :src="url" alt="avatar" width="40px"/>
          </span>
        </a-table>
      </a-spin>
    </a-card>
  </div>
</template>

<script>
import { getArticleList } from '@/api/tags'
export default {
  name: 'ArticleList',
  data () {
    return {
      loading: false,
      columns: [
        {
          title: '标题',
          dataIndex: 'title'
        },
        {
          title: '作者',
          dataIndex: 'author'
        },
        {
          title: '分类',
          dataIndex: 'category.name'
        },
        {
          title: '标签',
          dataIndex: 'tagsList',
          scopedSlots: { customRender: 'tags' }
        },
        {
          title: '图片',
          dataIndex: 'topimg',
          scopedSlots: { customRender: 'topimg' }
        },
        {
          title: '操作',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      sData: []
    }
  },
  methods: {
    getData () {
      this.loading = true
      getArticleList().then(res => {
        this.loading = false
        if (res.code === 0) {
          this.sData = res.result
        } else {
          this.$error(res.message)
        }
      })
    },
    edit (record) {
      console.log(record)
      this.$router.push({name: 'async_articlewrite', params: record, query: { id: record.id } })
    }
  },
  created () {
    this.getData()
  }
}
</script>

<style scoped>

</style>
