package edu.tartu.esi.kafka.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserBalanceMessage {

    private String requestId;
    private String userId;
    private String balance;
}
