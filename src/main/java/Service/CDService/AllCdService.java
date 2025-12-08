package Service.CDService;

import Entity.Cd;
import Repository.CdRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public class AllCdService {
    CdRepository cdRepository;
    AllCdService( CdRepository cdRepository){this.cdRepository=cdRepository;}
    public Collection<@NonNull Cd> getAllCds(){
        return cdRepository.findAll();
    }
}
