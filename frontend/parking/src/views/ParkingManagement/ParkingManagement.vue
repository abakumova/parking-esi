<template>
    <div class="parking-management">
        <h1>Parking Management: {{selectedTab}} </h1>
        <div class="tab-panel">
            <button @click="selectTab('all')" :class="{ active: selectedTab === 'all' }">All Parkings</button>
            <button @click="selectTab('active')" :class="{ active: selectedTab === 'active' }">Active Parkings</button>
            <button @click="selectTab('history')" :class="{ active: selectedTab === 'history' }">History</button>
        </div>
        <div class="tab-content">
            <component v-if="selectedTab === 'all'"
                       :is='ParkingManagementAllTab'
                       :slots='slots'
                       @delete-slot="deleteSlot"
            />
            <component v-if="selectedTab === 'active'" :is='ParkingManagementActiveTab' :slots='slots'/>
            <component v-if="selectedTab === 'history'" :is='ParkingManagementHistory' :slots='slots'/>
        </div>
    </div>
</template>

<script>
import './ParkingManagement.css'

import ParkingManagementAllTab from "@/components/ParkingManagement/ParkingTabs/ParkingManagementAllTab.vue";
import ParkingManagementActiveTab from "@/components/ParkingManagement/ParkingTabs/ParkingManagementActiveTab.vue";
import ParkingManagementHistory from "@/components/ParkingManagement/ParkingTabs/ParkingManagementHistory.vue";
import ApiService from "@/api/ApiService";
import auth from "@/auth";

export default {
    name: 'ParkingManagement',
    data() {
        return {
            selectedTab: 'all',
            tabComponents: {
                'all': () => ParkingManagementAllTab,
                'active': () => ParkingManagementActiveTab,
                'history': () => ParkingManagementHistory
            },
            slots: [],
        };
    },
    computed: {
        ParkingManagementHistory() {
            return ParkingManagementHistory
        },
        ParkingManagementActiveTab() {
            return ParkingManagementActiveTab
        },
        ParkingManagementAllTab() {
            return ParkingManagementAllTab
        },
    },
    methods: {
        selectTab(tabName) {
            this.selectedTab = tabName;
        },
        async fetchSlots() {
            const resp = await ApiService.parking.getParkingSlotByLandlordId(auth.user.userId)
            this.slots = resp.data
        },
        async deleteSlot(id) {
            await ApiService.parking.deleteParkingSlot(id)
            this.slots = this.slots.filter(slot => slot.id !== id);
        }
    },
    mounted() {
        this.fetchSlots().data
    },
};
</script>