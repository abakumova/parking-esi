<template>
    <div class="analytics">
        <p>Occupancy: {{ occupancy }}</p>
        <p>Revenue: {{ revenue }}</p>
        <p>Total Booking count: {{ totalBookingCount }}</p>
        <p>Total Booking duration: {{ totalBookingDuration }}</p>
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
            occupancy: 0,
            revenue: 0,
            totalBookingCount: 0,
            totalBookingDuration: 0
        };
    },
    async created() {
        const response = await AnalyticsService.getAnalyticsByParkingSlotId(this.slotId);
        this.occupancy = response.data.occupancy;
        this.revenue = response.data.revenue;
        this.totalBookingCount = response.data.totalBookingCount;
        this.totalBookingDuration = response.data.totalBookingDuration;
    }
};
</script>
