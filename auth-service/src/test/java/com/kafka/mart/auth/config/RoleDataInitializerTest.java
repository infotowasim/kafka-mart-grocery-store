package com.kafka.mart.auth.config;

import com.kafka.mart.auth.entity.Role;
import com.kafka.mart.auth.repository.RoleRepository;
import com.kafka.mart.auth.util.RoleConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleDataInitializerTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleDataInitializer roleDataInitializer;

    @Test
    void run_shouldCreateAllRolesWhenNotExist() throws Exception {

        when(
                roleRepository.existsByName(any())
        ).thenReturn(false);

        roleDataInitializer.run();

        verify(roleRepository, times(3))
                .save(any(Role.class));
    }

    @Test
    void run_shouldNotCreateRolesWhenAlreadyExist() throws Exception {

        when(
                roleRepository.existsByName(any())
        ).thenReturn(true);

        roleDataInitializer.run();

        verify(roleRepository, never())
                .save(any(Role.class));
    }

    @Test
    void run_shouldCreateOnlyMissingRoles() throws Exception {

        when(
                roleRepository.existsByName(
                        RoleConstants.ROLE_ADMIN
                )
        ).thenReturn(true);

        when(
                roleRepository.existsByName(
                        RoleConstants.ROLE_CUSTOMER
                )
        ).thenReturn(false);

        when(
                roleRepository.existsByName(
                        RoleConstants.ROLE_DELIVERY
                )
        ).thenReturn(false);

        roleDataInitializer.run();

        verify(roleRepository, times(2))
                .save(any(Role.class));
    }
}