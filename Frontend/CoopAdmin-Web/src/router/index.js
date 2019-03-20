import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Cooperator from '@/components/Cooperator'
import coopMain from '@/components/coopMain'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/coopadmin',
      name: 'Cooperator',
      component: Cooperator
    },
    {
      path: '/coopMain',
      name: 'coopMain',
      component: coopMain
    }
  ]
})
