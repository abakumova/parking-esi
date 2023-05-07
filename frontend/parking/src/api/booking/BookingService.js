import HttpService from "@/api/HttpService";
import {API_BOOKING_ROUTE} from "@/api/routes";

class BookingService extends HttpService{
    constructor() {
        super(API_BOOKING_ROUTE)
    }

    async getBooking(id) {
        await this.http.get(`/${id}`);
        const response = await this.http.get(`/${id}`)
        return response.data
    }

    async updateBooking(id, bookingData) {
        const response = await this.http.put(`$/${id}`, bookingData)
        return response.data
    }

    async deleteBooking(id) {
        const response = await this.http.delete(`/${id}`)
        return response.data
    }

    async createBooking(bookingData) {
        const response = await this.http.post(`/`, bookingData)
        return response.data
    }

    async getBookingsByUser(userId) {
        const response = await this.http.get(`/by-user/${userId}`)
        return response.data
    }
}

export default new BookingService();
