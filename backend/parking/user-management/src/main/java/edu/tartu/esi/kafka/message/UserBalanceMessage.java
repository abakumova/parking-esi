package edu.tartu.esi.kafka.message;

import lombok.Data;
@Data
public class UserBalanceMessage {
    private String requestId;    private String userId;
    private String balance;}
