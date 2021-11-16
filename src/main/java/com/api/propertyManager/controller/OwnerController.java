package com.api.propertyManager.controller;

import com.api.propertyManager.dto.OwnerResponse;
import com.api.propertyManager.dto.UpdateOwnerRequest;
import com.api.propertyManager.model.OwnerDetails;
import com.api.propertyManager.service.OwnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    Logger log = LoggerFactory.getLogger(PropertyController.class);
    @Autowired
    public OwnerService ownerService;

    @GetMapping("/")
    Collection<OwnerDetails> getOwner(){
        return ownerService.getOwner();
    }

    @GetMapping("/{code}")
    public OwnerResponse getOwner(@PathVariable long code){
        if(code == 0)
            throw new IllegalArgumentException("Invalid code");
        OwnerResponse response =  ownerService.getOwnerByCode(code);
        log.info("Get Owner by code-"+code+" response: {}",response);
        return response;
    }

    @PostMapping("/create")
    public OwnerResponse addOwner(@Validated @RequestBody OwnerDetails ownerDetails)  {
        log.info("Add Owner- request: {}",ownerDetails);
        OwnerResponse response = ownerService.addOwner(ownerDetails);
        log.info("Add Owner- response: {}",response);
        return response;
    }

    @PutMapping("/update")
    public OwnerResponse updateOwner(@RequestBody UpdateOwnerRequest request) {
        log.info("Update Owner- request: {}",request);
        OwnerResponse response = ownerService.updateOwnerDetails(request);
        log.info("Update Owner- response: {}",response);
        return response;
    }

    @DeleteMapping("/delete/{code}")
    public OwnerResponse deleteOwner(@PathVariable long code) {
        if(code == 0)
            throw new IllegalArgumentException("Invalid code");
        OwnerResponse response = ownerService.deleteOwner(code);
        log.info("Delete Owner- response: {}",response);
        return response;
    }
}
