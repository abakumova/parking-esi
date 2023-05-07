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
                    <parking-card
                        :name="parking.name"
                        :status="parking.status"
                        :price="parking.price"
                        :timeFrom="parking.timeFrom"
                        :timeUntil="parking.timeUntil"
                    />
                    <button @click="editParking(parking.id)">Edit</button>
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
            slot.id = this.parkings.length + 1;
            this.parkings.push(slot);
            this.showAddForm = false;
        },
        cancelAddForm() {
            this.showAddForm = false;
        },
        editParking(id) {

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
                },
                {
                    id: 2,
                    name: "Parking 2",
                    status: "Unavailable",
                    price: "$15",
                },
                {
                    id: 3,
                    name: "Parking 3",
                    status: "Available",
                    price: "$20",
                },
            ],
        };
    },
};
</script>
