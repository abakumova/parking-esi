<template>
    <div class="slot-card">
        <div>
            <h2>User Details</h2>
            <div class="grid">
                <div class="grid-item">
                    <label for="slotId">User ID</label>
                    <input type="text" id="slotId" v-model="slot.id" placeholder="slot id" disabled>
                </div>
                <div class="grid-item">
                    <label for="firstName">First Name</label>
                    <input type="text" id="firstName" v-model="slot.firstName" placeholder="first name">
                    <span class="error-message" v-if="firstNameError">{{ firstNameError }}</span>
                </div>
                <div class="grid-item">
                    <label for="lastName">Last Name</label>
                    <input type="text" id="lastName" v-model="slot.lastName" placeholder="last name">
                    <span class="error-message" v-if="lastNameError">{{ lastNameError }}</span>
                </div>
                <div class="grid-item">
                    <label for="email">Email</label>
                    <input type="email" id="email" v-model="slot.email" placeholder="email">
                    <span class="error-message" v-if="emailError">{{ emailError }}</span>

                </div>
                <div class="grid-item">
                    <label for="role">Role</label>
                    <select id="role" v-model="slot.role">
                        <option value="LANDLORD">Landlord</option>
                        <option value="USER">User</option>
                        <option value="ADMIN">Admin</option>
                    </select>
                </div>
            </div>
        </div>
        <div>
            <h2>Payment Method</h2>
            <div class="grid">
                <div class="grid-item">
                    <label for="cardNumber">Card Number</label>
                    <input type="text" id="cardNumber" v-model="slot.paymentMethod.cardNumber" placeholder="card number">
                    <span class="error-message" v-if="cardNumberError">{{ cardNumberError }}</span>

                </div>
                <div class="grid-item">
                    <label for="cardHolderName">Card Holder Name</label>
                    <input type="text" id="cardHolderName" v-model="slot.paymentMethod.cardHolderName" placeholder="card holder name">
                    <span class="error-message" v-if="cardHolderNameError">{{ cardHolderNameError }}</span>

                </div>
                <div class="grid-item">
                    <label for="balance">Balance</label>
                    <input type="text" id="balance" v-model="slot.paymentMethod.balance" placeholder="balance">
                    <span class="error-message" v-if="balanceError">{{ balanceError }}</span>

                </div>
                <div class="grid-item">
                    <label for="cvv">CVV</label>
                    <input type="text" id="cvv" v-model="slot.paymentMethod.cvv" placeholder="cvv">
                    <span class="error-message" v-if="cvvError">{{ cvvError }}</span>

                </div>
                <div class="grid-item">
                    <label for="expirationDate">Expiration Date</label>
                    <input type="text" id="expirationDate" v-model="slot.paymentMethod.expirationDate" placeholder="expiration date">
                    <span class="error-message" v-if="expirationDateError">{{ expirationDateError }}</span>

                </div>
                <div class="grid-item">
                    <label for="iban">IBAN</label>
                    <input type="text" id="iban" v-model="slot.paymentMethod.iban" placeholder="IBAN">
                    <span class="error-message" v-if="ibanError">{{ ibanError }}</span>
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
