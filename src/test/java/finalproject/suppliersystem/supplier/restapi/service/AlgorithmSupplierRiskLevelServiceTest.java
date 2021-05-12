package finalproject.suppliersystem.supplier.restapi.service;

import finalproject.suppliersystem.core.enums.CorporateSocialResponsibility;
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
class AlgorithmSupplierRiskLevelServiceTest
{
    private static final int rarelyZero = 0;
    private static final int rarelyFive = 5;
    private static final int sometimesSix = 6;
    private static final int sometimesTen = 10;
    private static final int oftenEleven = 11;
    private static final int oftenFifty = 50;
    private AlgorithmSupplierRiskLevelService algorithmSupplierRiskLevelService;

    @BeforeEach
    void setUp()
    {
        algorithmSupplierRiskLevelService = new AlgorithmSupplierRiskLevelService();
    }

    /**
     * This tests, that context is creating an algorithmSupplierRiskLevelService.
     */
    @Test
    public void contextLoads()
    {
        assertNotNull(algorithmSupplierRiskLevelService);
    }

    @ParameterizedTest
    @MethodSource("differentCombinationsOfIssuesConcerningCooperationAndAvailabilityIssuesWhereCorporateSocialResponsibilityIsHigh")
    void calculateSupplierRiskLevel_ReturnsHighIfCorporateSocialResponsibilityIsHigh(CorporateSocialResponsibility corporateSocialResponsibility, int issuesConcerningCooperation, int availabilityIssues)
    {
        assertEquals("High", algorithmSupplierRiskLevelService.calculateSupplierRiskLevel(corporateSocialResponsibility, issuesConcerningCooperation, availabilityIssues));
    }

