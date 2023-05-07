import axios from 'axios';

const API_BASE_URL = process.env.VUE_APP_API_BASE_URL;
const API_PAYMENT_ROUTE = process.env.VUE_APP_API_PAYMENT_ROUTE;

class PaymentService {
    constructor() {
        this.http = axios.create({
            baseURL: API_BASE_URL + API_PAYMENT_ROUTE
        });
    }

    async makePayment(paymentData) {
        const response = await this.http.post(`/make-payment`, paymentData);
        return response.data;
    }
}

export default new PaymentService();
