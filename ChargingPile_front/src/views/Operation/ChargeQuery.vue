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
      <el-table-column align="left" label="序号" prop="id" sortable="custom" width="80"></el-table-column>
      <el-table-column align="center" label="充电时间" sortable="custom" prop="transTime" min-width="60"></el-table-column>
      <el-table-column align="center" label="结束时间" prop="transTimeEnd" min-width="80"></el-table-column>
      <el-table-column align="center" label="用户手机" prop="phone" min-width="80"></el-table-column>
      <el-table-column align="center" label="姓名" prop="name" min-width="40"></el-table-column>
      <el-table-column align="center" label="充电卡" prop="chargeCardId" min-width="80"></el-table-column>
      <el-table-column align="center" label="充电站" min-width="stationName"></el-table-column>
      <el-table-column align="center" label="充电桩"  prop="pileId" min-width="30"></el-table-column>
      <el-table-column align="center" label="充电时长"  prop="transDuration" min-width="30"></el-table-column>
      <el-table-column align="center" label="SOC"  prop="SOC" min-width="30"></el-table-column>
      <el-table-column align="center" label="交易前余额"  prop="s1Money" min-width="30"></el-table-column>
      <el-table-column align="center" label="实扣款"  prop="s2Money" min-width="30"></el-table-column>
      <el-table-column align="center" label="卡内余额"  prop="s3Money" min-width="30"></el-table-column>
      <el-table-column align="center" label="网络"  prop="offline" min-width="30"></el-table-column>
      <el-table-column align="center" label="流水"  prop="transId" min-width="30"></el-table-column>

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
          timeOrder: '',
          idOrder: '',
          phone: '',
          startTime: '',
          endTime: ''
        },
        tempData: {
          id: '',
          transTime: '',
          transTimeEnd: '',
          phone: '',
          name: '',
          chargeCardId: '',
          stationName: '',
          pileId: '',
          transDuration: '',
          SOC: '',
          s1Money: '',
          s2Money: '',
          s3Money: '',
          offline: '',
          transId: ''
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
        console.log(this.listQuery);
        this.api({
          url: "ChargeQuery/list",
          method: "get",
          params: this.listQuery
        }).then(data => {
          console.log(data);
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
        })
      },
      handleSortChange(val) {
        // 排序
        if( val.prop === "id" ){
          this.listQuery.idOrder = val.order === 'descending' ? 'DESC' : 'ASC';
          this.listQuery.timeOrder = "";
        }
        else{
          this.listQuery.timeOrder = val.order === 'descending' ? 'DESC' : 'ASC';
          this.listQuery.idOrder = "";
        }
        // console.log(this.listQuery);
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
