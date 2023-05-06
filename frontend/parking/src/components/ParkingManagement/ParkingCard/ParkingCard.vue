<template>
    <div class="parking-card">
        <h2>{{ name }}</h2>
        <p>Status: {{ status }}</p>
        <p>Price: {{ price }} €</p>
        <p>Time from: {{ timeFrom }}</p>
        <p>Time until: {{ timeUntil }}</p>
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
        price: {
            type: Number,
            required: true
        },
        timeFrom: {
            type: String,
            required: true
        },
        timeUntil: {
            type: String,
            required: true
        }
    },
}
</script>
