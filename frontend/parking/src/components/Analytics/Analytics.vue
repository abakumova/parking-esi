<template>
    <div class="analytics">
        <p>Occupancy: {{ occupancy }}</p>
        <p>Revenue: {{ revenue }}</p>
    </div>
</template>

<script>

import AnalyticsService from "@/api/analytics/AnalyticsService";

export default {
    name: "Analytics",
    props: {
        slotId: {
            type: String,
            required: true,
        }
    },
    data() {
        return {
            occupancy: null,
            revenue: null
        };
    },
    async created() {
        const response = await AnalyticsService.getAnalyticsByParkingSlotId;
        this.occupancy = response.data.occupancy;
        this.revenue = response.data.revenue;
    }
};
</script>
