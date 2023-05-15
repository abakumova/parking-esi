import HttpService from "@/api/HttpService";
import {API_AUTH_ROUTE} from "@/api/routes";
import auth from "@/auth";
import { TOAST_CLOSE_TIME} from "@/constants/toast";

class AuthService extends HttpService {
    constructor() {
        super(API_AUTH_ROUTE)
    }

    async register(body) {
        try {
            const response = await this.http.post(`/register`, body)
            const token = response.data.access_token
            localStorage.setItem("access_token", token)
            auth.setUserByCurrentToken(token)

            this.toast.success(`Successfully signed up!`, {
                autoClose:TOAST_CLOSE_TIME
            })

            return response.data
        } catch (e) {
            this.toast.error(`Sign up failed! ${e.toString()}`, {
                autoClose:TOAST_CLOSE_TIME
            })
        }
    }

    async authenticate(body) {
        try {
            const response = await this.http.post(`/authenticate`, body)
            const token = response.data.access_token
            localStorage.setItem("access_token", token)
            auth.setUserByCurrentToken(token)

            this.toast.success(`Successfully signed in!`,{
                autoClose:TOAST_CLOSE_TIME
            })

            return response.data
        } catch (e) {
            this.toast.error(`Sign in failed\n${e.toString()}`,{
                autoClose:TOAST_CLOSE_TIME
            })
        }
    }
}

export default new AuthService();
