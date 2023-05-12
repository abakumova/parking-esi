<template>
    <div>
        <h1>All tab!</h1>
        <div class="parking-list">
            <div v-for="slot in slots" :key="slot.id">
                <div class="parking-card-container">
                    <parking-card :slot="slot"/>
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
};
</script>
