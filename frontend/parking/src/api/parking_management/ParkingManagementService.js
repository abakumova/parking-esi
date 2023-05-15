import HttpService from "@/api/HttpService";
import {API_PARKING_ROUTE} from "@/api/routes";
import {TOAST_CLOSE_TIME} from "@/constants/toast";

class ParkingManagementService extends HttpService{

    constructor() {
        super(API_PARKING_ROUTE)
    }

    getParkingSlotByLandlordId(landlordId) {
        try {
            const response = this.http.get(`/by-landlord/${landlordId}`)

            this.toast.success(`Got parking slot by landlord successfully!`,{
                autoClose:TOAST_CLOSE_TIME
            })

            return response;
        } catch (e){
            this.toast.error(`Get parking slot by landlord failed!\n${e.toString()}`,{
                autoClose:TOAST_CLOSE_TIME
            })
        }
    }

    async getParkingSlotById(slotId) {
        try {
            const response = (await this.http.get(`/by-id/${slotId}`)).data;

            this.toast.success(`Got parking slot successfully!`,{
                autoClose:TOAST_CLOSE_TIME
            })

            return response;
        } catch (e) {
            this.toast.error(`Get parking slot failed!\n${e.toString()}`,{
                autoClose:TOAST_CLOSE_TIME
            })
        }
    }

    updateParkingSlot(id, data) {
        try {
            const response = this.http.put(`/${id}`, data);

            this.toast.success(`Deleted parking slot successfully!`,{
                autoClose:TOAST_CLOSE_TIME
            })

            return response;
        } catch (e){
            this.toast.error(`Delete parking slots failed!\n${e.toString()}`,{
                autoClose:TOAST_CLOSE_TIME
            })
        }
    }

    deleteParkingSlot(id) {
        try {
            const response = this.http.delete(`/${id}`);

            this.toast.success(`Deleted parking slot successfully!`,{
                autoClose:TOAST_CLOSE_TIME
            })

            return response;
        } catch (e) {
            this.toast.error(`Delete parking slots failed!\n${e.toString()}`,{
                autoClose:TOAST_CLOSE_TIME
            })
        }
    }

    createParkingSlot(data) {
        try {
            const response = this.http.post(``, data);

            this.toast.success(`Created parking slot successfully!`,{
                autoClose:TOAST_CLOSE_TIME
            })

            return response.data;
        } catch (e) {
            this.toast.error(`Create parking slot failed!\n${e.toString()}`,{
                autoClose:TOAST_CLOSE_TIME
            })
        }
    }

    getParkingSlotsByStatus(status) {
        return this.http.get(`/by-status/${status}`);
    }

    async getParkingSlots({limit, page}) {
        try {
            const response = await this.http.get(``, {
                params: {
                    limit: limit,
                    page: page
                }
            })

            this.toast.success(`Fetched parking slots successfully!`,{
                autoClose:TOAST_CLOSE_TIME
            })

            return response.data;
        } catch (e) {
            this.toast.error(`Fetch parking slots failed!\n${e.toString()}`,{
                autoClose:TOAST_CLOSE_TIME
            })
        }
    }

    async getParkingSlotsByLocation(lat, lon) {
        try {
            const response = (await this.http.get(`/by-location/${lat}/${lon}`)).data;

            this.toast.success(`Parking got by location successfully!`,{
                autoClose:TOAST_CLOSE_TIME
            })

            return response
        } catch (e) {
            this.toast.error(`Parking get by location failed!\n${e.toString()}`,{
                autoClose:TOAST_CLOSE_TIME
            })
        }
    }
}

export default new ParkingManagementService();
