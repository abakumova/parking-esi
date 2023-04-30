package edu.tartu.esi.kafka.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequestMessage {
    private String requestId;
    private String userId;
}
