<template>
    <div class="parking-card">
        <h2>{{ name }}</h2>
        <p>Location: {{ location.formattedAddress }}</p>
        <p>Status: {{ status }}</p>
        <p>Price: {{ price }} €</p>
    </div>
</template>

<script>
import './ParkingCard.css'
export default {
    name:"Parking Card",
    props: {
        name: {
            type: String,
            required: true
        },
        status: {
            type: String,
            required: true
        },
        location: {
            type: Object,
            required: true
        },
        price: {
            type: Number,
            required: true
        }
    },
}
</script>
