import Pagination from './Pagination';
import SelectUser from './SelectUser';

import Vue from 'vue';

function registerCommonComponents(){
    Vue.component('Pagination', Pagination);
    Vue.component('SelectUser', SelectUser);
}

export {
    registerCommonComponents
}