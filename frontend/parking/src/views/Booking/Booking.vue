<template>
    <div>
        <h1>Booking for ID {{ bookingId }}</h1>
        <!-- rest of the booking form goes here -->
    </div>
</template>
<script>
export default {
    name: "Booking",
    computed: {
        bookingId() {
            return this.$route.params.id;
        }
    }
}
</script>