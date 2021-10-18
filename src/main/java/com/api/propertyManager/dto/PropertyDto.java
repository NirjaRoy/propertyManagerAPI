package com.api.propertyManager.dto;

import com.api.propertyManager.model.OwnerDetails;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyDto implements Serializable {
    public int propertyCode;
    public String propertyName;
    public int propertyRent;
    public String propertyLocation;
    private long ownerCode;
}
