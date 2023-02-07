import { createRouter, createWebHistory } from 'vue-router';
import LoginView from '@/views/LoginView';
import ServiceHomeView from '@/views/ServiceHomeView';
import CorporateHomeView from '@/views/CorporateHomeView';
import EvaluatorHomeView from '@/views/EvaluatorHomeView';
import ApplicantHomeView from '@/views/ApplicantHomeView';

const routes = [
  {
    path: '/',
    name: 'Login',
    component: LoginView,
  },
  {
    path: '/service',
    name: 'Service',
    component: ServiceHomeView,
    beforeEnter: [checkLogin],
  },
  {
    path: '/corporate',
    name: 'Corporate',
    component: CorporateHomeView,
    beforeEnter: [checkLogin],
  },
  {
    path: '/evaluate',
    name: 'Evaluator',
    component: EvaluatorHomeView,
    beforeEnter: [checkLogin],
  },
  {
    path: '/apply',
    name: 'Applicant',
    component: ApplicantHomeView,
    beforeEnter: [checkLogin],
  },
];

const router = createRouter({
  history: createWebHistory('/'),
  routes: routes,
});

function checkLogin() {
  const loggedIn = localStorage.getItem('user');
  if (!loggedIn) {
    router.push({ name: 'Login' });
  }
}

export default router;
