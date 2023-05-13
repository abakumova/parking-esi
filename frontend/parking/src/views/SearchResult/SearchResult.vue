<template>
    <div class="search-results-container">
        <h2>Search Results</h2>
        <div class="search-results-list">
            <div v-for="searchResult in searchResults" :key="searchResult.id">
                <div class="search-result-card-container">
                    <search-result-card :slot="searchResult"/>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import './SearchResult.css'
import SearchResultCard from '@/components/SearchResultCard/SearchResultCard.vue'
import ApiService from "@/api/ApiService";
import ParkingCard from "@/components/ParkingManagement/ParkingCard/ParkingCard.vue";

export default {
    name: "SearchResult",
    components: {
        ParkingCard,
        SearchResultCard
    },
    data() {
        return {
            searchResults: [],
        };
    },
    watch: {
        '$route.query': {
            handler: 'fetchSearchResults',
            immediate: true,
        },
    },
    methods: {
        async fetchSearchResults() {
            const { latitude, longitude } = this.$route.query;
            if (latitude && longitude) {
                this.searchResults = await ApiService.parking.getParkingSlotsByLocation(
                    latitude,
                    longitude
                );
            } else {
                this.searchResults = [];
            }
        },
    },
}
</script>