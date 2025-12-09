package Service.Fine;

import Entity.User;
import Repository.MediaItemRepository;
import Repository.UserRepository;
import Service.OverdueBorrowDetection.OverdueItemDetector;
import Util.DateCalculator.DateCalculator;
import Util.FineResolver;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component

public class CalculateFineService{
    private final MediaItemRepository mediaItemRepository;

    private final UserRepository userRepository;
    public CalculateFineService(MediaItemRepository mediaItemRepository,UserRepository userRepository){
        this.mediaItemRepository=mediaItemRepository;
        this.userRepository=userRepository;
    }
    @Transactional
    public void calculateFines( LocalDateTime currentDateTime) {
        var overdueItemDetector = new OverdueItemDetector(mediaItemRepository);
        var overdueList =overdueItemDetector.detectUsersWithOverdueBooks();
        for (var userData : overdueList) {
            User borrower = userData.user();
            var items = userData.items();

            for (var overdueItem : items) {
                var item = overdueItem.item();

                LocalDateTime lastCalculated = item.getLastTimeFineCalculated();
                if (lastCalculated == null) {
                    lastCalculated = item.getDueDate();
                }

                long daysOverdue = DateCalculator.daysDifference(currentDateTime, lastCalculated);
                if (daysOverdue > 0) {
                    BigDecimal fineAmount = FineResolver.getFine(item)
                            .multiply(BigDecimal.valueOf(daysOverdue));


                    borrower.increaseFine(fineAmount);

                    item.setLastTimeFineCalculated(currentDateTime);

                    mediaItemRepository.save(item);
                    userRepository.save(borrower);
                }
            }
        }
    }
}
