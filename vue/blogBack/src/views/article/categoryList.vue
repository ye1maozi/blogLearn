<script src="../../api/tags.js"></script>
<template>
  <div>
    <a-card title="tags">
      <a-spin :spinning="loading">
        <a-button-group>
          <a-button type="primary" @click="addClick">
            <a-icon type="plus">add</a-icon>
          </a-button>
          <a-button type="danger" @click="deleteClick">
            <a-icon type="delete">delete</a-icon>
          </a-button>
        </a-button-group>
        <a-table
          style="margin-top: 10px"
          :columns="columns"
          :dataSource="sData"
          rowKey="id"
        >
       <span slot="action" slot-scope="text, record">
          <a-button
            type="primary"
            size="small"
            icon="edit"
            @click="edit(record)">
            edit
          </a-button>
        </span>
        </a-table>
      </a-spin>
    </a-card>
    <a-modal
      :visible="showMenuModal"
      title="add"
      :maskCloseabled="false"
      @ok="editMenu"
      @cancel="closeMenu"
    >
      <a-form :form="form">
        <a-form-item v-show="false">
          <a-input type="hidden"v-decorator="['id']"></a-input>
        </a-form-item>
        <a-form-item label="name" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input type="text" v-decorator="['name', {rules:[ {required: true, message: 'input name'}] }]">
          </a-input>
        </a-form-item>
        <a-form-item label="desction" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input type="desction" v-decorator="['desction']">
          </a-input>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
import { getAllCateogry, addCategory, editCategory} from '@/api/tags'
export default {
  name: 'TagsList',
  data () {
    return {
      form: this.$form.createForm(this),
      loading: false,
      showMenuModal: false,
      columns: [
        {
          title: 'name',
          dataIndex: 'name'
        },
        {
          title: 'desction',
          dataIndex: 'desction'
        },
        {
          title: '操作',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      sData: [],
      modalParams: {},
      labelCol: {
        xs: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 17 }
      },
    }
  },
  methods: {
    addClick () {
      this.initRoleParam()
      this.showMenuModal = true
    },
    initRoleParam (){
      this.modalParams = {
        name: '',
        desction: ''
      }
    },
    deleteClick () {

    },
    edit (record) {
      this.showMenuModal = true
      this.modalParams = {
        name: record.name,
        desction: record.desction,
        id: record.id
      }
    },
    editMenu () {
      this.form.validateFields((err, values) => {
        if (this.modalParams.id != null && this.modalParams.id  > 0){
          editCategory(values).then(res => {
            if (res.code === 0) {
              this.getData()
              this.showMenuModal = false
            }
          })
        } else {
          addCategory(values).then(res => {
            if (res.code === 0) {
              this.getData()
              this.showMenuModal = false
            }
          })
        }
      })
    },
    closeMenu () {
      this.showMenuModal = false
    },
    getData () {
      this.loading = true
      getAllCateogry().then(res => {
        this.loading = false
        if (res.code === 0) {
          this.sData = res.result
        } else {
          this.$error(res.message)
        }
      })
    }
  },
  created () {
    this.getData()
  },
  watch: {
    showMenuModal (newVal) {
      if (newVal) {
        this.$nextTick(() => {
          this.form.setFieldsValue(this.modalParams)
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
