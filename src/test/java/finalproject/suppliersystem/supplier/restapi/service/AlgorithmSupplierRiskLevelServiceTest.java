package finalproject.suppliersystem.supplier.restapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlgorithmSupplierRiskLevelServiceTest
{
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
}