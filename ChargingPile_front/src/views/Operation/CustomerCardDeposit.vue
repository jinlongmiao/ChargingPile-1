<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form  :inline="true">
         <el-form-item label="日期">
           <el-date-picker v-model="dateInfo.dateData" type="daterange" value-format="yyyy-MM-dd" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期">
          </el-date-picker>  
        </el-form-item> 
         <el-form-item label="手机号">
          <el-input v-model="listQuery.phone"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleFilter">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit  highlight-current-row  @sort-change="handleSortChange"v-if="hasPerm('Public:read')">
      <el-table-column align="left" label="序号" prop="id" sortable="custom" width="50"></el-table-column>
      <el-table-column align="center" label="充电ID" prop="chargeCardId" min-width="80"></el-table-column>
      <el-table-column align="center" label="客户姓名" prop="name" min-width="80"></el-table-column>
      <el-table-column align="center" label="客户电话" prop="phone" min-width="50"></el-table-column>
      <el-table-column align="center" label="充值金额" prop="money" min-width="30"></el-table-column>
      <el-table-column align="center" label="网店编号" prop="stationname" min-width="50"></el-table-column>
      <el-table-column align="center" label="操作人" prop="createUser" min-width="40"></el-table-column>
      <el-table-column align="center" label="充值日期"  prop="createTime" min-width="80"></el-table-column>
      <el-table-column align="center" label="备注"  prop="mem"></el-table-column>
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
          pageRow: 10,//每页条数
          order: '',
          phone: '',
          startTime: '',
          endTime: ''
        },
        tempData: {
          id: '',
          chargeCardId: '',
          name: '',
          phone: '',
          money: '',
          stationname: '',
          createUser: '',
          createTime: '',
          mem: ''
        },
        stationNames: [],
        dateInfo: {}
      }
    }, 
    created() {
      this.getList();
    },
    methods: {
      getList() {
        //查询列表
        this.listLoading = true;
        // console.log(this.listQuery);
        this.api({
          url: "Customer/depositList",
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
        this.handleFilter();
      },
      handleSizeChange(val) {
        //改变每页数量
        this.listQuery.pageRow = val;
        this.handleFilter();
      },
      handleCurrentChange(val) {
        //改变页码
        this.listQuery.pageNum = val;
        this.getList();
      },
      handleFilter() {
        if(this.dateInfo.dateData != null ){
          this.listQuery.startTime = this.dateInfo.dateData[0];
          this.listQuery.endTime = this.dateInfo.dateData[1];
        }
        else{
          this.listQuery.startTime = '';
          this.listQuery.endTime = '';
        }
        //查询事件
        this.listQuery.pageNum = 1;
        this.getList();
      },
      getIndex($index) {
        //表格序号
        return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1
      }
    }
  }
</script>
