package Service_Test.CDService_Test.SearchStrategy_Test;

import Entity.Cd;
import Repository.CdRepository;
import Service.CDService.SearchStrategy.CdSearchByTitleStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CdSearchByTitleStrategy_Test {

    @Test
    void searchCd_returnsMatchingResults() {
        CdRepository repo = mock(CdRepository.class);
        List<Cd> cds = List.of(new Cd());
        when(repo.findByTitleContainingIgnoreCase("bishawi")).thenReturn(cds);
        CdSearchByTitleStrategy strategy = new CdSearchByTitleStrategy();
        assertEquals(cds, strategy.searchCd(repo, "bishawi"));
        verify(repo).findByTitleContainingIgnoreCase("bishawi");
    }}