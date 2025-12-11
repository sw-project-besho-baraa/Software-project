package Presentation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Main Spring Boot configuration class.
 * <p>
 * Scans all project packages for components.
 */

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = {"Repository", "Service", "Presentation", "Validation", "Session"})
public class AppConfig {
}
