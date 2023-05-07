package edu.tartu.esi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PaymentStatusEnum {

    PENDING("Pending", 1),
    COMPLETED("Completed", 2),
    DECLINED("Declined", 3);

    private String paymentStatusName;
    private int paymentStatusId;

    public static PaymentStatusEnum getPaymentStatusByName(String paymentSlotName) {
        if (paymentSlotName.equals(PENDING.getPaymentStatusName())) {
            return PENDING;
        } else if (paymentSlotName.equals(COMPLETED.getPaymentStatusName())){
            return COMPLETED;
        } else {
            return DECLINED;
        }
    }

    public static PaymentStatusEnum getPaymentStatusById(int parkingStatusId) {
        return switch (parkingStatusId) {
            case 1 -> PENDING;
            case 2 -> COMPLETED;
            case 3 -> DECLINED;
            default -> throw new IllegalStateException("Unexpected value: " + parkingStatusId);
        };
    }
}
