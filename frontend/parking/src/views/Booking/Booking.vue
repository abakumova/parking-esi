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
                <input type="datetime-local" id="timeFrom" v-model="timeFrom" required>
                <span class="error-message" v-if="timeFromError">{{ timeFromError }}</span>
            </div>

            <div class="form-group">
                <label for="timeUntil">Time Until:</label>
                <input type="datetime-local" id="timeUntil" v-model="timeUntil" required>
                <span class="error-message" v-if="timeUntilError">{{ timeUntilError }}</span>
            </div>
        </div>

        <div class="row">
            <div class="form-group">
                <label for="cardNumber">Card Number:</label>
                <input type="text" id="cardNumber" v-model="cardNumber" pattern="[0-9]{4}\s[0-9]{4}\s[0-9]{4}\s[0-9]{4}" required>
                <span class="error-message" v-if="cardNumberError">{{ cardNumberError }}</span>
            </div>
        </div>

        <div class="row">
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
                <span class="error-message" v-if="expirationMonthError">{{ expirationMonthError }}</span>
            </div>

            <div class="form-group">
                <label for="expirationYear">Expiration Year:</label>
                <input type="text" id="expirationYear" v-model="expirationYear" pattern="[0-9]{4}" required>
                <span class="error-message" v-if="expirationYearError">{{ expirationYearError }}</span>
            </div>
            <div class="form-group">
                <label for="cvv">CVV:</label>
                <input type="text" id="cvv" v-model="cvv" pattern="[0-9]{3,4}" required>
                <span class="error-message" v-if="cvvError">{{ cvvError }}</span>
            </div>
        </div>

        <button class="submit-button" @click="submitBooking">Submit Booking</button>
    </div>
</template>



<script>
import './Booking.css'
import auth from "@/auth";
import ApiService from "@/api/ApiService";

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
            timeUntil: '',
            timeFromError: '',
            timeUntilError: '',
            cardNumberError: '',
            expirationYearError: '',
            expirationMonthError: '',
            cvvError: ''
        };
    },
    mounted() {
        this.fetchParkingSlot();
    },
    methods: {
        async fetchParkingSlot() {
            try {
                const token = auth.getToken();
                const response = await ApiService.parking.getParkingSlotById(this.parkingSlotId, token);
                this.price = response.data.price;
                this.location.formattedAddress = response.data.location.formattedAddress;
            } catch (error) {
                console.error('Failed to fetch parking slot:', error);
            }
        },
        async submitBooking() {
            const isValid = await this.validateFields();
            if(isValid) {
                const expirationDate = `${this.expirationYear}-${this.expirationMonth}-01`;
                const localDate = new Date(expirationDate).toISOString().slice(0, 10);

                const bookingPayload = {
                    customerId: auth.user.userId,
                    parkingSlotId: this.parkingSlotId,
                    price: this.price,
                    timeFrom: this.timeFrom,
                    timeUntil: this.timeUntil,
                    landlordId: await ApiService.parking.getParkingSlotById(this.parkingSlotId, token).data().landlordId
                };

                try {
                    const token = auth.getToken();
                    const response = await this.createBooking(bookingPayload, token);
                    console.log("Booking submitted successfully:", response);
                } catch (error) {
                    console.error("Failed to submit booking:", error);
                }
            }
        },
        async validateFields() {
            let isValid = true;

            // Validate timeFrom
            if(!this.timeFrom) {
                this.timeFromError = 'Time from is required';
                isValid = false;
            } else if (new Date(this.timeFrom) < new Date()) {
                this.timeFromError = 'Time from cannot be in the past';
                isValid = false;
            } else {
                this.timeFromError = '';
            }


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

            // Validate cardNumber
            if(!this.cardNumber) {
                this.cardNumberError = 'Card number is required';
                isValid = false;
            } else if (!/^\d{12}$/.test(this.cardNumber)) {
                this.cardNumberError = 'Card number must be 12 digits long';
                isValid = false;
            } else {
                this.cardNumberError = '';
            }

            //Validate expirationMonth
            if(!this.expirationMonth) {
                this.expirationMonthError = 'Expiration month is required';
                isValid = false;
            } else if(!this.expirationMonth) {
                this.expirationMonthError = 'Expiration month is required'
                isValid = false
            } else {
                this.expirationMonthError = ''
            }

            // Validate expirationYear
            if(!this.expirationYear) {
                this.expirationYearError = 'Expiration year is required';
                isValid = false;
            } else if (!/^\d{4}$/.test(this.expirationYear)) {
                this.expirationYearError = 'Expiration year must be 4 digits long';
                isValid = false;
            } else {
                this.expirationYearError = '';
            }

            // Validate cvv
            if(!this.cvv) {
                this.cvvError = 'CVV is required';
                isValid = false;
            } else if (!/^\d{3}$/.test(this.cvv)) {
                this.cvvError = 'CVV must be 3 digits long';
                isValid = false;
            } else {
                this.cvvError = '';
            }

            return isValid;
        }

    }
};
</script>

<styles scoped>

</styles>

