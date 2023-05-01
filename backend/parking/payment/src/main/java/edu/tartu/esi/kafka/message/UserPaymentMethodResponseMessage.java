package edu.tartu.esi.kafka.message;

import edu.tartu.esi.dto.PaymentMethodDto;
import lombok.Data;

@Data
public class UserPaymentMethodResponseMessage {

    private String requestId;
    private String userId;
    private PaymentMethodDto paymentMethodDTO;
}
