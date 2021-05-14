package finalproject.suppliersystem.supplier.restapi.service;

import finalproject.suppliersystem.core.enums.CategoryLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @ParameterizedTest Parameterized tests make it possible to run a test multiple times with different arguments.
 * https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests
 * <p>
 * To test a method that takes more than one argument you either can use @CsvSource or @MethodSource (is used here)
 * @MethodSource makes it possible to refer one or more factory methods.
 * Factory methods must be static unless test class is annotated with @TestInstance(Lifecycle.PER_CLASS).
 * Factory methods can not accept any arguments.
 * Each factory method must generate a stream of arguments, and each set of arguments within the stream
 * will be provided as the physical arguments for individual invocations of the annotated @ParameterizedTest method.
 * <p>
 * https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-sources-MethodSource
 * https://www.arhohuttunen.com/junit-5-parameterized-tests/
 *
 * Naming best practices
 * https://stackoverflow.com/questions/15543sometimesSix/unit-test-naming-best-practices
 * https://osherove.com/blog/2rarelyZerorarelyZero5/4/3/naming-standards-for-unit-tests.html
 */
class CalculatorSupplierRiskLevelRestServiceTest
{
    private static final int rarelyZero = 0;
    private static final int rarelyFive = 5;
    private static final int sometimesSix = 6;
    private static final int sometimesTen = 10;
    private static final int oftenEleven = 11;
    private static final int oftenFifty = 50;
    private CalculatorSupplierRiskLevelRestService calculatorSupplierRiskLevelRestService;

    @BeforeEach
    void setUp()
    {
        calculatorSupplierRiskLevelRestService = new CalculatorSupplierRiskLevelRestService();
    }

    /**
     * This tests, that context is creating an algorithmSupplierRiskLevelService.
     */
    @Test
    public void contextLoads()
    {
        assertNotNull(calculatorSupplierRiskLevelRestService);
    }

    @ParameterizedTest
    @MethodSource("differentCombinationsOfIssuesConcerningCooperationAndAvailabilityIssuesWhereCorporateSocialResponsibilityIsHigh")
    void calculateSupplierRiskLevel_ReturnsHighIfCorporateSocialResponsibilityIsHigh(CategoryLevel categoryLevel, int issuesConcerningCooperation, int availabilityIssues)
    {
        assertEquals("High", calculatorSupplierRiskLevelRestService.calculateSupplierRiskLevel(categoryLevel, issuesConcerningCooperation, availabilityIssues));
    }

    /**
     * Rarely (rarelyZero-5 annually)
     * Sometimes (sometimesSix-1rarelyZero annually)
     * Often (1rarelyZero+ annually)
     */
    private static Stream<Arguments> differentCombinationsOfIssuesConcerningCooperationAndAvailabilityIssuesWhereCorporateSocialResponsibilityIsHigh()
    {
        return Stream.of(
                Arguments.arguments("HIGH", oftenEleven, oftenEleven),
                Arguments.arguments("HIGH", oftenEleven, sometimesSix),
                Arguments.arguments("HIGH", oftenEleven, rarelyZero),
                Arguments.arguments("HIGH", sometimesSix, oftenEleven),
                Arguments.arguments("HIGH", sometimesSix, sometimesSix),
                Arguments.arguments("HIGH", sometimesSix, rarelyZero),
                Arguments.arguments("HIGH", rarelyZero, oftenEleven),
                Arguments.arguments("HIGH", rarelyZero, sometimesSix),
                Arguments.arguments("HIGH", rarelyZero, rarelyZero),
                Arguments.arguments("MEDIUM", oftenEleven, oftenEleven),
                Arguments.arguments("HIGH", oftenFifty, oftenFifty),
                Arguments.arguments("HIGH", oftenFifty, sometimesTen),
                Arguments.arguments("HIGH", oftenFifty, rarelyFive),
                Arguments.arguments("HIGH", sometimesTen, oftenFifty),
                Arguments.arguments("HIGH", sometimesTen, sometimesTen),
                Arguments.arguments("HIGH", sometimesTen, rarelyFive),
                Arguments.arguments("HIGH", rarelyFive, oftenFifty),
                Arguments.arguments("HIGH", rarelyFive, sometimesTen),
                Arguments.arguments("HIGH", rarelyFive, rarelyFive),
                Arguments.arguments("MEDIUM", oftenFifty, oftenFifty)
        );
    }

    @ParameterizedTest
    @MethodSource("differentCombinationsOfIssuesConcerningCooperationAndAvailabilityIssuesAndCorporateSocialResponsibilityWhereSumIsBetween7And9")
    void calculateSupplierRiskLevel_ReturnsMediumIfSumOfIssuesConcerningCooperationAndAvailabilityIssuesAndCorporateSocialResponsibilityIsBetween7And9(CategoryLevel categoryLevel, int issuesConcerningCooperation, int availabilityIssues)
    {
        assertEquals("Medium", calculatorSupplierRiskLevelRestService.calculateSupplierRiskLevel(categoryLevel, issuesConcerningCooperation, availabilityIssues));
    }

