<template>
    <div>
        <h1>All parking slots!</h1>
        <div class="parking-list">
            <button @click="showAddForm = !showAddForm">Add Slot</button>
            <div v-if="showAddForm">
                <parking-add-form @add-slot="addSlot" @cancel="cancelAddForm" />
            </div>

            <div v-for="slot in slots" :key="slot.id">
                <div class="parking-card-container">
                    <parking-card :slot="slot"/>
                    <button @click="$emit('delete-slot', slot.id)">Delete</button>
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
            required: false,
        },
    },
    methods: {
        addSlot(slot) {
            //TODO: change to EMIT
            this.slots.push(slot);
            this.showAddForm = false;
        },
        cancelAddForm() {
            this.showAddForm = false;
        },
    },
    data() {
        return {
            showAddForm: false,
        };
    },

};
</script>
