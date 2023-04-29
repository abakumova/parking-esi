package edu.tartu.esi.search;

import edu.tartu.esi.model.Booking;
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

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class BookingSearchDto extends GenericSearchDto<Booking> {

    private String price;
    private LocalDateTime timeFrom;
    private LocalDateTime timeUntil;
    protected void addFilters(Root<Booking> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, List<Predicate> filters) {
        if (StringUtils.isNotEmpty(price)) {
            Predicate priceAsPredicate = criteriaBuilder.equal(root.get("price"), price);
            filters.add(priceAsPredicate);
        }

        if(StringUtils.isNotEmpty(timeFrom.toString())) {
            Predicate timeFromAsPredicate = criteriaBuilder.equal(root.get("timeFrom") , timeFrom);
            filters.add(timeFromAsPredicate);
        }

        if(StringUtils.isNotEmpty(timeUntil.toString())) {
            Predicate timeUntilAsPredicate = criteriaBuilder.equal(root.get("timeUntil") , timeUntil);
            filters.add(timeUntilAsPredicate);
        }
        //TODO: add more custom filters;
    }
}
