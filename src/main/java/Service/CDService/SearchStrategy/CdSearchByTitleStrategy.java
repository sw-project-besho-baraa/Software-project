package Service.CDService.SearchStrategy;

import Entity.Cd;
import Repository.CdRepository;

import java.util.List;

public class CdSearchByTitleStrategy implements ICdSearchStrategy<String>
{
    @Override
    public List<Cd> searchCd(CdRepository cdRepository,String title)
    {
        return cdRepository.findByTitleContainingIgnoreCase(title);
    }
}