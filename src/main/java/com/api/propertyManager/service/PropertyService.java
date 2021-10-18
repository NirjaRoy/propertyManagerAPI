package com.api.propertyManager.service;
import java.util.List;

import com.api.propertyManager.dto.*;

import com.api.propertyManager.model.PropertyDetails;

public interface PropertyService {

	List<PropertyDetails> getProperty();
	PropertyResponse getPropertyByCode(int code);
	List<PropertyDetails> getPropertyByName(String name);
	List<PropertyDetails> getPropertyByLocation(String location);
	PropertyResponse addProperty(PropertyRequest propertyDetails);
	PropertyResponse updateProperty(PropertyRequest property);
	PropertyResponse deleteProperty(int code);
}
