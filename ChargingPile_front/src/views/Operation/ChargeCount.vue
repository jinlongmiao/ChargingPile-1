<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form  :inline="true">
        <el-form-item label="充电站">
          <el-select v-model="listQuery.stationId" placeholder="请选择">
            <el-option
              v-for="item in stationNames"
              :key="item.id"
              :label="item.stationName"
              :value="item.stationId">
            </el-option>
          </el-select>
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
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit  highlight-current-row  @sort-change="handleSortChange"v-if="hasPerm('Public:read')"  show-summary  :summary-method="getSummaries" max-height="600" >
      <el-table-column align="left" label="充电站" prop="stationName" min-width="80" ></el-table-column>
      <el-table-column align="center" label="充电桩" prop="pileId" min-width="60"></el-table-column>
      <el-table-column align="center" label="充电次数" prop="transNumbers" min-width="80"></el-table-column>
      <el-table-column align="center" label="充电时长(min)" prop="transDuration" min-width="30"></el-table-column>
      <el-table-column align="center" label="充电量(度)" prop="wast" min-width="80"></el-table-column>
      <el-table-column align="center" label="充电金额(元)" prop="money" min-width="40"></el-table-column>
    </el-table>
  </div>
</template>
<script>

  export default {
    data() {
      return {
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          startTime: '',
          endTime: '',
          stationId: ''
        },
        tempData: {
          stationName: '',
          pileId: '',
          transNumbers: '',
          transDuration: '',
          wast: '',
          money: ''
        },
        stationNames: [],
        dateInfo: {}
      }
    },
    created() {
      this.getList();
      this.getStationInfo();
    },
    methods: {
      getList() {
        //查询列表
        this.listLoading = true;
        console.log(this.listQuery);
        this.api({
          url: "ChargeCount/list",
          method: "get",
          params: this.listQuery
        }).then(data => {
          console.log(data);
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
        })
      },
      getStationInfo(){
        this.api({
          url: "/ChargingPile/getStationInfo",
          method: "get"
        }).then(data => {
          this.stationNames = data.list;
          console.log(this.stationNames);
        })
      },
      getSummaries(param) {
        const { columns, data } = param;
        const sums = [];
        columns.forEach((column, index) => {
          if (index === 0) {
            sums[index] = '总和';
            return;
          }
          const values = data.map(item => Number(item[column.property]));
          if (!values.every(value => isNaN(value))) {
            sums[index] = values.reduce((prev, curr) => {
              const value = Number(curr);
              if (!isNaN(value)) {
                return prev + curr;
              } else {
                return prev;
              }
            }, 0);
            sums[index] += '';
          } else {
            sums[index] = 'N/A';
          }

        });
          sums[1] = '';
          sums[2] += '元';
          sums[3] = (sums[3] / 60).toFixed(2)  + '小时';
          sums[4] += '度';
          sums[5] = (sums[5] / 1).toFixed(2)  + '元';
          console.log(sums);
        return sums;
      },
       handleSortChange(val) {
        // 排序
        // console.log(this.listQuery);
        this.handleFilter();
      },
      // handleSizeChange(val) {
      //   //改变每页数量
      //   this.listQuery.pageRow = val;
      //   this.handleFilter();
      // },
      // handleCurrentChange(val) {
      //   //改变页码
      //   this.listQuery.pageNum = val;
      //   this.getList();
      // },
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
