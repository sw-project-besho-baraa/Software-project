package Service.CDService;


import Entity.Cd;
import Repository.CdRepository;
import org.springframework.stereotype.Service;

@Service
public class AddCdService {
    private final CdRepository repository;
    public AddCdService(CdRepository repository)
    {
        this.repository = repository;
    }
    public void addCd(Cd cd)
    {
        repository.save(cd);
    }


}