    /**
     * Rarely (rarelyZero-5 annually)
     * Sometimes (sometimesSix-1rarelyZero annually)
     * Often (1rarelyZero+ annually)
     */
    private static Stream<Arguments> differentCombinationsOfIssuesConcerningCooperationAndAvailabilityIssuesWhereCorporateSocialResponsibilityIsHigh()
    {
        return Stream.of(
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_HIGH", oftenEleven, oftenEleven),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_HIGH", oftenEleven, sometimesSix),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_HIGH", oftenEleven, rarelyZero),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_HIGH", sometimesSix, oftenEleven),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_HIGH", sometimesSix, sometimesSix),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_HIGH", sometimesSix, rarelyZero),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_HIGH", rarelyZero, oftenEleven),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_HIGH", rarelyZero, sometimesSix),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_HIGH", rarelyZero, rarelyZero),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_MEDIUM", oftenEleven, oftenEleven),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_HIGH", oftenFifty, oftenFifty),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_HIGH", oftenFifty, sometimesTen),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_HIGH", oftenFifty, rarelyFive),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_HIGH", sometimesTen, oftenFifty),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_HIGH", sometimesTen, sometimesTen),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_HIGH", sometimesTen, rarelyFive),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_HIGH", rarelyFive, oftenFifty),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_HIGH", rarelyFive, sometimesTen),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_HIGH", rarelyFive, rarelyFive),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_MEDIUM", oftenFifty, oftenFifty)
        );
    }

    @ParameterizedTest
    @MethodSource("differentCombinationsOfIssuesConcerningCooperationAndAvailabilityIssuesAndCorporateSocialResponsibilityWhereSumIsBetween7And9")
    void calculateSupplierRiskLevel_ReturnsMediumIfSumOfIssuesConcerningCooperationAndAvailabilityIssuesAndCorporateSocialResponsibilityIsBetween7And9(CorporateSocialResponsibility corporateSocialResponsibility, int issuesConcerningCooperation, int availabilityIssues)
    {
        assertEquals("Medium", algorithmSupplierRiskLevelService.calculateSupplierRiskLevel(corporateSocialResponsibility, issuesConcerningCooperation, availabilityIssues));
    }

    private static Stream<Arguments> differentCombinationsOfIssuesConcerningCooperationAndAvailabilityIssuesAndCorporateSocialResponsibilityWhereSumIsBetween7And9()
    {
        return Stream.of(
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_MEDIUM", oftenEleven, sometimesSix),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_MEDIUM", oftenEleven, rarelyZero),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_MEDIUM", sometimesSix, oftenEleven),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_MEDIUM", sometimesSix, sometimesSix),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_MEDIUM", sometimesSix, rarelyZero),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_MEDIUM", rarelyZero, oftenEleven),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_MEDIUM", rarelyZero, sometimesSix),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_LOW", oftenEleven, oftenEleven),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_LOW", oftenEleven, sometimesSix),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_LOW", sometimesSix, oftenEleven),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_MEDIUM", oftenFifty, sometimesTen),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_MEDIUM", oftenFifty, rarelyFive),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_MEDIUM", sometimesTen, oftenFifty),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_MEDIUM", sometimesTen, sometimesTen),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_MEDIUM", sometimesTen, rarelyFive),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_MEDIUM", rarelyFive, oftenFifty),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_MEDIUM", rarelyFive, sometimesTen),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_LOW", oftenFifty, oftenFifty),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_LOW", oftenFifty, sometimesTen),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_LOW", sometimesTen, oftenFifty)
        );
    }

    @ParameterizedTest
    @MethodSource("differentCombinationsOfIssuesConcerningCooperationAndAvailabilityIssuesAndCorporateSocialResponsibilityWhereSumIssometimesSixExceptWhereSumsometimesSixShouldReturnLow")
    void calculateSupplierRiskLevel_ReturnsMediumIfSumOfIssuesConcerningCooperationAndAvailabilityIssuesAndCorporateSocialResponsibilityIssometimesSixExceptWhereSumsometimesSixShouldReturnLow(CorporateSocialResponsibility corporateSocialResponsibility, int issuesConcerningCooperation, int availabilityIssues)
    {
        assertEquals("Medium", algorithmSupplierRiskLevelService.calculateSupplierRiskLevel(corporateSocialResponsibility, issuesConcerningCooperation, availabilityIssues));
    }

    private static Stream<Arguments> differentCombinationsOfIssuesConcerningCooperationAndAvailabilityIssuesAndCorporateSocialResponsibilityWhereSumIssometimesSixExceptWhereSumsometimesSixShouldReturnLow()
    {
        return Stream.of(
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_MEDIUM", rarelyZero, rarelyZero),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_LOW", oftenEleven, rarelyZero),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_LOW", rarelyZero, oftenEleven),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_MEDIUM", rarelyFive, rarelyFive),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_LOW", oftenFifty, rarelyFive),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_LOW", rarelyFive, oftenFifty)
        );
    }

    @ParameterizedTest
    @MethodSource("differentCombinationsOfIssuesConcerningCooperationAndAvailabilityIssuesWhereSumIssometimesSixExceptWhereSumsometimesSixShouldReturnMedium")
    void calculateSupplierRiskLevel_ReturnsLowIfSumOfIssuesConcerningCooperationAndAvailabilityIssuesAndCorporateSocialResponsibilityIsBetween4AndsometimesSixExceptWhereSumsometimesSixShouldReturnMedium(CorporateSocialResponsibility corporateSocialResponsibility, int issuesConcerningCooperation, int availabilityIssues)
    {
        assertEquals("Low", algorithmSupplierRiskLevelService.calculateSupplierRiskLevel(corporateSocialResponsibility, issuesConcerningCooperation, availabilityIssues));
    }

    private static Stream<Arguments> differentCombinationsOfIssuesConcerningCooperationAndAvailabilityIssuesWhereSumIssometimesSixExceptWhereSumsometimesSixShouldReturnMedium()
    {
        return Stream.of(
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_LOW", sometimesSix, sometimesSix),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_LOW", sometimesSix, rarelyZero),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_LOW", rarelyZero, sometimesSix),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_LOW", rarelyZero, rarelyZero),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_LOW", sometimesTen, sometimesTen),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_LOW", sometimesTen, rarelyFive),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_LOW", rarelyFive, sometimesTen),
                Arguments.arguments("CORPORATE_SOCIAL_RESPONSIBILITY_LOW", rarelyFive, rarelyFive)
        );
    }
}