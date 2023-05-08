import HttpService from "@/api/HttpService";
import {API_PAYMENT_ROUTE} from "@/api/routes";

class PaymentService extends HttpService{
    constructor() {
        super(API_PAYMENT_ROUTE)
    }

    async makePayment(paymentData) {
        const response = await this.http.post(`/make-payment`, paymentData);
        return response.data;
    }
}

export default new PaymentService();
