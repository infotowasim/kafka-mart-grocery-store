package com.kafkamart.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafkamart.productservice.entity.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long> {

}
