import HttpService from "@/api/HttpService";
import {API_USERS_ROUTE} from "@/api/routes";

class UserManagementService extends HttpService{

    constructor() {
        super(API_USERS_ROUTE)
    }

    async getUserById(id, token) {
        const response = await this.http.get(`/${id}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        return response.data;
    }

    async updateUser(id, user) {
        const response = await this.http.put(`/${id}`, user);
        return response.data;
    }

    async deleteUser(id) {
        const response = await this.http.delete(`/${id}`);
        return response.data;
    }

    async getUserBalance(id) {
        const response = await this.http.get(`/${id}/balance`);
        return response.data;
    }

    async updateUserBalance(id, balance) {
        const response = await this.http.put(`/${id}/balance`, { balance });
        return response.data;
    }

    async getUsers({limit, page}) {
        const response = await this.http.get(``, {
            params: {
                limit: limit,
                page: page
            }
        });
        return response.data;
    }

    async createUser(user) {
        const response = await this.http.post('', user);
        return response.data;
    }
}

export default new UserManagementService();
