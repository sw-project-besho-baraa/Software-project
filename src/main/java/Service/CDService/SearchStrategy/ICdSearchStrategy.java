package Service.CDService.SearchStrategy;

import Entity.Cd;
import Repository.CdRepository;
import java.util.List;

/**
 * Defines a strategy for searching CDs using different criteria.
 *
 * @param <TValue>
 *            type of the search value (e.g., String)
 */
public interface ICdSearchStrategy<TValue>
{

    /**
     * Searches for CDs based on the provided value.
     *
     * @param cdRepository
     *            CD repository
     * @param value
     *            search value (e.g., title)
     * @return list of matching CDs
     */
    List<Cd> searchCd(CdRepository cdRepository,TValue value);
}
