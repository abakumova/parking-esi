<template>
    <div>
        <h1>All tab!</h1>
        <div class="parking-list">
            <button @click="showAddForm = !showAddForm">Add Slot</button>
            <div v-if="showAddForm">
                <parking-add-form @add-slot="addSlot" @cancel="cancelAddForm" />
            </div>

            <div v-for="parking in slots" :key="parking.id">
                <div class="parking-card-container">
                    <parking-card
                        :name="parking.name"
                        :status="parking.status"
                        :price="parking.price"
                        :location="parking.location"
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
            slot.id = this.slots.length + 1;
            this.slots.push(slot);
            this.showAddForm = false;
        },
        cancelAddForm() {
            this.showAddForm = false;
        },
        editParking(id) {

        },
        deleteParking(id) {
            this.slots = this.slots.filter(parking => parking.id !== id)
        },
    },
    data() {
        return {
            showAddForm: false,
        };
    },
};
</script>
