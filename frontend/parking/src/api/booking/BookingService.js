import axios from 'axios';

class BookingService {
    API_BASE_URL = process.env.VUE_APP_API_BASE_URL;
    API_BOOKING_ROUTE = process.env.VUE_APP_API_BOOKING_ROUTE;

    constructor() {
        this.http = axios.create({
            baseURL: this.API_BASE_URL + this.API_BOOKING_ROUTE
        });
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
