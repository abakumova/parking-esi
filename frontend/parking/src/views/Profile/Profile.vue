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
            <div class="button-container">
                <button class="btn-close" @click="close">Close</button>
                <button class="btn-delete" @click="deleteAccount">Delete</button>
            </div>
        </div>
    </div>
</template>

<script>
import './Profile.css'
import UserManagementService from "@/api/user_management/UserManagementService";
import auth from "@/auth";

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
        async deleteAccount() {
            await UserManagementService.deleteUser(auth.user.userId);
            auth.logout();
            this.$router.push({ name: "signin"});
        }
    },
    async mounted() {
        const userData = await UserManagementService.getUserById(auth.user.userId, auth.getToken());
        this.firstName = userData.firstName;
        this.lastName = userData.lastName;
        this.email = userData.email;
        this.address = userData.address;
    }
};
</script>
