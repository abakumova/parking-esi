<template>
    <div class="signup-container">
        <div class="signup-form">
            <h1><router-link to="/">
                <i class="arrow"/>
            </router-link>Sign Up</h1>

            <label for="email">Email</label>
            <input id="email" type="email" v-model="email" required>

            <label for="password">Password</label>
            <input id="password" type="password" v-model="password" required>

            <label for="firstName">First Name</label>
            <input id="firstName" type="text" v-model="firstName" required>

            <label for="lastName">Last Name</label>
            <input id="lastName" type="text" v-model="lastName" required>

            <label for="role">Role</label>
            <select id="role" v-model="role">
                <option value="landlord">Landlord</option>
                <option value="user">User</option>
            </select>

            <button class="btn-sign-up" @click="signUp">Sign Up</button>
            <button class="btn-go-to-sign-in" @click="openSignIn">Go To Sign In</button>
        </div>
    </div>
</template>

<script>
import './SignUp.css'
export default {
    name: "SignUp",
    data() {
        return {
            email: '',
            password: '',
            firstName: '',
            lastName: '',
            role: 'user'
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