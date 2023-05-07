<template>
    <div class="signin-container">
        <div class="signin-form">

        <h1><router-link to="/">
            <i class="arrow"></i>
        </router-link>Sign In</h1>
        <label for="email">Email</label>
        <input id="email" type="email" v-model="email" required>

        <label for="password">Password</label>
        <input id="password" type="password" v-model="password" required>

        <button class="btn-sign-in" @click="signIn">Sign In</button>
        <button class="btn-go-to-sign-up" @click="openSignUp">Go To Sign Up</button>
        </div>
    </div>
</template>

<script>
import './SignIn.css'
export default {
  name: "SignIn",
  data() {
      return {
          email: '',
          password: ''
      }
  },
  methods: {
    openSignUp() {
      this.$router.push({ name: "signup" });
    },
    signIn() {
      // TODO: Implement sign in
    },
  },
};
</script>

<style scoped></style>
