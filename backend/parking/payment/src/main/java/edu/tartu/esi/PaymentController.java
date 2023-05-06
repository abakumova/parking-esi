package edu.tartu.esi;

import edu.tartu.esi.model.PaymentStatusEnum;
import edu.tartu.esi.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Validated
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping(value = "/make-payment")
    public ResponseEntity<PaymentStatusEnum> createTransaction(@RequestBody String bookingId) {

        PaymentStatusEnum payment = paymentService.makePayment(bookingId);

        return new ResponseEntity<PaymentStatusEnum>(payment, HttpStatus.OK);
    }
}
