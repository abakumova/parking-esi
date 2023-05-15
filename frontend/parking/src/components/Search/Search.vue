<template>
    <div class="search-container">
        <input type="text" v-model="searchTerm" placeholder="Search..." class="search-input">
        <button @click="search" class="search-button">Search</button>
    </div>
</template>

<script>
import './Search.css'
import ApiService from "@/api/ApiService";
import {toast} from "vue3-toastify";
export default {
    data() {
        return {
            searchTerm: '',
        };
    },
    methods: {
        async search() {
            if(!this.searchTerm) {
                toast.error("Search string should not be empty")
                return
            }
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