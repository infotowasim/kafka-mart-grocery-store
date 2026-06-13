package com.kafka.mart.auth.audit;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BaseAuditEntityTest {

    private static class TestAuditEntity
            extends BaseAuditEntity {
    }

    @Test
    void settersAndGetters_shouldWork() {

        TestAuditEntity entity =
                new TestAuditEntity();

        LocalDateTime now =
                LocalDateTime.of(
                        2025,
                        1,
                        1,
                        10,
                        0
                );

        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        entity.setCreatedBy("SYSTEM");
        entity.setUpdatedBy("ADMIN");
        entity.setVersion(1L);

        assertEquals(
                now,
                entity.getCreatedAt()
        );

        assertEquals(
                now,
                entity.getUpdatedAt()
        );

        assertEquals(
                "SYSTEM",
                entity.getCreatedBy()
        );

        assertEquals(
                "ADMIN",
                entity.getUpdatedBy()
        );

        assertEquals(
                1L,
                entity.getVersion()
        );
    }

    @Test
    void shouldCreateEntity() {

        TestAuditEntity entity =
                new TestAuditEntity();

        assertNotNull(
                entity
        );
    }
}