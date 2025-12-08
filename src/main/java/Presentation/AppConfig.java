package Presentation;

import Service.MediaItem.OverdueBorrowDetection.OverdueItemDetector;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
@SpringBootApplication
@Configuration
@ComponentScan(basePackages = {"Repository", "Service", "Presentation", "Validation", "Session"})
public class AppConfig {


}