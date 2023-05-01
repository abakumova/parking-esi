package edu.tartu.esi.search;

import edu.tartu.esi.model.ParkingSlot;
import edu.tartu.esi.model.SlotStatusEnum;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ParkingSlotSearchDto extends GenericSearchDto<ParkingSlot> {

    private String price;
    private SlotStatusEnum parkingSlotStatus;

    protected void addFilters(Root<ParkingSlot> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, List<Predicate> filters) {
        if (StringUtils.isNotEmpty(price)) {
            Predicate priceAsPredicate = criteriaBuilder.equal(root.get("price"), price);
            filters.add(priceAsPredicate);
        }
        if (StringUtils.isNotEmpty(parkingSlotStatus.getParkingStatusName())) {
            Predicate parkingSlotsStatusAsPredicate = criteriaBuilder.equal(root.get("parkingSlotStatus"), parkingSlotStatus.getParkingStatusName());
            filters.add(parkingSlotsStatusAsPredicate);
        }

        //TODO: filter by restrictions
    }
}
