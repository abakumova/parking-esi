<template>
    <div>
        <div v-for="slot in slots" :key="slot.id">
            <div class="admin-slot-container">
                <slot-card
                        :slot="slot"
                        @update-slot="updateSlot"
                        @delete-slot="deleteSlot"
                />
            </div>
        </div>
    </div>
</template>

<script>
import SlotCard from "@/components/Admin/SlotCard/SlotCard.vue";
import ApiService from "@/api/ApiService";

export default {
    name: "AdminParkingSlots",
    components: {SlotCard},
    props: {
        slots: {
            type: Array,
            required: false,
        },
    },
    methods: {
        async deleteSlot(slotId) {
            await ApiService.parking.deleteSlot(slotId);
            console.log("PARENT DELETE")
            this.slots = this.slots.filter(slot => slot.id !== slotId);
        },

        async updateSlot(updatedSlot) {
            await ApiService.parking.updateSlot(updatedSlot.id, updatedSlot);
            const index = this.slots.findIndex((slot) => slot.id === updatedSlot.id);
            this.$set(this.slots, index, updatedSlot);
        },
    },
}
</script>

<style scoped>
.admin-slot-container {
    border-radius: 8px;
    box-shadow: 0px 2px 6px rgba(0, 0, 0, 0.4);
    padding: 20px;
    margin-bottom: 25px;
}
</style>