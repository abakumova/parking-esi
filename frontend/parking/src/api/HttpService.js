import axios from "axios";

export default class HttpService {
    API_BASE_URL=process.env.VUE_APP_API_BASE_URL;
    constructor(route) {
        this.ROUTE=route
        this.http = axios.create({
            baseURL: this.API_BASE_URL + this.ROUTE
        });
        this.http.interceptors.request.use(config => {
            config.headers["Content-Type"] = 'application/json'
            return config
        })
    }
}

