<template>
  <div>
    <a-card>
      <a-form layout="inline">
        <a-row :gutter="16" type="flex" justify="start">
          <a-col :span="6">
            <a-form-item label="状态" >
              <a-radio-group v-model="params.status">
                <a-radio-button value="0">正常</a-radio-button>
                <a-radio-button value="1">禁用</a-radio-button>
              </a-radio-group>
             </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item>
              <a-button type="primary" @click="goSearch"> <a-icon type="search"></a-icon>搜索 </a-button>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-card>
    <a-card title="tags">
      <a-spin :spinning="loading">
        <a-table
          style="margin-top: 10px"
          :columns="columns"
          :dataSource="sData"
          rowKey="id"
          :pagination="paginationProps"
        >
       <span slot="action" slot-scope="text, record">
          <a-button
            type="danger"
            size="small"
            icon="close"
            @click="edit(record)"
            v-if="record.status==0">
            禁言
          </a-button>
           <a-button
             type="primary"
             size="small"
             icon="check"
             @click="edit(record)"
             v-else>
           还原
          </a-button>
        </span>
        </a-table>
      </a-spin>
    </a-card>
  </div>
</template>

<script>
// eslint-disable-next-line standard/object-curly-even-spacing
import { getAllComments, changeCommentStatus, getCommentTotal } from '@/api/tags'
export default {
  name: 'commentsList',
  data () {
    return {
      button: {
        icon: 'edit',
        mode: 'edit'
      },
      params: {
        status: '0',
        pageNo: 1
      },
      loading: false,
      columns: [
        {
          title: 'name',
          dataIndex: 'nickname'
        },
        {
          title: 'article',
          dataIndex: 'article.title'
        },
        {
          title: 'content',
          dataIndex: 'content'
        },
        {
          title: 'updateTime',
          dataIndex: 'updatetime'
        },
        {
          title: 'ip',
          dataIndex: 'ip'
        },
        {
          title: 'status',
          dataIndex: 'status'
        },
        {
          title: '操作',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      sData: [],
      paginationProps: {
        page: 1,
        current: 1,
        onChange: (page) => this.handleTableChange(page),
        total: 10
      }
    }
  },
  methods: {
    edit (record) {
      // eslint-disable-next-line no-unused-vars
      var status
      // eslint-disable-next-line eqeqeq
      if (record.status == 0) {
        status = 1
      } else {
        status = 0
      }
      const id = record.id
      changeCommentStatus(status,id).then(res => {
        // eslint-disable-next-line eqeqeq
        if (res.code == 0) {
          record.status = status
        } else {
          this.$error({ title: res.message })
        }
      })
    },
    getData () {
      this.loading = true
      getAllComments(this.params).then(res => {
        this.loading = false
        if (res.code === 0) {
          this.sData = res.result
        } else {
          this.$error(res.message)
        }
      })
    },
    handleTableChange (pagination) {
      // this.params.pageNo = pagination.current
      // console.log(pagination)
      this.params.pageNo = pagination
      this.paginationProps.current = pagination
      this.getData()
    },
    getTotal () {
      getCommentTotal(this.params.status).then(res => {
        // eslint-disable-next-line eqeqeq
        if (res.code == 0) {
          this.paginationProps.total = res.result
        }
      })
    },
    goSearch () {
      this.params.pageNo = 1
      this.paginationProps.current = 1
      this.getTotal()
      this.getData()
    }
  },
  created () {
    this.getTotal()
    this.getData()
  }
}
</script>

<style scoped>

</style>
