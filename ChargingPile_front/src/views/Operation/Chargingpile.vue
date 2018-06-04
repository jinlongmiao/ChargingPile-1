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
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit highlight-current-row @sort-change="handleSortChange" v-if="hasPerm('Public:read')">
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="所属充电站" prop="stationName" sortable="custom" ></el-table-column>
      <el-table-column align="center" label="绑定网关" prop="gateId" sortable="custom"></el-table-column>
      <el-table-column align="center" label="桩编码" prop="pileId" width="100"></el-table-column>
      <el-table-column align="center" label="类型码" prop="pileTypeCode" width="170"></el-table-column>
      <el-table-column align="center" label="运行状态" prop="STATUS" width="170"></el-table-column>
      <el-table-column align="center" label="管理" width="220">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" v-if="hasPerm('Public:edit')" @click="showUpdate(scope.$index)">修改</el-button>
          <el-button type="danger" icon="delete" v-if="hasPerm('Public:delete')" @click="removeChargingPile(scope.$index)">删除
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
        <el-form-item label="所属充电站"  required>
          <el-select v-model="tempData.stationId" placeholder="请选择">
            <el-option
              v-for="item in stationNames"
              :key="item.id"
              :label="item.stationName"
              :value="item.stationId">
            </el-option>
          </el-select>
        </el-form-item>
         <el-form-item label="绑定网关" required>
          <el-input type="text" v-model="tempData.gateId">
          </el-input>
        </el-form-item>
        <el-form-item label="桩编码" required>
          <el-input type="text" v-model="tempData.pileId">
          </el-input>
        </el-form-item>
        <el-form-item label="类型" required>
          <el-select v-model="tempData.pileTypeCode" placeholder="请选择"  style='width: 320px;'>
            <el-option
              v-for="item in pileTypes"
              :key="item.id"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createChargingPile">创 建</el-button>
        <el-button type="primary" v-else @click="updateChargingPile">修 改</el-button>
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
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 20,//每页条数
          orderByStation: '',
          orderByGate: ''
        },
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          create: '新建电桩'
        },
        tempData: {
          id: '',
          stationName: '',
          gateId: '',
          pileId: '',
          pileTypeCode: '',
          stationId: '',
          STATUS:''
        },
        pileTypes: [],
        stationNames: []
      }
    },
    created() {
      this.getList();
      this.getAllPileType();
      this.getStationInfo();
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
        this.api({
          url: "/ChargingPile/list",
          method: "get",
          params: this.listQuery
        }).then(data => {
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
        })
      },
      getAllPileType() {
        this.api({
          url: "/ChargingPile/getAllPileType",
          method: "get"
        }).then(data => {
          this.pileTypes = data.list;
          // console.log(this.pileTypes);
        })
      },
      getStationInfo(){
        this.api({
          url: "/ChargingPile/getStationInfo",
          method: "get"
        }).then(data => {
          this.stationNames = data.list;
          // console.log(this.stationNames);
        })
      },
      handleSortChange(column) {
        // console.log(column);
        if ( column.prop === 'stationName' ) {
          this.listQuery.orderByStation = column.order === 'descending' ? 'DESC' : 'ASC';
          this.listQuery.orderByGate = '';
        }
        else{
          this.listQuery.orderByGate = column.order === 'descending' ? 'DESC' : 'ASC';
          this.listQuery.orderByStation = '';
        }
        // console.log(this.listQuery);
        this.listQuery.pageNum = 1;
        this.getList();
      },
      createChargingPile() {
        // console.log(this.tempData);
        //添加电桩类型
        this.api({
          url: "/ChargingPile/addChargingPile",
          method: "post",
          data: this.tempData
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
      removeChargingPile($index) {
        let _vue = this;
        this.$confirm('确定删除此电桩类型?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let delData = _vue.list[$index];
          // console.log("待删除的DATA");
          // console.log(delData);
          _vue.api({
            url: "/ChargingPile/deleteChargingPile",
            method: "post",
            data: delData
          }).then(() => {
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("删除失败")
          })
        })
      },
      updateChargingPile() {
        //修改电桩类型
        let _vue = this;
        // console.log('ChargingPile.vue/ChargingPile/updateChargingPile');
        // console.log(this.tempData);
        this.api({
          url: "/ChargingPile/updateChargingPile",
          method: "post",
          data: this.tempData
        }).then(() => {
          let msg = "修改成功";
          this.dialogFormVisible = false
          // if (this.userId === this.tempData.userId) {
            msg = '修改成功,部分信息重新登录后生效'
          // }
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
        this.tempData.id = "";
        this.tempData.stationName = "";
        this.tempData.gateId = "";
        this.tempData.pileId = "";
        this.tempData.pileTypeCode = "";
        this.tempData.stationId = "";
        this.dialogStatus = "create";
        this.dialogFormVisible = true;
      },
      showUpdate($index) {
        console.log(this.list);
        console.log($index);
        let ChargingPile = this.list[$index];
        this.tempData = ChargingPile;
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
