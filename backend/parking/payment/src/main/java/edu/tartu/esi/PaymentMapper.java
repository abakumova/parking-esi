package edu.tartu.esi;

import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentDTO toDto(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setPayerId(payment.getPayerId());
        dto.setReceiverId(payment.getReceiverId());
        dto.setBookingId(payment.getBookingId());
        dto.setTime(payment.getTime());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        return dto;
    }

    public Payment fromDto(PaymentDTO dto) {
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
