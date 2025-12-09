package Service_Test.CDService_Test;

import Entity.Cd;
import Repository.CdRepository;
import Service.CDService.AllCdService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class AllCdService_Test
{

    @Test
    void getAllCds_returnsAllFromRepository()
    {
        CdRepository repo = mock(CdRepository.class);
        List<Cd> cds = List.of(new Cd());
        when(repo.findAll()).thenReturn(cds);
        AllCdService service = new AllCdService(repo);
        assertSame(cds,service.getAllCds());
        verify(repo).findAll();
    }
}