package edu.tartu.esi.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentMethodDto {

    private String id;
    private String cardNumber;
    private LocalDate expirationDate;
    private String cvv;
    private String cardHolderName;
    private String iban;
    private String balance;
}
