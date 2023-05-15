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
                <ProfileAvatar @click="openProfile"/>
                <li><button @click="signOut">Sign Out</button></li>
            </div>
        </div>
    </header>
</template>

<script>
import Search from "@/components/Search/Search.vue";
import ProfileAvatar from "@/components/ProfileAvatar/ProfileAvatar.vue";

import './Header.css'
import auth from "@/auth";
import {ROLES} from "@/constants/roles";
export default {
    name:"Header",
    data() {
        return {
            authenticated: auth.user.authenticated,
            isLandlord: auth.getUserRole() === ROLES.LANDLORD,
            isAdmin: auth.getUserRole() === ROLES.ADMIN,
            musicPlaying: false,
        }
    },
    components: {
        ProfileAvatar,
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
    }
}
</script>