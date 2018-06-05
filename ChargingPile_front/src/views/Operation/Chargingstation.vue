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
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit highlight-current-row v-if="hasPerm('Public:read')">
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="城市" prop="city"></el-table-column>
      <el-table-column align="center" label="站点编号" prop="stationId"></el-table-column>
      <el-table-column align="center" label="站点名称" prop="stationName" width="100"></el-table-column>
      <el-table-column align="center" label="地址" prop="address" width="170"></el-table-column>
      <el-table-column align="center" label="经度" prop="lat" width="170"></el-table-column>
      <el-table-column align="center" label="纬度" prop="lng" width="170"></el-table-column>
      <el-table-column align="center" label="桩数" prop="capacity" width="70"></el-table-column>
      <el-table-column align="center" label="网关ID" prop="gateId" width="170"></el-table-column>
      <el-table-column align="center" label="运行日期" prop="openDate" width="170"></el-table-column>
      <el-table-column align="center" label="状态" prop="status" width="70"></el-table-column>
      <el-table-column align="center" label="管理" width="220">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" v-if="hasPerm('Public:edit')" @click="showUpdate(scope.$index)">修改</el-button>
          <el-button type="danger" icon="delete" v-if="hasPerm('Public:delete')" @click="removeChargingStation(scope.$index)">删除
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
        <el-form-item label="城市" >
          <el-select v-model="tempData.city" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.city"
              :label="item.city"
              :value="item.city">
            </el-option>
          </el-select>
        </el-form-item>
         <el-form-item label="站点编号" >
          <el-input type="text" v-model="tempData.stationId">
          </el-input>
        </el-form-item>
        <el-form-item label="站点名称" >
          <el-input type="text" v-model="tempData.stationName">
          </el-input>
        </el-form-item>
        <el-form-item label="网关ID" >
          <el-input type="text" v-model="tempData.gateId">
          </el-input>
        </el-form-item>
        <el-form-item label="地址" >
          <el-input type="text" v-model="tempData.address">
          </el-input>
        </el-form-item>
        <el-form-item label="桩数" >
          <el-input type="text" v-model="tempData.capacity">
          </el-input>
        </el-form-item>
        <el-form-item label="经度" >
          <el-input type="text" v-model="tempData.lat">
          </el-input>
        </el-form-item>
        <el-form-item label="纬度" >
          <el-input type="text" v-model="tempData.lng">
          </el-input>
        </el-form-item>
        <el-form-item label="运行日期" v-if="dialogStatus=='create'">
          <el-date-picker
            v-model="tempData.openDate"
            type="datetime"
            placeholder="选择日期时间"
            format="yyyy 年 MM 月 dd 日"
            value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createChargingStation">创 建</el-button>
        <el-button type="primary" v-else @click="updateChargingStation">修 改</el-button>
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
        },
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          create: '新建'
        },
        tempData: {
          id: '',
          city: '',
          stationId: '',
          stationName: '',
          address: '',
          lat: '',
          lng: '',
          openDate: '',
          capacity: '',
          createTime: '',
          gateId: '',
          status:'',
          createUser: ''
        },
        options: []
      }
    },
    created() {
      this.getList();
      this.tempData.createUser = this.nickname;
      // console.log(this);
    },
    computed: {
      ...mapGetters([
        'userId',
        'nickname'
      ])
    },
    methods: {
      getList() {
        //查询列表
        this.listLoading = true;
        this.api({
          url: "/ChargingStation/list",
          method: "get",
          params: this.listQuery
        }).then(data => {
          // console.log(data);
          this.listLoading = false;
          this.list = data.list;
          for (var i = data.list.length - 1; i >= 0; i--) {
            var temp = {};
            temp.city = data.list[i].city;
            if( JSON.stringify(this.options).indexOf(JSON.stringify(temp)) <= 0 ){
                this.options.push(temp);
            }
          }
          this.totalCount = data.totalCount;
        })
      },
      
      createChargingStation() {
        this.tempData.createUser = this.nickname;
        console.log(this.tempData);
        //添加
        this.api({
          url: "/ChargingStation/add",
          method: "post",
          data: this.tempData
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
      removeChargingStation($index) {
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
            url: "/ChargingStation/delete",
            method: "post",
            data: delData
          }).then(() => {
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("删除失败")
          })
        })
      },
      updateChargingStation() {
        //修改
        let _vue = this;
        this.api({
          url: "/ChargingStation/update",
          method: "post",
          data: this.tempData
        }).then(() => {
          let msg = "修改成功";
          this.dialogFormVisible = false
          msg = '修改成功,部分信息重新登录后生效'
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
        for(var Key in this.tempData){
          if( this.tempData[Key] ){
            this.tempData[Key] = null;
          };
        }
        this.dialogStatus = "create";
        this.dialogFormVisible = true;
      },
      showUpdate($index) {
        let ChargingStation = this.list[$index];
        this.tempData = util.deepcopy(ChargingStation);
        // this.tempData = ChargingStation;
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
