package edu.tartu.esi;

import edu.tartu.esi.model.PaymentStatusEnum;
import edu.tartu.esi.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Validated
public class PaymentController {

    @PostMapping(value = "/make-payment")
    public ResponseEntity<PaymentStatusEnum> createTransaction(@RequestBody String bookingId) {

        PaymentStatusEnum payment = PaymentService.makePayment(bookingId);

        return new ResponseEntity<PaymentStatusEnum>(payment, HttpStatus.OK);
    }
}
