<template>
    <div class="parking-management">
        <h1>Selected tab {{selectedTab}} </h1>
        <div class="tab-panel">
            <button @click="selectTab('users')" :class="{ active: selectedTab === 'users' }">Users</button>
            <button @click="selectTab('slots')" :class="{ active: selectedTab === 'slots' }">Parking Slots</button>
        </div>
        <div class="tab-content">
            <component v-if="selectedTab === 'users'"
                       :is='AdminUsers'/>

            <component v-if="selectedTab === 'slots'"
                       :is='AdminParkingSlots'/>
        </div>
    </div>
</template>

<script>


import AdminUsers from "@/components/Admin/AdminUsers.vue";
import AdminParkingSlots from "@/components/Admin/AdminParkingSlots.vue";
import ApiService from "@/api/ApiService";
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
        }
    }
};
</script>