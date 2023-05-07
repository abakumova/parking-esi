import axios from 'axios';

class PaymentService {
    API_BASE_URL = process.env.VUE_APP_API_BASE_URL;
    API_PAYMENT_ROUTE = process.env.VUE_APP_API_PAYMENT_ROUTE;

    constructor() {
        this.http = axios.create({
            baseURL: this.API_BASE_URL + this.API_PAYMENT_ROUTE
        });
    }

    async makePayment(paymentData) {
        const response = await this.http.post(`/make-payment`, paymentData);
        return response.data;
    }
}

export default new PaymentService();
