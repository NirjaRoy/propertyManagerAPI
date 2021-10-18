package com.api.propertyManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.propertyManager.model.PropertyDetails;

public interface PropertyRepository extends JpaRepository<PropertyDetails, Integer> {
	
	@Query(value = "SELECT * FROM property_details WHERE property_location LIKE %?1%", nativeQuery = true)
	public List<PropertyDetails> getPropertyDetailsByLocation(String location);

	@Query(value = "SELECT * FROM property_details WHERE property_name = ?1 and property_location = ?2", nativeQuery = true)
	public PropertyDetails getPropertyByNameAndLocation(String name,String location);
}