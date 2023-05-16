<template>
    <div class="parking-management">
        <h1>Parking Management: {{selectedTab}} </h1>
        <div class="tab-panel">
            <button @click="selectTab('all')" :class="{ active: selectedTab === 'all' }">All Parkings</button>
            <button @click="selectTab('open')" :class="{ active: selectedTab === 'open' }">Open Parkings</button>
            <button @click="selectTab('closed')" :class="{ active: selectedTab === 'closed' }">Closed Parkings</button>
        </div>
        <div class="tab-content">
            <component v-if="selectedTab === 'all'"
                       :is='ParkingManagementAllTab'
                       :slots='slots'
                       @delete-slot="deleteSlot"/>

            <component v-if="selectedTab === 'open'"
                       :is='ParkingManagementOpenTab'
                       :slots='slots.filter(slot => slot.status === PARKING_STATUS.OPEN)'/>

            <component v-if="selectedTab === 'closed'"
                       :is='ParkingManagementClosedTab'
                       :slots='slots.filter(slot => slot.status === PARKING_STATUS.CLOSED)'/>
        </div>
    </div>
</template>

<script>
import './ParkingManagement.css'

import ParkingManagementAllTab from "@/components/ParkingManagement/ParkingTabs/ParkingManagementAllTab.vue";
import ParkingManagementOpenTab from "@/components/ParkingManagement/ParkingTabs/ParkingManagementOpenTab.vue";
import ParkingManagementClosedTab from "@/components/ParkingManagement/ParkingTabs/ParkingManagementClosedTab.vue";
import ApiService from "@/api/ApiService";
import auth from "@/auth";
import {PARKING_STATUS} from "@/constants/parkingStatus";

export default {
    name: 'ParkingManagement',
    data() {
        return {
            selectedTab: 'all',
            tabComponents: {
                'all': () => ParkingManagementAllTab,
                'open': () => ParkingManagementOpenTab,
                'closed': () => ParkingManagementClosedTab
            },
            slots: [],
        };
    },
    computed: {
        PARKING_STATUS() {
            return PARKING_STATUS
        },
        ParkingManagementClosedTab() {
            return ParkingManagementClosedTab
        },
        ParkingManagementOpenTab() {
            return ParkingManagementOpenTab
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
            console.warn(`Fetched slots:`)
            console.warn(this.slots)
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