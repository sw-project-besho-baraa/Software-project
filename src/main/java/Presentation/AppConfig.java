package Presentation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"Repository", "Service", "Presentation", "Validation", "Session"})
public class AppConfig {

}