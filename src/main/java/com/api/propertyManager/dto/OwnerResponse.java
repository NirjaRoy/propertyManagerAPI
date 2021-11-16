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
public class OwnerResponse implements Serializable {
    private OwnerDetails ownerDetails;
    private String statusCode;
    private String message;
}
