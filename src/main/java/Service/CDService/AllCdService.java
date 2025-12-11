package Service.CDService;

import Entity.Cd;
import Repository.CdRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import java.util.Collection;

/**
 * Service for retrieving all CDs from the repository.
 */
@Service
public class AllCdService
{

    private final CdRepository cdRepository;

    /**
     * Creates a new service for CD operations.
     *
     * @param cdRepository
     *            repository for CD data
     */
    public AllCdService(CdRepository cdRepository)
    {
        this.cdRepository = cdRepository;
    }

    /**
     * Returns all CDs stored in the system.
     *
     * @return collection of all CDs
     */
    public Collection<@NonNull Cd> getAllCds()
    {
        return cdRepository.findAll();
    }
}
