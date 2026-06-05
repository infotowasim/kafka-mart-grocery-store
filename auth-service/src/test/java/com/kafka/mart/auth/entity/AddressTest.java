package com.kafka.mart.auth.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void builderSuccess() {

        User user = User.builder()
                .id(1L)
                .firstName("Wasim")
                .lastName("Akram")
                .email("wasim@gmail.com")
                .phone("9999999999")
                .password("password")
                .build();

        Address address = Address.builder()
                .id(1L)
                .addressLine1("Street 1")
                .addressLine2("Near Market")
                .city("Kolkata")
                .state("West Bengal")
                .country("India")
                .zipCode("700001")
                .user(user)
                .build();

        assertEquals(1L, address.getId());
        assertEquals("Street 1", address.getAddressLine1());
        assertEquals("Near Market", address.getAddressLine2());
        assertEquals("Kolkata", address.getCity());
        assertEquals("West Bengal", address.getState());
        assertEquals("India", address.getCountry());
        assertEquals("700001", address.getZipCode());
        assertEquals(user, address.getUser());
    }

    @Test
    void getterSetterSuccess() {

        Address address = new Address();

        address.setId(2L);
        address.setAddressLine1("Address Line 1");
        address.setAddressLine2("Address Line 2");
        address.setCity("Delhi");
        address.setState("Delhi");
        address.setCountry("India");
        address.setZipCode("110001");

        assertEquals(2L, address.getId());
        assertEquals("Address Line 1", address.getAddressLine1());
        assertEquals("Address Line 2", address.getAddressLine2());
        assertEquals("Delhi", address.getCity());
        assertEquals("Delhi", address.getState());
        assertEquals("India", address.getCountry());
        assertEquals("110001", address.getZipCode());
    }

    @Test
    void noArgsConstructorSuccess() {

        Address address = new Address();

        assertNotNull(address);
    }

    @Test
    void allArgsConstructorSuccess() {

        User user = new User();

        Address address = new Address(
                1L,
                "Line1",
                "Line2",
                "Mumbai",
                "Maharashtra",
                "India",
                "400001",
                user
        );

        assertEquals(1L, address.getId());
        assertEquals("Line1", address.getAddressLine1());
        assertEquals("Line2", address.getAddressLine2());
        assertEquals("Mumbai", address.getCity());
        assertEquals("Maharashtra", address.getState());
        assertEquals("India", address.getCountry());
        assertEquals("400001", address.getZipCode());
        assertEquals(user, address.getUser());
    }
}