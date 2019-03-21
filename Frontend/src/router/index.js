import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Cooperator from '@/components/Cooperator'
import coopMain from '@/components/coopMain'
import Student from '@/components/Student'
import Employer from '@/components/Employer'


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
    },
    {
      path: '/students',
      name: 'Student',
      component: Student
    },
    {
      path: '/employers',
      name: 'Employer',
      component: Employer
    }
  ]
})