import axios from "axios";
import {API_BASE_URL} from "@/api/routes";


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
            return config
        })
    }
}

