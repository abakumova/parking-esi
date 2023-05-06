<template>
    <div>
        <h1>History</h1>
        <div class="parking-list">
            <parking-card v-for="parking in parkings" :key="parking.id" :parking="parking" />
        </div>
    </div>
</template>

<script>
import './ParkingManagementTab.css'
import ParkingCard from "@/components/ParkingManagement/ParkingCard/ParkingCard.vue";
export default {
    name:"ParkingManagementHistory",
    components: {
        ParkingCard,
    },
    data() {
        return {
            parkings: [
                {
                    id: 1,
                    name: "Parking 1",
                    status: "Available",
                    price: "$10",
                    timeFrom: "2023-05-06T09:00:00Z",
                    timeUntil: "2023-05-06T11:00:00Z",
                },
                {
                    id: 2,
                    name: "Parking 2",
                    status: "Unavailable",
                    price: "$15",
                    timeFrom: "2023-05-06T10:00:00Z",
                    timeUntil: "2023-05-06T12:00:00Z",
                },
                {
                    id: 3,
                    name: "Parking 3",
                    status: "Available",
                    price: "$20",
                    timeFrom: "2023-05-06T11:00:00Z",
                    timeUntil: "2023-05-06T13:00:00Z",
                },
            ],
        };
    },
};
</script>
