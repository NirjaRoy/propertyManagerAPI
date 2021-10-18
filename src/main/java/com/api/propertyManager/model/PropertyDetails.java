package com.api.propertyManager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Table(name="property_Details")
public class PropertyDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="property_Code")
	public int propertyCode;

	@Column(name="property_Name")
	public String propertyName;
	
	@Column(name="property_Rent")
	public int propertyRent;
	
	@Column(name="property_Location")
	public String propertyLocation;
	
	@ManyToOne
	@JoinColumn(name="owner_Code", nullable = false)
	private OwnerDetails owner;
}