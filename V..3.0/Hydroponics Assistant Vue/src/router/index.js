import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Homeadmin from '@/components/Homeadmin'
import About1 from '@/components/About/About'
import Cloud from '@/components/notifikasi/CloudMassaging'
import ViewCloud from '@/components/notifikasi/ViewCloud'
import Riwayat from '@/components/notifikasi/Riwayat'
import Chart from '@/components/Chart'
import Perangkat from '@/components/Perangkat/Perangkat'
import Signup from '@/components/User/Signup'
import GraphPH from '@/components/Graph/GraphPh'
import GraphEC from '@/components/Graph/GraphEC'
import Graphketing from '@/components/Graph/GraphKeting'
import GraphA from '@/components/Graph/GraphpupukA'
import GraphB from '@/components/Graph/GraphpupukB'
import GraphSuhu from '@/components/Graph/GraphSuhu'
import Signin from '@/components/User/Signin'
import Profile from '@/components/User/Profile'
import Profileid from '@/components/User/Profileid'
import CreateMonitor from '@/components/Pantau/CreateMonitor'
import CreateControl from '@/components/Kendali/CreateControl'
import Controlling from '@/components/Kendali/Controlling'
import Controls from '@/components/Kendali/Controls'
import Monitors from '@/components/Pantau/Monitors'
import Monitor from '@/components/Pantau/Monitor'
import Peramalan from '@/components/Peramalan/Peramalan'
import AuthGuard from './auth-guard'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/homeadmin',
      name: 'HomeAdmin',
      component: Homeadmin,
      beforeEnter: AuthGuard
    },
    {
      path: '/signup',
      name: 'Signup',
      component: Signup
    },
    {
      path: '/signin',
      name: 'Signin',
      component: Signin
    },
    {
      path: '/profile/',
      name: 'Profile',
      component: Profile,
      beforeEnter: AuthGuard
    },
    {
      path: '/profileid/:id',
      name: 'Profileid',
      props: true,
      component: Profileid,
      beforeEnter: AuthGuard
    },
    {
      path: '/createmonitor',
      name: 'CreateMonitor',
      component: CreateMonitor,
      beforeEnter: AuthGuard
    },
    {
      path: '/monitor/:id/',
      name: 'Monitor',
      props: true,
      component: Monitor,
      beforeEnter: AuthGuard
    },
    {
      path: '/monitor',
      name: 'Monitors',
      component: Monitors,
      beforeEnter: AuthGuard
    },
    {
      path: '/Chart/:idk',
      name: 'Chart',
      component: Chart,
      props: true,
      beforeEnter: AuthGuard
    },
    {
      path: '/GraphPh/:idk',
      name: 'GraphPh',
      component: GraphPH,
      props: true,
      beforeEnter: AuthGuard
    },
    {
      path: '/GraphEC/:idk',
      name: 'GraphEC',
      component: GraphEC,
      props: true,
      beforeEnter: AuthGuard
    },
    {
      path: '/GraphSuhu/:idk',
      name: 'GraphSuhu',
      component: GraphSuhu,
      props: true,
      beforeEnter: AuthGuard
    },
    {
      path: '/Graphketing/:idk',
      name: 'Graphketing',
      component: Graphketing,
      props: true,
      beforeEnter: AuthGuard
    },
    {
      path: '/GraphpupukA/:idk',
      name: 'GraphA',
      component: GraphA,
      props: true,
      beforeEnter: AuthGuard
    },
    {
      path: '/GraphpupukB/:idk',
      name: 'GraphB',
      component: GraphB,
      props: true,
      beforeEnter: AuthGuard
    },
    {
      path: '/control',
      name: 'Control',
      component: Controlling,
      beforeEnter: AuthGuard
    },
    {
      path: '/controls/:id',
      props: true,
      name: 'Controls',
      component: Controls
    },
    {
      path: '/createControl/',
      name: 'CreateControl',
      component: CreateControl,
      beforeEnter: AuthGuard
    },
    {
      path: '/Perangkat/',
      name: 'Perangkat',
      component: Perangkat,
      beforeEnter: AuthGuard
    },
    {
      path: '/Cloud/',
      name: 'Cloud',
      component: Cloud,
      beforeEnter: AuthGuard
    },
    {
      path: '/ViewCloud/',
      name: 'ViewCloud',
      component: ViewCloud,
      beforeEnter: AuthGuard
    },
    {
      path: '/history/',
      name: 'History',
      component: Riwayat,
      beforeEnter: AuthGuard
    },
    {
      path: '/About/',
      name: 'About',
      component: About1
    },
    {
      path: '/Peramalan/',
      name: 'Peramalan',
      component: Peramalan
    }
  ]
})
