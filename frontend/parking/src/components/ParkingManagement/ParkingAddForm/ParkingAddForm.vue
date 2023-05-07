<template>
    <div class="parking-add-form">
        <form>
            <div class="form-group">
                <label for="price">Price:</label>
                <input type="number" id="price" v-model="price">
            </div>
            <div class="form-group">
                <label for="location">Location:</label>
                <input type="text" id="location" v-model="location">
            </div>
<!--            <div class="form-group">-->
<!--                <label for="timeFrom">Time From:</label>-->
<!--                <input type="datetime-local" id="timeFrom" v-model="timeFrom" :min="minDateTime" required>-->
<!--            </div>-->
<!--            <div class="form-group">-->
<!--                <label for="timeUntil">Time Until:</label>-->
<!--                <input type="datetime-local" id="timeUntil" v-model="timeUntil" :min="minDateTime" required>-->
<!--            </div>-->
            <div class="form-buttons">
                <button type="button" @click="save">Save</button>
                <button type="button" @click="cancel">Cancel</button>
            </div>
        </form>
    </div>
</template>

<script>
export default {
    name: "ParkingAddForm",
    props: {
        slots: {
            type: Array,
            required: true,
        },
    },
    data() {
        return {
            location: "",
            price: "",
            status:"Available",
            // timeFrom: null,
            // timeUntil: null,
        };
    },
    computed: {
        // timeFrom: {
        //     get() {
        //         return this.timeFrom ? this.timeFrom.toISOString().slice(0, -8) : "";
        //     },
        //     set(value) {
        //         this.timeFrom = value ? new Date(value) : null;
        //     },
        // },
        // timeUntil: {
        //     get() {
        //         return this.timeUntil ? this.timeUntil.toISOString().slice(0, -8) : "";
        //     },
        //     set(value) {
        //         this.timeUntil = value ? new Date(value) : null;
        //     },
        // },
        // minDateTime() {
        //     const now = new Date();
        //     return now.toISOString().slice(0, 16);
        // },
    },
    methods: {
        save() {
            const slot = {
                price: this.price,
                location: this.location,
                // timeFrom: this.timeFrom,
                // timeUntil: this.timeUntil,
                status: this.status
            };
            this.$emit('add-slot', slot);
            this.price = '';
            this.location = '';
            // this.timeFrom = null;
            // this.timeUntil = null;
        },
        cancel() {
            this.price = '';
            this.location = '';
            // this.timeFrom = null;
            // this.timeUntil = null;
            this.$emit('cancel');
        },
    }
};
</script>
