<template>
    <div class="parking-add-form">
        <form @submit.prevent="save">
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" v-model="name">
                <div v-if="!name" class="error-message">Name is required</div>
            </div>
            <div class="form-group">
                <label for="price">Price:</label>
                <input type="number" id="price" v-model="price">
                <div v-if="!price" class="error-message">Price is required</div>
            </div>
            <div class="form-group">
                <label for="location">Location:</label>
                <input type="text" id="location" v-model="location">
                <div v-if="!location" class="error-message">Location is required</div>
                <div v-if="locationError" class="error-message">{{ locationError }}</div>
            </div>
            <div class="form-buttons">
                <button type="button" @click="save">Save</button>
                <button type="button" @click="cancel">Cancel</button>
            </div>
        </form>
    </div>
</template>

<script>
import "./ParkingAddForm.css"
import ApiService from "@/api/ApiService";

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
            name: "",
            location: "",
            price: "",
            status:"Available",
            locationError: "",
        };
    },
    methods: {
        async save() {
            if (!this.name || !this.price || !this.location)
                return;
            const locationData = (await ApiService.location.getLocation(this.location))
            if (!locationData) {
                this.locationError = "Invalid location";
                return;
            }

            const slot = {
                name: this.name,
                price: this.price,
                location: locationData,
                status: this.status
            };
            this.$emit('add-slot', slot);
            this.name = '';
            this.price = '';
            this.location = '';
        },
        cancel() {
            this.name = '';
            this.price = '';
            this.location = '';
            this.$emit('cancel');
        },
    }
};
</script>
