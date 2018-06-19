<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form  class="small-space" label-width="80px" :inline="true">
        <el-form-item label="日期选择" >
          <el-date-picker v-model="dateData" type="daterange" value-format="yyyy-MM-dd" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期">
          </el-date-picker>    
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="listQuery.phone">
          </el-input>
        </el-form-item>
        <el-form-item label="卡号">
          <el-input v-model="listQuery.chargeCardId">
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onFind">查询</el-button>
        </el-form-item>
      </el-form>
      <el-button type="success" @click="showCreate">添加</el-button>
      <el-button type="primary" style='margin-bottom:30px;'>金额: {{sumMoney}}</el-button>

    </div>

    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border highlight-current-row  @sort-change="handleSortChange" v-if="hasPerm('Public:read')" >
      <el-table-column align="center" label="序号" width="80" prop="id" sortable="custom"></el-table-column>
      <el-table-column align="center" label="充电卡ID" prop="chargeCardId" min-width="200"  ></el-table-column>
      <el-table-column align="center" label="卡状态" prop="stateName" ></el-table-column>
      <el-table-column align="center" label="姓名" prop="name" ></el-table-column>
      <el-table-column align="center" label="手机号" prop="phone" min-width="150"></el-table-column>
      <el-table-column align="center" label="卡余额" prop="chargeCardMoney" min-width="100"></el-table-column>
      <el-table-column align="center" label="状态更新" prop="stateUpdateTime" min-width="200"></el-table-column>
      <el-table-column align="center" label="建档日期" prop="createTime" min-width="200"></el-table-column>
      <el-table-column align="center" label="操作员" prop="createUser" ></el-table-column>
      <el-table-column align="center" label="状态" prop="status" min-width="80"></el-table-column>
      <el-table-column align="center" label="操作"  min-width="180">
         <template slot-scope="scope">
         <el-button type="text" size="mini"  @click="rechargeCustomer(scope.$index)">充值</el-button>
         <el-button type="text" size="mini" @click="lockCustomer(scope.$index)">注销</el-button>
         <el-button type="text" size="mini" @click="showUpdate(scope.$index)">修改</el-button>
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
      <el-form class="small-space" :model="tempData" label-position="left" label-width="80px" style='width: 550px; margin-left:50px;'>
        <el-form-item label="姓名" required>
          <el-input type="text" v-model="tempData.name" style='width: 200px'></el-input>
        </el-form-item>
        <el-form-item label="电话" required>
          <el-input type="text" v-model="tempData.phone" style='width: 200px'></el-input>
        </el-form-item>
        <el-form-item label="充点卡ID" required>
          <el-input type="text" v-model="tempData.chargeCardId" style='width: 200px'></el-input>
          <span style="font-size: 12px">支持8位或20位卡号录入（8位自动补全20位）</span>
        </el-form-item>
        <el-form-item label="备注" >
          <el-input type="text" v-model="tempData.mem"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createCustomer">创 建</el-button>
        <el-button type="primary" v-else @click="updateCustomer">修 改</el-button>
      </div>
    </el-dialog>

    <el-dialog title="充值"  :visible.sync="dialogChargeVisible">
      <!-- <span>{{money.id}}</span> -->
      <el-form class="small-space" :model="money" label-position="left" label-width="80px" style='width: 550px; margin-left:50px;'>
        <el-form-item label="金额" required>
          <el-input type="text" v-model="money.num" style='width: 200px'></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="chargeNum">充值</el-button>
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
        sumMoney: 0,
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 10,//每页条数
          order: 'DESC',
          startTime: '',
          endTime: '',
          phone: '',
          chargeCardId: ''
        },
        dateData: [],
        tempData: {
          id: '',
          chargeCardId: '',
          stateName: '',
          name: '',
          phone: '',
          chargeCardMoney: '',
          stateUpdateTime: '',
          createTime: '',
          createUser: '',
          status: '',
          mem: ''
        },
        textMap: {
          create: '新建',
          update: '修改'
        },
        dialogStatus: 'create',
        dialogFormVisible: false,
        dialogChargeVisible: false,
        money: {}
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
        // console.log(this.listQuery);
        this.api({
          url: "/Customer/list",
          method: "get",
          params: this.listQuery
        }).then(data => {
          console.log(data);
          this.listLoading = false;
          this.sumMoney = data.sumMoney;
          this.list = data.list;
          this.totalCount = data.totalCount;
          // this.getSumMoney();
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
        if(this.dateData === null){
          this.listQuery.startTime = '';
          this.listQuery.endTime = '';
        }
        else{
          this.listQuery.startTime = this.dateData[0];
          this.listQuery.endTime = this.dateData[1];
        }
        this.listQuery.pageNum = 1;
        console.log(this.listQuery);
        this.getList();
      },
      showCreate() {
        //显示新增对话框
        for(var Key in this.tempData){
          if( this.tempData[Key] ){
            this.tempData[Key] = null;
          }; 
        }
        console.log(this.userId);
        this.dialogStatus = "create";
        this.dialogFormVisible = true;
      },
      showUpdate($index) {
        console.log(this.role);
        this.tempData = this.list[$index];
        this.dialogStatus = "update";
        this.dialogFormVisible = true;
      },
      createCustomer() {
        this.tempData.userId = this.userId;
        console.log(this.tempData);
        if( this.tempData.chargeCardId.length === 8 ){
          this.tempData.chargeCardId = this.tempData.chargeCardId + '000000000000';
        }
        if( this.tempData.chargeCardId.length === 20 ){
          this.api({
            url: "/Customer/add",
            method: "post",
            data: this.tempData
          }).then(() => {
            this.getList();
            this.dialogFormVisible = false
          })
        }
        
      },
      updateCustomer(){
        this.api({
          url: "/Customer/update",
          method: "post",
          data: this.tempData
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
      lockCustomer($index) {
        this.tempData = this.list[$index];
        console.log(this.tempData);
         if(this.tempData.status === 1){
          let _vue = this;
          this.$confirm('确认修改该状态?', '提示', {
            confirmButtonText: '确定',
            showCancelButton: false,
            type: 'warning'
          }).then(() => {
            let changeData = _vue.list[$index];
            console.log("DATA");
            console.log(changeData);
            _vue.api({
              url: "/Customer/lockCustomer",
              method: "post",
              data: changeData
            }).then(() => {
              _vue.getList()
            }).catch(() => {
              _vue.$message.error("修改失败")
            })
          })

         }
         else{
          this.$message({
            message: '已经注销',
            type: 'warning'
          });
         }
      },
      rechargeCustomer($index){
        this.dialogChargeVisible = true;
        this.money.id = this.list[$index].id;
      },
      chargeNum(){
        console.log(this.money);
         this.api({
          url: "/Customer/chargeNum",
          method: "post",
          data: this.money
        }).then(() => {
          this.getList();
          this.dialogChargeVisible = false
        })
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
