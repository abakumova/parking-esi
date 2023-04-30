package edu.tartu.esi;

import lombok.Data;

@Data
public class UserBalanceMessage {
    private String requestId;    private String userId;
    private String balance;}
