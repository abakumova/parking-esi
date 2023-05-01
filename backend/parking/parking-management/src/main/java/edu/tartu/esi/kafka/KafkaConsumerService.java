package edu.tartu.esi.kafka;

import edu.tartu.esi.kafka.message.SlotCreatedMessage;
import edu.tartu.esi.kafka.message.SlotDeletedMessage;
import edu.tartu.esi.kafka.message.SlotUpdatedMessage;
import edu.tartu.esi.model.CarCategoryEnum;
import edu.tartu.esi.model.ParkingRestriction;
import edu.tartu.esi.model.ParkingSlot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class KafkaConsumerService {

    private final KafkaTemplate<String, SlotDeletedMessage> slotDeletedMessageKafkaTemplate;
    private final KafkaTemplate<String, SlotCreatedMessage> slotCreatedMessageKafkaTemplate;
    private final KafkaTemplate<String, SlotUpdatedMessage> slotUpdatedMessageKafkaTemplate;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaConsumerService(
            KafkaTemplate<String, SlotDeletedMessage> slotDeletedMessageKafkaTemplate,
            KafkaTemplate<String, SlotCreatedMessage> slotCreatedMessageKafkaTemplate,
            KafkaTemplate<String, SlotUpdatedMessage> slotUpdatedMessageKafkaTemplate,
            KafkaTemplate<String, Object> kafkaTemplate) {
        this.slotDeletedMessageKafkaTemplate = slotDeletedMessageKafkaTemplate;
        this.slotCreatedMessageKafkaTemplate = slotCreatedMessageKafkaTemplate;
        this.slotUpdatedMessageKafkaTemplate = slotUpdatedMessageKafkaTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendManagementStatusUpdated(ParkingSlot parkingSlot) {
        slotUpdatedMessageKafkaTemplate.send("slot-updated-topic", new SlotUpdatedMessage(UUID.randomUUID().toString(), parkingSlot.getId(), parkingSlot.getParkingSlotStatus()));
    }

    public void sendManagementStatusDeleted(String id) {
        slotDeletedMessageKafkaTemplate.send("slot-deleted-topic", new SlotDeletedMessage(UUID.randomUUID().toString(), id));
    }

    public void sendManagementStatusCreated(ParkingSlot parkingSlot) {
        String requestId = UUID.randomUUID().toString();
        List<CarCategoryEnum> categories = parkingSlot.getParkingRestrictions()
                .stream()
                .map(ParkingRestriction::getCategory)
                .toList();
        SlotCreatedMessage slotCreatedMessage = new SlotCreatedMessage(requestId, parkingSlot.getId(), parkingSlot.getParkingSlotStatus(),
                parkingSlot.getLocation(), parkingSlot.getPrice(), CarCategoryEnum.valueOf(categories.get(0).toString()));
        log.warn("-- Message {}", slotCreatedMessage);
        slotCreatedMessageKafkaTemplate.send("slot-created-topic", slotCreatedMessage);
    }
}
