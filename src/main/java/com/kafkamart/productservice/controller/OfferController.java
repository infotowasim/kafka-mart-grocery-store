package com.kafkamart.productservice.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kafkamart.productservice.dto.request.OfferRequest;
import com.kafkamart.productservice.dto.response.OfferResponse;
import com.kafkamart.productservice.service.OfferService;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(
            OfferService offerService) {

        this.offerService = offerService;
    }

    @PostMapping
    public ResponseEntity<OfferResponse> createOffer(
            @RequestBody OfferRequest request) {

        return ResponseEntity.ok(
                offerService.createOffer(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferResponse> getOfferById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                offerService.getOfferById(id));
    }

    @GetMapping
    public ResponseEntity<List<OfferResponse>> getAllOffers() {

        return ResponseEntity.ok(
                offerService.getAllOffers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfferResponse> updateOffer(
            @PathVariable Long id,
            @RequestBody OfferRequest request) {

        return ResponseEntity.ok(
                offerService.updateOffer(id, request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOffer(
            @PathVariable Long id) {

        offerService.deleteOffer(id);

        return ResponseEntity.ok(
                "Offer Deleted Successfully");
    }
}