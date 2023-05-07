import axios from 'axios';

class UserManagementService {
    API_BASE_URL = process.env.VUE_APP_API_BASE_URL;
    API_USERS_ROUTE = process.env.VUE_APP_API_USERS_ROUTE;

    constructor() {
        this.http = axios.create({
            baseURL: this.API_BASE_URL + this.API_USERS_ROUTE
        });
    }

    async getUserById(id) {
        const response = await this.http.get(`/${id}`);
        return response.data;
    }

    async updateUser(id, user) {
        const response = await this.http.put(`/${id}`, user);
        return response.data;
    }

    async deleteUser(id) {
        await this.http.delete(`/${id}`);
    }

    async getUserBalance(id) {
        const response = await this.http.get(`/${id}/balance`);
        return response.data;
    }

    async updateUserBalance(id, balance) {
        const response = await this.http.put(`/${id}/balance`, { balance });
        return response.data;
    }

    async getUsers() {
        const response = await this.http.get('');
        return response.data;
    }

    async createUser(user) {
        const response = await this.http.post('', user);
        return response.data;
    }
}

export default new UserManagementService();
