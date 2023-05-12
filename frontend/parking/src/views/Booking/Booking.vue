<template>
    <div class="booking-form">
        <h1>Booking for ID {{ parkingSlotId }}</h1>

        <div class="form-group">
            <label for="price">Price:</label>
            <input type="text" id="price" v-model="price" required readonly>
        </div>

        <div class="form-group">
            <label for="formattedAddress">Formatted Address:</label>
            <input type="text" id="formattedAddress" v-model="location.formattedAddress" required readonly>
        </div>

        <div class="form-group">
            <label for="cardNumber">Card Number:</label>
            <input type="text" id="cardNumber" v-model="cardNumber" pattern="[0-9]{16}" required>
        </div>

        <div class="form-group">
            <label for="expirationMonth">Expiration Month:</label>
            <select id="expirationMonth" v-model="expirationMonth" required>
                <option value="">-- Select Month --</option>
                <option value="01">January</option>
                <option value="02">February</option>
                <option value="03">March</option>
                <option value="04">April</option>
                <option value="05">May</option>
                <option value="06">June</option>
                <option value="07">July</option>
                <option value="08">August</option>
                <option value="09">September</option>
                <option value="10">October</option>
                <option value="11">November</option>
                <option value="12">December</option>
            </select>
        </div>

        <div class="form-group">
            <label for="expirationYear">Expiration Year:</label>
            <input type="text" id="expirationYear" v-model="expirationYear" pattern="[0-9]{4}" required>
        </div>

        <div class="form-group">
            <label for="cvv">CVV:</label>
            <input type="text" id="cvv" v-model="cvv" pattern="[0-9]{3,4}" required>
        </div>

        <div class="form-group">
            <label for="timeFrom">Time From:</label>
            <input type="datetime-local" id="timeFrom" v-model="timeFrom" required>
        </div>

        <div class="form-group">
            <label for="timeUntil">Time Until:</label>
            <input type="datetime-local" id="timeUntil" v-model="timeUntil" required>
        </div>

        <button class="submit-button" @click="submitBooking">Submit Booking</button>
    </div>
</template>


<script>
import auth from "@/auth";

export default {
    name: "Booking",
    data() {
        return {
            parkingSlotId: this.$route.params.id,
            price: '',
            location: {
                formattedAddress: ''
            },
            cardNumber: '',
            expirationMonth: '',
            expirationYear: '',
            cvv: '',
            timeFrom: '',
            timeUntil: ''
        };
    },
    mounted() {
        this.fetchParkingSlot();
    },
    methods: {
        async fetchParkingSlot() {
            try {
                const token = auth.getToken();
                const response = await getParkingSlotById(this.parkingSlotId, token);
                this.price = response.data.price;
                this.location.formattedAddress = response.data.location.formattedAddress;
            } catch (error) {
                console.error('Failed to fetch parking slot:', error);
            }
        },
        async submitBooking() {
            const expirationDate = `${this.expirationYear}-${this.expirationMonth}-01`;
            const localDate = new Date(expirationDate).toISOString().slice(0, 10);

            const bookingPayload = {
                customerId: auth.user.userId,
                parkingSlotId: this.parkingSlotId,
                price: this.price,
                timeFrom: this.timeFrom,
                timeUntil: this.timeUntil,
                landlordId: await getParkingSlotById(this.parkingSlotId, token).data().landlordId
            };

            try {
                const token = auth.getToken();
                const response = await this.createBooking(bookingPayload, token);
                console.log("Booking submitted successfully:", response);
            } catch (error) {
                console.error("Failed to submit booking:", error);
            }
        }
    }
};
</script>

