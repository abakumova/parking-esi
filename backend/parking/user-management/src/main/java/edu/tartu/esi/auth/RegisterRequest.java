package edu.tartu.esi.auth;

import edu.tartu.esi.model.PaymentMethod;
import edu.tartu.esi.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role userRole;
    private PaymentMethod paymentMethod;
}