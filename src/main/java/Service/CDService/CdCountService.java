package Service.CDService;

import Repository.CdRepository;
import org.springframework.stereotype.Service;

/**
 * Service for counting CDs in the system.
 */
@Service
public class CdCountService
{

    private final CdRepository cdRepository;

    /**
     * Creates a new CD count service.
     *
     * @param cdRepository
     *            repository for CD data
     */
    public CdCountService(CdRepository cdRepository)
    {
        this.cdRepository = cdRepository;
    }

    /**
     * Counts the total number of CDs.
     *
     * @return total CD count
     */
    public long countCds()
    {
        return cdRepository.count();
    }
}
