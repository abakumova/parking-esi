import axios from 'axios';

const API_BASE_URL = process.env.VUE_APP_API_BASE_URL;
const API_ANALYTICS_ROUTE = process.env.VUE_APP_API_ANALYTICS_ROUTE;

class AnalyticsService {
    constructor() {
        this.http = axios.create({
            baseURL: API_BASE_URL + API_ANALYTICS_ROUTE,
        });
    }

    getAnalyticsByParkingSlotId(parkingSlotId) {
        return this.http.get(`/${parkingSlotId}`);
    }
}

export default new AnalyticsService();
