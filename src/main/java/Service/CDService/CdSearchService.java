package Service.CDService;

import lombok.NonNull;
import Entity.Cd;
import Repository.CdRepository;
import Service.CDService.SearchStrategy.ICdSearchStrategy;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service that delegates CD search operations to the selected strategy.
 */
@Service
public class CdSearchService {

    private final CdRepository cdRepository;

    /**
     * Creates a new CD search service.
     *
     * @param cdRepository repository used for CD queries
     */
    public CdSearchService(@NonNull CdRepository cdRepository) {
        this.cdRepository = cdRepository;
    }

    /**
     * Executes a search using the given strategy and value.
     *
     * @param strategy search strategy (e.g., by title)
     * @param value search value
     * @param <TValue> type of the search value
     * @return list of matching CDs
     * @see Service.CDService.SearchStrategy.ICdSearchStrategy
     */
    public <TValue> List<Cd> search(@NonNull ICdSearchStrategy<TValue> strategy,
                                    @NonNull TValue value) {
        return strategy.searchCd(cdRepository, value);
    }
}
