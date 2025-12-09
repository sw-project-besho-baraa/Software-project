package Util.CurrentLocalTimeDateResolver;

import java.time.LocalDateTime;

public class CurrentLocalDateTimeResolver implements ICurrentLocalTimeDateResolver
{
    @Override
    public LocalDateTime getCurrentLocalDateTime()
    {
        return LocalDateTime.now();
    }
}
