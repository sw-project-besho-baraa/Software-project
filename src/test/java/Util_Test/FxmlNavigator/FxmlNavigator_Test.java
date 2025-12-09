package Util_Test.FxmlNavigator;

import Util.FxmlMapper.RoleToFxmlMapper;
import Util.FxmlNavigator.FxmlNavigator;
import Util.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FxmlNavigator_Test
{

    private FxmlNavigator createNavigator()
    {
        return new FxmlNavigator(mock(RoleToFxmlMapper.class));
    }

    @Test
    void navigateTo_success_doesNotThrow() throws Exception
    {
        Stage stage = mock(Stage.class);
        FXMLLoader loader = mock(FXMLLoader.class);
        when(loader.load()).thenReturn(mock(Parent.class));
        try (MockedStatic<SpringFXMLLoader> st = mockStatic(SpringFXMLLoader.class);
                MockedConstruction<Scene> sc = mockConstruction(Scene.class))
        {
            st.when(() -> SpringFXMLLoader.load("/main.fxml")).thenReturn(loader);
            assertDoesNotThrow(() -> createNavigator().navigateTo(stage,"/main.fxml","Main"));
        }
    }

    @Test
    void navigateTo_ioException_isCaught() throws Exception
    {
        Stage stage = mock(Stage.class);
        FXMLLoader loader = mock(FXMLLoader.class);
        when(loader.load()).thenThrow(new IOException("boom"));
        try (MockedStatic<SpringFXMLLoader> st = mockStatic(SpringFXMLLoader.class);
                MockedConstruction<Scene> sc = mockConstruction(Scene.class))
        {
            st.when(() -> SpringFXMLLoader.load("/broken.fxml")).thenReturn(loader);
            assertDoesNotThrow(() -> createNavigator().navigateTo(stage,"/broken.fxml","Broken"));
        }
    }

    @Test
    void logout_callsNavigateToWithLoginTitle()
    {
        FxmlNavigator nav = spy(createNavigator());
        Stage stage = mock(Stage.class);
        doNothing().when(nav).navigateTo(any(),any(),any());
        nav.logout(stage,"/login.fxml");
        verify(nav).navigateTo(stage,"/login.fxml","Login");
    }

    @Test
    void openInNewWindow_success_returnsController() throws Exception
    {
        FXMLLoader loader = mock(FXMLLoader.class);
        when(loader.load()).thenReturn(mock(Parent.class));
        when(loader.getController()).thenReturn("ctrl");
        try (MockedStatic<SpringFXMLLoader> st = mockStatic(SpringFXMLLoader.class);
                MockedConstruction<Stage> stg = mockConstruction(Stage.class);
                MockedConstruction<Scene> sc = mockConstruction(Scene.class))
        {
            st.when(() -> SpringFXMLLoader.load("/dlg.fxml")).thenReturn(loader);
            Object result = createNavigator().openInNewWindow("/dlg.fxml","Dialog");
            assertEquals("ctrl",result);
        }
    }

    @Test
    void openInNewWindow_ioException_returnsNull() throws Exception
    {
        FXMLLoader loader = mock(FXMLLoader.class);
        when(loader.load()).thenThrow(new IOException("bad fxml"));
        try (MockedStatic<SpringFXMLLoader> st = mockStatic(SpringFXMLLoader.class);
                MockedConstruction<Stage> stg = mockConstruction(Stage.class);
                MockedConstruction<Scene> sc = mockConstruction(Scene.class))
        {
            st.when(() -> SpringFXMLLoader.load("/error.fxml")).thenReturn(loader);
            Object result = createNavigator().openInNewWindow("/error.fxml","Error");
            assertNull(result);
        }
    }
}
