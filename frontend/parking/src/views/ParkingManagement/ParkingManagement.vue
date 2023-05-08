<template>
    <div class="parking-management">
        <h1>Parking Management: {{selectedTab}} </h1>
        <div class="tab-panel">
            <button @click="selectTab('all')" :class="{ active: selectedTab === 'all' }">All Parkings</button>
            <button @click="selectTab('active')" :class="{ active: selectedTab === 'active' }">Active Parkings</button>
            <button @click="selectTab('history')" :class="{ active: selectedTab === 'history' }">History</button>
        </div>
        <div class="tab-content">
            <component v-if="selectedTab === 'all'" :is='ParkingManagementAllTab' :slots='parkings'/>
            <component v-if="selectedTab === 'active'" :is='ParkingManagementActiveTab' :slots='parkings'/>
            <component v-if="selectedTab === 'history'" :is='ParkingManagementHistory' :slots='parkings'/>
        </div>
    </div>
</template>

<script>
import './ParkingManagement.css'

import ParkingManagementAllTab from "@/components/ParkingManagement/ParkingTabs/ParkingManagementAllTab.vue";
import ParkingManagementActiveTab from "@/components/ParkingManagement/ParkingTabs/ParkingManagementActiveTab.vue";
import ParkingManagementHistory from "@/components/ParkingManagement/ParkingTabs/ParkingManagementHistory.vue";

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
            parkings: [
                {
                    id: 1,
                    name: "Parking 1",
                    status: "Available",
                    price: "$10",
                    location: "Raatuse 22"
                },
                {
                    id: 2,
                    name: "Parking 2",
                    status: "Unavailable",
                    price: "$15",
                    location: "Narva mnt 27"
                },
                {
                    id: 3,
                    name: "Parking 3",
                    status: "Available",
                    price: "$20",
                    location: "Marshala Tymoshenka 3"
                },
            ],
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
        }
    }
};
</script>