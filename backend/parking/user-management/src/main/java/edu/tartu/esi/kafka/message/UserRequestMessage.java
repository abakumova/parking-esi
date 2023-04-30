package edu.tartu.esi.kafka.message;

import lombok.Data;

@Data
public class UserRequestMessage {

    private String requestId;
    private String userId;
}
