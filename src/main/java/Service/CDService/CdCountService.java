package Service.CDService;

import Repository.CdRepository;
import org.springframework.stereotype.Service;

@Service
public class CdCountService
{
    private CdRepository cdRepository;
    public CdCountService(CdRepository cdRepository)
    {
        this.cdRepository = cdRepository;
    }

    public long countCds()
    {
        return cdRepository.count();
    }
}
