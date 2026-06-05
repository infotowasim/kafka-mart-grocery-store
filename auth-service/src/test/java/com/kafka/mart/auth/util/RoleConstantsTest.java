package com.kafka.mart.auth.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleConstantsTest {

    @Test
    void roleConstantsSuccess() {

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
}