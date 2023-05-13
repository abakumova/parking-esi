package edu.tartu.esi;

import edu.tartu.esi.model.PaymentStatusEnum;
import edu.tartu.esi.security.JwtRole;
import edu.tartu.esi.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static edu.tartu.esi.security.Role.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@Validated
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @JwtRole(roles = {ADMIN, USER})
    @PostMapping(value = "/make-payment")
    public ResponseEntity<PaymentStatusEnum> createTransaction(@RequestBody String bookingId) throws JSONException {

        PaymentStatusEnum payment = paymentService.makePayment(bookingId);

        return new ResponseEntity<PaymentStatusEnum>(payment, HttpStatus.OK);
    }
}
