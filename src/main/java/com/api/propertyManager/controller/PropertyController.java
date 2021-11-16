package com.api.propertyManager.controller;

import java.util.Collection;
import java.util.List;
import com.api.propertyManager.dto.PropertyRequest;
import com.api.propertyManager.dto.PropertyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.api.propertyManager.model.PropertyDetails;
import com.api.propertyManager.service.PropertyService;

@RestController
@RequestMapping("/property")
public class PropertyController {

    Logger log = LoggerFactory.getLogger(PropertyController.class);
	@Autowired
    public PropertyService propertyService;
    
    @GetMapping("/")
    Collection<PropertyDetails> getProperty(){
    	return propertyService.getProperty();
    }
    
    @GetMapping("/{code}")
    public PropertyResponse getProperty(@PathVariable Integer code){
    	if(code == 0)
    		throw new IllegalArgumentException("Invalid code");
    	return	propertyService.getPropertyByCode(code);
    }
    
    @PostMapping("/create")
    public PropertyResponse addProperty(@Validated @RequestBody PropertyRequest propertyRequest)  throws Exception {
        log.info("Add Property- request: {}",propertyRequest);
        PropertyResponse response = propertyService.addProperty(propertyRequest);
        log.info("Add Property- response: {}",response);
        return response;
    }

    @PutMapping("/update")
    public PropertyResponse updateProperty(@RequestBody PropertyRequest request) {
        log.info("Update Property- request: {}",request);
        PropertyResponse response = propertyService.updateProperty(request);
        log.info("Update Property- response: {}",response);
        return response;
    }

    @DeleteMapping("/delete/{code}")
    public PropertyResponse deleteProperty(@PathVariable Integer code) {
        if(code == 0)
            throw new IllegalArgumentException("Invalid code");
        return propertyService.deleteProperty(code);
    }

    @GetMapping("/name/{name}")
    List<PropertyDetails> getPropertyByName(@PathVariable String name){
        if(name.equals(""))
            throw new IllegalArgumentException("Invalid name");
        return	propertyService.getPropertyByName(name);
    }

    @GetMapping("/location/{location}")
    List<PropertyDetails> getPropertyByLocation(@PathVariable String location){
        if( location.equals(""))
            throw new IllegalArgumentException("Invalid location");
        return	propertyService.getPropertyByLocation(location);
    }

}
