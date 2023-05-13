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

    async getParkingSlotsByLocation(lat, lon) {
        console.log(`Lat ${lat} \t lon: ${lon}`)
        return (await this.http.get(`/by-location/${lat}/${lon}`)).data;
    }

    async getParkingSlotById(slotId) {
        return (await this.http.get(`/by-id/${slotId}`)).data;
    }

}

export default new ParkingManagementService();
