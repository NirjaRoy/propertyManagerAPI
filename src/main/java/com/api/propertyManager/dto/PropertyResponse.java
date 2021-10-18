package com.api.propertyManager.dto;

import java.io.Serializable;

import com.api.propertyManager.model.PropertyDetails;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyResponse implements Serializable{

	private PropertyDto propertyDto;
	private String statusCode;
	private String message;
}

