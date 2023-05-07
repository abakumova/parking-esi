<template>
    <div>
        <h1>All tab!</h1>
        <div class="parking-list">
            <button @click="showAddForm = true">Add Slot</button>
            <div v-if="showAddForm">
                <parking-add-form @add-slot="addSlot" @cancel="cancelAddForm" />
            </div>

            <div v-for="parking in parkings" :key="parking.id">
                <div class="parking-card-container">
                    <parking-card :parking="parking" />
                    <button @click="deleteParking(parking.id)">Delete</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import './ParkingManagementTab.css'
import ParkingCard from "@/components/ParkingManagement/ParkingCard/ParkingCard.vue";
import ParkingAddForm from "@/components/ParkingManagement/ParkingAddForm/ParkingAddForm.vue";

export default {
    name:"ParkingManagementAllTab",
    components: {
        ParkingAddForm,
        ParkingCard,
    },
    props: {
        slots: {
            type: Array,
            required: true,
        },
    },
    methods: {
        showAddForm() {
            this.showAddForm = true;
        },
        addSlot(slot) {
            this.$emit('add-slot', slot);
            this.showAddForm = false;
        },
        cancelAddForm() {
            this.showAddForm = false;
        },
        deleteParking(id) {
            this.parkings = this.parkings.filter(parking => parking.id !== id)
        },
    },
    data() {
        return {
            showAddForm: false,
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
