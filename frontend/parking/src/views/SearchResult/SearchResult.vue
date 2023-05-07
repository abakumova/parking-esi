<template>
    <div class="search-results-container">
        <h2>Search Results</h2>
        <div class="search-results-list">
            <search-result-card
                    v-for="(result, index) in searchResults"
                    :key="index"
                    :id="result.id"
                    :name="result.name"
                    :price="result.price"
            />
        </div>
    </div>
</template>

<script>
import './SearchResult.css'
import SearchResultCard from '@/components/SearchResultCard/SearchResultCard.vue'

export default {
    name: "SearchResult",
    components: {
        SearchResultCard
    },
    props: {
        searchResults: {
            type: Array,
            default: () => [
                {id:"1", name:"Test1", price:123.4},
                {id:"2", name:"Test1", price:123.4},
                {id:"3", name:"Test1", price:123.4},
                {id:"4", name:"Test1", price:123.4},
                {id:"5", name:"Test1", price:123.4},
                {id:"6", name:"Test1", price:123.4},
                {id:"7", name:"Test1", price:123.4},
                {id:"8", name:"Test1", price:123.4},
                {id:"9", name:"Test1", price:123.4},
            ]
        },
    },
}
</script>