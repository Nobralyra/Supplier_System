package finalproject.suppliersystem.supplier.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * read comments in the class AddressServiceTest
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AddressTest {

    private Address address;

    /*
    @Mock
    IAddressRepository iAddressRepository;*/


    //An instance of Address for testing

    @BeforeEach
    void setUp(){
        address = new Address();
        Country country = new Country();
        country.setCountryName("Denmark");
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setSupplierId(5L);
        address.setSupplierId(5L);
        address.setStreetName("Frederiksvej");
        address.setBuildingInformation("47 st.");
        address.setPostalCode(2000);
        address.setPostalDistrict("Frb");
        address.setCountry(country);
        address.setContactInformation(contactInformation);
    }

    @Test
    void alArgsConstructorTest(){
        Country country = new Country();
        ContactInformation contactInformation = new ContactInformation();
        Address address = new Address(1L, "street x", "65B", 1000, "Kbh", country, contactInformation);

        assertNotNull(address);
    }

    @Test
    void getSupplierIdTest(){ assertEquals(5L, address.getSupplierId());}

    @Test
    void getStreetNameTest(){ assertEquals("Frederiksvej", address.getStreetName());}

    @Test
    void getBuildingInformationTest(){ assertEquals("47 st.", address.getBuildingInformation());}

    @Test
    void getPostalCodeTest(){ assertEquals(2000, address.getPostalCode());}

    @Test
    void getPostalDistrictTest(){ assertEquals("Frb", address.getPostalDistrict());}

    @Test
    void getCountryTest(){

        Country country = new Country();
        country.setCountryName("Denmark");

        assertEquals(country.getCountryName(), address.getCountry().getCountryName());
    }

    @Test
    void getContactInformationTest(){

        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setSupplierId(5L);

        assertEquals(contactInformation.getSupplierId(), address.getContactInformation().getSupplierId());
    }

    @Test
    void setSupplierIdTest(){
        address.setSupplierId(6L);
        assertEquals(6L, address.getSupplierId());}

    @Test
    void setStreetNameTest(){
        address.setStreetName("Mathildevej");
        assertEquals("Mathildevej", address.getStreetName());}

    @Test
    void setBuildingInformationTest(){
        address.setBuildingInformation("16B");
        assertEquals("16B", address.getBuildingInformation());}

    @Test
    void setPostalCodeTest(){
        address.setPostalCode(2500);
        assertEquals(2500, address.getPostalCode());}

    @Test
    void setPostalDistrictTest(){
        address.setPostalDistrict("Kbh");
        assertEquals("Kbh", address.getPostalDistrict());}

    @Test
    void setCountryTest(){

        Country country = new Country();
        country.setCountryName("Sweden");
        address.setCountry(country);

        assertEquals("Sweden", address.getCountry().getCountryName());
    }

    @Test
    void setContactInformationTest(){

        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setWebpage("a@b.dk");
        address.setContactInformation(contactInformation);

        assertEquals("a@b.dk", address.getContactInformation().getWebpage());
    }




}
