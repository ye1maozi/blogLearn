<template>
  <div>
    <a-card :borderd="false" title="角色">
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
            @click="editRoleClick"
            type="primary">
            <a-icon type="edit">edit</a-icon>
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
          :defaultExpandedRowKeys="expandedRowKeys"
          :rowSelection="rowSelection"
          :customRow="customRow"
          :pagination="false">
           <span slot="action" slot-scope="text, record">
               <a-button
               type="primary"
               size="small"
               icon="edit"
               @click="editPermission(record)"
               >权限编辑</a-button>
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
        <a-form-item label="desction" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input type="text" v-decorator="['desction']">
          </a-input>
        </a-form-item>
        <a-form-item label="parent" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-tree-select
            style="width: 100%"
            :dropdownStyle="{maxHeight:'400px',overflow:'auto'}"
            :treeData="modalTreeData"
            :treeDefaultExpandedKeys="expandedRowKeys"
            treeNodeLabelProp="name"
            treeNodeFilterProp="id"
            @select="parentTreeSelect"
            v-decorator="['pname',{rules:[{required:false}] }]"
          >
          </a-tree-select>
        </a-form-item>
      </a-form>
    </a-modal>
    <roleModal v-bind:show="showRoleModal" v-bind:showData="showData" v-on:listenChild="listenChild"></roleModal>
  </div>
</template>

<script>
import { getRoleList, addRole, updateRole } from '@/api/menu'
import roleModal from '@/views/permission/role2Permission'

export default {
  name: 'RoleList',
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
          title: '描述',
          dataIndex: 'desction'
        },
        {
          title: '状态',
          dataIndex: 'status',
          customRender: (status, row, index) => {
            return (
              <div>
                正常
              </div>
            )
          }
        },
        {
          title: '操作',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      sData: [],
      rowSelection: {
        type: 'radio',
        selectedRowKeys: []
      },
      labelCol: {
        xs: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 17 }
      },
      showMenuModal: false,
      editType: '',
      modalParams: {},
      modalTreeData: [],
      expandedRowKeys: [],
      parentTreeSelected: {},
      params: {}
    }
  },
  methods: {
    addRoleClick () {
      this.editType = 'add'
      this.initRoleParam()
      this.modalParams.pname = this.tableSelected.name ? this.tableSelected.name : 'root'
      this.modalParams.pid = this.tableSelected.id ? this.tableSelected.id : 0
      this.showMenuModal = true
    },
    editRoleClick () {
      if (this.tableSelected.id == null) {
        this.$error({ title: '没有选择目标' })
        return
      }
      this.editType = 'edit'
      this.modalParams = {
        state: this.tableSelected.state,
        desction: this.tableSelected.desction,
        name: this.tableSelected.name,
        id: this.tableSelected.id
      }
      var parentMenu = this.getMenuById(this.modalTreeData, this.tableSelected.parent)
      if (parentMenu != null) {
        console.log('[edit]', parentMenu)
        this.modalParams.pname = parentMenu.title ? parentMenu.title : 'root'
        this.modalParams.pid = parentMenu.id ? parentMenu.id : 0
      } else {
        this.modalParams.pname = 'root'
        this.modalParams.pid = 0
      }

      this.showMenuModal = true
    },
    getMenuById (data, id) {
      if (id === null) return null
      for (var i = 0; i < data.length; i++) {
        if (data[i].id === id) {
          return data[i]
        }
        if (data[i].children) {
          var ret = null
          if ((ret = this.getMenuById(data[i].children, id))) {
            return ret
          }
        }
      }
    },
    deleteClick () {

    },
    customRow (record) {
      return {
        on: {
          click: () => {
            this.rowSelection.selectedRowKeys = [record.id]
            this.tableSelected = record
          }
        }
      }
    },
    getData () {
      console.log('[rolelist] getdata start')
      this.loading = true
      getRoleList().then(res => {
        this.loading = false
        this.sData = res.result
        console.log('[rolelist]end ', this.sData)
        if (Object.keys(this.params).length === 0 && this.modalTreeData.length <= 0) {
          this.modalTreeData = [{ key: 0, title: 'root', value: 'root', desction: '' }]
          this.makeTreeDataSafe(this.modalTreeData[0], this.sData)
        }
      })
    },
    makeTreeDataSafe: function (modalData, data) {
      var children = []
      for (var i = data.length - 1; i >= 0; i--) {
        var item = {}
        var dinfo = data[i]
        item.title = dinfo.name
        item.key = dinfo.id
        item.value = dinfo.name
        item.id = dinfo.id
        item.desction = dinfo.desction
        children.push(item)
        if (dinfo.children) {
          this.makeTreeDataSafe(item, dinfo.children)
        }
      }
      if (children.length <= 0) {
        return
      }
      console.log('[menu]add children', children)
      modalData.children = children
    },
    initRoleParam () {
      this.modalParams = {
        state: 1,
        desction: '',
        name: ''
      }
    },
    parentTreeSelect (value, node, extra) {
      this.parentTreeSelected = extra.selectedNodes[0].data.props
    },
    editMenu () {
      // this.showMenuModal = false
      if (this.parentTreeSelected.id) {
        this.modalParams.pid = this.parentTreeSelected.id
      } else {
        this.modalParams.pid = this.modalParams.pid
      }

      this.form.validateFields((err, values) => {
        console.log(err)
        values.pid = this.modalParams.pid
        if (!err) {
          if (this.editType === 'add') {

            console.log(values)
            addRole(values).then(res => {
              if (res.code === 0) {
                // var data = res.result
                this.showMenuModal = false
                this.$message.success('添加成功', 5)
                this.getData()
                this.updateModalTreeIfNeed()
              } else {
                this.$error(res.message)
              }
            })
          } else if (this.editType === 'edit') {
            console.log(values)
            updateRole(values).then(res => {
              if (res.code === 0) {
                const data = res.result
                this.showMenuModal = false
                this.$message.success('修改成功', 5)
                // eslint-disable-next-line eqeqeq
                if (this.parentTreeSelected.id != this.tableSelected.parent) {
                  // 更新下树结构
                  this.getData()
                  this.updateModalTreeIfNeed()
                } else {
                  // update data only
                  this.updateModalData(data)
                }
              }
            })
          }
        }
      })
    },
    updateModalTreeIfNeed () {
      if (Object.keys(this.params).length === 0) {
        this.modalTreeData = []
        this.regetTreeData()
      }
    },
    regetTreeData () {
      this.modalTreeData = [{ key: 0, title: 'root', value: 'root', desction: '' }]
      this.makeTreeDataSafe(this.modalTreeData[0], this.sData)
    },
    updateModalData (value) {
      var old = this.getMenuById(this.sData, value.id)
      old.name = value.name
      old.desction = value.desction
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
        this.parentTreeSelected = {
          id: this.modalParams.pid
        }
        this.$nextTick(() => {
          this.form.setFieldsValue(this.modalParams)
          this.form.setFieldsValue({ pname: this.modalParams.pname })
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
