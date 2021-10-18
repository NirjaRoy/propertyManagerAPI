package com.api.propertyManager.dto;

import com.api.propertyManager.model.PropertyDetails;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyRequest {
    private PropertyDto propertyDto;
}
