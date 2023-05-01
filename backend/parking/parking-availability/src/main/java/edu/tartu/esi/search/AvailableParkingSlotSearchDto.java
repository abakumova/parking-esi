package edu.tartu.esi.search;

import edu.tartu.esi.model.AvailableParkingSlot;
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
public class AvailableParkingSlotSearchDto extends GenericSearchDto<AvailableParkingSlot> {

    private String price;

    protected void addFilters(Root<AvailableParkingSlot> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, List<Predicate> filters) {
        if (StringUtils.isNotEmpty(price)) {
            Predicate emailAsPredicate = criteriaBuilder.equal(root.get("price"), price);
            filters.add(emailAsPredicate);
        }
    }
}
