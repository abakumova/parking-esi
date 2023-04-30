package edu.tartu.esi;

import lombok.Data;

@Data
public class UserResponseMessage {
    private String requestId;
    private String userId;
    private PaymentMethodDTO paymentMethodDTO;
}
