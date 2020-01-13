<template>
  <div>
    <a-card :borderd="false" title="用户">
    </a-card>
    <a-card hoverable>
      <a-spin :spinning="loading">
        <a-button-group>
          <a-button
            @click="addRoleClick"
            type="primary">
            <a-icon type="plus">add</a-icon>
          </a-button>
          <a-button
            @click="deleteClick"
            type="danger">
            <a-icon type="delete">delete</a-icon>
          </a-button>
        </a-button-group>
        <a-table
          style="margin-top:10px"
          :columns="colunms"
          :dataSource="sData"
          rowKey="id"
          :pagination="false">
          <span slot="action" slot-scope="text, record">
            <a-button
              type="primary"
              size="small"
              icon="edit"
              @click="editPermission(record)"
            >角色编辑</a-button>
          </span>
        </a-table>
      </a-spin>
    </a-card>
    <a-modal
      :visible="showMenuModal"
      title="change"
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
        <a-form-item label="nickname" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input type="text" v-decorator="['nickname']">
          </a-input>
        </a-form-item>
        <a-form-item label="password" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input type="password" v-decorator="['password', {rules:[ {required: true, message: 'input password'}] }]">
          </a-input>
        </a-form-item>
        <a-form-item label="password2" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input type="password" v-decorator="['password2', {rules:[ {required: true, message: 'input password'}] }]">
          </a-input>
        </a-form-item>
      </a-form>
    </a-modal>
    <roleModal :show="showRoleModal" :showData="showData" @listenChild="listenChild"></roleModal>
  </div>
</template>

<script>
import { getAllUser, registerUser } from '@/api/menu'
import roleModal from '@/views/permission/user2Role'

export default {
  name: 'UserList',
  components: {
    roleModal
  },
  data () {
    return {
      showRoleModal: false,
      showData: {},
      form: this.$form.createForm(this),
      loading: true,
      tableSelected: {},
      colunms: [
        {
          title: 'name',
          dataIndex: 'name'
        },
        {
          title: '昵称',
          dataIndex: 'nickname'
        },
        {
          title: '操作',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      sData: [],
      labelCol: {
        xs: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 17 }
      },
      showMenuModal: false,
      modalParams: {},
      params: {}
    }
  },
  methods: {
    addRoleClick () {
      this.editType = 'add'
      this.initRoleParam()
      this.showMenuModal = true
    },
    deleteClick () {

    },
    getData () {
      console.log('[rolelist] getdata start')
      this.loading = true
      getAllUser().then(res => {
        this.loading = false
        this.sData = res.result
        console.log('[rolelist]end ', this.sData)
      })
    },
    initRoleParam () {
      this.modalParams = {
        name: '',
        password: '',
        password2: '',
        nickname: ''
      }
    },
    editMenu () {
      this.form.validateFields((err, values) => {
        console.log(err)
        // eslint-disable-next-line eqeqeq
        if (values.password != values.password2) {
          this.$error({ title: '密码不相同' })
        } else {
          console.log(values)
          registerUser(values).then(res => {
            // eslint-disable-next-line eqeqeq
            if (res.code == 0) {
              this.getData()
            } else {
              this.$error({ title: res.message })
            }
          })
        }
      })
    },
    closeMenu () {
      this.showMenuModal = false
    },
    editPermission (record) {
      console.log('click this', record)
      this.showData = record
      this.showRoleModal = true
    },
    listenChild (value) {
      console.log('listtenChild', value)
      this.showRoleModal = value
    }
  },
  created () {
    console.log('[menu]created')
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
