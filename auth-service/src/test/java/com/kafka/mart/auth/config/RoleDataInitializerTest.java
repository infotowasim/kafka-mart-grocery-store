package com.kafka.mart.auth.config;

import com.kafka.mart.auth.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleDataInitializerTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleDataInitializer roleDataInitializer;

    @Test
    void runSuccess() throws Exception {

        when(
                roleRepository.existsByName(anyString())
        ).thenReturn(false);

        roleDataInitializer.run();

        verify(
                roleRepository,
                times(3)
        ).save(any());
    }

    @Test
    void roleAlreadyExistsSuccess() throws Exception {

        when(
                roleRepository.existsByName(anyString())
        ).thenReturn(true);

        roleDataInitializer.run();

        verify(
                roleRepository,
                never()
        ).save(any());
    }
}