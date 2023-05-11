import { createRouter, createWebHistory } from "vue-router";
import Home from '@/views/Home/Home.vue'
import SignIn from '@/views/SignIn/SignIn.vue'
import SignUp from '@/views/SignUp/SignUp.vue'
import Profile from "@/views/Profile/Profile.vue";
import SearchResult from "@/views/SearchResult/SearchResult.vue";
import ParkingManagement from "@/views/ParkingManagement/ParkingManagement.vue";
import Booking from "@/views/Booking/Booking.vue";
import auth from "../auth";

const ADMIN_ROLE = 'ADMIN'
const USER_ROLE = 'USER'
const LANDLORD_ROLE = 'LANDLORD'

const routes = [

  // {
  //   path: "/",
  //   name: "home",
  //   component: HomeView,
  //   beforeEnter: async(to, from, next) => {
  //     let authResult = await auth.authenticated();
  //     if (!authResult) {
  //       next('/login')
  //     } else {
  //       next();
  //     }
  //   }
  // },
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/signin',
    name: 'signin',
    component: SignIn
  },
  {
    path: '/signup',
    name: 'signup',
    component: SignUp
  },
  {
    path: '/profile',
    name: 'profile',
    component: Profile
  },
  {
    path: '/search',
    name: 'search',
    component: SearchResult,
    meta: {
      requiresAuth: true,
      roles: [USER_ROLE]
    }
  },
  {
    path: '/parking',
    name: 'parking',
    component: ParkingManagement,
    meta: {
      requiresAuth: true, // requires authentication
      roles: [LANDLORD_ROLE, ADMIN_ROLE], // can access this route
    },
  },
  {
    booking: '/booking/:id',
    name: 'booking',
    component: Booking,
    meta: {
      requiresAuth: true, // requires authentication
      roles: [USER_ROLE]
    }
  },

  { //will route to AllPosts view if none of the previous routes apply
    path: "/:catchAll(.*)",
    name: "home",
    component: Home,
  }
];

export const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach((to, from, next) => {
  const isAuthenticated = auth.isAuthenticated() // check if user is authenticated
  const requiresAuth = to.matched.some((record: any) => record.meta.requiresAuth)  // check if route requires authentication
  const roles = to.meta.roles // get the allowed roles for the route

  if (requiresAuth && !isAuthenticated) {
    next('/signin') // redirect to login page if route requires authentication and user is not authenticated
  } else if (roles && !roles.includes(auth.getUserRole())) {
    next('/') // redirect to home page if user does not have the appropriate role to access the route
  } else {
    next() // allow access to the route
  }
})

export default router;