package com.kafka.mart.auth.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class RoleConstantsTest {

    @Test
    void constants_shouldHaveExpectedValues() {

        assertEquals(
                "ROLE_ADMIN",
                RoleConstants.ROLE_ADMIN
        );

        assertEquals(
                "ROLE_CUSTOMER",
                RoleConstants.ROLE_CUSTOMER
        );

        assertEquals(
                "ROLE_DELIVERY",
                RoleConstants.ROLE_DELIVERY
        );
    }

    @Test
    void constructor_shouldBeCovered()
            throws Exception {

        Constructor<RoleConstants> constructor =
                RoleConstants.class.getDeclaredConstructor();

        constructor.setAccessible(true);

        RoleConstants instance =
                constructor.newInstance();

        assertNotNull(instance);
    }
}