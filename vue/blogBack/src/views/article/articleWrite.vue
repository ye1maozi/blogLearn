<script src="../../api/menu.js"></script>
<script src="../../api/tags.js"></script>
<template>
  <div>
    <a-card>
      <a-form :form="form" @submit="handleSubmit">
        <a-form-item>
          <a-input
            type="text"
            placeholder="标题"
            v-decorator="['title',{rules:[ {required: true, message: 'input title'}]}]"></a-input>
        </a-form-item>
        <a-form-item label="标题图片">
          <a-upload
            class="avatar-uploader"
            :showUploadList="false"
            :beforeUpload="beforeUpload"
            @change="handleChangeTopImg"
            name="avatar"
            list-type="picture">
            <img v-if="imageUrl" :src="imageUrl" alt="avatar" />
            <div v-else>
              <a-button>
                <a-icon :type="loading ? 'loading' : 'plus'" />
                Upload
              </a-button>
            </div>
          </a-upload>
        </a-form-item>
        <a-form-item>
          <mavon-editor ref=md  v-model="contentMd" style="z-index: 0" @imgAdd="imgAdd" @imgDel="imgDel"></mavon-editor>
        </a-form-item>
        <a-form-item label="类型" :label-col="labelCol" :wrapper-col="wrapperCol" >
          <a-select
            showSearch
            placeholder="Select a category"
            style="width: 200px"
            @change="handleChange"
            optionFilterProp="children"
            :filterOption="filterOption"
            :value="selectValue"
          >
            <div slot="dropdownRender" slot-scope="menu">
              <v-nodes :vnodes="menu"/>
              <a-divider style="margin: 4px 0;" />
              <div style="padding: 8px; cursor: pointer;" @mousedown="clickAddCategory">
                <a-icon type="plus" /> Add item
              </div>
            </div>
            <a-select-option v-for="(item, i) in category" :key="i">{{ item.name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="标签" :label-col="labelCol" :wrapper-col="wrapperCol">
          <span v-for="tag in tags" :key="tag.id">
            <a-checkable-tag
              :key="tag.id"
              :checked="selectedTags.indexOf(tag.id) > -1"
              @change="(checked) => handleCheckChange(tag, checked)"
            >
              {{ tag.name }}
            </a-checkable-tag>
          </span>
          <a-input
            v-if="newTag.inputVisible"
            ref="input"
            type="text"
            size="small"
            :style="{ width: '78px' }"
            :value="newTag.inputValue"
            @change="handleInputChange"
            @blur="handleInputConfirm"
            @keyup.enter="handleInputConfirm"
          />
          <a-tag v-else @click="showInput" style="background: #fff; borderStyle: dashed;">
            <a-icon type="plus" /> New Tag
          </a-tag>
        </a-form-item>
        <a-form-item :wrapper-col="wrapperCol">
          <a-button type="primary" html-type="submit" >
            提交
          </a-button>
        </a-form-item>
      </a-form>
    </a-card>

    <a-modal
      :visible="showModalCategory"
      title="add"
      :maskCloseabled="false"
      @ok="editMenu"
      @cancel="closeMenu"
    >
      <a-form :form="formCateogry">
        <a-form-item v-show="false">
          <a-input type="hidden"v-decorator="['id']"></a-input>
        </a-form-item>
        <a-form-item label="name" :label-col="labelCol2" :wrapper-col="wrapperCol2">
          <a-input type="text" v-decorator="['name', {rules:[ {required: true, message: 'input name'}] }]">
          </a-input>
        </a-form-item>
        <a-form-item label="desction" :label-col="labelCol2" :wrapper-col="wrapperCol2">
          <a-input type="desction" v-decorator="['desction']">
          </a-input>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
import { getAllTags, addTag, getAllCateogry, addCategory, handleUpload, postArticle, getArticleById, updateArticle } from '@/api/tags'
function getBase64 (img, callback) {
  const reader = new FileReader()
  reader.addEventListener('load', () => callback(reader.result))
  reader.readAsDataURL(img)
}
export default {
  name: 'ArticleWrite',
  components: {
    VNodes: {
      functional: true,
      render: (h, ctx) => ctx.props.vnodes
    }
  },
  data () {
    return {
      newTag: {
        inputValue: '',
        inputVisible: false
      },
      labelCol: {
        sm: { span: 1 },
        xs: { span: 24 }

      },
      wrapperCol: {
        sm: { span: 12 },
        xs: { span: 24 }
      },
      labelCol2: {
        xs: { span: 5 }
      },
      wrapperCol2: {
        xs: { span: 17 }
      },
      value: '',
      category: [],
      selectCategory: [],
      tags: [],
      selectedTags: [],
      contentMd: '',
      defaultData: 'preview',
      formData: {
        title: '',
        categoryId: '',
        contentMd: '',
        isMarkdown: 1,
        topImg: ''
      },
      form: this.$form.createForm(this),
      formCateogry: this.$form.createForm(this),
      showModalCategory: false,
      imageUrl: '',
      loading: false,
      topFile: null,
      img_file: {},
      uptopImg: '',
      selectValue: '',
      type: '',
    }
  },
  methods: {
    handleChange (value) {
      console.log(value)
      this.selectValue = value
      this.formData.categoryId = this.category[value].id
    },
    filterOption (input, option) {
      return option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
    },
    handleCheckChange (tag, checked) {
      const { selectedTags } = this
      const nextSelectedTags = checked
        ? [...selectedTags, tag.id]
        : selectedTags.filter(t => t !== tag.id)
      console.log('You are interested in: ', checked, tag, nextSelectedTags)
      this.selectedTags = nextSelectedTags
    },
    getAllData () {
      this.getCategorys()
      this.getAllTags()
    },
    getCategorys () {
      getAllCateogry().then(res => {
        if (res.code === 0) {
          this.category = res.result
          this.showSelectCategory()
        } else {
          this.$error(res.message)
        }
      })
    },
    getAllTags () {
      getAllTags().then(res => {
        if (res.code === 0) {
          this.tags = res.result
          this.showSelectTags()
        } else {
          this.$error(res.message)
        }
      })
    },
    showInput () {
      this.newTag.inputVisible = true
      this.$nextTick(function () {
        this.$refs.input.focus()
      })
    },

    handleInputChange (e) {
      this.newTag.inputValue = e.target.value
    },

    handleInputConfirm () {
      const inputValue = this.newTag.inputValue
      let tags = this.tags
      if (inputValue && tags.indexOf(inputValue) === -1) {
        tags = [...tags, inputValue]
      }
      this.newTag.inputValue = ''
      this.newTag.inputVisible = false
      addTag({ name: inputValue }).then(res => {
        this.getAllTags()
      })
    },
    clickAddCategory () {
      console.log('clickAddCategory')
      this.initRoleParam()
      this.showModalCategory = true
    },
    editMenu () {
      this.formCateogry.validateFields((err, values) => {
        addCategory(values).then(res => {
          this.getCategorys()
          this.showModalCategory = false
        })
      })
    },
    closeMenu () {
      this.showModalCategory = false
    },
    initRoleParam () {
      this.modalParams = {
        name: '',
        desction: ''
      }
    },
    handleSubmit (e) {
      e.preventDefault()
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log('Received values of form: ', values)
          // eslint-disable-next-line eqeqeq
          if (this.contentMd == '') {
            this.$error({ title: 'not content' })
          } else {
            // 处理markdown图片
            // 处理标题图片
            this.uploadimg().then(res => {
              const param = {
                title: values.title,
                contentmd: this.contentMd,
                ismarkdown: 1,
                categoryid: this.formData.categoryId,
                tags: this.selectedTags,
                topimg: this.uptopImg,
                id: this.initData.id
              }
              if ( this.type == 'edit') {
                updateArticle(param).then(res => {
                  console.log('post article', res)
                  // eslint-disable-next-line eqeqeq
                  if (res.code == 0) {
                    this.$message.success('添加成功', 5)
                  } else {
                    this.$error(res.message)
                  }
                })
              } else {
                postArticle(param).then(res => {
                  console.log('post article', res)
                  // eslint-disable-next-line eqeqeq
                  if (res.code == 0) {
                    this.$message.success('添加成功', 5)
                  } else {
                    this.$error(res.message)
                  }
                })
              }
              // console.log(this.selectValue )
              console.log('update', param)
            })
          }
        } else {
        }
      })
    },
    beforeUpload (file) {
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('Image must smaller than 2MB!')
      }
      return false
    },
    handleChangeTopImg: function (info) {
      console.log(info)
      this.topFile = info.file
      // this.imageUrl = info
      // if (info.file.status === 'uploading') {
      //   this.loading = true
      //   return
      // }
      // if (info.file.status === 'done') {
      // Get this url from response in real world.
      getBase64(info.file, (imageUrl) => {
        this.imageUrl = imageUrl
        this.loading = false
      })
      // }
    },
    // 绑定@imgAdd event
    imgAdd (pos, $file) {
      // 缓存图片信息
      console.log($file)
      this.img_file[pos] = $file
    },
    imgDel (pos) {
      delete this.img_file[pos]
    },
    uploadimg: function () {
      return new Promise((resolve, reject) => {
        const formData = new FormData()
        var files = []
        var names = []
        if (this.topFile != null) {
          files.push(this.topFile)
          formData.append('files', this.topFile)
          names.push('topImg')
        }
        console.log('post mk')
        for (var _img in this.img_file) {
          console.log(_img)
          formData.append('files', this.img_file[_img])
          files.push(this.img_file[_img])
          names.push(_img)
        }
        formData.append('types', names)
        console.log('post daat', formData)
        handleUpload(formData).then(res => {
          /**
           * 例如：返回数据为  { name url}
           * name 为原图片标志（0）
           * url 为上传后图片的url地址
           */
          console.log('upload end ', res.result)
          var path = process.env.VUE_APP_API_BASE_URL2
          for (var index in res.result) {
            // $vm.$img2Url 详情见本页末尾
            // eslint-disable-next-line eqeqeq
            var data = res.result[index]
            console.log(data)
            // eslint-disable-next-line eqeqeq
            if (data.name == 'topImg') {
              this.uptopImg = path + data.url
            } else {
              this.$refs.md.$img2Url(data.name, path + data.url)
            }
          }
          resolve()
        })
      })
    },
    getParams () {
      const routerParams = this.$route.params
      console.log('getParams')
      const queryParam = this.$route.query
      if (routerParams.id != null) {
        // 有传值
        this.setDefaultArticle(routerParams)
      } else if (queryParam.id) {
        getArticleById(queryParam.id).then(res => {
          if (res.code == 0) {
            this.setDefaultArticle(res.result)
          }
        })
      }
    },
    showSelectCategory () {
      if (this.category && this.initData){
        for (let i = 0; i < this.category.length; i++) {
          const c = this.category[i]
          // eslint-disable-next-line eqeqeq
          if (c.id == this.initData.categoryid) {
            this.selectValue = i
          }
        }
      }
    },
    showSelectTags () {
      if (this.tags && this.initData){
        this.selectedTags = []
        this.initData.tagsList.forEach(tag => {
          this.selectedTags.push(tag.id)
        })
      }
    },
    setDefaultArticle: function (data) {
      this.type = 'edit'
      this.initData = data
      this.formData.title = data.title
      this.formData.categoryId = data.categoryid
      this.formData.uptopImg = data.topimg
      this.imageUrl = data.topimg
      this.contentMd = data.contentmd
      this.showSelectCategory()
      this.showSelectTags()
      this.$nextTick(() => {
        this.form.setFieldsValue({ title: data.title })
      })
    }
  },
  created () {
    this.form.setFieldsValue(this.formData)
    this.getAllData()
    this.getParams()
  },
  watch: {
    showModalCategory (newVal) {
      if (newVal) {
        this.$nextTick(() => {
          this.formCateogry.setFieldsValue(this.modalParams)
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
