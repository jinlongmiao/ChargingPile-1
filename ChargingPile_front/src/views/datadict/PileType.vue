<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="success" icon="plus" v-if="hasPerm('Public:add')"  @click="showCreate">添加
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row v-if="hasPerm('Public:read')">
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="类型码" prop="typeCode" width="100"></el-table-column>
      <el-table-column align="center" label="类型名" prop="typeName" ></el-table-column>
      <el-table-column align="center" label="额定功率或电流" prop="wi" width="100"></el-table-column>
      <el-table-column align="center" label="充电口数量" prop="port" width="170"></el-table-column>
      <el-table-column align="center" label="交流/直流" prop="type" width="170"></el-table-column>
      <el-table-column align="center" label="管理" width="220">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" v-if="hasPerm('Public:edit')" @click="showUpdate(scope.$index)">修改</el-button>
          <el-button type="danger" icon="delete" v-if="hasPerm('Public:delete')" @click="removePileType(scope.$index)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.pageNum"
      :page-size="listQuery.pageRow"
      :total="totalCount"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="tempPileType" label-position="left" label-width="180px"
               style='width: 500px; margin-left:50px;'>
        <el-form-item label="ID" v-if="dialogStatus=='update'">
          <span>{{tempPileType.id}}</span>
        </el-form-item>
        <el-form-item label="类型码"  >
          <el-input type="typeCode" v-model="tempPileType.typeCode">
          </el-input>
        </el-form-item>
         <el-form-item label="类型名" >
          <el-input type="typeName" v-model="tempPileType.typeName">
          </el-input>
        </el-form-item>
        <el-form-item label="额定功率或电流" >
          <el-input type="text" v-model="tempPileType.wi">
          </el-input>
        </el-form-item>
        <el-form-item label="充电口数量" >
          <el-input type="text" v-model="tempPileType.port">
          </el-input>
        </el-form-item>
        <el-form-item label="交流/直流" >
          <el-input type="text" v-model="tempPileType.type">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createPileType">创 建</el-button>
        <el-button type="primary" v-else @click="updatePileType">修 改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'

  export default {
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 20,//每页条数
        },
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          create: '新建电桩'
        },
        tempPileType: {
          id: '',
          typeCode: '',
          typeName: '',
          wi: '',
          port: '',
          type: ''
        }
      }
    },
    created() {
      this.getList();
    },
    computed: {
      ...mapGetters([
        'userId'
      ])
    },
    methods: {
      getList() {
        //查询列表
        this.listLoading = true;
        this.api({
          url: "/PileType/list",
          method: "get",
          params: this.listQuery
        }).then(data => {
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
        })
      },
      createPileType() {
        console.log(this.tempPileType);
        //添加电桩类型
        this.api({
          url: "/PileType/addPileType",
          method: "post",
          data: this.tempPileType
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
      removePileType($index) {
        let _vue = this;
        this.$confirm('确定删除此电桩类型?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let delData = _vue.list[$index];
          console.log("待删除的DATA");
          console.log(delData);
          _vue.api({
            url: "/PileType/deletePileType",
            method: "post",
            data: delData
          }).then(() => {
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("删除失败")
          })
        })
      },
      updatePileType() {
        //修改电桩类型
        let _vue = this;
        console.log('PileType.vue/PileType/updatePileType');
        console.log(this.tempPileType);
        this.api({
          url: "/PileType/updatePileType",
          method: "post",
          data: this.tempPileType
        }).then(() => {
          let msg = "修改成功";
          this.dialogFormVisible = false
          // if (this.userId === this.tempPileType.userId) {
          //   msg = '修改成功,部分信息重新登录后生效'
          // }
          this.$message({
            message: msg,
            type: 'success',
            duration: 1 * 1000,
            onClose: () => {
              _vue.getList();
            }
          })
        })
      },

      showCreate() {
        //显示新增对话框
        this.tempPileType.id = "";
        this.tempPileType.typeCode = "";
        this.tempPileType.typeName = "";
        this.tempPileType.wi = "";
        this.tempPileType.port = "";
        this.tempPileType.type = "";
        this.dialogStatus = "create";
        this.dialogFormVisible = true;
      },
      showUpdate($index) {
        console.log(this.list);
        console.log($index);
        let PileType = this.list[$index];
        this.tempPileType = PileType;
        this.dialogStatus = "update"
        this.dialogFormVisible = true
      },

      handleSizeChange(val) {
        //改变每页数量
        this.listQuery.pageRow = val
        this.handleFilter();
      },
      handleCurrentChange(val) {
        //改变页码
        this.listQuery.pageNum = val
        this.getList();
      },
      handleFilter() {
        //查询事件
        this.listQuery.pageNum = 1
        this.getList()
      },
      getIndex($index) {
        //表格序号
        return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1
      }
    }
  }
</script>
