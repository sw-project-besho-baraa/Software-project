package Service.CDService;

import lombok.NonNull;
import Entity.Cd;
import Repository.CdRepository;
import Service.CDService.SearchStrategy.ICdSearchStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CdSearchService
{
    private final CdRepository cdRepository;

    public CdSearchService(@NonNull CdRepository cdRepository)
    {
        this.cdRepository = cdRepository;
    }

    public <TValue> List<Cd> search(@NonNull ICdSearchStrategy<TValue> strategy, @NonNull TValue value)
    {
        return strategy.searchCd(cdRepository, value);
    }
}