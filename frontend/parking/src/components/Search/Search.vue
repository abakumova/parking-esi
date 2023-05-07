<template>
    <div class="search-container">
        <input type="text" v-model="searchTerm" placeholder="Search..." class="search-input">
        <button @click="search" class="search-button">Search</button>
    </div>
</template>

<script>
import './Search.css'
import LocationService from "@/api/location/LocationService";
export default {
    data() {
        return {
            searchTerm: '',
        };
    },
    methods: {
        search() {
            // TODO: perform search action
            LocationService.getLocation(this.searchTerm)
                .then((response) => {
                    console.log(response);
                    // Handle the response from the LocationService
                })
                .catch((error) => {
                    console.log(error);
                    // Handle the error from the LocationService
                });
            // this.$router.push({ name: "search" });
        },
    },
};
</script>