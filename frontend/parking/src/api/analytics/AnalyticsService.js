import axios from 'axios';

class AnalyticsService {
    API_BASE_URL=process.env.VUE_APP_API_BASE_URL;
    API_ANALYTICS_ROUTE=process.env.VUE_APP_API_ANALYTICS_ROUTE;

    constructor() {
        this.http = axios.create({
            baseURL: this.API_BASE_URL + this.API_ANALYTICS_ROUTE,
        });
    }

    getAnalyticsByParkingSlotId(parkingSlotId) {
        return this.http.get(`/${parkingSlotId}`);
    }
}

export default new AnalyticsService();
