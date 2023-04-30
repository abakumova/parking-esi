package edu.tartu.esi.kafka.message;

import edu.tartu.esi.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseMessage {

    String requestId;
    String userId;
    PaymentMethod paymentMethod;
}
