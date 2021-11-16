package com.api.propertyManager;

import com.api.propertyManager.model.OwnerDetails;
import com.api.propertyManager.repository.OwnerRepository;
import com.api.propertyManager.service.OwnerServiceImpl;
import lombok.var;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OwnerServiceImplTest {
    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @Test
    public void testGetOwner(){
        when(ownerRepository.findAll()).thenReturn(DummyOwnerList());
        Assert.assertEquals(ownerService.getOwner().size(),2);
    }

    @Test
    public void testGetOwner_failure(){
        when(ownerRepository.findAll()).thenReturn(null);
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            ownerService.getOwner();
        });
        Assert.assertTrue(exception.getMessage().contains("No owner details exists"));
    }

    @Test
    public void testGetOwnerByCode(){
         when(ownerRepository.findById(1L)).thenReturn(Optional.of( DummyOwnerList().get(0)));

        var response = ownerService.getOwnerByCode(1L);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getMessage(),"Owner fetched successfully");
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK.toString());
        Assert.assertEquals("Nirja",response.getOwnerDetails().ownerName);
    }

    @Test
    public void testGetOwnerByCode_failure(){
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            ownerService.getOwnerByCode(1000L);
        });

        Assert.assertTrue(exception.getMessage().contains("No owner exists with code- 10000"));
        Assert.assertEquals(exception.getStatus(),HttpStatus.NOT_FOUND);
    }

    @Test
    public void testAddOwner(){
        OwnerDetails owner =  OwnerDetails.builder()
                .ownerCode(12L)
                .ownerName("Anisha")
                .ownerContact("984566667")
                .ownerAddress("Sector 11, Delhi")
                .build();

        var response = ownerService.addOwner(owner);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getMessage(),"Owner successfully registered");
        Assert.assertEquals(response.getStatusCode(),HttpStatus.CREATED.toString());
        Assert.assertEquals("Anisha",response.getOwnerDetails().ownerName);
    }

    @Test
    public void testDeleteOwner() {
        when(ownerRepository.findById(1L)).thenReturn(Optional.of(DummyOwnerList().get(0)));
        var response = ownerService.deleteOwner(1L);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getMessage(),"Owner deleted successfully");
        Assert.assertEquals(response.getStatusCode(),HttpStatus.ACCEPTED.toString());
    }

    @Test
    public void testDeleteOwner_failure() {
        when(ownerRepository.findById(1000L)).thenReturn(null);

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            ownerService.deleteOwner(10000L);
        });

        Assert.assertTrue(response.getMessage().contains("Please enter valid owner code to delete"));
        Assert.assertEquals(response.getStatus(),HttpStatus.BAD_REQUEST);
    }

    public List<OwnerDetails> DummyOwnerList(){
        OwnerDetails owner1 =  OwnerDetails.builder()
                .ownerCode(1L)
                .ownerName("Nirja")
                .ownerContact("984566767")
                .ownerAddress("Sector 110, Delhi")
                .build();
        OwnerDetails owner2 =  OwnerDetails.builder()
                .ownerCode(2L)
                .ownerName("Ana")
                .ownerContact("984566667")
                .ownerAddress("Sector 11, Delhi")
                .build();
        List<OwnerDetails> list = new ArrayList<>();
        list.add(owner1);
        list.add(owner2);
        return list;
    }
}
