import Vue from 'vue'
import Router from 'vue-router'
// in development env not use Lazy Loading,because Lazy Loading too many pages will cause webpack hot update too slow.so only in production use Lazy Loading
/* layout */
import Layout from '../views/layout/Layout'

const _import = require('./_import_' + process.env.NODE_ENV)
Vue.use(Router)
export const constantRouterMap = [
  {path: '/login', component: _import('login/index'), hidden: true},
  {path: '/404', component: _import('404'), hidden: true},
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: '首页',
    hidden: true,
    children: [{
      path: 'dashboard', component: _import('dashboard/index')
    }]
  }
] 
export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({y: 0}),
  routes: constantRouterMap
})
export const asyncRouterMap = [
  {
    path: '/App',
    component: Layout,
    redirect: '/App/User',
    name: '系统管理',
    meta: {title: '系统管理', icon: 'manage'},
    children: [
      {
        path: 'User',
        name: '用户管理',
        component: _import('App/User'),
        meta: {title: '用户管理', icon: 'user'},
        menu: 'User'
      },
      {
        path: 'Role',
        name: '权限管理',
        component: _import('App/Role'),
        meta: {title: '权限管理', icon: 'permissions'},
        menu: 'Role'
      },
      {
        path: 'UserLog',
        name: '系统操作日志',
        component: _import('App/UserLog'),
        meta: {title: '系统操作日志', icon: 'log'},
        menu: 'UserLog'
      },
    ]
  },
  {
    path: '/Datadict',
    component: Layout,
    redirect: '/Datadict/PileType',
    name: '基础数据字典',
    meta: {title: '基础数据字典', icon: 'tree'},
    children: [
      {
        path: 'BatteryType',
        name: '电池类型代码',
        component: _import('Datadict/BatteryType'),
        meta: {title: '电池类型代码', icon: 'batteryType'},
        menu: 'BatteryType'
      },
      {
        path: 'PileState',
        name: '电桩状态代码',
        component: _import('Datadict/PileState'),
        meta: {title: '电桩状态代码', icon: 'pileState'},
        menu: 'PileState'
      },
      {
        path: 'PileType',
        name: '电桩类型代码',
        component: _import('Datadict/PileType'),
        meta: {title: '电桩类型代码', icon: 'pileType'},
        menu: 'BatteryType'
      },
      {
        path: 'Area',
        name: '区域代码',
        component: _import('Datadict/Area'),
        meta: {title: '区域代码', icon: 'Area'},
        menu: 'BatteryType'
      },
    ]
  },
  {
    path: '/Operation',
    component: Layout,
    redirect: '/Operation/AppAlipaytrade',
    name: '综合运维服务',
    meta: {title: '综合运维服务', icon: 'eye'},
    children: [
      {
        path: 'AppAlipaytrade',
        name: '支付宝充值订单',
        component: _import('Operation/AppAlipaytrade'),
        meta: {title: '支付宝充值订单', icon: 'alipay'},
        menu: 'AppAlipaytrade'
      },
      {
        path: 'ChargeQuery',
        name: '充电查询',
        component: _import('Operation/ChargeQuery'),
        meta: {title: '充电查询', icon: 'ChargeQuery'},
        menu: 'ChargeQuery'
      },
      {  
        path: 'PileOff',
        name: '故障设备',
        component: _import('Operation/PileOff'),
        meta: {title: '故障设备', icon: 'PileOff'},
        menu: 'PileOff'
      },
      {
        path: 'chargeCount',
        name: '充电统计',
        component: _import('App/Role'),
        meta: {title: '充电统计', icon: 'example'},
        menu: 'chargeCount'
      },
      {
        path: 'RateType',
        name: '费率标准设置',
        component: _import('Operation/RateType'),
        meta: {title: '费率标准设置', icon: 'RateType'},
        menu: 'RateType'
      },
      {
        path: 'VChargingProc',
        name: '实时充电数据监测',
        component: _import('App/Role'),
        meta: {title: '实时充电数据监测', icon: 'example'},
        menu: 'VChargingProc'
      },
      {
        path: 'AppEmptycards',
        name: 'App卡库管理',
        component: _import('Operation/AppEmptycards'),
        meta: {title: 'App卡库管理', icon: 'appEmptycards'},
        menu: 'AppEmptycards'
      },
      {
        path: 'pileservicedata',
        name: '充电桩运行数据',
        component: _import('App/Role'),
        meta: {title: '充电桩运行数据', icon: 'example'},
        menu: 'pileservicedata'
      },
      {
        path: 'Chargingstation',
        name: '充电站管理',
        component: _import('Operation/Chargingstation'),
        meta: {title: '充电站管理', icon: 'pileStation'},
        menu: 'Chargingstation'
      },
      {
        path: 'Chargingpile',
        name: '充电桩管理',
        component: _import('Operation/Chargingpile'),
        meta: {title: '充电桩管理', icon: 'pileType'},
        menu: 'Chargingpile'
      },
      {
        path: 'Customer',
        name: '充电卡管理',
        component: _import('Operation/Customer'),
        meta: {title: '充电卡管理', icon: 'Customer'},
        menu: 'Customer'
      },
      {
        path: 'service',
        name: '综合运行数据',
        component: _import('App/Role'),
        meta: {title: '综合运行数据', icon: 'example'},
        menu: 'service'
      }
    ]
  },
    {
    path: '/SocketData',
    component: Layout,
    redirect: '/SocketData/PileTransStep3',
    name: '通讯数据监控',
    meta: {title: '通讯数据监控', icon: 'table'},
    children: [
      {
        path: 'PileTransStep3',
        name: '充电电能数据',
        component: _import('SocketData/PileTransStep'),
        meta: {title: '充电电能数据', icon: 'PileTransStep'},
        menu: 'PileTransStep3'
      },
      {
        path: 'SocketDtl',
        name: '网关通讯',
        component: _import('SocketData/SocketDtl'),
        meta: {title: '网关通讯', icon: 'SocketDtl'},
        menu: 'SocketDtl'
      },
      {
        path: 'PileTrans',
        name: '充电交易流水',
        component: _import('SocketData/PileTrans'),
        meta: {title: '充电交易流水', icon: 'PileTrans'},
        menu: 'PileTrans'
      },
      {
        path: 'VCProc',
        name: '充电过程数据',
        component: _import('SocketData/PileChargingProc'),
        meta: {title: '充电过程数据', icon: 'VCProc'},
        menu: 'VCProc'
      },
    ]
  },
  {path: '*', redirect: '/404', hidden: true}
]
