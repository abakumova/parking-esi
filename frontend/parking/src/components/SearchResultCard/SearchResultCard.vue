<template>
    <div class="search-result-card">
        <div>{{id}}</div>
        <div class="booking-name">{{ name }}</div>
        <div class="price">{{ price }} EUR</div>
        <button class="book-button" @click="openBooking">Book</button>

    </div>
</template>

<script>
import './SearchResultCard.css'
export default {
    name: "SearchResultCard",
    props: {
        id: {
            type: String
        },
        name: {
            type: String,
            required: true
        },
        price: {
            type: Number,
            required: true
        }
    },
    methods: {
        openBooking() {
            this.$router.push({ name: 'booking', params: { id: this.id }})
        }
    }
}
</script>