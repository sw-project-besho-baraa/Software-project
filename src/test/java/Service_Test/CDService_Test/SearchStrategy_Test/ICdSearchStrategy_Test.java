package Service_Test.CDService_Test.SearchStrategy_Test;


import Entity.Cd;
import Service.CDService.SearchStrategy.ICdSearchStrategy;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ICdSearchStrategy_Test {

    @Test
    void customImplementation_returnsExpectedList() {
        ICdSearchStrategy<String> strategy = (repo, val) -> List.of(new Cd());
        List<Cd> result = strategy.searchCd(null, "any");
        assertEquals(1, result.size());
        assertNotNull(result.get(0));
    }
}