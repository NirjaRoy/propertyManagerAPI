package com.api.propertyManager.service;

import com.api.propertyManager.dto.*;
import com.api.propertyManager.model.OwnerDetails;
import com.api.propertyManager.model.PropertyDetails;
import com.api.propertyManager.repository.OwnerRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OwnerServiceImpl  implements OwnerService{

    @Autowired
    public OwnerRepository ownerRepository;

    @Override
    public List<OwnerDetails> getOwner() {
        List<OwnerDetails> data = ownerRepository.findAll();
        if(data == null)
            throw new NoSuchElementException("No owner details exists");
        return data;
    }

    @Override
    public OwnerResponse getOwnerByCode(Long code) {
        OwnerDetails details = ownerRepository.findById(code)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"No owner exists with code- "+code));

        return OwnerResponse.builder()
                .ownerDetails(details)
                .statusCode(HttpStatus.OK.toString())
                .message("Owner fetched successfully")
                .build();
    }

    @Override
    public OwnerResponse addOwner(OwnerDetails ownerDetails) {
        ownerRepository.save(ownerDetails);
        return OwnerResponse.builder()
                .ownerDetails(ownerDetails)
                .statusCode(HttpStatus.CREATED.toString())
                .message("Owner successfully registered")
                .build();
    }

    @Override
    public OwnerResponse updateOwnerDetails(UpdateOwnerRequest request) {
        ownerRepository.findById(request.ownerCode)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Please enter valid owner code to update"));

        OwnerDetails ownerDetails = convertOwnerRequestToOwnerDetails(request);

        ownerRepository.save(ownerDetails);
        return OwnerResponse.builder()
                .ownerDetails(ownerDetails)
                .statusCode(HttpStatus.OK.toString())
                .message("Owner successfully updated")
                .build();
    }

    private OwnerDetails convertOwnerRequestToOwnerDetails(UpdateOwnerRequest ownerRequest) {
        return OwnerDetails.builder()
                .ownerName(ownerRequest.getOwnerName())
                .ownerCode(ownerRequest.getOwnerCode())
                .ownerContact(ownerRequest.getOwnerContact())
                .ownerAddress(ownerRequest.getOwnerAddress())
                .build();
    }

    @Override
    public OwnerResponse deleteOwner(Long code) {
        ownerRepository.findById(code)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Please enter valid owner code to delete"));

        ownerRepository.deleteById(code);
        return OwnerResponse.builder()
                .statusCode(HttpStatus.ACCEPTED.toString())
                .message("Owner deleted successfully")
                .build();
    }
}
