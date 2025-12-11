package Service.CDService.SearchStrategy;

import Entity.Cd;
import Repository.CdRepository;
import java.util.List;

/**
 * Search strategy for finding CDs by title.
 */
public class CdSearchByTitleStrategy implements ICdSearchStrategy<String>
{

    /**
     * Finds all CDs whose titles contain the given value.
     *
     * @param cdRepository
     *            CD repository
     * @param title
     *            part or full CD title
     * @return list of matching CDs
     */
    @Override
    public List<Cd> searchCd(CdRepository cdRepository,String title)
    {
        return cdRepository.findByTitleContainingIgnoreCase(title);
    }
}
