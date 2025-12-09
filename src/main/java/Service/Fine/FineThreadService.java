package Service.Fine;

import Entity.User;
import Repository.MediaItemRepository;
import Repository.UserRepository;
import Service.OverdueBorrowDetection.OverdueItemDetector;
import Util.CurrentLocalTimeDateResolver.CurrentLocalDateTimeResolver;
import Util.DateCalculator.DateCalculator;
import Util.FineResolver;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class FineThreadService implements Runnable{
    private final MediaItemRepository mediaItemRepository;

    private final UserRepository userRepository;
    public FineThreadService(MediaItemRepository mediaItemRepository,UserRepository userRepository){
        this.mediaItemRepository=mediaItemRepository;
        this.userRepository=userRepository;
    }
    @Override
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void run() {
        var service =new CalculateFineService(mediaItemRepository,userRepository);
        var currentDateTimeResolver=new CurrentLocalDateTimeResolver();
        service.calculateFines(currentDateTimeResolver.getCurrentLocalDateTime());
    }
}
