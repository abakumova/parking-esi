import HttpService from "@/api/HttpService";
import {API_PARKING_ROUTE} from "@/api/routes";

class ParkingManagementService extends HttpService{

    constructor() {
        super(API_PARKING_ROUTE)
    }

    getParkingSlotByLandlordId(landlordId) {
        return this.http.get(`/by-landlord/${landlordId}`)
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

    createParkingSlot(data) {
        return this.http.post(``, data);
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
