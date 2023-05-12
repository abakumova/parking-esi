<template>
    <div class="parking-card">
        <h2>{{ slot.name }}</h2>
<!--        <p>Parking Id: {{ slot.id }}</p>-->
        <p>Location: {{ slot.location.formattedAddress }}</p>
        <p>Status: {{ slot.status }}</p>
        <p>Price: {{ slot.price }} €</p>
    </div>
</template>

<script>
import './ParkingCard.css'
export default {
    name:"Parking Card",
    props: {
        slot: {
            type: Object,
            required: true,
        }
    },
}
</script>
