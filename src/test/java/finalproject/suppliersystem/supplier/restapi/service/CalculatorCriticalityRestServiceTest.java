package finalproject.suppliersystem.supplier.restapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CalculatorCriticalityRestServiceTest {

    private CalculatorCriticalityRestService calculatorCriticalityRestService;

    @BeforeEach
    void setUp()
    {
        calculatorCriticalityRestService = new CalculatorCriticalityRestService();
    }

    @Test
    void calculateCriticalityTest(){
    }


}
