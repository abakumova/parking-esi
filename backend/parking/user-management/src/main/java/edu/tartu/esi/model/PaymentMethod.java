package edu.tartu.esi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "payment_methods")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethod {

    @Id
    private String id;
    private String cardNumber;
    private LocalDate expirationDate;
    private int cvv;
    private String cardHolderName;
    private String iban;
}