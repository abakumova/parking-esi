<template>
    <div class="signin-container">
        <div class="signin-form">

        <h1><router-link to="/">
            <i class="arrow"></i>
        </router-link>Sign In</h1>
        <div class="form-group">
            <label for="email">Email</label>
            <input id="email" type="email" v-model="email" placeholder="email" required>
            <span class="error-message" v-if="emailError">{{ emailError }}</span>
        </div>


        <div class="form-group">
            <label for="password">Password</label>
            <input id="password" type="password" v-model="password" placeholder="password" required>
            <span class="error-message" v-if="passwordError">{{ passwordError }}</span>
        </div>

        <button class="btn-sign-in" @click="signIn">Sign In</button>
        <button class="btn-go-to-sign-up" @click="openSignUp">Go To Sign Up</button>
        </div>
    </div>
</template>

<script>
import './SignIn.css'
import ApiService from "@/api/ApiService";
import {emailRegex} from "@/constants/regex";
export default {
  name: "SignIn",
  data() {
      return {
          email: '',
          password: '',

          emailError: '',
          passwordError: '',
      }
  },
  methods: {
    openSignUp() {
      this.$router.push({ name: "signup" });
    },
    async signIn() {
        const isValid = await this.validateFields()
        if(isValid) {
            const user = {
                email: this.email,
                password: this.password
            };

            await ApiService.auth.authenticate(user)
            this.$router.push({name: 'home'})
        }
    },
      async validateFields() {
          let isValid = true;

          // Validate email
          if(!this.email) {
              this.emailError = 'Email is required';
              isValid = false;
          } else if (!emailRegex.test(this.email)) {
              this.emailError = 'Email format is invalid';
              isValid = false;
          } else {
              this.emailError = '';
          }

          // Validate password
          if(!this.password) {
              this.passwordError = 'Password is required';
              isValid = false;
          } else if (this.password.length > 50) {
              this.passwordError = 'Max length is 50 symbols';
              isValid = false;
          } else if (this.password.length < 5) {
              this.passwordError = 'Min length is 5 symbols';
              isValid = false;
          } else {
              this.passwordError = '';
          }

          return isValid
      }
  },
};
</script>

<style scoped></style>
