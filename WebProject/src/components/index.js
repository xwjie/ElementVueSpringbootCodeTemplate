import ConfigAdd from './ConfigAdd.vue';
import ConfigTable from './table/ConfigTable';
import LoginDialog from './LoginDialog';
import ConfigTableSimple from './table/ConfigTableSimple';
import ConfigTableSimpleFilter from './table/ConfigTableSimpleFilter';

//tree
import SimpleTree from './tree/SimpleTree';

import Vue from 'vue';

function registerComponents(){
    
    Vue.component('ConfigAdd', ConfigAdd);
    Vue.component('ConfigTable', ConfigTable);
    Vue.component('LoginDialog', LoginDialog);
    Vue.component('ConfigTableSimple', ConfigTableSimple);
    Vue.component('ConfigTableSimpleFilter', ConfigTableSimpleFilter);
    
    //tree
    Vue.component('SimpleTree', SimpleTree);
}

export {
    registerComponents
}