package finalproject.suppliersystem.supplier.calculatorrestapi.restservice;

import finalproject.suppliersystem.core.enums.CategoryLevel;
import org.junit.jupiter.api.BeforeEach;
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
 * https://stackoverflow.com/questions/155436/unit-test-naming-best-practices
 * https://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html
 */
class CalculatorSupplierRiskLevelRestServiceTest
{
    private ICalculatorSupplierRiskLevelRestService testService;

    //Arrange
    @BeforeEach
    void setUp()
    {
        testService = new CalculatorSupplierRiskLevelRestService();
    }

    /**
     * @param corporateSocialResponsibility
     * @param issuesConcerningCooperation
     * @param availabilityIssues
     */
    @ParameterizedTest
    @MethodSource("testCasesForHigh")
    void calculateSupplierRiskLevel_CorporateSocialResponsibilityIsHigh_ReturnsHigh(CategoryLevel corporateSocialResponsibility, int issuesConcerningCooperation, int availabilityIssues)
    {
        // Act
        Enum<CategoryLevel> result = testService.calculateSupplierRiskLevel(corporateSocialResponsibility, issuesConcerningCooperation, availabilityIssues);
        // Assert
        assertEquals("HIGH", result.name());
    }

    /**
     * Rarely (0-5 annually)
     * Sometimes (6-10 annually)
     * Often (10+ annually)
     */
    private static Stream<Arguments> testCasesForHigh()
    {
        return Stream.of(
                Arguments.arguments("HIGH", 11, 11),
                Arguments.arguments("HIGH", 11, 6),
                Arguments.arguments("HIGH", 11, 0),
                Arguments.arguments("HIGH", 6, 11),
                Arguments.arguments("HIGH", 6, 6),
                Arguments.arguments("HIGH", 6, 0),
                Arguments.arguments("HIGH", 0, 11),
                Arguments.arguments("HIGH", 0, 6),
                Arguments.arguments("HIGH", 0, 0),
                Arguments.arguments("HIGH", 50, 50),
                Arguments.arguments("HIGH", 50, 10),
                Arguments.arguments("HIGH", 50, 5),
                Arguments.arguments("HIGH", 10, 50),
                Arguments.arguments("HIGH", 10, 10),
                Arguments.arguments("HIGH", 10, 5),
                Arguments.arguments("HIGH", 5, 50),
                Arguments.arguments("HIGH", 5, 10),
                Arguments.arguments("HIGH", 5, 5)
        );
    }

    /**
     * TODO: Ændre Stream<Arguments> til at have bedre navngivning
     * CSR = Corporate Social Responsibility
     * ICC = Issues Concerning Cooperation
     * AVAIS = Availability Issues
     *
     * @param corporateSocialResponsibility
     * @param issuesConcerningCooperation
     * @param availabilityIssues
     */
    @ParameterizedTest
    @MethodSource("testCasesForHighWhereCSRIsMediumAndICCAndAVAISIsHigh")
    void calculateSupplierRiskLevel_CSRIsMediumAndICCIsHighAndAVAISIsHigh_ReturnsHigh(CategoryLevel corporateSocialResponsibility, int issuesConcerningCooperation, int availabilityIssues)
    {
        // Act
        Enum<CategoryLevel> result = testService.calculateSupplierRiskLevel(corporateSocialResponsibility, issuesConcerningCooperation, availabilityIssues);
        // Assert
        assertEquals("HIGH", result.name());
    }

    /**
     * Rarely (0-5 annually)
     * Sometimes (6-10 annually)
     * Often (10+ annually)
     */
    private static Stream<Arguments> testCasesForHighWhereCSRIsMediumAndICCAndAVAISIsHigh()
    {
        return Stream.of(
                Arguments.arguments("MEDIUM", 11, 11),
                Arguments.arguments("MEDIUM", 50, 50)
        );
    }

    @ParameterizedTest
    @MethodSource("testCasesForMedium")
    void calculateSupplierRiskLevel_CorporateSocialResponsibilityIsMedium_ReturnsMedium(CategoryLevel corporateSocialResponsibility, int issuesConcerningCooperation, int availabilityIssues)
    {
        // Act
        Enum<CategoryLevel> result = testService.calculateSupplierRiskLevel(corporateSocialResponsibility, issuesConcerningCooperation, availabilityIssues);
        // Assert
        assertEquals("MEDIUM", result.name());
    }

