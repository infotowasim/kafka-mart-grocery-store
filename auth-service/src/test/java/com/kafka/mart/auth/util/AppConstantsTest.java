package com.kafka.mart.auth.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppConstantsTest {

    @Test
    void constructorSuccess() {

        AppConstants appConstants =
                new AppConstants();

        assertNotNull(appConstants);
    }
}