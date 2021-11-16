package com.api.propertyManager.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="ownerDetails")
public class OwnerDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="owner_Code")
	public Long ownerCode;
	
	@Column(name="owner_Name")
	public String ownerName;
	
	@Column(name="owner_Contact")
	public String ownerContact;
	
	@Column(name="owner_Address")
	public String ownerAddress;
	
	//@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
	//private Set<PropertyDetails> propertyDetails;
	
}
