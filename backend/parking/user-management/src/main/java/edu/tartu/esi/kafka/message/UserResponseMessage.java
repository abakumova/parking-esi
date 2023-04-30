package edu.tartu.esi.kafka.message;

import edu.tartu.esi.model.PaymentMethod;
import lombok.Data;

@Data
public class UserResponseMessage {

    String requestId;
    String userId;
    PaymentMethod paymentMethod;
}
