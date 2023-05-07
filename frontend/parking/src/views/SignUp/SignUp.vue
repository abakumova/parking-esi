<template>
    <div class="signup-container">
        <div class="signup-form">
            <h1><router-link to="/">
                <i class="arrow"/>
            </router-link>{{en}}Sign Up</h1>
            <label for="email">Email</label>
            <input id="email" type="email" v-model="email" required>

            <label for="password">Password</label>
            <input id="password" type="password" v-model="password" required>

            <label for="firstName">First Name</label>
            <input id="firstName" type="text" v-model="firstName" required>

            <label for="lastName">Last Name</label>
            <input id="lastName" type="text" v-model="lastName" required>

            <label for="role">Role</label>
            <select id="role" v-model="userRole">
                <option value="LANDLORD">Landlord</option>
                <option value="USER">User</option>
            </select>

            <button class="btn-sign-up" @click="signUp">Sign Up</button>
            <button class="btn-go-to-sign-in" @click="openSignIn">Go To Sign In</button>
        </div>
    </div>
</template>

<script>
import './SignUp.css'
import ApiService from "@/api/ApiService";
export default {
    name: "SignUp",
    data(){ return {
        en: process.env.SOMETHING,
        val: {
            email: '',
            password: '',
            firstName: '',
            lastName: '',
            userRole: 'user'
        }
    }
    },
    methods: {
        methods: {
            signUp() {
                // TODO: Implement sign up
            }
        },
        openSignIn() {
            this.$router.push({ name: 'signin' })
        }
    }
}
</script>

<style scoped>

</style>