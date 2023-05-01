package edu.tartu.esi.kafka;

import edu.tartu.esi.kafka.message.*;
import edu.tartu.esi.model.AvailableParkingSlot;
import edu.tartu.esi.model.SlotStatusEnum;
import edu.tartu.esi.service.AvailableParkingSlotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import static java.lang.Double.parseDouble;

@Service
@Slf4j
public class KafkaConsumerService {

    @Autowired
    private AvailableParkingSlotService availableParkingSlotService;
    private final KafkaTemplate<String, BookingPriceResponseMessage> bookingPriceMessageKafkaTemplate;
    private final KafkaTemplate<String, BookingPriceRequestMessage> bookingPriceRequestMessageKafkaTemplate;
    private final KafkaTemplate<String, BookingUpdateStatusMessage> bookingUpdateStatusMessageKafkaTemplate;
    private final KafkaTemplate<String, LocationMessage> locationMessageKafkaTemplate;
    private final KafkaTemplate<String, SlotDeletedMessage> slotDeletedMessageKafkaTemplate;
    private final KafkaTemplate<String, SlotCreatedMessage> slotCreatedMessageKafkaTemplate;
    private final KafkaTemplate<String, SlotUpdatedMessage> slotUpdatedMessageKafkaTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaConsumerService(AvailableParkingSlotService availableParkingSlotService,
                                KafkaTemplate<String, BookingPriceResponseMessage> bookingPriceMessageKafkaTemplate,
                                KafkaTemplate<String, BookingPriceRequestMessage> bookingPriceRequestMessageKafkaTemplate,
                                KafkaTemplate<String, BookingUpdateStatusMessage> bookingUpdateStatusMessageKafkaTemplate,
                                KafkaTemplate<String, LocationMessage> locationMessageKafkaTemplate,
                                KafkaTemplate<String, SlotDeletedMessage> slotDeletedMessageKafkaTemplate,
                                KafkaTemplate<String, SlotCreatedMessage> slotCreatedMessageKafkaTemplate,
                                KafkaTemplate<String, SlotUpdatedMessage> slotUpdatedMessageKafkaTemplate,
                                KafkaTemplate<String, Object> kafkaTemplate) {
        this.availableParkingSlotService = availableParkingSlotService;
        this.bookingPriceMessageKafkaTemplate = bookingPriceMessageKafkaTemplate;
        this.bookingPriceRequestMessageKafkaTemplate = bookingPriceRequestMessageKafkaTemplate;
        this.bookingUpdateStatusMessageKafkaTemplate = bookingUpdateStatusMessageKafkaTemplate;
        this.locationMessageKafkaTemplate = locationMessageKafkaTemplate;
        this.slotDeletedMessageKafkaTemplate = slotDeletedMessageKafkaTemplate;
        this.slotCreatedMessageKafkaTemplate = slotCreatedMessageKafkaTemplate;
        this.slotUpdatedMessageKafkaTemplate = slotUpdatedMessageKafkaTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "booking-price-request-topic", groupId = "availability-group")
    public void listenBookingPrice(@Payload BookingPriceRequestMessage message) {
        log.warn("-- Message is received {}", message.toString());
        AvailableParkingSlot parkingSlot = availableParkingSlotService.findSlotById(message.getSlotId());
        BookingPriceResponseMessage bookingPriceResponseMessage = new BookingPriceResponseMessage(message.getRequestId(), message.getSlotId(), parseDouble(parkingSlot.getPrice()));
        bookingPriceMessageKafkaTemplate.send("booking-price-response-topic", bookingPriceResponseMessage);
    }

    @KafkaListener(topics = "booking-update-status-topic", groupId = "availability-group")
    public void listenBookingStatus(@Payload BookingUpdateStatusMessage message) {
        log.warn("-- Message is received {}", message.toString());
        availableParkingSlotService.updateBookingStatus(message.getSlotId(), message.getStatus());
    }

    @KafkaListener(topics = "slot-updated-topic", groupId = "availability-group")
    public void listenManagementStatusUpdated(@Payload SlotUpdatedMessage message) {
        log.warn("-- Message is received {}", message.toString());
        availableParkingSlotService.updateManagementStatus(message.getSlotId(), message.getStatus());
    }

    @KafkaListener(topics = "slot-deleted-topic", groupId = "availability-group")
    public void listenManagementStatusDeleted(@Payload SlotDeletedMessage message) {
        log.warn("-- Message is received {}", message.toString());
        availableParkingSlotService.deleteSlot(message.getSlotId());
    }

    @KafkaListener(topics = "slot-created-topic", groupId = "availability-group")
    public void listenManagementStatusCreated(@Payload SlotCreatedMessage message) {
        log.warn("-- Message is received {}", message.toString());
        AvailableParkingSlot parkingSlot = AvailableParkingSlot.builder()
                .slotId(message.getSlotId())
                .managementStatus(message.getStatus())
                .bookingStatus(SlotStatusEnum.AVAILABLE)
                .category(message.getCategory())
                .location(message.getLocation())
                .price(message.getPrice())
                .build();
        availableParkingSlotService.createSlot(parkingSlot);
    }
}
