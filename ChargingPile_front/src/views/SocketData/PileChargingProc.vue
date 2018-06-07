<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form  :inline="true">
        <el-form-item label="设备号">
          <el-input v-model="listQuery.pileId"></el-input>
        </el-form-item>
        <el-form-item label="流水号">
          <el-input v-model="listQuery.tranId"></el-input>
        </el-form-item>
         <el-form-item label="日期">
           <el-date-picker v-model="dateInfo.dateData" type="daterange" value-format="yyyy-MM-dd" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期">
          </el-date-picker>  
        </el-form-item> 
        <el-form-item>
          <el-button type="primary" @click="handleFilter">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit  highlight-current-row  @sort-change="handleSortChange"v-if="hasPerm('Public:read')">

      <el-table-column align="left" label="序号" prop="id" sortable="custom" width="80" fixed></el-table-column>
      <el-table-column align="center" label="流水时间" prop="createTime" min-width="60"></el-table-column>
      <el-table-column align="center" label="交易流水" prop="tranId" min-width="30"></el-table-column>
      <el-table-column align="center" label="网关号" prop="gateId" min-width="80"></el-table-column>
      <el-table-column align="center" label="设备号" prop="pileId" min-width="80"></el-table-column>
      <el-table-column align="center" label="充电口" prop="port" min-width="30"></el-table-column>
      <el-table-column align="center" label="充电时长" prop="chargingTime" min-width="40"></el-table-column>
      <el-table-column align="center" label="充电阶段" prop="step" min-width="30"></el-table-column>
      <el-table-column align="center" label="充电百分比"  min-width="30">
        <template slot-scope="scope">
          <span>{{ scope.row.percent }}%</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="输出电压" prop="outV" min-width="30"></el-table-column>
      <el-table-column align="center" label="输出电流" prop="outA" min-width="30"></el-table-column>
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
          pileId: '',
          chargeCardId: '',
          order: 'DESC',
          startTime: '',
          endTime: ''
        },
        tempData: {
          id: '',
          createTime: '',
          tranId: '',
          gateId: '',
          pileId: '',
          port:'',
          chargingTime: '',
          step:'',
          percent:'',
          outV: '',
          outA: ''
        },
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
        this.api({
          url: "/PileChargingProc/list",
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
