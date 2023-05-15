import { createRouter, createWebHistory } from "vue-router";
import Home from '@/views/Home/Home.vue'
import SignIn from '@/views/SignIn/SignIn.vue'
import SignUp from '@/views/SignUp/SignUp.vue'
import Profile from "@/views/Profile/Profile.vue";
import SearchResult from "@/views/SearchResult/SearchResult.vue";
import ParkingManagement from "@/views/ParkingManagement/ParkingManagement.vue";
import Booking from "@/views/Booking/Booking.vue";
import auth from "../auth";
import {ROLES} from "@/constants/roles";
import Admin from "@/views/Admin/Admin.vue";
import {toast} from 'vue3-toastify'


const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
    meta: {
      requiresAuth: false,
      roles: [ROLES.ADMIN, ROLES.GUEST, ROLES.USER, ROLES.LANDLORD]
    }
  },
  {
    path: '/signin',
    name: 'signin',
    component: SignIn,
    meta: {
      // requiresAuth: false,
      roles: [ROLES.GUEST]
    }
  },
  {
    path: '/signup',
    name: 'signup',
    component: SignUp,
    meta: {
      // requiresAuth: false,
      roles: [ROLES.GUEST]
    }
  },
  {
    path: '/profile',
    name: 'profile',
    component: Profile,
    meta: {
      requiresAuth: true,
      roles: [ROLES.USER, ROLES.LANDLORD, ROLES.ADMIN],
    }
  },
  {
    path: '/search',
    name: 'search',
    component: SearchResult,
    meta: {
      // requiresAuth: false,
      roles: [ROLES.ADMIN, ROLES.LANDLORD, ROLES.GUEST, ROLES.USER]
    },
  },
  {
    path: '/parking',
    name: 'parking',
    component: ParkingManagement,
    meta: {
      requiresAuth: true, // requires authentication
      roles: [ROLES.ADMIN, ROLES.LANDLORD], // can access this route
    },
  },
  {
    path: '/admin',
    name: 'admin',
    component: Admin,
    meta: {
      requiresAuth: true, // requires authentication
      roles: [ROLES.ADMIN]
    }
  },
  {
    path: '/booking/:id',
    name: 'booking',
    component: Booking,
    meta: {
      requiresAuth: true, // requires authentication
      roles: [ROLES.USER]
    }
  },
  // { //will route to AllPosts view if none of the previous routes apply
  //   path: "/:catchAll(.*)",
  //   name: "home",
  //   component: Home,
  // }
];

export const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach((to, from, next) => {
  const isAuthenticated = auth.isAuthenticated() // check if user is authenticated
  const requiresAuth = to.matched.some((record ) => record.meta.requiresAuth)  // check if route requires authentication
  const roles = to.meta.roles // get the allowed roles for the route


  if (requiresAuth && !isAuthenticated) {
    next('/signin') // redirect to login page if route requires authentication and user is not authenticated
    toast.info("You need to be authorized to visit this page")
  } else if (roles && !roles.includes(auth.getUserRole())) {
    next('/') // redirect to home page if user does not have the appropriate role to access the route
    toast.info("You don't have required roles to visit this page")
  } else {
    next() // allow access to the route
  }
})

export default router;