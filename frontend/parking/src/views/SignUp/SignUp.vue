<template>
    <div class="signup-container">
        <div class="signup-form">
            <h1>
                <router-link to="/">
                    <i class="arrow"/>
                </router-link>
                Sign Up
            </h1>
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
    data() {
        return {
            email: '',
            password: '',
            firstName: '',
            lastName: '',
            userRole: 'USER',
            token: '',
            decodedToken: '',
            balance: '' //TODO: set default balance
        }
    },
    methods: {
        async signUp() {
            const userData = {
                userRole: this.userRole,
                firstName: this.firstName,
                lastName: this.lastName,
                email: this.email,
                password: this.password,
                paymentMethod: {
                    balance: this.balance
                }
            };

            await ApiService.auth.register(userData)
            this.$router.push({name: 'home'})
        },
        openSignIn() {
            this.$router.push({name: 'signin'})
        }
    }
}
</script>

<style scoped>

</style>