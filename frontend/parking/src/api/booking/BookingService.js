import axios from 'axios';


const API_BASE_URL = process.env.VUE_APP_API_BASE_URL;
const API_USERS_ROUTE = process.env.VUE_APP_API_BOOKING_ROUTE;

class BookingService {
    constructor() {
        this.http = axios.create({
            baseURL: API_BASE_URL + API_USERS_ROUTE
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
