<template>
  <div>
    <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick">
      <el-tab-pane label="充点卡信息管理"  name="first">
        <cardService></cardService>
      </el-tab-pane>
      <el-tab-pane label="充值记录查询"  name="second">
        <cardDeposit></cardDeposit>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
  export default {
    components: {
       cardService: () => import("./CustomerCardService.vue"),
       cardDeposit: () => import("./CustomerCardDeposit.vue")
    },
    data() {
      return {
        activeName: 'first'
      };
    },
    methods: {
      handleClick(tab, event) {
        // console.log(tab, event);
      }
    }
  };
</script>
