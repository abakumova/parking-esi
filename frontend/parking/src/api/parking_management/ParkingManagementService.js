import axios from 'axios';

const API_BASE_URL = process.env.VUE_APP_API_BASE_URL;
const API_PARKING_ROUTE = process.env.VUE_APP_API_PARKING_ROUTE;

class ParkingManagementService {
    constructor() {
        this.http = axios.create({
            baseURL: API_BASE_URL + API_PARKING_ROUTE
        });
    }
    
    getParkingSlotById(id) {
        return this.http.get(`/${id}`);
    }

    updateParkingSlot(id, data) {
        return this.http.put(`/${id}`, data);
    }

    deleteParkingSlot(id) {
        return this.http.delete(`/${id}`);
    }

    updateParkingSlotStatus(id, data) {
        return this.http.put(`/${id}/status`, data);
    }

    createParkingSlot(data) {
        return this.http.post(`/`, data);
    }

    getParkingSlotsByStatus(status) {
        return this.http.get(`/by-status/${status}`);
    }

    getParkingSlotsByLocation(lat, lon) {
        return this.http.get(`/by-location/${lat}/${lon}`);
    }

    getParkingSlotById(slotId) {
        return this.http.get(`/by-id/${slotId}`);
    }
}

export default new ParkingManagementService();
