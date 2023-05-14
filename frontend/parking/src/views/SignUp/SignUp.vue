<template>
    <div class="signup-container">
        <div class="signup-form">
            <h1>
                <router-link to="/">
                    <i class="arrow"/>
                </router-link>
                Sign Up
            </h1>
            <div class="row">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input id="email" type="email" v-model="email" required>
                    <span class="error-message" v-if="emailError">{{ emailError }}</span>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="password">Password</label>
                    <input id="password" type="password" v-model="password" required>
                    <span class="error-message" v-if="passwordError">{{ passwordError }}</span>
                </div>

                <div class="form-group">
                    <label for="role">Role</label>
                    <select id="role" v-model="userRole">
                        <option value="LANDLORD">Landlord</option>
                        <option value="USER">User</option>
                    </select>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="firstName">First Name</label>
                    <input id="firstName" type="text" v-model="firstName" required>
                    <span class="error-message" v-if="firstNameError">{{ firstNameError }}</span>
                </div>

                <div class="form-group">
                    <label for="lastName">Last Name</label>
                    <input id="lastName" type="text" v-model="lastName" required>
                    <span class="error-message" v-if="lastNameError">{{ lastNameError }}</span>
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
                    <label for="expirationMonth">Expiration</label>
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
                    <label for="expirationYear">date</label>
                    <input type="text" id="expirationYear" v-model="expirationYear" pattern="[0-9]{4}" required>
                    <span class="error-message" v-if="expirationYearError">{{ expirationYearError }}</span>
                </div>

                <div class="form-group">
                    <label for="cvv">CVV:</label>
                    <input type="password" id="cvv" v-model="cvv" pattern="[0-9]{3,4}" required>
                    <span class="error-message" v-if="cvvError">{{ cvvError }}</span>
                </div>
            </div>

            <button class="btn-sign-up" @click="signUp">Sign Up</button>
            <button class="btn-go-to-sign-in" @click="openSignIn">Go To Sign In</button>
        </div>
    </div>
</template>

<script>
import './SignUp.css'
import ApiService from "@/api/ApiService";
import {formatDateLocal} from "@/utils";
import {emailRegex, nameRegex} from "@/constants/regex";

export default {
    name: "SignUp",
    data() {
        return {
            email: '',
            password: '',
            firstName: '',
            lastName: '',
            userRole: 'USER',
            balance: '100',
            isValid: false,

            cardNumber: '',
            expirationMonth: '',
            expirationYear: '',
            cvv: '',

            emailError:'',
            passwordError:'',
            firstNameError:'',
            lastNameError:'',

            cardNumberError: '',
            expirationYearError: '',
            expirationMonthError: '',
            cvvError: ''
        }
    },
    mounted() {
        setInterval(() => {
            this.timeFrom = formatDateLocal(new Date());
        }, 1000);
    },
    methods: {
        async signUp() {
            const isValid = await this.validateFields();
            //YYYY-MM-DD
            console.log(`Month: ${this.expirationMonth} \t year: ${this.expirationYear}`)
            console.log(`Is valid: ${isValid}`)
            if(isValid) {
                const userData = {
                    userRole: this.userRole,
                    firstName: this.firstName,
                    lastName: this.lastName,
                    email: this.email,
                    password: this.password,
                    paymentMethod: {
                        cardNumber: this.cardNumber,
                        expirationDate: `${this.expirationYear}-${this.expirationMonth}-15`,
                        cvv: this.cvv,
                        cardHolderName: `${this.firstName} ${this.lastName}`,
                        balance: this.balance,
                    }
                };

                console.log(`Sending: ${userData}`)

                await ApiService.auth.register(userData)
                this.$router.push({name: 'home'})
            }
        },
        openSignIn() {
            this.$router.push({name: 'signin'})
        },
        async validateFields() {
            let isValid = true;

            // Validate email
            if(!this.email) {
                this.emailError = 'Email is required';
                isValid = false;
            } else if (!emailRegex.test(this.email)) {
                this.emailError = 'Email format is invalid';
                isValid = false;
            } else {
                this.emailError = '';
            }

            // Validate password
            if(!this.password) {
                this.passwordError = 'Password is required';
                isValid = false;
            } else if (this.password.length > 50) {
                this.passwordError = 'Max length is 50 symbols';
                isValid = false;
            } else if (this.password.length < 5) {
                this.passwordError = 'Min length is 5 symbols';
                isValid = false;
            } else {
                this.passwordError = '';
            }

            // Validate first name
            if(!this.firstName) {
                this.firstNameError = 'First name is required';
                isValid = false;
            } else if (!nameRegex.test(this.firstName)) {
                this.firstNameError = 'First name should consist only of letters. Length: 5-20';
                isValid = false;
            } else {
                this.firstNameError = '';
            }

            // Validate last name
            if(!this.lastName) {
                this.lastNameError = 'Last name is required';
                isValid = false;
            } else if (!nameRegex.test(this.lastName)) {
                this.lastNameError = 'Last name should consist only of letters. Length: 5-20';
                isValid = false;
            } else {
                this.lastNameError = '';
            }

            // Validate cardNumber
            if(!this.cardNumber) {
                this.cardNumberError = 'Card number is required';
                isValid = false;
            } else if (!/^\d{16}$/.test(this.cardNumber)) {
                this.cardNumberError = 'Card number must be 16 digits long';
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
            } else if (parseInt(this.expirationYear) < 2023 || parseInt(this.expirationYear) > 2030) {
                this.expirationYearError = 'Expiration year must be between 2023 and 2030';
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

            return isValid
        }
    }
}
</script>

<style scoped>

</style>