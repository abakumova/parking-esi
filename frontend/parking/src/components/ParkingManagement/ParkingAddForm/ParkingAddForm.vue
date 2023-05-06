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
        };
    },
    methods: {
        save() {
            const slot = {price: this.price, location: this.location};
            this.$emit('add-slot', slot);
            this.price = '';
            this.location = '';
        },
        cancel() {
            this.price = '';
            this.location = '';
            this.$emit('cancel');
        },
    }
};
</script>
