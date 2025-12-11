package Util.CurrentLocalTimeDateResolver;

import java.time.LocalDateTime;

/**
 * Provides the current local date and time.
 * <p>
 * This implementation of {@link ICurrentLocalTimeDateResolver} simply returns
 * {@link LocalDateTime#now()}.
 */
public class CurrentLocalDateTimeResolver implements ICurrentLocalTimeDateResolver
{

    /**
     * Returns the current local date and time of the system clock.
     *
     * @return the current {@link LocalDateTime}
     * @see LocalDateTime#now()
     */
    @Override
    public LocalDateTime getCurrentLocalDateTime()
    {
        return LocalDateTime.now();
    }
}
