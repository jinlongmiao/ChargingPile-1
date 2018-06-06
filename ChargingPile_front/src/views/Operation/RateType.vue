<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form label-width="80px" :inline="true">
        <el-form-item label="标准版本">
          <el-input v-model="listQuery.type_code"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleFilter">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit highlight-current-row  @sort-change="handleSortChange"v-if="hasPerm('Public:read')">
      <el-table-column align="center" label="序号" min-width="33">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="费率标准版本" sortable="custom" prop="type_code" min-width="60"></el-table-column>
      <el-table-column align="center" label="开始执行日期" prop="start_date"></el-table-column>
      <el-table-column align="center" label="时段1" prop="time1" min-width="80"></el-table-column>
      <el-table-column align="center" label="费率1" prop="rate1" min-width="50"></el-table-column>
      <el-table-column align="center" label="时段2" prop="time2" min-width="80"></el-table-column>
      <el-table-column align="center" label="费率2" prop="rate2" min-width="50"></el-table-column>
      <el-table-column align="center" label="时段3" prop="time3" min-width="80"></el-table-column>
      <el-table-column align="center" label="费率3" prop="rate3" min-width="50"></el-table-column>
      <el-table-column align="center" label="时段4" prop="time4" min-width="80"></el-table-column>
      <el-table-column align="center" label="费率4" prop="rate4" min-width="50"></el-table-column>
      <el-table-column align="center" label="时段5" prop="time5" min-width="80"></el-table-column>
      <el-table-column align="center" label="费率5" prop="rate5" min-width="50"></el-table-column>
      <el-table-column align="center" label="时段6" prop="time6" min-width="80"></el-table-column>
      <el-table-column align="center" label="费率6" prop="rate6" min-width="50"></el-table-column>
      <el-table-column align="center" label="基础费率" prop="rate_base" min-width="50"></el-table-column>
      <el-table-column align="center" label="状态" prop="status" min-width="50"></el-table-column>
      <el-table-column align="center" label="操作"  min-width="65">
        <template slot-scope="scope">
         <el-button type="danger" size="mini" v-if = "scope.row.status === 1"  @click="changeStatus(scope.$index)">锁定</el-button>
         <el-button type="primary" size="mini" v-else @click="changeStatus(scope.$index)">启用</el-button>
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

  export default {
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 20,//每页条数
          type_code: '',
          order: 'ASC'
        },
        tempData: {
          type_code: '', 
          start_date: '',
          time1: '',
          rate1: '',
          time2: '',
          rate2: '',
          time3: '',
          rate3: '',
          time4: '',
          rate4: '',
          time5: '',
          rate5: '',
          time6: '',
          rate6: '',
          rate_base: '',
          status: ''
        },
        options: []
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
          url: "/RateType/list",
          method: "get",
          params: this.listQuery
        }).then(data => {
          console.log(data);
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
        })
      },
      changeStatus(val){
        // console.log(val);
        let _vue = this;
        this.$confirm('确认修改该状态?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let changeData = _vue.list[val];
          // console.log("DATA");
          // console.log(changeData);
          _vue.api({
            url: "/RateType/changeStatus",
            method: "post",
            data: changeData
          }).then(() => {
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("修改失败")
          })
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
