package com.api.propertyManager;

import com.api.propertyManager.dto.PropertyDto;
import com.api.propertyManager.dto.PropertyRequest;
import com.api.propertyManager.dto.PropertyResponse;
import com.api.propertyManager.model.PropertyDetails;
import com.api.propertyManager.repository.PropertyRepository;
import com.api.propertyManager.service.PropertyServiceImpl;
import lombok.var;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Assert;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PropertyServiceImplTest {
    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyServiceImpl propertyService;

    @Test
    public void testGetProperty(){
        when(propertyRepository.findAll()).thenReturn(DummyPropertyList());
        Assert.assertEquals(propertyService.getProperty().size(),2);
    }

    @Test
    public void testGetProperty_failure(){
        when(propertyRepository.findAll()).thenReturn(null);
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            propertyService.getProperty();
        });
        Assert.assertTrue(exception.getMessage().contains("No property details exists"));
    }

    @Test
    public void testGetPropertyByCode(){
        PropertyDto propertyDto = PropertyDto.builder()
                .propertyCode(1)
                .propertyName("SFS7 Apt")
                .propertyLocation("Sector 110, Delhi")
                .propertyRent(5000)
                .build();

        when(propertyRepository.findById(1)).thenReturn(Optional.of(DummyPropertyDetails()));

        var response = propertyService.getPropertyByCode(1);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getMessage(),"Property fetched successfully");
        Assert.assertEquals(response.getStatusCode(),HttpStatus.OK.toString());
        Assert.assertEquals(propertyDto.propertyCode,response.getPropertyDto().propertyCode);
    }

    @Test
    public void testGetPropertyByCode_failure(){
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            propertyService.getPropertyByCode(10000);
        });

        Assert.assertTrue(exception.getMessage().contains("No property details exists with property code- 10000"));
        Assert.assertEquals(exception.getStatus(),HttpStatus.NOT_FOUND);
    }


    @Test
    public void testAddProperty(){
        PropertyDto property = PropertyDto.builder()
                .propertyCode(100)
                .propertyName("SFS7 Apt")
                .propertyLocation("Sector 110, Delhi")
                .propertyRent(5000)
                .ownerCode(1)
                .build();

        PropertyRequest request = PropertyRequest.builder()
                .propertyDto(property)
                .build();

        var response = propertyService.addProperty(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getMessage(),"Property has been added successfully");
        Assert.assertEquals(response.getStatusCode(),HttpStatus.CREATED.toString());
        Assert.assertEquals(property.propertyCode,response.getPropertyDto().propertyCode);
    }

    @Test
    public void testDeleteProperty() {
        when(propertyRepository.findById(1)).thenReturn(Optional.of(DummyPropertyDetails()));
        var response = propertyService.deleteProperty(1);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getMessage(),"Property deleted successfully");
        Assert.assertEquals(response.getStatusCode(),HttpStatus.ACCEPTED.toString());
    }

    @Test
    public void testDeleteProperty_failure() {
        when(propertyRepository.findById(1000)).thenReturn(null);

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            propertyService.deleteProperty(10000);
        });

        Assert.assertTrue(response.getMessage().contains("Please enter valid property code to delete"));
        Assert.assertEquals(response.getStatus(),HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetPropertyByName() {
        when(propertyService.getProperty()).thenReturn(DummyPropertyList());

        var response = propertyService.getPropertyByName("SFS2 Apt");
        Assert.assertNotNull(response);
        Assert.assertEquals(1,response.size());
    }

    @Test
    public void testGetPropertyByName_failure() {
        when(propertyService.getProperty()).thenReturn(DummyPropertyList());

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            propertyService.getPropertyByName("SFS3 Apt");
        });
        Assert.assertTrue(response.getMessage().contains("Cannot find Property with name SFS3 Apt"));
        Assert.assertEquals(response.getStatus(),HttpStatus.NOT_FOUND);
    }

    public PropertyResponse DummyResponse(PropertyDto propertyDto){
         return PropertyResponse.builder()
                .propertyDto(propertyDto)
                .statusCode(HttpStatus.OK.toString())
                .message("Property fetched successfully")
                .build();
    }

    public PropertyDetails DummyPropertyDetails(){
        return PropertyDetails.builder()
                .propertyCode(1)
                .propertyName("SFS7 Apt")
                .propertyLocation("Sector 110, Delhi")
                .propertyRent(5000)
                .build();
    }

    public List<PropertyDetails> DummyPropertyList(){
        PropertyDetails p1 =  PropertyDetails.builder()
                .propertyCode(1)
                .propertyName("SFS7 Apt")
                .propertyLocation("Sector 110, Delhi")
                .propertyRent(5000)
                .build();
        PropertyDetails p2 =  PropertyDetails.builder()
                .propertyCode(2)
                .propertyName("SFS2 Apt")
                .propertyLocation("Sector 15, Delhi")
                .propertyRent(9000)
                .build();
        List<PropertyDetails> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        return list;
    }
}
