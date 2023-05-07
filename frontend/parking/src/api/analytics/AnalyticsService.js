import HttpService from "@/api/HttpService";
import {API_ANALYTICS_ROUTE} from "@/api/routes";

class AnalyticsService extends HttpService {

    constructor() {
        super(API_ANALYTICS_ROUTE)
    }

    getAnalyticsByParkingSlotId(parkingSlotId) {
        return this.http.get(`/${parkingSlotId}`);
    }
}

export default new AnalyticsService();
