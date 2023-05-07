<template>
    <div class="search-container">
        <input type="text" v-model="searchTerm" placeholder="Search..." class="search-input">
        <button @click="search" class="search-button">Search</button>
    </div>
</template>

<script>
import './Search.css'
export default {
    data() {
        return {
            searchTerm: '',
        };
    },
    methods: {
        search() {
            // TODO: perform search action
            this.$router.push({ name: "search" });
        },
    },
};
</script>