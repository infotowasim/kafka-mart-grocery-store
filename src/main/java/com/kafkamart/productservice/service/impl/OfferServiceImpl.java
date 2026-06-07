package com.kafkamart.productservice.service.impl;


import org.springframework.stereotype.Service;

import com.kafkamart.productservice.dto.request.OfferRequest;
import com.kafkamart.productservice.dto.response.OfferResponse;
import com.kafkamart.productservice.entity.Offer;
import com.kafkamart.productservice.repository.OfferRepository;
import com.kafkamart.productservice.service.OfferService;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    public OfferServiceImpl(
            OfferRepository offerRepository) {

        this.offerRepository = offerRepository;
    }

    @Override
    public OfferResponse createOffer(
            OfferRequest request) {

        Offer offer = new Offer();

        offer.setOfferName(request.getOfferName());
        offer.setOfferCode(request.getOfferCode());
        offer.setDiscountPercentage(
                request.getDiscountPercentage());
        offer.setDescription(
                request.getDescription());
        offer.setActive(request.getActive());

        Offer savedOffer =
                offerRepository.save(offer);

        OfferResponse response =
                new OfferResponse();

        response.setId(savedOffer.getId());
        response.setOfferName(
                savedOffer.getOfferName());
        response.setOfferCode(
                savedOffer.getOfferCode());
        response.setDiscountPercentage(
                savedOffer.getDiscountPercentage());
        response.setDescription(
                savedOffer.getDescription());
        response.setActive(
                savedOffer.getActive());

        return response;
    }

    @Override
    public OfferResponse getOfferById(Long id) {

        Offer offer = offerRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Offer Not Found"));

        OfferResponse response =
                new OfferResponse();

        response.setId(offer.getId());
        response.setOfferName(
                offer.getOfferName());
        response.setOfferCode(
                offer.getOfferCode());
        response.setDiscountPercentage(
                offer.getDiscountPercentage());
        response.setDescription(
                offer.getDescription());
        response.setActive(
                offer.getActive());

        return response;
    }

    @Override
    public List<OfferResponse> getAllOffers() {

        return offerRepository.findAll()
                .stream()
                .map(offer -> {

                    OfferResponse response =
                            new OfferResponse();

                    response.setId(offer.getId());
                    response.setOfferName(
                            offer.getOfferName());

                    response.setOfferCode(
                            offer.getOfferCode());

                    response.setDiscountPercentage(
                            offer.getDiscountPercentage());

                    response.setDescription(
                            offer.getDescription());

                    response.setActive(
                            offer.getActive());

                    return response;
                })
                .toList();
    }

    @Override
    public OfferResponse updateOffer(
            Long id,
            OfferRequest request) {

        Offer offer = offerRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Offer Not Found"));

        offer.setOfferName(
                request.getOfferName());

        offer.setOfferCode(
                request.getOfferCode());

        offer.setDiscountPercentage(
                request.getDiscountPercentage());

        offer.setDescription(
                request.getDescription());

        offer.setActive(
                request.getActive());

        Offer updatedOffer =
                offerRepository.save(offer);

        OfferResponse response =
                new OfferResponse();

        response.setId(updatedOffer.getId());
        response.setOfferName(
                updatedOffer.getOfferName());

        response.setOfferCode(
                updatedOffer.getOfferCode());

        response.setDiscountPercentage(
                updatedOffer.getDiscountPercentage());

        response.setDescription(
                updatedOffer.getDescription());

        response.setActive(
                updatedOffer.getActive());

        return response;
    }

    @Override
    public void deleteOffer(Long id) {

        Offer offer = offerRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Offer Not Found"));

        offerRepository.delete(offer);
    }
}