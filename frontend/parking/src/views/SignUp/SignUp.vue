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
import jwt_decode from "jwt-decode";
export default {
    name: "SignUp",
    data(){
        return{
            email: '',
            password: '',
            firstName: '',
            lastName: '',
            userRole: 'USER',
            token:'',
            decodedToken: ''
        }
    },
    methods: {
        async signUp() {
            // TODO: Implement sign up
            const userData = {
                userRole: this.userRole,
                firstName: this.firstName,
                lastName: this.lastName,
                email: this.email,
                password: ApiService.hashv(this.password),
                paymentMethod: {
                    balance: this.balance
                }
            };

            // using Fetch - post method - send an HTTP post request to the specified URI with the defined body
            await fetch("http://localhost:8090/api/auth/signup", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                credentials: 'include',
                body: JSON.stringify(data),
            })
                .then(response => response.text())
                .then(response => {
                    //saving the jwt in the token variable
                    this.token = response;
                    if (this.token.startsWith("ey")){
                        //decoding the jwt and save it in the decodedToken variable
                        this.decodedToken = jwt_decode(this.token);
                        // saving the returned user role into the roles variable
                        this.roles = this.decodedToken.roles
                        console.log(this.decodedToken.roles);
                        // saving the token into the windows local storage
                        localStorage.setItem('jwtToken',  this.token);
                        console.log(localStorage.getItem('jwtToken'));
                        this.$router.push("/");
                    }})
                .catch((e) => {
                    console.log(e);
                    console.log("error");
                });
        },
        openSignIn() {
            this.$router.push({ name: 'signin' })
        }
    }
}
</script>

<style scoped>

</style>