<script src="../../store/modules/app.js"></script>
<template>
  <!--  类型:0目录1菜单2按钮-->
  <div>
    <a-card :bordered="false" title="所有权限" >
      <a-form layout="inline">
        <a-row :gutter="16" type="flex" justify="start">
          <a-col :span="6">
          </a-col>
        </a-row>
      </a-form>
    </a-card>
    <a-card hoverable>
      <a-spin tip="loading ..." :spinning="loading">
        <!--            :disabled="tableSelected.type !=null && tableSelected.type === 0"-->
        <a-button-group>
          <a-button
            type="primary"
            @click="addClick">
            <a-icon type="plus"/>add
          </a-button>
          <a-button
            type="primary"
            @click="editClick"
          >
            <a-icon type="edit" />edit
          </a-button>
          <a-button
            type="danger"
            @click="deleteClick"
          >
            <a-icon type="delete">delete</a-icon>
          </a-button>
        </a-button-group>
        <a-table
          style="margin-top: 10px"
          :columns="colunms"
          :dataSource="sData"
          rowKey="id"
          :defaultExpandedRowKeys="expandedRowKeys"
          :rowSelection="rowSelection"
          :customRow="customRow"
          :pagination="false">
        </a-table>
      </a-spin>
    </a-card>
    <a-modal :visible="showMenuModal" title="菜单" :maskClosable="false" @ok="editMenu" @cancel="closeMenu">
      <a-form :form="form">
        <a-form-item v-show="false">
          <a-input type="hidden" v-decorator="['id']"></a-input>
        </a-form-item>
        <a-form-item v-show="false">
          <a-input type="hidden" v-decorator="['pid']" ></a-input>
        </a-form-item>
        <a-form-item v-show="false">
          <a-input type="hidden" v-decorator="['status']"></a-input>
        </a-form-item>
        <a-form-item label="name" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input type="text" v-decorator="['name',{rules:[{required:true, message: 'please input name'}]}]">
          </a-input>
        </a-form-item>
        <a-form-item label="parent" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-tree-select
            style="width: 100%"
            :dropdownStyle="{maxHeight:'400px',overflow: 'auto'}"
            :treeData="modalTreeData"
            :treeDefaultExpandedKeys="expandedRowKeys"
            treeNodeLabelProp="name"
            treeNodeFilterProp="id"
            @select="parentTreeSelect"
            v-decorator="['pname',{rules:[{required:false}] }]"
          >
            <!--            :disabled="modalParams.type != 2 "-->
          </a-tree-select>
        </a-form-item>
        <a-form-item label="path" v-show="modalParams.type == 1" :label-col="labelCol" :wrapperCol="wrapperCol">
          <a-input type="text" v-decorator="['path',{rules:[{required: modalParams.type != 2 , message: 'please input path' }]}]"></a-input>
        </a-form-item>
        <a-form-item label="apiurl" v-show="modalParams.type == 2" :label-col="labelCol" :wrapperCol="wrapperCol">
          <a-input type="text" v-decorator="['apiurl',{rules:[{required: modalParams.type == 2 , message: 'please input apiurl' }]}]"></a-input>
        </a-form-item>
        <a-form-item label="component" v-show="modalParams.type == 1" :label-col="labelCol" :wrapperCol="wrapperCol">
          <a-input type="text" v-decorator="['component',{rules:[{required: modalParams.type == 1 , message: 'please input component'}]}]"></a-input>
        </a-form-item>
        <a-form-item label="icon" :label-col="labelCol" :wrapperCol="wrapperCol">
          <a-radio-group
            buttonStype="solid"
            v-decorator="['type',{rules: [ { required:true,message:'select icon type'}, {validator: makeMenuTypeSafe } ] } ]"
          >
            <a-radio-button value="0" >目录</a-radio-button>
            <a-radio-button value="1" >菜单</a-radio-button>
            <a-radio-button value="2" >按钮</a-radio-button>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="desction" :label-col="labelCol" :wrapperCol="wrapperCol">
          <a-input type="text" v-decorator="['desction']"></a-input>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
import { getAllPermissions, addPermission, updatePermission } from '@/api/menu'

