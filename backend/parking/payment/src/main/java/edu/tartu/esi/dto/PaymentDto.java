package edu.tartu.esi.dto;

import edu.tartu.esi.model.PaymentStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentDto {

    private String id;
    private String payerId;
    private String receiverId;
    private String bookingId;
    private LocalDateTime time;
    private BigDecimal amount;
    private PaymentStatusEnum status;
}
