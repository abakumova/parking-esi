<template>
    <div class="wrapper">
        <Header v-if="!hideHeader"/>
        <div id="content">
            <router-view />
        </div>
    </div>
</template>

<script>
import Header from '@/components/Header/Header.vue';
import './App.css'

export default {
    name: 'App',
    components: {
        Header,
    },
    computed: {
        hideHeader() {
            return this.$route.path === "/signin" || this.$route.path === "/signup";
        },
    },
};
</script>
<script setup>
</script>