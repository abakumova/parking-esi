package edu.tartu.esi.mapper;

import edu.tartu.esi.dto.PaymentDto;
import edu.tartu.esi.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentEntityMapper {

    public PaymentDto toDto(Payment payment) {
        PaymentDto dto = new PaymentDto();
        dto.setId(payment.getId());
        dto.setPayerId(payment.getPayerId());
        dto.setReceiverId(payment.getReceiverId());
        dto.setBookingId(payment.getBookingId());
        dto.setTime(payment.getTime());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        return dto;
    }

    public Payment fromDto(PaymentDto dto) {
        Payment payment = new Payment();
        payment.setId(dto.getId());
        payment.setPayerId(dto.getPayerId());
        payment.setReceiverId(dto.getReceiverId());
        payment.setBookingId(dto.getBookingId());
        payment.setTime(dto.getTime());
        payment.setAmount(dto.getAmount());
        payment.setStatus(dto.getStatus());
        return payment;
    }
}
