<template>
  <div class="app-container">
    <div class="station">
      <el-button>接入充电站: {{list.length}}</el-button>
      <el-button>接入充电桩: {{pilesNum.sum}}  在线: {{pilesNum.onLine}}  离线: {{pilesNum.offLine}}</el-button>
    </div>

    <div class="station">
      <el-button type="info" @click="showPiles(item)"  plain v-for="item in list"  :key="item.stationId">
        {{item.stationName}}
        <span class="badge">{{item.piles.length}}</span>
      </el-button>
    </div>
    <div class="piles">
      <div  class="pile" v-for="pile in piles" :key="pile.pileId">
        <img v-if="pile.imgSrc === 'ACDisconnect'" :src="ACDisconnect" 
        :alt="pile.imgSrc">
        <img v-if="pile.imgSrc === 'ACNormal'" :src="ACNormal" 
        :alt="pile.imgSrc">
        <img v-if="pile.imgSrc === 'DCDisconnect'" :src="DCDisconnect" 
        :alt="pile.imgSrc">
        <img v-if="pile.imgSrc === 'DCNormal'" :src="DCNormal" 
        :alt="pile.imgSrc">
        <img v-if="pile.imgSrc === 'ACOffline'" :src="ACOffline" 
        :alt="pile.imgSrc">
        <img v-if="pile.imgSrc === 'DCOffline'" :src="DCOffline" 
        :alt="pile.imgSrc">
        <el-tooltip class="item" effect="dark" :content="pile.toolTip" placement="top-start">
          <el-button type="success" round size="small" >
            {{pile.pileId.slice(-4)}}
          </el-button>
        </el-tooltip>
      </div>
    </div>
  </div>
</template>

<script>
  import ACDisconnect from '@/assets/piles/ac_disconnect.png'
  import ACNormal from '@/assets/piles/ac_normal.png'
  import DCDisconnect from '@/assets/piles/dc_disconnect.png'
  import DCNormal from '@/assets/piles/dc_normal.png'
  import ACOffline from '@/assets/piles/dc_offline.png'
  import DCOffline from '@/assets/piles/dc_offline.png'
  export default {
    data() {
      return {
        ACDisconnect,
        ACNormal,
        DCDisconnect,
        DCNormal,
        ACOffline,
        DCOffline,
        listQuery: {},
        list: {},
        dialogStatus: 'create',
        dialogFormVisible: false,
        piles: {},
        pilesNum: {
          sum: 0,
          onLine: 0,
          offLine: 0
        }
      }
    },
    created() {
      this.getList();
    },
    methods: {
      getList() {
        //查询列表
        this.api({
          url: "/Board/getPiles",
          method: "get",
          params: this.listQuery
        }).then(data => {
          
          this.changeList(data);
          this.list = data.list;

        })
      },

      changeList(data){
        let list = data.list;
        let pilesNum = 0;
        let pileOff = 0;
        let pileOn = 0;
        let nowTimeMap = Date.parse(new Date()) / 1000 ;

        for(let i = 0; i < list.length; i++){
            pilesNum +=  list[i].piles.length;
            let piles =  list[i].piles;
            for(let j = 0; j < piles.length; j++ ){
              piles[j]['imgSrc'] = piles[j].type;
              let port1 = piles[j].pileStateCode.substr(0,1);
              let port2 = piles[j].pileStateCode.substr(1,2);
              // 大于3min
              if( (nowTimeMap - list[i].stateUpdateTime)/60 > 3 ){
                piles[j].imgSrc += 'Disconnect';
                piles[j]['toolTip'] = "port1: 通讯中断  port2: 通讯中断";
                pileOff++;
              }

              // 通过端口判断是offline(00)还是在线
              else if( piles[j].pileStateCode === '00' ){
                piles[j].imgSrc += 'Offline';
                piles[j]['toolTip'] = "port1: " + this.getPortStatus(port1) + "   port2: " + this.getPortStatus(port2);
                pileOff++;
              }

              else {
                piles[j].imgSrc += 'Normal';
                piles[j]['toolTip'] = "port1: " + this.getPortStatus(port1) + "   port2: " + this.getPortStatus(port2);
                pileOn++;
              }

            }
          }
          this.pilesNum.sum = pilesNum;
          this.pilesNum.onLine = pileOn;
          this.pilesNum.offLine = pileOff;
      },

      showPiles(item){
        console.log(item);
        console.log(this);
        this.piles = item.piles;
      },

      

      getPortStatus(code) {
        let msg = "";
        switch(code){
          case "0":
            msg = "下线";
            break;
          case "1":
            msg = "空闲";
            break;
          case "2":
            msg = "预约";
            break;
          case "3":
            msg = "充电中";
            break;
          case "4":
            msg = "充电结束";
            break;
          case "5":
            msg = "故障";
            break;
          case "6":
            msg = "远程控制充电";
            break;
          case "7":
            msg = "设备维护";
            break;
        } 
        return msg;
      }
    }
  }
</script>


<style>
  .staion { 
    margin: 20px;
  }

  .station>button {
    margin: 4px
  }

  .item {
    margin-top: 10px;
  }

  .piles{
    margin: 4px;
  }

  .pile {
    float: left;
    margin: 4px;
    border: 1px solid #ddd;
    border-radius: 4px;
    padding: 5px;
  }

  .pile>img {
    height: 157px;
    display: block;
    margin: 0 auto;
  }

  .pile>button {
    padding: 9px;
  }

  .badge {
    display: inline-block;
    min-width: 10px;
    padding: 3px 7px;
    font-size: 12px;
    font-weight: 700;
    line-height: 1;
    color: #909399;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    background-color: #fff;
    border-radius: 10px;
    position: relative;
    top: -1px;
  }
</style>