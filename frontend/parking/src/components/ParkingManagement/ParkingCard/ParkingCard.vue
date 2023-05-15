<template>
    <div className="parking-card">
        <h2>{{ slot.name }}</h2>
        <p>Location: {{ slot.location.formattedAddress }}</p>
        <p>Status: {{ slot.status }}</p>
        <p>Price: {{ slot.price }} €</p>
        <Analytics :slotId="slot.id"/>
    </div>
</template>

<script>
import './ParkingCard.css'
import Analytics from "@/components/Analytics/Analytics.vue";

export default {
    name: "Parking Card",
    components: {
        Analytics
    },
    props: {
        slot: {
            type: Object,
            required: true,
        }
    },
}
</script>
