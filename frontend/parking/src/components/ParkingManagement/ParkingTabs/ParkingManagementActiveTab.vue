<template>
    <div>
        <h1>All tab!</h1>
        <div class="parking-list">
            <div v-for="parking in slots" :key="parking.id">
                <div class="parking-card-container">
                    <parking-card
                        :name="parking.name"
                        :status="parking.status"
                        :price="parking.price"
                        :location="parking.location"
                    />
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
    name:"ParkingManagementActiveTab",
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
