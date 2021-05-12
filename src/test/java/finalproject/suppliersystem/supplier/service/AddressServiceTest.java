package finalproject.suppliersystem.supplier.service;

import finalproject.suppliersystem.supplier.domain.Address;
import finalproject.suppliersystem.supplier.repository.IAddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Assert-methods:
 * https://www.guru99.com/junit-assert.html#1
 * <p>
 * Spring-guide til getting started with tests:
 * https://spring.io/guides/gs/testing-web/
 * <p>
 * About Mockito-library, that includes for instance @Mock-annotation:
 * https://www.baeldung.com/mockito-annotations
 * <p>
 * ExtendWith-annotation has replaced RunWith-annotation.
 * It enables annotations from Mockito-library:
 * https://www.baeldung.com/junit-5-runwith
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AddressServiceTest
{

    private AddressService addressService;

    /**
     * We create a mock of Repository that is connected to the addressService
     * Annotation Mock is a shortcut to Mockito.mock() method from Mockito-library.
     * It creates a mock object of a class or an interface.
     * The mock is used to return values for class' methods and verify,
     * that methods are called.
     * https://www.baeldung.com/java-spring-mockito-mock-mockbean
     */
    @Mock
    IAddressRepository iAddressRepository;

    /**
     * We need to create an instance of AddressService for testing,
     * so we can interact with this mock object.
     */
    @BeforeEach
    void setUp()
    {
        addressService = new AddressService(iAddressRepository);
    }

    /**
     * This tests, that context is creating an addressService.
     */
    @Test
    public void contextLoads()
    {
        assertNotNull(addressService);
    }

    @Test
    void findAll()
    {
        //We create a list with two addresses to testing.
        Address address1 = new Address();
        Address address2 = new Address();
        ArrayList<Address> addressList = new ArrayList<>();
        addressList.add(address1);
        addressList.add(address2);

        //When we call findAll() in Repository, then it will return this addressList.
        when(iAddressRepository.findAll()).thenReturn(addressList);

        /*
         We perform the test: we ask addressService to findAll()
         and conduct assert-tests: is length 2?
         */

        List<Address> allAdresses = addressService.findAll();
        assertEquals(2, allAdresses.size(), "Length should be 2");

        // verifies, that findAll() in Repository is called and only once
        verify(iAddressRepository, times(1)).findAll();
    }


    @Test
    void save()
    {
        // an address to testing
        Address address = new Address();
        address.setSupplierId(5L);

        //When we call save() in Repository, then it will save address and return it
        when(iAddressRepository.save(any(Address.class))).thenReturn(address);

         /*
         We perform the test: we ask addressService to save()
         and conduct assert-tests:
             - do we have en instance?
             - is supplierId as expected?
         */
        addressService.save(address);

        assertNotNull(address, "We have an address");
        assertEquals(5, address.getSupplierId(), "SupplierId is 5");

        // verifies, that save() in Repository is called and only once
        verify(iAddressRepository, times(1)).save(address);
    }

    @Test
    void findById()
    {
        // an address1 to testing
        Address address1 = new Address();
        address1.setSupplierId(5L);

        //When we call findById() in Repository, then it return this address
        when(iAddressRepository.findById(5L)).thenReturn(Optional.of(address1));

         /*
         We perform the test: we ask addressService to findById()
         and conduct assert-tests:
             - is it the same object?
             - is supplierId the same?
         */
        Address address2 = addressService.findById(5L);
        assertEquals(address2, address2, "It is the same object");
        assertEquals(address1.getSupplierId(), address2.getSupplierId(), "The same id");

        // verifies, that findById() in Repository is called and only once
        verify(iAddressRepository, times(1)).findById(5L);
    }

    /**
     * https://stackoverflow.com/questions/53734415/junit-for-void-delete-method
     */
    @Test
    void deleteById()
    {
        // an address1 to testing
        Address address = new Address();
        address.setSupplierId(5L);

        addressService.deleteByID(5L);

        //verifies, that deleteById() in Repository is called and only once
        verify(iAddressRepository, times(1)).deleteById(any());
    }
}

