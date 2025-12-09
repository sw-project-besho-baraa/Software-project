package Service_Test.CDService_Test;

import Repository.CdRepository;
import Service.CDService.CdCountService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CdCountService_Test {

    @Test
    void countCds_returnsRepositoryCount() {
        CdRepository repo = mock(CdRepository.class);
        when(repo.count()).thenReturn(10L);

        CdCountService service = new CdCountService(repo);

        assertEquals(10L, service.countCds());
        verify(repo).count();
    }
}
