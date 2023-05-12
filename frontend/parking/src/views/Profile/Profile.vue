<template>
    <div class="profile-container">
        <h1>My Profile</h1>
        <div class="form-container">
            <div class="form-group">
                <label for="first-name">First Name</label>
                <input type="text" id="first-name" v-model="firstName" disabled>
            </div>
            <div class="form-group">
                <label for="last-name">Last Name</label>
                <input type="text" id="last-name" v-model="lastName" disabled>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" v-model="email" disabled>
            </div>
            <div class="form-group">
                <label for="address">Address</label>
                <input type="text" id="address" v-model="address" disabled>
            </div>
            <div class="button-container">
                <button class="btn-close" @click="close">Close</button>
            </div>
        </div>
    </div>
</template>

<script>
import './Profile.css'
export default {
    name: "Profile",
    data() {
        return {
            firstName: "",
            lastName: "",
            email: "",
            address: ""
        };
    },
    methods: {
        close() {
            this.$router.push({name: 'home'})
        },

    },
    mounted() {
        // Fetch user data from server and populate data properties
    }
};
</script>
