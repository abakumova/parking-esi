import axios from 'axios';

class LocationService {
    API_BASE_URL = process.env.VUE_APP_API_BASE_URL;
    API_LOCATION_ROUTE = process.env.VUE_APP_API_LOCATION_ROUTE;

    constructor() {
        this.http = axios.create({
            baseURL: this.API_BASE_URL + this.API_LOCATION_ROUTE
        });
    }

    async getLocation(address) {
        return this.http.get("", {
            headers: { "X-Address": address },
        });
    }
}

export default new LocationService();
