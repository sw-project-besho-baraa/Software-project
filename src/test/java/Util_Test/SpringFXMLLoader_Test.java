package Util_Test;

import Util.ApplicationContextProvider;
import Util.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SpringFXMLLoader_Test
{

    private static ApplicationContext ctx;

    @BeforeAll
    static void setup()
    {
        ctx = mock(ApplicationContext.class);
        new ApplicationContextProvider(ctx);
    }

    @Test
    void load_setsControllerFactory_andLocation()
    {
        FXMLLoader loader = SpringFXMLLoader.load("/");
        assertNotNull(loader);
        assertNotNull(loader.getControllerFactory());
        assertNotNull(loader.getLocation());
    }

    @Test
    void controllerFactory_usesApplicationContext()
    {
        when(ctx.getBean(String.class)).thenReturn("bean");
        FXMLLoader loader = SpringFXMLLoader.load("/");
        Object bean = loader.getControllerFactory().call(String.class);
        assertEquals("bean",bean);
        verify(ctx).getBean(String.class);
    }
}