    private static Stream<Arguments> testCasesForMedium()
    {
        return Stream.of(
                Arguments.arguments("MEDIUM", 11, 6),
                Arguments.arguments("MEDIUM", 11, 0),
                Arguments.arguments("MEDIUM", 6, 11),
                Arguments.arguments("MEDIUM", 6, 6),
                Arguments.arguments("MEDIUM", 6, 0),
                Arguments.arguments("MEDIUM", 0, 11),
                Arguments.arguments("MEDIUM", 0, 6),
                Arguments.arguments("MEDIUM", 0, 0),
                Arguments.arguments("MEDIUM", 50, 10),
                Arguments.arguments("MEDIUM", 50, 5),
                Arguments.arguments("MEDIUM", 10, 50),
                Arguments.arguments("MEDIUM", 10, 10),
                Arguments.arguments("MEDIUM", 10, 5),
                Arguments.arguments("MEDIUM", 5, 50),
                Arguments.arguments("MEDIUM", 5, 10),
                Arguments.arguments("MEDIUM", 5, 5)
        );
    }

    @ParameterizedTest
    @MethodSource("testCasesForMediumWhereCorporateSocialResponsibilityIsLowAndIssuesConcerningCooperationIsHigh")
    void calculateSupplierRiskLevel_CorporateSocialResponsibilityIsLowAndIssuesConcerningCooperationIsHigh_ReturnsMedium(CategoryLevel corporateSocialResponsibility, int issuesConcerningCooperation, int availabilityIssues)
    {
        // Act
        Enum<CategoryLevel> result = testService.calculateSupplierRiskLevel(corporateSocialResponsibility, issuesConcerningCooperation, availabilityIssues);
        // Assert
        assertEquals("MEDIUM", result.name());
    }

    private static Stream<Arguments> testCasesForMediumWhereCorporateSocialResponsibilityIsLowAndIssuesConcerningCooperationIsHigh()
    {
        return Stream.of(
                Arguments.arguments("LOW", 11, 11),
                Arguments.arguments("LOW", 11, 6),
                Arguments.arguments("LOW", 11, 0),
                Arguments.arguments("LOW", 50, 50),
                Arguments.arguments("LOW", 50, 10),
                Arguments.arguments("LOW", 50, 5)
        );
    }

    @ParameterizedTest
    @MethodSource("testCasesForMediumWhereCorporateSocialResponsibilityIsLowAndAvailabilityIssuesIsHigh")
    void calculateSupplierRiskLevel_CorporateSocialResponsibilityIsLowAndAvailabilityIssuesIsHigh_ReturnsMedium(CategoryLevel corporateSocialResponsibility, int issuesConcerningCooperation, int availabilityIssues)
    {
        // Act
        Enum<CategoryLevel> result = testService.calculateSupplierRiskLevel(corporateSocialResponsibility, issuesConcerningCooperation, availabilityIssues);
        // Assert
        assertEquals("MEDIUM", result.name());
    }

    private static Stream<Arguments> testCasesForMediumWhereCorporateSocialResponsibilityIsLowAndAvailabilityIssuesIsHigh()
    {
        return Stream.of(
                Arguments.arguments("LOW", 11, 11),
                Arguments.arguments("LOW", 6, 11),
                Arguments.arguments("LOW", 0, 11),
                Arguments.arguments("LOW", 50, 50),
                Arguments.arguments("LOW", 10, 50),
                Arguments.arguments("LOW", 5, 50)
        );
    }

    @ParameterizedTest
    @MethodSource("testCasesForLow")
    void calculateSupplierRiskLevel_AllOthersCombinations_ReturnsLow(CategoryLevel corporateSocialResponsibility, int issuesConcerningCooperation, int availabilityIssues)
    {
        // Act
        Enum<CategoryLevel> result = testService.calculateSupplierRiskLevel(corporateSocialResponsibility, issuesConcerningCooperation, availabilityIssues);
        // Assert
        assertEquals("LOW", result.name());
    }

    private static Stream<Arguments> testCasesForLow()
    {
        return Stream.of(
                Arguments.arguments("LOW", 6, 6),
                Arguments.arguments("LOW", 6, 0),
                Arguments.arguments("LOW", 0, 6),
                Arguments.arguments("LOW", 0, 0),
                Arguments.arguments("LOW", 10, 10),
                Arguments.arguments("LOW", 10, 5),
                Arguments.arguments("LOW", 5, 10),
                Arguments.arguments("LOW", 5, 5)
        );
    }
}