import axios from 'axios';

const API_BASE_URL = process.env.VUE_APP_API_BASE_URL;
const API_LOCATION_ROUTE = process.env.VUE_APP_API_LOCATION_ROUTE;

class LocationService {
    constructor() {
        this.http = axios.create({
            baseURL: API_BASE_URL + API_LOCATION_ROUTE
        });
    }

    async getLocation(address) {
        return this.http.get("", {
            headers: { "X-Address": address },
        });
    }
}

export default new LocationService();
