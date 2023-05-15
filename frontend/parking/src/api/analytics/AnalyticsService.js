import HttpService from "@/api/HttpService";
import {API_ANALYTICS_ROUTE} from "@/api/routes";

class AnalyticsService extends HttpService {

    constructor() {
        super(API_ANALYTICS_ROUTE)
    }

    getAnalyticsByParkingSlotId(parkingSlotId) {
        try {
            const response = this.http.get(`/${parkingSlotId}`);
            return response
        } catch (e) {
            this.toast.error(`Analytics receiving failed\n ${e.toString()}`)
        }
    }
}

export default new AnalyticsService();
