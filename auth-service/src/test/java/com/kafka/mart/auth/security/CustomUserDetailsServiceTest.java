package com.kafka.mart.auth.security;

import com.kafka.mart.auth.entity.Role;
import com.kafka.mart.auth.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsTest {

    @Test
    void allMethodsSuccess() {

        Role role = new Role();
        role.setName("ROLE_USER");

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("123456");
        user.setRole(role);
        user.setEnabled(true);
        user.setAccountNonLocked(true);

        CustomUserDetails customUserDetails =
                new CustomUserDetails(user);

        Collection<? extends GrantedAuthority> authorities =
                customUserDetails.getAuthorities();

        assertEquals(
                "ROLE_USER",
                authorities.iterator().next().getAuthority()
        );

        assertEquals(
                "123456",
                customUserDetails.getPassword()
        );

        assertEquals(
                "test@gmail.com",
                customUserDetails.getUsername()
        );

        assertTrue(
                customUserDetails.isAccountNonExpired()
        );

        assertTrue(
                customUserDetails.isAccountNonLocked()
        );

        assertTrue(
                customUserDetails.isCredentialsNonExpired()
        );

        assertTrue(
                customUserDetails.isEnabled()
        );
    }

    @Test
    void accountLockedAndDisabled() {

        Role role = new Role();
        role.setName("ROLE_ADMIN");

        User user = new User();
        user.setRole(role);
        user.setAccountNonLocked(false);
        user.setEnabled(false);

        CustomUserDetails customUserDetails =
                new CustomUserDetails(user);

        assertFalse(
                customUserDetails.isAccountNonLocked()
        );

        assertFalse(
                customUserDetails.isEnabled()
        );
    }
}