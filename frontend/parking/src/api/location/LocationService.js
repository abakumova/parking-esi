import axios from 'axios';
import {API_LOCATION_ROUTE} from "@/api/routes";
import HttpService from "@/api/HttpService";

class LocationService extends HttpService{
    constructor() {
        super(API_LOCATION_ROUTE)
    }

    async getLocation(address) {
        return this.http.get("", {
            headers: { "X-Address": address },
        });
    }
}

export default new LocationService();
