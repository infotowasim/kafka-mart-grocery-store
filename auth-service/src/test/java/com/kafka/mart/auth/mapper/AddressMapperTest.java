package com.kafka.mart.auth.mapper;

import com.kafka.mart.auth.entity.Address;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class AddressMapperTest {

    private final AddressMapper addressMapper =
            Mappers.getMapper(AddressMapper.class);

    @Test
    void toEntitySuccess() {

        Address address =
                Address.builder()
                        .id(1L)
                        .addressLine1("Street 1")
                        .addressLine2("Near Market")
                        .city("Kolkata")
                        .state("West Bengal")
                        .country("India")
                        .zipCode("700001")
                        .build();

        Address result =
                addressMapper.toEntity(address);

        assertNotNull(result);

        assertEquals(
                address.getId(),
                result.getId()
        );

        assertEquals(
                address.getAddressLine1(),
                result.getAddressLine1()
        );

        assertEquals(
                address.getCity(),
                result.getCity()
        );

        assertEquals(
                address.getCountry(),
                result.getCountry()
        );
    }
}