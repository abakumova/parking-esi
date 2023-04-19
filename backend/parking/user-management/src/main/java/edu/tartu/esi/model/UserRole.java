package edu.tartu.esi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_roles")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserRole {

    @Id
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String role;
}