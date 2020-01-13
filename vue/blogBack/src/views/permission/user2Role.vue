<template>
  <div>
    <a-modal
      :visible="show"
      destroyOnClose
      title="change"
      :maskCloseabled="false"
      @ok="editMenu"
      @cancel="cancelMenu"
      @confirmLoading="modalTree.menuModalLoading"
    >
      <a-spin tips="loading" :spinning="modalTree.loading">
        <a-row >
          <a-col :span="12">
            <a-card title="Roles">
              <a-tree
                :treeData="modalTree.treeData"
                showIcon
                checkable
                showLine
                @check="checkTreeMenu"
                @expand="expandTreeMenu"
                defaultExpandAll
                :checkStrictly="modalTree.checkStrictly"
                :defaultCheckedKeys="modalTree.selectedTreeKeys"
                :defaultSelectedKeys="modalTree.selectedTreeKeys"
                :defaultExpandedKeys="modalTree.defaultExpandedKeys"
              >
              </a-tree>
            </a-card>
          </a-col>
          <a-col :span="12">
            <a-card title="Permissions">
              <a-tree
                :treeData="modalTree.permissionData"
                showLine
                defaultExpandAll
              ></a-tree>
            </a-card>
          </a-col>
        </a-row>

      </a-spin>
    </a-modal>
  </div>
</template>

<script>
import { getUserRoles, getAllUserRoles, changeUserRoles, getPermissionByUser } from '@/api/menu'
export default {
  name: 'user2Role',
  props: ['show', 'showData'],
  data () {
    return {
      modalTree: {
        menuModalLoading: false,
        loading: false,
        treeData: [],
        selectedTreeKeys: [],
        changeMenuDta: [], // 权限选择变化过的节点
        defaultExpandedKeys: [],
        checkStrictly: false,
        permissionData: []
      },
      getAll: false
    }
  },
  methods: {
    getData () {
      this.modalTree.selectedTreeKeys = []
      this.modalTree.defaultExpandedKeys = []
      console.log('[user2Role] getdata start')
      this.modalTree.loading = true
      if (this.getAll === false) {
        getAllUserRoles(this.showData.name).then(res => {
          this.getAll = true
          const data = res.result
          this.modalTree.treeData = [{ key: 0, title: 'root', value: 'root', type: 0 }]
          this.makeTreeDataSafe(this.modalTree.treeData[0], data)
          this.modalTree.defaultExpandedKeys = data.map(item => item.id)
          // console.log('make tree data', this.modalTree.defaultExpandedKeys)
        })
      }
      getUserRoles(this.showData.name).then(res => {
        this.modalTree.loading = false
        const data = res.result
        this.modalTree.selectedMenuData = [{ key: 0, title: 'root', value: 'root', type: 0 }]
        this.makeTreeDataSafe(this.modalTree.selectedMenuData[0], data)
        console.log('make tree', this.modalTree.selectedMenuData)
        this.getSelectedTreeKeys(this.modalTree.selectedMenuData)
        console.log('make selectedTreeKeys', this.modalTree.selectedTreeKeys)
      })
      getPermissionByUser(this.showData.name).then(res => {
        const data = res.result
        this.modalTree.permissionData = [{ key: 0, title: 'root', value: 'root', type: 0 }]
        this.makeTreeDataSafe(this.modalTree.permissionData[0], data)
      })
    },
    getSelectedTreeKeys (treeData) {
      for (let i = 0; i < treeData.length; i++) {
        if (treeData[i].children) {
          this.getSelectedTreeKeys((treeData[i].children))
        }
        if (treeData[i].id) {
          this.modalTree.selectedTreeKeys.push(treeData[i].id)
        }
      }
    },
    makeTreeDataSafe (modalData, data) {
      var children = []
      for (var i = data.length - 1; i >= 0; i--) {
        var item = {}
        var dinfo = data[i]
        item.title = dinfo.name
        item.key = dinfo.id
        item.value = dinfo.name
        item.type = dinfo.type
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
    editMenu () {
      var param = {}
      param.id = this.showData.id
      param.changeList = this.modalTree.changeMenuData
      console.log('editMenu changeMenuData ', this.modalTree.changeMenuData)
      console.log({ ...param })
      changeUserRoles(param).then(res => {
        // eslint-disable-next-line eqeqeq
        if (res.code == 0) {
          // success
          this.$message.success('添加成功', 5)
          this.getData()
        } else {
          this.$error(res.message)
        }
      })
    },
    cancelMenu () {
      this.$emit('listenChild', false)
    },
    checkTreeMenu (checkedKeys, info) {
      // console.log('onCheck', checkedKeys, info)
      let keys = checkedKeys
      if (checkedKeys.checked) {
        keys = checkedKeys.checked
      }
      this.modalTree.changeMenuData = []
      // this.modalTree.changeMenuData =
      this.makeSelectedDeptData(this.modalTree.treeData, keys, false)
      console.log('checkTreeMenu changeMenuData ', this.modalTree.changeMenuData)
    },
    makeSelectedDeptData: function (data, keys, disableCheck) {
      const selectKeys = this.modalTree.selectedTreeKeys
      for (let i = 0; i < data.length; i++) {
        const item = data[i]
        const id = item.key
        const oldSelect = selectKeys.indexOf(id)
        const curSelect = keys.indexOf(id)
        // console.log('--', id, oldSelect, curSelect)
        if (oldSelect >= 0 && curSelect >= 0) {
          // 没有变化的
          // console.log('not change ', id)
        } else if (oldSelect >= 0 && curSelect < 0) {
          // 刪除的
          // console.log('delete ', id)
          this.modalTree.changeMenuData.push({ groupid: id, newFlag: false })
        } else if (oldSelect < 0 && curSelect >= 0) {
          // 新增的
          // console.log('new flag ', id)
          this.modalTree.changeMenuData.push({ groupid: id, newFlag: true })
        }
        if (item.children) {
          this.makeSelectedDeptData(item.children, keys, disableCheck)
        }
      }
    },
    expandTreeMenu (expandedKeys) {
      console.log('onExpand', expandedKeys)
      this.modalTree.defaultExpandedKeys = expandedKeys
    }
  },
  created () {
    console.log('[menu]created')
  },
  watch: {
    show (newVal) {
      if (newVal) {
        this.modalTree.checkStrictly = true
        this.getData()
      }
    }
  }
}
</script>

<style scoped>

</style>
