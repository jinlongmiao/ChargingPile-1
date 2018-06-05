<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form label-width="80px" :inline="true">
        <el-form-item>
          <el-button type="success" icon="plus" v-if="hasPerm('Public:add')"  @click="showCreate">添加
          </el-button>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="listQuery.phone"></el-input>
        </el-form-item>
        <el-form-item label="卡号">
          <el-input v-model="listQuery.chargeCardId"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleFilter">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit highlight-current-row v-if="hasPerm('Public:read')">
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="充点卡ID" prop="chargeCardId"></el-table-column>
      <el-table-column align="center" label="手机号" prop="phone"></el-table-column>
      <el-table-column align="center" label="绑定状态" prop="cardState" width="100"></el-table-column>
      <el-table-column align="center" label="添加日期" prop="createTime" width="170"></el-table-column>
      <el-table-column align="center" label="操作员" prop="createUser" width="170"></el-table-column>
      <el-table-column align="center" label="状态" prop="status" width="170"></el-table-column>
      <el-table-column align="center" label="管理" width="220">
        <template slot-scope="scope">
          <el-button type="danger" icon="delete" v-if="hasPerm('Public:delete')" @click="removeAppEmptycards(scope.$index)">删除
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
      <el-form class="small-space" :model="tempData" label-position="left" label-width="180px" style='width: 500px; margin-left:50px;'>
        <el-form-item label="充电卡ID" >
          <el-input type="text" v-model="tempData.chargeCardId"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="success" @click="createAppEmptycards">创 建</el-button>
       
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'
  import * as util from '@/utils/index.js'

  export default {
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 20,//每页条数
          phone: '',
          chargeCardId: ''
        },
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          create: '新建'
        },
        tempData: {
          id: '',
          phone: '',
          chargeCardId: '',
          cardState: '',
          createTime: '',
          createUser: '',
          status: ''
        }
      }
    },
    created() {
      this.getList();
    },
    computed: {
      ...mapGetters([
        'nickname'
      ])
    },
    methods: {
      getList() {
        //查询列表
        // console.log(this.listQuery);
        this.listLoading = true;
        this.api({
          url: "/AppEmptycards/list",
          method: "get",
          params: this.listQuery
        }).then(data => {
          // console.log(data.list);
          this.listLoading = false;
          this.list = data.list;
          for(let Key in this.list){
             this.list[Key].createTime = util.parseTime(this.list[Key].createTime);
          }
          this.totalCount = data.totalCount;
        })
      },
      createAppEmptycards() {
        this.tempData.createUser = this.nickname;
        //添加
        this.api({
          url: "/AppEmptycards/add",
          method: "post",
          data: this.tempData
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
      removeAppEmptycards($index) {
        let _vue = this;
        this.$confirm('确定删除?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let delData = _vue.list[$index];
          // console.log("待删除的DATA");
          // console.log(delData);
          _vue.api({
            url: "/AppEmptycards/delete",
            method: "post",
            data: delData
          }).then(() => {
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("删除失败")
          })
        })
      },

      showCreate() {
        //显示新增对话框
        for(var Key in this.tempData){
          if( this.tempData[Key] ){
            this.tempData[Key] = null;
          };
        }
        this.dialogStatus = "create";
        this.dialogFormVisible = true;
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
