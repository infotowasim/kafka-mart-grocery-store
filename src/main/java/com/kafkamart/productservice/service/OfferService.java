package com.kafkamart.productservice.service;



import java.util.List;

import com.kafkamart.productservice.dto.request.OfferRequest;
import com.kafkamart.productservice.dto.response.OfferResponse;

public interface OfferService {

    OfferResponse createOffer(OfferRequest request);
    OfferResponse getOfferById(Long id);
    List<OfferResponse> getAllOffers();
    OfferResponse updateOffer(
            Long id,
            OfferRequest request);
    void deleteOffer(Long id);

}
