<template>
    <header class="header">
        <div class="header-left">
            <i @click="play" id="audio" class="audio">
                <audio id="audio" ref="audioPlayer" style="display:none" controls autoplay loop>
                    <source src="@/assets/alexander-nakarada-frost.mp3" type="audio/mp3">
                </audio>
            </i>

            <router-link to="/">Home</router-link>
            <router-link to="/parking" v-if="isLandlord">Create Parking</router-link>
            <router-link to="/admin" v-if="isAdmin">Godlike</router-link>
        </div>
        <div class="header-middle">
            <Search />
        </div>
        <div class="header-right">
            <div class="header-right-no-auth" v-if="!authenticated">
                <li><button @click="openSignIn">Sign In</button></li>
                <li><button @click="openSignUp">Sign Up</button></li>
            </div>
            <div class="header-right-auth" v-if="authenticated">
                <img class="profile-icon" src="@/assets/avatar.png" @click="openProfile">
                <p>{{this.userBalance}}€</p>
                <li><button @click="signOut">Sign Out</button></li>
            </div>
        </div>
    </header>
</template>

<script>
import Search from "@/components/Search/Search.vue";

import './Header.css'
import auth from "@/auth";
import {ROLES} from "@/constants/roles";
import ApiService from "@/api/ApiService";
export default {
    name:"Header",
    data() {
        return {
            authenticated: auth.user.authenticated,
            isLandlord: auth.getUserRole() === ROLES.LANDLORD,
            isAdmin: auth.getUserRole() === ROLES.ADMIN,
            musicPlaying: false,
            userBalance: null,
        }
    },
    components: {
        Search,
    },
    methods: {
        openSignIn() {
            this.$router.push({ name: "signin" });
        },
        openSignUp() {
            this.$router.push({ name: "signup" });
        },
        openProfile() {
            this.$router.push({ name: "profile"})
        },
        signOut() {
            auth.logout()
            this.$router.push({ name: "signin"})
        },
        play() {
            if(!this.musicPlaying) {
                this.$refs.audioPlayer.play()
                this.musicPlaying = true;
            }
            else {
                this.$refs.audioPlayer.pause()
                this.musicPlaying = false;
            }

        }
    },
    async mounted() {
        if(auth.getToken()) {
            this.userBalance = (await ApiService.user.getUserById(auth.decodeAccessToken().userId)).paymentMethod.balance
            console.log(this.userBalance)
        }
    }
}
</script>