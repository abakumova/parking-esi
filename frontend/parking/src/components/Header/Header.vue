<template>
    <header class="header">
        <div class="header-left">
            <router-link to="/">Home</router-link> |
            <router-link to="/parking">Create Parking</router-link>
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
export default {
    name:"Header",
    data() {
        return {
            authenticated: auth.user.authenticated
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
        }
    }
}
</script>