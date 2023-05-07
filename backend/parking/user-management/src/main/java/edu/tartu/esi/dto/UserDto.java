package edu.tartu.esi.dto;

import edu.tartu.esi.model.PaymentMethod;
import edu.tartu.esi.model.UserRoleEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"password", "paymentMethod"})
@ToString(exclude = {"password", "paymentMethod"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Id
    private String id;
    private UserRoleEnum userRole;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private PaymentMethod paymentMethod;
}