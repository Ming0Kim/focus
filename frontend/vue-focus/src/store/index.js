import { createStore } from 'vuex';
import axios from 'axios';

const BASE_URL = 'https://i8a106.p.ssafy.io/api';

export default createStore({
  state: {
    user: null,
  },
  getters: {
    loggedIn(state) {
      return !!state.user;
    },
  },
  mutations: {
    SET_USER_DATA(state, userData) {
      state.user = userData;
      localStorage.setItem('user', JSON.stringify(userData));
      axios.defaults.headers.common[
        'Authorization'
      ] = `Bearer ${userData.token}`;
      console.log(
        'axios headers: ',
        axios.defaults.headers.common['Authorization'],
      );
    },
    LOGOUT(state) {
      state.user = null;
      localStorage.removeItem('user');
      axios.defaults.headers.common['Authorization'] = null;

      // 더 좋은 코드?
      // localStorage.removeItem('user')
      // location.reload()
    },
  },
  actions: {
    login({ commit }, credentials) {
      console.log('LOGIN ACTIONS', credentials);
      return axios
        .post(`${BASE_URL}/auth/login`, credentials)
        .then(({ data }) => {
          console.log('user data is: ', data);
          // commit('SET_USER_DATA', data);
          // console.log('state.user: ', this.state.user);
          // console.log('getters.loggedIn', this.getters.loggedIn);
        });
    },
    logout({ commit }) {
      commit('LOGOUT');
    },
  },
});
