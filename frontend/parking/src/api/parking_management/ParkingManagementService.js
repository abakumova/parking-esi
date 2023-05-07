import axios from 'axios';

class ParkingManagementService {
    API_BASE_URL = process.env.VUE_APP_API_BASE_URL;
    API_PARKING_ROUTE = process.env.VUE_APP_API_PARKING_ROUTE;

    constructor() {
        this.http = axios.create({
            baseURL: this.API_BASE_URL + this.API_PARKING_ROUTE
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
