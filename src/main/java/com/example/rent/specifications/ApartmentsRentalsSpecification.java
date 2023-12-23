package com.example.rent.specifications;

import com.example.rent.models.ApartmentsRentals;
import org.springframework.data.jpa.domain.Specification;

public class ApartmentsRentalsSpecification {
    public static Specification<ApartmentsRentals> hasFormattedRentStartMonth(String formattedRentStartMonth) {
        return (root, query, builder) ->
                builder.equal(builder.function("DATE_FORMAT", String.class, root.get("startDate"), builder.literal("%Y-%m")), formattedRentStartMonth);
    }

    public static Specification<ApartmentsRentals> hasFormattedEndDate(String formattedEndDate) {
        return (root, query, builder) ->
                builder.equal(builder.function("DATE_FORMAT", String.class, root.get("endDate"), builder.literal("%Y-%m")), formattedEndDate);
    }

    public static Specification<ApartmentsRentals> hasShortAverageRentTerm() {
        return (root, query, builder) ->
                builder.lessThan(builder.function("DATEDIFF", Integer.class, root.get("endDate"), root.get("startDate")), 30);
    }

    public static Specification<ApartmentsRentals> hasLongAverageRentTerm() {
        return (root, query, builder) ->
                builder.greaterThan(builder.function("DATEDIFF", Integer.class, root.get("endDate"), root.get("startDate")), 365);
    }
}
