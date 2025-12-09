package Service.CDService.SearchStrategy;

import Entity.Cd;
import Repository.CdRepository;
import java.util.List;

public interface ICdSearchStrategy<TValue>
{
    List<Cd> searchCd(CdRepository cdRepository,TValue value);
}