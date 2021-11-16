package com.api.propertyManager.repository;

import com.api.propertyManager.model.OwnerDetails;
import com.api.propertyManager.model.PropertyDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OwnerRepository extends JpaRepository<OwnerDetails, Long> {

}
