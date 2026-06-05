package com.kafka.mart.auth.config;

import com.kafka.mart.auth.entity.Role;
import com.kafka.mart.auth.repository.RoleRepository;
import com.kafka.mart.auth.util.RoleConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleDataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        createRole(RoleConstants.ROLE_ADMIN);
        createRole(RoleConstants.ROLE_CUSTOMER);
        createRole(RoleConstants.ROLE_DELIVERY);
    }

    private void createRole(String roleName) {

        if (!roleRepository.existsByName(roleName)) {

            roleRepository.save(
                    Role.builder()
                            .name(roleName)
                            .build()
            );
        }
    }
}