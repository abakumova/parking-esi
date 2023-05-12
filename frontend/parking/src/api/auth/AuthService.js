import HttpService from "@/api/HttpService";
import {API_AUTH_ROUTE} from "@/api/routes";
import auth from "@/auth";

class AuthService extends HttpService {
    constructor() {
        super(API_AUTH_ROUTE)
    }

    async register(body) {
        const response = await this.http.post(`/register`, body)
        const token = response.data.access_token
        localStorage.setItem("access_token", token)
        auth.setUserByCurrentToken(token)

        return response.data
    }

    async authenticate(body) {
        console.log(body)

        const response = await this.http.post(`/authenticate`, body)
        const token = response.data.access_token
        localStorage.setItem("access_token", token)
        auth.setUserByCurrentToken(token)

        return response.data
    }
}

export default new AuthService();
