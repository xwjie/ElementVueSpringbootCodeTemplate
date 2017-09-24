import Vue from 'vue';
import Vuex from 'vuex';
import config from './modules/config';


Vue.use(Vuex);

const store = new Vuex.Store({
    modules: {
      config,
  },
  
});

export default store;
