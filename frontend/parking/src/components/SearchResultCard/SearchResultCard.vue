<template>
    <div class="card">
        <div class="name">{{ slot.name }}</div>
        <div class="price">{{ slot.price }} EUR</div>
        <div class="location">{{ slot.location.formattedAddress }}</div>
        <button class="book-button" @click="openBooking">Book</button>
    </div>
</template>

<script>
import { useRouter } from 'vue-router';
import './SearchResultCard.css'
export default {
    name: "SearchResultCard",
    props: {
        slot: {
            type: Object
        }
    },
    setup(props) {
        const router = useRouter();

        const openBooking = () => {
            router.push({ name: 'booking', params: { id: props.slot.id }})
        }

        return {
            openBooking
        }
    }
}
</script>
