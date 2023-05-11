import HttpService from "@/api/HttpService";
import {API_AUTH_ROUTE} from "@/api/routes";

class AuthService extends HttpService {
    constructor() {
        super(API_AUTH_ROUTE)
    }

    async register(body) {
        const response = await this.http.post(`/register`, body)
        return response.data
    }

    async authenticate(body) {
        const response = await this.http.post(`/authenticate`, body)
        return response.data
    }
}

export default new AuthService();
