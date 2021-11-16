package com.api.propertyManager.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOwnerRequest {
    public long ownerCode;
    public String ownerName;
    public String ownerContact;
    public String ownerAddress;
}
