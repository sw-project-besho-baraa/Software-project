package Util;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Provides static access to the Spring {@link ApplicationContext}.
 * <p>
 * This utility allows non-managed classes (those not created by Spring)
 * to retrieve Spring-managed beans at runtime.
 */
@Component
public class ApplicationContextProvider {

    private static ApplicationContext context;

    /**
     * Initializes the static application context.
     *
     * @param applicationContext the Spring context injected by the framework
     */
    public ApplicationContextProvider(ApplicationContext applicationContext) {
        ApplicationContextProvider.context = applicationContext;
    }

    /**
     * Returns the active {@link ApplicationContext}.
     *
     * @return the Spring application context instance
     */
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    /**
     * Retrieves a Spring-managed bean by its type.
     *
     * @param <T>   the type of the bean to retrieve
     * @param clazz the bean class
     * @return the bean instance, or {@code null} if not found
     */
    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }
}
