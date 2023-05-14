<template>
    <div class="parking-management">
        <h1>Selected tab {{selectedTab}} </h1>
        <div class="tab-panel">
            <button @click="selectTab('users')" :class="{ active: selectedTab === 'users' }">Users</button>
            <button @click="selectTab('slots')" :class="{ active: selectedTab === 'slots' }">Parking Slots</button>
        </div>
        <div class="tab-content">
            <component v-if="selectedTab === 'users'"
                       :is='AdminUsers'
                       :users='users'
                       @update-user="updateUser"
                       @delete-user="deleteUser"/>

            <component v-if="selectedTab === 'slots'"
                       :is='AdminParkingSlots'
                       :slots='slots'
                       @update-user="updateSlot"
                       @delete-user="deleteSlot"/>
        </div>
    </div>
</template>

<script>


import AdminUsers from "@/components/Admin/AdminUsers.vue";
import AdminParkingSlots from "@/components/Admin/AdminParkingSlots.vue";
import ApiService from "@/api/ApiService";
import auth from "@/auth";
import {PARKING_STATUS} from "@/constants/parkingStatus";

export default {
    name: "Admin",
    data() {
        return {
            selectedTab: 'users',
            tabComponents: {
                'users': () => AdminUsers,
                'slots': () => AdminParkingSlots,
            },
            slots: [],
            users: []
        };
    },
    computed: {
        PARKING_STATUS() {
            return PARKING_STATUS
        },
        AdminUsers() {
            return AdminUsers
        },
        AdminParkingSlots() {
            return AdminParkingSlots
        },
    },
    methods: {
        selectTab(tabName) {
            this.selectedTab = tabName;
        },
        async fetchSlots() {
            const resp = await ApiService.parking.getParkingSlots()
            this.slots = resp.data
            console.warn(`Fetched parking slots:`)
            console.warn(this.slots)
        },

        async fetchUsers() {
            const params = {
                limit:100000,
                page:0,
            }
            const resp = await ApiService.user.getUsers(params)
            this.users = resp.data
            console.warn(`Fetched users:`)
            console.warn(this.users)
        },
        // async deleteSlot(id) {
        //     await ApiService.parking.deleteParkingSlot(id)
        //     this.slots = this.slots.filter(slot => slot.id !== id);
        // }
    },
    async mounted() {
        // this.fetchSlots()
        await this.fetchUsers()
    },
};
</script>