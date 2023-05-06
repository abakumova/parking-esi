import { createRouter, createWebHistory } from "vue-router";
import Home from '@/views/Home/Home.vue'
import SignIn from '@/views/SignIn/SignIn.vue'
import SignUp from '@/views/SignUp/SignUp.vue'
import Profile from "@/views/Profile/Profile.vue";
import SearchResult from "@/views/SearchResult/SearchResult.vue";
import ParkingManagement from "@/views/ParkingManagement/ParkingManagement.vue";
import Booking from "@/views/Booking/Booking.vue";

const routes = [
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
    component: SearchResult
  },
  {
    path: '/parking',
    name: 'parking',
    component: ParkingManagement
  },
  {
    booking: '/booking/:id',
    name: 'booking',
    component: Booking,
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
