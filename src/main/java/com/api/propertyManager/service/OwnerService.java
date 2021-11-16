package com.api.propertyManager.service;

import com.api.propertyManager.dto.OwnerResponse;
import com.api.propertyManager.dto.PropertyRequest;
import com.api.propertyManager.dto.PropertyResponse;
import com.api.propertyManager.dto.UpdateOwnerRequest;
import com.api.propertyManager.model.OwnerDetails;
import com.api.propertyManager.model.PropertyDetails;

import java.util.List;

public interface OwnerService {
    List<OwnerDetails> getOwner();
    OwnerResponse getOwnerByCode(Long code);
    OwnerResponse addOwner(OwnerDetails ownerDetails);
    OwnerResponse updateOwnerDetails(UpdateOwnerRequest request);
    OwnerResponse deleteOwner(Long code);
}
