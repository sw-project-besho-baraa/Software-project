package Service_Test.CDService_Test;

import Entity.Cd;
import Repository.CdRepository;
import Service.CDService.CdSearchService;
import Service.CDService.SearchStrategy.ICdSearchStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CdSearchService_Test {

    @Test
    void constructor_throwsNullPointer_whenRepositoryIsNull() {
        assertThrows(NullPointerException.class, () -> new CdSearchService(null));
    }

    @Test
    void constructor_doesNotCallRepository_whenRepositoryIsNotNull() {
        CdRepository repo = mock(CdRepository.class);
        assertDoesNotThrow(() -> new CdSearchService(repo));
        verifyNoInteractions(repo);
    }

    @Test
    void search_delegatesToStrategyAndReturnsResult() {
        CdRepository repo = mock(CdRepository.class);
        CdSearchService service = new CdSearchService(repo);

        @SuppressWarnings("unchecked")
        ICdSearchStrategy<String> strategy = mock(ICdSearchStrategy.class);

        List<Cd> expected = List.of(new Cd());
        when(strategy.searchCd(repo, "bbbbbb")).thenReturn(expected);

        List<Cd> result = service.search(strategy, "bbbbbb");

        assertSame(expected, result);
        verify(strategy).searchCd(repo, "bbbbbb");
        verifyNoMoreInteractions(strategy);
        verifyNoInteractions(repo);
    }

    @Test
    void search_throwsNullPointer_whenStrategyIsNull() {
        CdRepository repo = mock(CdRepository.class);
        CdSearchService service = new CdSearchService(repo);

        assertThrows(NullPointerException.class, () -> service.search(null, "value"));
        verifyNoInteractions(repo);
    }

    @Test
    void search_throwsNullPointer_whenValueIsNull() {
        CdRepository repo = mock(CdRepository.class);
        CdSearchService service = new CdSearchService(repo);

        @SuppressWarnings("unchecked")
        ICdSearchStrategy<String> strategy = mock(ICdSearchStrategy.class);

        assertThrows(NullPointerException.class, () -> service.search(strategy, null));
        verifyNoInteractions(repo);
        verifyNoInteractions(strategy);
    }
}
