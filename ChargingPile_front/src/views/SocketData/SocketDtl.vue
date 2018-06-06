<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form  :inline="true">
         <el-form-item label="网关SN">
          <el-input v-model="listQuery.gateId"></el-input>
        </el-form-item>
        <el-form-item label="设备号">
          <el-input v-model="listQuery.pileId"></el-input>
        </el-form-item>
        <el-form-item label="功能号">
          <el-input v-model="listQuery.code"></el-input>
        </el-form-item>
         <el-form-item label="日期">
          <el-date-picker v-model="listQuery.createTime" value-format="yyyy-MM-dd">
          </el-date-picker>  
        </el-form-item> 
        <el-form-item>
          <el-button type="primary" @click="handleFilter">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit highlight-current-row  @sort-change="handleSortChange"v-if="hasPerm('Public:read')">
      <el-table-column align="center" label="序号" prop="id" sortable="custom"  min-width="30">
      </el-table-column>
      <el-table-column align="center" label="时间" prop="createTime" min-width="40"></el-table-column>
      <el-table-column align="center" label="网关号" prop="gateId" min-width="40"></el-table-column>
      <el-table-column align="center" label="设备号" prop="pileId" min-width="40"></el-table-column>
      <el-table-column align="center" label="方向" prop="direct" min-width="20"></el-table-column>
      <el-table-column align="center" label="指令" prop="code" min-width="20"></el-table-column>
      <el-table-column align="center" label="数据包" prop="pack" ></el-table-column>
      <el-table-column align="center" label="网关IP" prop="clientIp" min-width="40"></el-table-column>
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
  </div>
</template>
<script>

  export default {
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 20,//每页条数
          createTime: '',
          pileId: '',
          chargeCardId: '',
          gateId: '',
          code: '',
          order: 'DESC'
        },
        tempData: {
          id: '',
          createTime: '',
          gateId: '',
          pileId: '',
          transId: '',
          chargeCardId: '',
          time1W: '',
          time2W: '',
          time3W: '',
          time4W: '',
          time5W: '',
          time6W: ''
        },
        options: []
      }
    },
    created() {
      this.getList();
    },
    methods: {
      getList() {
        console.log(this.listQuery);
        //查询列表
        this.listLoading = true;
        this.api({
          url: "/SocketDtl/list",
          method: "get",
          params: this.listQuery
        }).then(data => {
          // console.log(data);
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
        })
      },
       handleSortChange(val) {
        // 排序
        this.listQuery.order = val.order === 'descending' ? 'DESC' : 'ASC';
        // console.log(this.listQuery);
        this.handleFilter();
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
