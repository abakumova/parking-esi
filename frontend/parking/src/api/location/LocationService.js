import {API_LOCATION_ROUTE} from "@/api/routes";
import HttpService from "@/api/HttpService";

class LocationService extends HttpService{
    constructor() {
        super(API_LOCATION_ROUTE)
    }

    async getLocation(address) {
        const response =  await this.http.get("", {
            params: {
                address: address
            },
        });
        return response.data
    }
}

export default new LocationService();
