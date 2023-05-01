package edu.tartu.esi.kafka;

import edu.tartu.esi.model.SlotStatusEnum;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

public class MessageHandler {

    private KafkaTemplate<String, BookingPriceRequestMessage> priceRequest;
    private KafkaTemplate<String, BookingUpdateStatusMessage> statusUpdate;
    private KafkaTemplate<String, BookingParkingRestrictionRequestMessage> restrictionRequest;

    public void sendBookingPriceRequest(String slotId) {
        String requestId = UUID.randomUUID().toString();
        BookingPriceRequestMessage message = new BookingPriceRequestMessage(requestId, slotId);
        priceRequest.send("booking-price-request-topic", message);
    }

    public void sendBookingUpdateStatusMessage(String slotId, SlotStatusEnum status) {
        String requestId = UUID.randomUUID().toString();
        BookingUpdateStatusMessage message = new BookingUpdateStatusMessage(requestId, slotId, status);
        statusUpdate.send("booking-update-status-topic", message);
    }

    public void sendBookingParkingRestrictionRequest(String slotId) {
        String requestId = UUID.randomUUID().toString();
        BookingParkingRestrictionRequestMessage message = new BookingParkingRestrictionRequestMessage(requestId, slotId);
        restrictionRequest.send("booking-restriction-request-topic", message);
    }
}
