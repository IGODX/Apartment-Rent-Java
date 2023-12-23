package com.example.rent.specifications;

import com.example.rent.models.Client;
import org.springframework.data.jpa.domain.Specification;

public class ClientSpecification {

    public static Specification<Client> hasFullName(String fullName) {
        return (root, query, builder) ->
                builder.like(
                        builder.function(
                                "CONCAT",
                                String.class,
                                builder.concat(root.get("user").get("firstname"), " "),
                                builder.concat(root.get("user").get("surname"), " "),
                                builder.concat(root.get("user").get("patronymic"), " ")
                        ),
                        "%" + fullName + "%"
                );
    }

    public static Specification<Client> hasUserTelephoneNumber(String telephoneNumber) {
        return (root, query, builder) -> builder.equal(root.get("user").get("telephoneNumber"), telephoneNumber);
    }

    public static Specification<Client> hasNumRooms(int numRooms) {
        return (root, query, builder) -> builder.equal(root.get("apartmentPreference").get("numRooms"), numRooms);
    }

    public static Specification<Client> hasAddress(String address) {
        return (root, query, builder) -> builder.equal(root.get("apartmentPreference").get("address"), address);
    }
}
