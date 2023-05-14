<template>
    <div class="user-card">
        <div>
            <h2>User Details</h2>
            <div class="grid">
                <div class="grid-item">
                    <label for="userId">User ID</label>
                    <input type="text" id="userId" v-model="user.id" disabled>
                </div>
                <div class="grid-item">
                    <label for="firstName">First Name</label>
                    <input type="text" id="firstName" v-model="user.firstName">
                </div>
                <div class="grid-item">
                    <label for="lastName">Last Name</label>
                    <input type="text" id="lastName" v-model="user.lastName">
                </div>
                <div class="grid-item">
                    <label for="email">Email</label>
                    <input type="email" id="email" v-model="user.email">
                </div>
                <div class="grid-item">
                    <label for="role">Role</label>
                    <input type="text" id="role" v-model="user.role">
                </div>
            </div>
        </div>
        <div>
            <h2>Payment Method</h2>
            <div class="grid">
                <div class="grid-item">
                    <label for="cardNumber">Card Number</label>
                    <input type="text" id="cardNumber" v-model="user.paymentMethod.cardNumber">
                </div>
                <div class="grid-item">
                    <label for="cardHolderName">Card Holder Name</label>
                    <input type="text" id="cardHolderName" v-model="user.paymentMethod.cardHolderName">
                </div>
                <div class="grid-item">
                    <label for="balance">Balance</label>
                    <input type="text" id="balance" v-model="user.paymentMethod.balance">
                </div>
                <div class="grid-item">
                    <label for="cvv">CVV</label>
                    <input type="text" id="cvv" v-model="user.paymentMethod.cvv">
                </div>
                <div class="grid-item">
                    <label for="expirationDate">Expiration Date</label>
                    <input type="text" id="expirationDate" v-model="user.paymentMethod.expirationDate">
                </div>
                <div class="grid-item">
                    <label for="iban">IBAN</label>
                    <input type="text" id="iban" v-model="user.paymentMethod.iban">
                </div>
            </div>
        </div>
    </div>
    <div>
        <button @click="$emit('update-user', user)">Update</button>
        <button @click="$emit('delete-user', user.id)">Delete</button>
    </div>
</template>

<script>
import './UserCard.css'
import ApiService from "@/api/ApiService";
export default {
    name: "UserCard",
    props: {
        user: {
            type: Object,
            required: true,
        }
    },
    methods: {
        // async updateUser() {
        // },
        // async deleteUser() {
        //     console.warn(`USER HAS BEEN DELETED: ${this.user.id}`)
        //     console.warn(this.user)
        //     await this.$emit('delete-user', this.user.id)
        // },
    },
}
</script>
