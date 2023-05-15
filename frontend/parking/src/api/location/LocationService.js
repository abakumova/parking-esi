import {API_LOCATION_ROUTE} from "@/api/routes";
import HttpService from "@/api/HttpService";
import {TOAST_CLOSE_TIME} from "@/constants/toast";

class LocationService extends HttpService{
    constructor() {
        super(API_LOCATION_ROUTE)
    }

    async getLocation(address) {
        try {
            const response =  await this.http.get("", {
                params: {
                    address: address
                },
            });

            this.toast.success(`Location got successfully!`,{
                autoClose:TOAST_CLOSE_TIME
            })

            return response.data
        } catch (e) {
            this.toast.error(`Location processing failed!\n${e.toString()}`,{
                autoClose:TOAST_CLOSE_TIME
            })
        }

    }
}

export default new LocationService();
