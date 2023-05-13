<template>
    <div class="search-container">
        <input type="text" v-model="searchTerm" placeholder="Search..." class="search-input">
        <button @click="search" class="search-button">Search</button>
    </div>
</template>

<script>
import './Search.css'
import ApiService from "@/api/ApiService";
export default {
    data() {
        return {
            searchTerm: '',
        };
    },
    methods: {
        async search() {
            const locationData = await ApiService.location.getLocation(this.searchTerm)
            this.$router.push({ name: "search",
                query: {
                    latitude: locationData.latitude,
                    longitude: locationData.longitude
                }
            });
        },
    },
};
</script>