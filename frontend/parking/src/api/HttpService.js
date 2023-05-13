import axios from "axios";
import {API_BASE_URL} from "@/api/routes";
import auth from "@/auth";


export default class HttpService {
    constructor(route) {
        this.ROUTE=route

        this.http = axios.create({
            baseURL: API_BASE_URL + this.ROUTE
        });
        this.http.defaults.headers.common["Access-Control-Allow-Origin"] = "*"
        this.http.interceptors.request.use(config => {
            config.headers["Content-Type"] = 'application/json'
            config.headers["Access-Control-Allow-Origin"] = "*"
            config.headers["Authorization"] = `Bearer ${auth.getToken()}`

            return config
        })
    }
}

