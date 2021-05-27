package finalproject.suppliersystem.supplier.calculatorrestapi.restservice;

import finalproject.suppliersystem.core.enums.CategoryLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class CalculatorCriticalityRestServiceTest {

    private ICalculatorCriticalityRestService ICalculatorCriticalityRestService;

    @BeforeEach
    void setUp()
    {
        ICalculatorCriticalityRestService = new CalculatorCriticalityRestService();
    }

    /**
     * https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-sources-MethodSource
     * Read explanation in CalculatorSupplierRiskelLevelRestServiceTest
     */

    @ParameterizedTest
    @MethodSource("argumentProviderHigh")
    void calculateCriticalityHighTest(Long volume, CategoryLevel supplierRiskLevel){
        assertEquals(CategoryLevel.HIGH, ICalculatorCriticalityRestService.calculateCriticality(volume, supplierRiskLevel));
    }

    @ParameterizedTest
    @MethodSource("argumentProviderMedium")
    void calculateCriticalityMediumTest(Long volume, CategoryLevel supplierRiskLevel){
        assertEquals(CategoryLevel.MEDIUM, ICalculatorCriticalityRestService.calculateCriticality(volume, supplierRiskLevel));
    }

    @ParameterizedTest
    @MethodSource("argumentProviderLow")
    void calculateCriticalityLowTest(Long volume, CategoryLevel supplierRiskLevel){
        assertEquals(CategoryLevel.LOW, ICalculatorCriticalityRestService.calculateCriticality(volume, supplierRiskLevel));
    }

    /*
        factory methods, that provides arguments to calculateCriticalityTest

          volumeLow volume >= 0 && volume <= 10000;
          volume > 10000 && volume <= 30000;
          volumeHigh > 30000;
     */
    private static Stream<Arguments> argumentProviderHigh()
    {
        return Stream.of(
                Arguments.arguments(30001L, CategoryLevel.HIGH),
                Arguments.arguments(20000L, CategoryLevel.HIGH),
                Arguments.arguments(10000L, CategoryLevel.HIGH),
                Arguments.arguments(30001L, CategoryLevel.MEDIUM)
        );
    }

    private static Stream<Arguments> argumentProviderMedium()
    {
        return Stream.of(
                Arguments.arguments(20000L, CategoryLevel.MEDIUM),
                Arguments.arguments(10000L, CategoryLevel.MEDIUM),
                Arguments.arguments(30001L, CategoryLevel.LOW),
                Arguments.arguments(20000L, CategoryLevel.LOW)
        );
    }

    private static Stream<Arguments> argumentProviderLow()
    {
        return Stream.of(
                Arguments.arguments(10000L, CategoryLevel.LOW)
        );
    }

    private static Stream<Arguments> argumentProviderVolumeLevel()
    {
        return Stream.of(
                Arguments.arguments(0L),
                Arguments.arguments(10000L),
                Arguments.arguments(20000L),
                Arguments.arguments(30001L)

        );
    }


    @Test
    void calculateVolumeLevelTest(){
        assertEquals(CategoryLevel.LOW, ICalculatorCriticalityRestService.calculateVolumeLevel(0L));
        assertEquals(CategoryLevel.MEDIUM, ICalculatorCriticalityRestService.calculateVolumeLevel(20000L));
        assertEquals(CategoryLevel.HIGH, ICalculatorCriticalityRestService.calculateVolumeLevel(30001L));

    }

}
