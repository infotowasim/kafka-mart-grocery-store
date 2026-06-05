package com.kafka.mart.auth.repository;


import com.kafka.mart.auth.entity.Address;
import com.kafka.mart.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUser(User user);

}
