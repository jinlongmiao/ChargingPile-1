import {default as api} from '../../utils/api'
import store from '../../store'
import router from '../../router'


const info = {
  state: {
     users: [],
  },
  mutations: {
    SET_USER: (state, info) => {
      state.users = info.users;
    },
    RESET_USER: (state) => {
      state.users = [];
    }
  },
  // actions: { 
  //    GetInfo({commit, state}) {
  //     return new Promise((resolve, reject) => {
  //       api({
  //         url: '/User/list',
  //         method: 'get'
  //       }).then(data => {
  //         console.log(data);
  //         // commit('SET_USER', data.list);
  //         // resolve(data)
  //       }).catch(error => {
  //         reject(error)
  //       })
  //     })
  //   },
  // }
}
export default info
