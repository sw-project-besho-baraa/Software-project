package Service_Test.CDService_Test;
import Entity.Cd;
import Repository.CdRepository;
import Service.CDService.AddCdService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class AddCdService_Test {

    @Test
    void addCd_callsRepositorySave() {
        CdRepository repo = mock(CdRepository.class);
        AddCdService service = new AddCdService(repo);
        Cd cd = new Cd();
        service.addCd(cd);
        verify(repo).save(cd);
        verifyNoMoreInteractions(repo);
    }
}