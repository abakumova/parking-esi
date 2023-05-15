<template>
    <div class="booking-form">
        <div class="row">
            <div class="form-group">
                <label for="formattedAddress">Address:</label>
                <input type="text" id="formattedAddress" v-model="location.formattedAddress" required disabled>
            </div>
            <div class="form-group">
                <label for="price">Price:</label>
                <input type="text" id="price" v-model="price" required disabled>
            </div>
        </div>

        <div class="row">
            <div class="form-group">
                <label for="timeFrom">Time From:</label>
                <input type="datetime-local" id="timeFrom" v-model="timeFrom" required disabled>
                <span class="error-message" v-if="timeFromError">{{ timeFromError }}</span>
            </div>

            <div class="form-group">
                <label for="timeUntil">Time Until:</label>
                <input type="datetime-local" id="timeUntil" v-model="timeUntil" required>
                <span class="error-message" v-if="timeUntilError">{{ timeUntilError }}</span>
            </div>
        </div>


        <button class="submit-button" @click="submitBooking">Submit Booking</button>
    </div>
</template>



<script>
import './Booking.css'
import auth from "@/auth";
import ApiService from "@/api/ApiService";
import {formatDateLocal} from "@/utils";
import BookingService from "@/api/booking/BookingService";

export default {
    name: "Booking",
    data() {
        return {
            parkingSlotId: this.$route.params.id,
            price: '',
            location: {
                formattedAddress: ''
            },
            timeFrom: formatDateLocal(new Date()),
            timeUntil: '',
            timeFromError: '',
            timeUntilError: '',
            landlordId: '',
        };
    },
    mounted() {
        this.fetchParkingSlot();
    },
    methods: {
        async fetchParkingSlot() {
            try {
                const response = await ApiService.parking.getParkingSlotById(this.parkingSlotId);
                this.price = response.price;
                this.location.formattedAddress = response.location.formattedAddress;
                this.landlordId = response.landlordId;
            } catch (error) {
                console.error('Failed to fetch parking slot:', error);
            }
        },
        async submitBooking() {
            const isValid = await this.validateFields();
            if(isValid) {
                const expirationDate = `${this.expirationYear}-${this.expirationMonth}-01`;
                const localDate = new Date(expirationDate).toISOString().slice(0, 10);
                console.log(this.landlordId);
                const bookingPayload = {
                    customerId: auth.user.userId,
                    parkingSlotId: this.parkingSlotId,
                    price: this.price,
                    timeFrom: this.timeFrom,
                    timeUntil: localDate,
                    landlordId: this.landlordId
                };

                try {
                    const response = await BookingService.createBooking(bookingPayload);
                    console.log("Booking submitted successfully:", response);
                } catch (error) {
                    console.error("Failed to submit booking:", error);
                }
            }
        },
        async validateFields() {
            let isValid = true;

            // Validate timeUntil
            if(!this.timeUntil) {
                this.timeUntilError = 'Time from is required';
                isValid = false;
            } else if (new Date(this.timeUntil) < new Date(this.timeFrom)) {
                this.timeUntilError = 'Time until cannot be before time from';
                isValid = false;
            } else {
                this.timeUntilError = '';
            }

            return isValid;
        }

    }
};
</script>

