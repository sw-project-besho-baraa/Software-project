package Util_Test;

import Util.ApplicationContextProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ApplicationContextProvider_Test
{

    private ApplicationContext mockContext;

    @BeforeEach
    void setup()
    {
        mockContext = mock(ApplicationContext.class);
    }

    @Test
    void constructor_setsStaticContext()
    {
        new ApplicationContextProvider(mockContext);
        assertSame(mockContext,ApplicationContextProvider.getApplicationContext());
    }

    @Test
    void getApplicationContext_returnsNullBeforeInitialization()
    {
        new ApplicationContextProvider(null);
        assertNull(ApplicationContextProvider.getApplicationContext());
    }
}