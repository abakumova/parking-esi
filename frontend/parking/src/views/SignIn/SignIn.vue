<template>
    <div class="signin-container">
        <div class="signin-form">

        <h1><router-link to="/">
            <i class="arrow"></i>
        </router-link>Sign In</h1>
        <label for="email">Email</label>
        <input id="email" type="email" v-model="email" placeholder="email" required>

        <label for="password">Password</label>
        <input id="password" type="password" v-model="password" placeholder="password" required>

        <button class="btn-sign-in" @click="signIn">Sign In</button>
        <button class="btn-go-to-sign-up" @click="openSignUp">Go To Sign Up</button>
        </div>
    </div>
</template>

<script>
import './SignIn.css'
import ApiService from "@/api/ApiService";
export default {
  name: "SignIn",
  data() {
      return {
          email: '',
          password: '',
      }
  },
  methods: {
    openSignUp() {
      this.$router.push({ name: "signup" });
    },
    async signIn() {
      // TODO: Implement sign in
        const user = {
            email: this.email,
            password: this.password
        };

        await ApiService.auth.authenticate(user)
        this.$router.push({name: 'home'})

        // using Fetch - post method - send an HTTP post request to the specified URI with the defined body
        // await fetch("http://localhost:8090/api/auth/login", {
        //     method: "POST",
        //     headers: {
        //         "Content-Type": "application/json",
        //     },
        //     credentials: 'include',
        //     body: JSON.stringify(data),
        // })
        //     .then(response => response.text())
        //     .then(response => {
        //         // saving the jwt returned in the response into the token variable
        //         this.token = response;
        //         //checking if a jwt token is retuned, all jwt tokes start with "ey"
        //         if (this.token.startsWith("ey"))
        //         {
        //             // decoding the jwt and save it in the decodedToken variable
        //             this.decodedToken = jwt_decode(this.token);
        //             // saving the returned user role into the roles variable
        //             this.roles = this.decodedToken.roles
        //             // saving the token into the windows local storage
        //             localStorage.setItem('jwtToken',  this.token);
        //             console.log(localStorage.getItem('jwtToken'));
        //             this.$router.push("/");
        //         }
        //     })
        //     .catch((e) => {
        //         console.log(e);
        //         console.log("error");
        //     });

    },
  },
};
</script>

<style scoped></style>
