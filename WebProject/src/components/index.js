import ConfigAdd from './ConfigAdd.vue';
import ConfigTable from './ConfigTable';
import LoginDialog from './LoginDialog';
import ConfigTableSimple from './ConfigTableSimple';
import ConfigTableSimpleFilter from './ConfigTableSimpleFilter';


import Vue from 'vue';

function registerComponents(){
    
    Vue.component('ConfigAdd', ConfigAdd);
    Vue.component('ConfigTable', ConfigTable);
    Vue.component('LoginDialog', LoginDialog);
    Vue.component('ConfigTableSimple', ConfigTableSimple);
    Vue.component('ConfigTableSimpleFilter', ConfigTableSimpleFilter);
    
}

export {
    registerComponents
}