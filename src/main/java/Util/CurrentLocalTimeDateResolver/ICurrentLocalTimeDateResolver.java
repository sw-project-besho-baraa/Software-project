package Util.CurrentLocalTimeDateResolver;

import java.time.LocalDateTime;

/**
 * Provides an abstraction for obtaining the current local date and time.
 */
public interface ICurrentLocalTimeDateResolver {

    /**
     * Gets the current local date and time.
     *
     * @return the current {@link LocalDateTime}
     */
    LocalDateTime getCurrentLocalDateTime();
}