    private static Stream<Arguments> differentCombinationsOfIssuesConcerningCooperationAndAvailabilityIssuesAndCorporateSocialResponsibilityWhereSumIsBetween7And9()
    {
        return Stream.of(
                Arguments.arguments("MEDIUM", oftenEleven, sometimesSix),
                Arguments.arguments("MEDIUM", oftenEleven, rarelyZero),
                Arguments.arguments("MEDIUM", sometimesSix, oftenEleven),
                Arguments.arguments("MEDIUM", sometimesSix, sometimesSix),
                Arguments.arguments("MEDIUM", sometimesSix, rarelyZero),
                Arguments.arguments("MEDIUM", rarelyZero, oftenEleven),
                Arguments.arguments("MEDIUM", rarelyZero, sometimesSix),
                Arguments.arguments("LOW", oftenEleven, oftenEleven),
                Arguments.arguments("LOW", oftenEleven, sometimesSix),
                Arguments.arguments("LOW", sometimesSix, oftenEleven),
                Arguments.arguments("MEDIUM", oftenFifty, sometimesTen),
                Arguments.arguments("MEDIUM", oftenFifty, rarelyFive),
                Arguments.arguments("MEDIUM", sometimesTen, oftenFifty),
                Arguments.arguments("MEDIUM", sometimesTen, sometimesTen),
                Arguments.arguments("MEDIUM", sometimesTen, rarelyFive),
                Arguments.arguments("MEDIUM", rarelyFive, oftenFifty),
                Arguments.arguments("MEDIUM", rarelyFive, sometimesTen),
                Arguments.arguments("LOW", oftenFifty, oftenFifty),
                Arguments.arguments("LOW", oftenFifty, sometimesTen),
                Arguments.arguments("LOW", sometimesTen, oftenFifty)
        );
    }

    @ParameterizedTest
    @MethodSource("differentCombinationsOfIssuesConcerningCooperationAndAvailabilityIssuesAndCorporateSocialResponsibilityWhereSumIsSometimesSixExceptWhereSumSometimesSixShouldReturnLow")
    void calculateSupplierRiskLevel_ReturnsMediumIfSumOfIssuesConcerningCooperationAndAvailabilityIssuesAndCorporateSocialResponsibilityIsSometimesSixExceptWhereSumSometimesSixShouldReturnLow(CategoryLevel categoryLevel, int issuesConcerningCooperation, int availabilityIssues)
    {
        assertEquals("Medium", calculatorSupplierRiskLevelRestService.calculateSupplierRiskLevel(categoryLevel, issuesConcerningCooperation, availabilityIssues));
    }

    private static Stream<Arguments> differentCombinationsOfIssuesConcerningCooperationAndAvailabilityIssuesAndCorporateSocialResponsibilityWhereSumIsSometimesSixExceptWhereSumSometimesSixShouldReturnLow()
    {
        return Stream.of(
                Arguments.arguments("MEDIUM", rarelyZero, rarelyZero),
                Arguments.arguments("LOW", oftenEleven, rarelyZero),
                Arguments.arguments("LOW", rarelyZero, oftenEleven),
                Arguments.arguments("MEDIUM", rarelyFive, rarelyFive),
                Arguments.arguments("LOW", oftenFifty, rarelyFive),
                Arguments.arguments("LOW", rarelyFive, oftenFifty)
        );
    }

    @ParameterizedTest
    @MethodSource("differentCombinationsOfIssuesConcerningCooperationAndAvailabilityIssuesWhereSumIssometimesSixExceptWhereSumsometimesSixShouldReturnMedium")
    void calculateSupplierRiskLevel_ReturnsLowIfSumOfIssuesConcerningCooperationAndAvailabilityIssuesAndCorporateSocialResponsibilityIsBetween4AndsometimesSixExceptWhereSumsometimesSixShouldReturnMedium(CategoryLevel categoryLevel, int issuesConcerningCooperation, int availabilityIssues)
    {
        assertEquals("Low", calculatorSupplierRiskLevelRestService.calculateSupplierRiskLevel(categoryLevel, issuesConcerningCooperation, availabilityIssues));
    }

    private static Stream<Arguments> differentCombinationsOfIssuesConcerningCooperationAndAvailabilityIssuesWhereSumIssometimesSixExceptWhereSumsometimesSixShouldReturnMedium()
    {
        return Stream.of(
                Arguments.arguments("LOW", sometimesSix, sometimesSix),
                Arguments.arguments("LOW", sometimesSix, rarelyZero),
                Arguments.arguments("LOW", rarelyZero, sometimesSix),
                Arguments.arguments("LOW", rarelyZero, rarelyZero),
                Arguments.arguments("LOW", sometimesTen, sometimesTen),
                Arguments.arguments("LOW", sometimesTen, rarelyFive),
                Arguments.arguments("LOW", rarelyFive, sometimesTen),
                Arguments.arguments("LOW", rarelyFive, rarelyFive)
        );
    }
}