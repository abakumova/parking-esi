package edu.tartu.esi.model;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String cardNumber;
    private LocalDate expirationDate;
    private String cvv;
    private String cardHolderName;
    private String iban;
}