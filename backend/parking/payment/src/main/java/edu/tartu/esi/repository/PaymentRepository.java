package edu.tartu.esi.repository;

import edu.tartu.esi.model.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PaymentRepository extends CrudRepository<Payment, String> {
}
