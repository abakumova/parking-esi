import HttpService from "@/api/HttpService";
import {API_BOOKING_ROUTE} from "@/api/routes";
import {TOAST_CLOSE_TIME} from "@/constants/toast";

class BookingService extends HttpService{
    constructor() {
        super(API_BOOKING_ROUTE)
    }

    async getBooking(id) {
        try{
            await this.http.get(`/${id}`);
            const response = await this.http.get(`/${id}`)

            this.toast.success(`Booking got successfully!`,{
                autoClose:TOAST_CLOSE_TIME
            })

            return response.data
        } catch (e) {
            this.toast.error(`Error during fetching booking!\n${e.toString()}`,{
                autoClose:TOAST_CLOSE_TIME
            })
        }



    }

    async updateBooking(id, bookingData) {
        try {
            const response = await this.http.put(`$/${id}`, bookingData)

            this.toast.success(`Booking updated successfully!`,{
                autoClose:TOAST_CLOSE_TIME
            })

            return response.data
        } catch (e) {
            this.toast.error(`Booking update failed!\n${e.toString()}`,{
                autoClose:TOAST_CLOSE_TIME
            })
        }
    }

    async deleteBooking(id) {
        try {
            const response = await this.http.delete(`/${id}`)

            this.toast.success(`Booking deleted successfully!`,{
                autoClose:TOAST_CLOSE_TIME
            })

            return response.data
        } catch (e) {
            this.toast.error(`Booking deletion failed!\n${e.toString()}`,{
                autoClose:TOAST_CLOSE_TIME
            })
        }

    }

    async createBooking(bookingData) {
        try {
            const response = await this.http.post("", bookingData);

            this.toast.success(`Booking created successfully!`,{
                autoClose:TOAST_CLOSE_TIME
            })

            return response.data;
        } catch (e) {
            this.toast.error(`Booking creation failed!\n${e.toString()}`,{
                autoClose:TOAST_CLOSE_TIME
            })
        }

    }

    async getBookingsByUser(userId) {
        try {
            const response = await this.http.get(`/by-user/${userId}`)

            this.toast.success(`Bookings got by user successfully!`,{
                autoClose:TOAST_CLOSE_TIME
            })

            return response.data
        } catch (e) {
                this.toast.error(`Booking get by user failed!\n${e.toString()}`,{
                    autoClose:TOAST_CLOSE_TIME
                })
        }

    }
}

export default new BookingService();