export default {
  name: 'PermissionList',
  data () {
    return {
      loading: true,
      colunms: [
        {
          title: '名称',
          dataIndex: 'name'
        },
        {
          title: '路径',
          dataIndex: 'path'
        },
        {
          title: '图标',
          dataIndex: 'icon'
        },
        {
          title: '类型',
          dataIndex: 'type'
        },
        {
          title: '描述',
          dataIndex: 'desction'
        },
        {
          title: '组件',
          dataIndex: 'component'
        },
        {
          title: '权限',
          dataIndex: 'apiurl'
        },
      ],
      sData: [],
      expandedRowKeys: [], // 展开的行
      rowSelection: {
        type: 'radio',
        selectedRowKeys: []
      },
      tableSelected: {},
      // modal used
      modalParams: { type: 0, pid: 0 },
      modalTreeData: [],
      showMenuModal: false,
      labelCol: {
        xs: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 17 }
      },
      form: this.$form.createForm(this),
      editType: null,
      params: {},
      parentTreeSelected: {}
    }
  },
  methods: {
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
    addClick () {
      if (this.tableSelected.type && this.tableSelected.type === 2) {
        this.$error({ title: 'button cannot create child' })
        return
      }
      this.editType = 'add'
      this.initMenuParams()
      console.log('[emnu]click show menu')
      console.log(this.tableSelected)
      this.modalParams.pname = this.tableSelected.name ? this.tableSelected.name : 'root'
      this.modalParams.pid = this.tableSelected.id ? this.tableSelected.id : 0
      if (this.tableSelected.type === 1) {
        this.modalParams.type = 2
      }
      this.showMenuModal = true
      console.log('[menu]show modal add', this.modalParams)
    },
    editClick () {
      if (this.tableSelected.id == null) {
        this.$error({ title: '没有选择目标' })
        return
      }
      this.editType = 'edit'
      this.initMenuParams()
      this.tableSelected = this.getMenuById(this.sData, this.tableSelected.id)
      Object.assign(this.modalParams, this.tableSelected)

      var parentMenu = this.getMenuById(this.modalTreeData, this.modalParams.pid)
      console.log('[edit]', this.tableSelected, this.modalParams)
      if (parentMenu != null) {
        console.log('[edit]', parentMenu)
        this.modalParams.pname = parentMenu.title ? parentMenu.title : 'root'
        this.modalParams.pid = parentMenu.id ? parentMenu.id : 0
      } else {
        this.modalParams.pname = 'root'
        this.modalParams.pid = 0
      }

      this.showMenuModal = true
      console.log('[menu]show modal add', this.modalParams)
    },
    deleteClick () {

    },
    getData: function () {
      this.loading = true
      getAllPermissions().then(res => {
        console.log('[menu] data back')
        console.log(res)
        this.sData = res.result
        this.loading = false
        // this.expandedRowKeys.push(...this.data.map(item => item.id))
        if (Object.keys(this.params).length === 0 && this.modalTreeData.length <= 0) {
          this.modalTreeData = [{ key: 0, title: 'root', value: 'root', type: 0, id: 0 }]
          // this.modalTreeData[0].children =
          this.makeTreeDataSafe(this.modalTreeData[0], this.sData)
          // console.log(this.modalTreeData)
        }
      })
    },
    closeMenu () {
      this.showMenuModal = false
    },
    editMenu () {
      console.log('[menu]ok click')
      if (this.parentTreeSelected.id) {
        this.modalParams.pid = this.parentTreeSelected.id
      }
      this.form.validateFields((err, values) => {
        console.log(err)
        if (!err) {
          if (this.editType === 'add') {
            values.pid = this.modalParams.pid
            console.log(values)
            addPermission(values).then(res => {
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
            updatePermission(values).then(res => {
              if (res.code === 0) {
                const data = res.result
                this.showMenuModal = false
                this.$message.success('修改成功', 5)
                // eslint-disable-next-line eqeqeq
                if (this.parentTreeSelected.id != this.tableSelected.pid) {
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
    updateModalData (value) {
      var old = this.getMenuById(this.sData, value.id)
      old.name = value.name
      old.path = value.path
      old.component = value.component
      old.desction = value.desction
      old.icon = value.icon
      old.type = value.type
    },
    updateModalTreeIfNeed () {
      if (Object.keys(this.params).length === 0) {
        this.modalTreeData = []
        this.regetTreeData()
      }
    },
    regetTreeData () {
      this.modalTreeData = [{ key: 0, title: 'root', value: 'root', type: 0 }]
      this.makeTreeDataSafe(this.modalTreeData[0], this.sData)
    },
    parentTreeSelect (value, node, extrs) {
      console.log('parentTreeSelect')
      this.parentTreeSelected = extrs.selectedNodes[0].data.props
    },
    makeTreeDataSafe: function (modalData, data) {
      var children = []
      for (var i = data.length - 1; i >= 0; i--) {
        var item = {}
        var dinfo = data[i]
        if (dinfo.type !== 2) {
          item.title = dinfo.name
          item.key = dinfo.id
          item.value = dinfo.name
          item.type = dinfo.type
          item.id = dinfo.id
          children.push(item)
          if (dinfo.children) {
            this.makeTreeDataSafe(item, dinfo.children)
          }
        }
      }
      if (children.length <= 0) {
        return
      }
      console.log('[menu]add children', children)
      modalData.children = children
    },
    makeMenuTypeSafe (rule, value, cb) {
      // console.log('makeMenuTypeSafe',value,this.modalParams)
      // console.log(this.parentTreeSelected)
      var parentType = 0
      if (this.parentTreeSelected.type != null) {
        parentType = this.parentTreeSelected.type
      } else if (this.modalParams.pid != null) {
        var parentMenu = this.getMenuById(this.modalTreeData, this.modalParams.pid)
        // console.log("find parnet")
        // console.log(parentMenu)
        parentType = parentMenu ? parentMenu.type : 0
      }

      // eslint-disable-next-line eqeqeq
      if (parentType == 1 && value != 2) {
        cb(new Error('菜单下只能包含按钮'))
        return
        // eslint-disable-next-line eqeqeq
      } else if (parentType == 0 && value == 2) {
        cb(new Error('目录下不能包含按钮'))
        return
        // eslint-disable-next-line eqeqeq
      } else if (parentType == 2) {
        cb(new Error('按钮下不能包含子项'))
        return
      }
      // console.log('success')
      this.modalParams.type = value
      // this.form.setFieldsValue({type:value.toString()})
      cb()
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
    initMenuParams () {
      // 初始化 模态里面用到的数值
      this.modalParams = {
        id: 0,
        pid: '',
        type: 0,
        name: '',
        path: '',
        component: '',
        icon: '',
        desction: '',
        pname: '',
        apiurl:''
      }
    }
  },
  created () {
    console.log('[role2]created')
    this.getData()
  },
  watch: {
    showMenuModal (newVal) {
      if (newVal) {
        this.parentTreeSelected = {}
        this.$nextTick(() => {
          this.form.setFieldsValue(this.modalParams)
          this.form.setFieldsValue({ type: this.modalParams.type.toString(), pname: this.modalParams.pname })
          console.log('fresh', this.modalParams)
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
