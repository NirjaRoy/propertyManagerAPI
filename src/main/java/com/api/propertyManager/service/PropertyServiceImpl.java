package com.api.propertyManager.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.api.propertyManager.dto.PropertyDto;
import com.api.propertyManager.dto.PropertyRequest;
import com.api.propertyManager.dto.PropertyResponse;
import com.api.propertyManager.model.OwnerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.api.propertyManager.model.PropertyDetails;
import com.api.propertyManager.repository.PropertyRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PropertyServiceImpl implements PropertyService{

	@Autowired
    public PropertyRepository propertyRepository;

	@Override
	public List<PropertyDetails> getProperty() {
		List<PropertyDetails> data = propertyRepository.findAll();
		if(data == null)
			throw new NoSuchElementException("No property details exists");
		return data;
	}

	@Override
	public PropertyResponse getPropertyByCode(int propertyCode) {
		PropertyDetails details = propertyRepository.findById(propertyCode)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"No property details exists with property code- "+propertyCode));

		PropertyDto propertyDto = convertPropertyDetailsToPropertyDTO(details);

		return PropertyResponse.builder()
				.propertyDto(propertyDto)
				.statusCode(HttpStatus.OK.toString())
				.message("Property fetched successfully")
				.build();
	}

	@Override
	public PropertyResponse addProperty(PropertyRequest property) {
		PropertyDetails existingProperty = propertyRepository.getPropertyByNameAndLocation(property.getPropertyDto().getPropertyName(),
				property.getPropertyDto().getPropertyLocation());
		if(existingProperty != null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Property already exists");

		PropertyDetails propertyData = convertPropertyRequestToPropertyDetails(property);
		propertyRepository.save(propertyData);

		PropertyDto propertyDto = convertPropertyDetailsToPropertyDTO(propertyData);

		return PropertyResponse.builder()
						.propertyDto(propertyDto)
						.statusCode(HttpStatus.CREATED.toString())
						.message("Property has been added successfully")
						.build();
	}

	private PropertyDto convertPropertyDetailsToPropertyDTO(PropertyDetails propertyData) {
		return PropertyDto.builder()
				.propertyCode(propertyData.getPropertyCode())
				.propertyName(propertyData.getPropertyName())
				.propertyLocation(propertyData.getPropertyLocation())
				.propertyRent(propertyData.getPropertyRent())
				//.ownerCode(propertyData.getOwner().getOwnerCode())
				.build();
	}

	private PropertyDetails convertPropertyRequestToPropertyDetails(PropertyRequest property) {
		OwnerDetails ownerData = OwnerDetails.builder()
				.ownerCode(property.getPropertyDto().getOwnerCode())
				.build();

		return PropertyDetails.builder()
				.propertyCode(property.getPropertyDto().getPropertyCode())
				.propertyName(property.getPropertyDto().getPropertyName())
				.propertyLocation(property.getPropertyDto().getPropertyLocation())
				.propertyRent(property.getPropertyDto().getPropertyRent())
				.owner(ownerData)
				.build();
	}

	@Override
	public PropertyResponse updateProperty(PropertyRequest property) {
		PropertyDetails existingProperty = propertyRepository.findById(property.getPropertyDto().propertyCode)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Please enter valid property code to update- "+property.getPropertyDto().propertyCode));

		PropertyDetails propertyDetails = convertPropertyRequestToPropertyDetails(property);
		PropertyDetails updatedProperty = propertyRepository.save(propertyDetails);
		PropertyDto updatedPropertyDto = convertPropertyDetailsToPropertyDTO(updatedProperty);

		return PropertyResponse.builder()
				.propertyDto(updatedPropertyDto)
				.statusCode(HttpStatus.OK.toString())
				.message("Property updated successfully")
				.build();
	}

	@Override
	public PropertyResponse deleteProperty(int code) {
		PropertyDetails data = propertyRepository.findById(code)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Please enter valid property code to delete- "));

		propertyRepository.deleteById(code);
		return PropertyResponse.builder()
				.statusCode(HttpStatus.ACCEPTED.toString())
				.message("Property deleted successfully")
				.build();
	}

	@Override
	public List<PropertyDetails> getPropertyByName(String name) {
		List<PropertyDetails> propertyList = getProperty().stream()
				.filter(property->property.getPropertyName().contains(name))
				.collect(Collectors.toList());
		if(propertyList.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Cannot find Property with name "+name);
		return propertyList;
	}

	@Override
	public List<PropertyDetails> getPropertyByLocation(String location) {
		List<PropertyDetails> details = propertyRepository.getPropertyDetailsByLocation(location);
		if(details.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Cannot find Property with location "+location);
		return details;
	}
}