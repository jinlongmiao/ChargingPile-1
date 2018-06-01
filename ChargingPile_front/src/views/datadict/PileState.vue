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
      <el-table-column align="center" label="状态码" prop="stateCode" width="400"></el-table-column>
      <el-table-column align="center" label="状态名" prop="stateName" ></el-table-column>
      <el-table-column align="center" label="管理" width="220">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" v-if="hasPerm('Public:edit')" @click="showUpdate(scope.$index)">修改</el-button>
          <el-button type="danger" icon="delete" v-if="hasPerm('Public:delete')" @click="removePileState(scope.$index)">删除
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
      <el-form class="small-space" :model="tempData" label-position="left" label-width="180px"
               style='width: 500px; margin-left:50px;'>
        <el-form-item label="状态码"  >
          <el-input type="stateCode" v-model="tempData.stateCode">
          </el-input>
        </el-form-item>
         <el-form-item label="状态名" >
          <el-input type="stateName" v-model="tempData.stateName">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createPileState">创 建</el-button>
        <el-button type="primary" v-else @click="updatePileState">修 改</el-button>
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
        tempData: {
          id: '',
          stateCode: '',
          stateName: ''
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
          url: "/PileState/list",
          method: "get",
          params: this.listQuery
        }).then(data => {
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
        })
      },
      createPileState() {
        console.log(this.tempData);
        //添加电桩类型
        this.api({
          url: "/PileState/addPileState",
          method: "post",
          data: this.tempData
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
      removePileState($index) {
        let _vue = this;
        this.$confirm('确定删除此电桩状态?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let delData = _vue.list[$index];
          console.log("待删除的DATA");
          console.log(delData);
          _vue.api({
            url: "/PileState/deletePileState",
            method: "post",
            data: delData
          }).then(() => {
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("删除失败")
          })
        })
      },
      updatePileState() {
        //修改电桩状态
        let _vue = this;
        console.log('PileState.vue/PileState/updatePileState');
        console.log(this.tempData);
        this.api({
          url: "/PileState/updatePileState",
          method: "post",
          data: this.tempData
        }).then(() => {
          let msg = "修改成功";
          this.dialogFormVisible = false
          // if (this.userId === this.tempData.userId) {
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
        this.tempData.id = "";
        this.tempData.stateCode = "";
        this.tempData.stateName = "";
        this.dialogStatus = "create";
        this.dialogFormVisible = true;
      },
      showUpdate($index) {
        console.log(this.list);
        console.log($index);
        let PileState = this.list[$index];
        this.tempData = PileState;
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
