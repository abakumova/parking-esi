<template>
    <div class="slot-card">
        <div>
            <h2>Parking Details</h2>
            <div class="grid">
                <div class="grid-item">
                    <label for="slotId">Slot ID</label>
                    <input type="text" id="slotId" v-model="slot.id" placeholder="slot id" disabled>
                </div>
                <div class="grid-item">
                    <label for="landlordId">Landlord ID</label>
                    <input type="text" id="landlordId" v-model="slot.landlordId" placeholder="landlord id" disabled>
                </div>
                <div class="grid-item">
                    <label for="name">Slot name</label>
                    <input type="text" id="name" v-model="slot.name" placeholder="slot name">
<!--                    <span class="error-message" v-if="lastNameError">{{ lastNameError }}</span>-->
                </div>
                <div class="grid-item">
                    <label for="price">Price</label>
                    <input type="number" id="price" v-model="slot.price" placeholder="price">
<!--                    <span class="error-message" v-if="emailError">{{ emailError }}</span>-->
                </div>
                <div class="grid-item">
                    <label for="status">Status</label>
                    <select id="status" v-model="slot.status">
                        <option value="OPEN">Open</option>
                        <option value="CLOSED">Closed</option>
                    </select>
                </div>
            </div>
        </div>
        <div>
            <h2>Location</h2>
            <div class="grid">
                <div class="grid-item">
                    <label for="formattedAddress">Formatted Address</label>
                    <input type="text" id="formattedAddress" v-model="slot.location.formattedAddress" placeholder="formatted address">
<!--                    <span class="error-message" v-if="cardNumberError">{{ cardNumberError }}</span>-->
                </div>
                <div class="grid-item">
                    <label for="city">City</label>
                    <input type="text" id="city" v-model="slot.location.city" placeholder="city" disabled>
<!--                    <span class="error-message" v-if="cardHolderNameError">{{ cardHolderNameError }}</span>-->
                </div>
                <div class="grid-item">
                    <label for="country">EE</label>
                    <input type="text" id="country" v-model="slot.location.country" placeholder="country" disabled>
<!--                    <span class="error-message" v-if="balanceError">{{ balanceError }}</span>-->
                </div>
                <div class="grid-item">
                    <label for="postalCode">Postal Code</label>
                    <input type="text" id="postalCode" v-model="slot.location.postalCode" placeholder="postal code" disabled>
                    <!--                    <span class="error-message" v-if="ibanError">{{ ibanError }}</span>-->
                </div>
                <div class="grid-item">
                    <label for="latitude">Latitude</label>
                    <input type="text" id="latitude" v-model="slot.location.latitude" placeholder="latitude" disabled>
<!--                    <span class="error-message" v-if="cvvError">{{ cvvError }}</span>-->
                </div>
                <div class="grid-item">
                    <label for="longitude">Longitude</label>
                    <input type="text" id="longitude" v-model="slot.location.longitude" placeholder="longitude" disabled>
<!--                    <span class="error-message" v-if="expirationDateError">{{ expirationDateError }}</span>-->
                </div>

            </div>
        </div>
    </div>
    <div>
        <button @click="$emit('update-slot', slot)">Update</button>
        <button @click="$emit('delete-slot', this.slot.id)">Delete</button>
    </div>
</template>

<script>
import './SlotCard.css'
export default {
    name: "SlotCard",
    props: {
        slot: {
            type: Object,
            required: true,
        }
    },
    data() {
        return {
            emailError:'',
            passwordError:'',
            firstNameError:'',
            lastNameError:'',

            balanceError: '',
            cardHolderNameError: '',
            cardNumberError: '',
            expirationYearError: '',
            expirationMonthError: '',
            cvvError: '',
            expirationDateError: '',
            ibanError:''
        }
    },
    methods: {
        //TODO: Add emit methods and add validations
        // async updateUser() {
        // },
        // async deleteUser() {
        //     console.warn(`USER HAS BEEN DELETED: ${this.slot.id}`)
        //     console.warn(this.slot)
        //     await this.$emit('delete-slot', this.slot.id)
        // },
    },
}
</script>
