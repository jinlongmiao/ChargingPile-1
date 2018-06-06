<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form  class="small-space" :model="tempData" label-width="80px" :inline="true">
        <el-form-item label="日期选择" >
          <el-date-picker v-model="tempData.dateData" type="daterange" value-format="yyyy-MM-dd" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期">
          </el-date-picker>    
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="tempData.telephone">
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onFind">查询</el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" style='margin-bottom:30px;'>金额: {{sumMoney}}</el-button>
    </div>

    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit highlight-current-row  @sort-change="handleSortChange" v-if="hasPerm('Public:read')" >
      <el-table-column align="center" label="序号" width="80" >
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="时间" prop="notifyTime" width="250" sortable="custom" >
      </el-table-column>
      <el-table-column align="center" label="交易类目" prop="subject" ></el-table-column>
      <el-table-column align="center" label="金额" prop="totalAmount" width="80"></el-table-column>
      <el-table-column align="center" label="支付账户" prop="buyerLoginId" width="150"></el-table-column>
      <el-table-column align="center" label="APP绑定手机" prop="telephone" width="150"></el-table-column>
      <el-table-column align="center" label="交易订单号" prop="tradeNo" width="255"></el-table-column>
      <el-table-column align="center" label="支付订单号" prop="outTradeNo" width="255"></el-table-column>
     <!--  <el-table-column align="center" label="交易状态"  width="180"  
      :filters="[{ text: '交易成功', value: '交易成功' }, { text: '交易关闭', value: '交易关闭' }]"
      :filter-method="filterTag"
      filter-placement="bottom-end" prop="tradeStatus">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.tradeStatus==='TRADE_SUCCESS'">交易成功</el-tag>
          <el-tag type="primary" v-else>交易关闭</el-tag>
        </template>
      </el-table-column> -->
      <el-table-column align="center" label="交易状态"  width="180">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.tradeStatus==='TRADE_SUCCESS'">交易成功</el-tag>
          <el-tag type="primary" v-else>交易关闭</el-tag>
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
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'

  export default {
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
        sumMoney: 0,
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 20,//每页条数
          order: 'DESC',
          startTime: '',
          endTime: '',
          telephone: ''
        },
        tempData: {
          dateData: '',
          telephone: ''
        },
        isFind: false
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
          url: "/Alipaytrade/list",
          method: "get",
          params: this.listQuery
        }).then(data => {
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
          this.getSumMoney();
        })
      },
      getSumMoney() {
        // console.log(this.listQuery);
        this.api({
          url: "/Alipaytrade/getMoney",
          method: "get",
          params: this.listQuery
        }).then(data => {
          this.sumMoney = data;
        })
      },
      onFind(){
        if(this.tempData.dateData === null){
          this.listQuery.startTime = '';
          this.listQuery.endTime = '';
        }
        else{
          this.listQuery.startTime = this.tempData.dateData[0];
          this.listQuery.endTime = this.tempData.dateData[1];
        }
        this.listQuery.telephone = this.tempData.telephone;
        this.listQuery.pageNum = 1;
        this.getList();
      },
      handleSortChange(column) {
        // console.log(column);
        this.listQuery.order = column.order === 'descending' ? 'DESC' : 'ASC';
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
        this.listQuery.pageNum = val;
        this.getList();
      },
      handleFilter() {
        //查询事件
        this.listQuery.pageNum = 1
        this.getList();
      },
      getIndex($index) {
        //表格序号
        return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1
      }
    }
  }
</script>
