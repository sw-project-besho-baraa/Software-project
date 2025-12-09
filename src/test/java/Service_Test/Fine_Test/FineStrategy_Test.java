package Service_Test.Fine_Test;

import Service.Fine.FineStrategy;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class FineStrategy_Test {

    @Test
    void lambdaImplementation_returnsExpectedValue() {
        FineStrategy s = days -> BigDecimal.valueOf(days * 5);
        assertEquals(BigDecimal.valueOf(25), s.calculateFine(5));
    }
}