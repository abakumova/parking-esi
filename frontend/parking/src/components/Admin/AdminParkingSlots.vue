<template>
    <div>
        <div v-for="slot in this.slots" :key="slot.id">
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
    data() {
        return {
            slots: []
        }
    },
    methods: {
        async deleteSlot(slotId) {
            await ApiService.parking.deleteParkingSlot(slotId);
            console.log("PARENT DELETE")
            this.slots = this.slots.filter(slot => slot.id !== slotId);
        },

        async updateSlot(updatedSlot) {
            updatedSlot.location = await ApiService.location.getLocation(updatedSlot.location.formattedAddress)
            await ApiService.parking.updateParkingSlot(updatedSlot.id, updatedSlot);
            const index = this.slots.findIndex((slot) => slot.id === updatedSlot.id);
            this.$set(this.slots, index, updatedSlot);
        },
        async fetchSlots() {
            const params = {
                limit:100000,
                page:0,
            }
            const resp = await ApiService.parking.getParkingSlots(params)
            this.slots = resp.data

            console.warn(`Fetched parking slots:`)
            console.warn(this.slots)
        },
    },
    async mounted() {
        await this.fetchSlots()
    },
}
</script>

<style scoped>
.admin-slot-container {
    border-radius: 25px;
    background-color: rgba(55, 33, 17, 0.19);
    box-shadow: 4px 4px 4px rgba(0, 0, 0, 0.5);
    padding: 20px;
    margin-bottom: 25px;
}
</style>