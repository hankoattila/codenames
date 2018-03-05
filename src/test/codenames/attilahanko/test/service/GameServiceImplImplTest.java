package codenames.attilahanko.test.service;

import com.codenames.attilahanko.controller.GameController;
import com.codenames.attilahanko.model.player.User;
import com.codenames.attilahanko.utils.Path;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;
//import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(value = GameController.class, secure = false)
public class GameServiceImplImplTest {


    @Test
    public void handleSelectName() {
        User user = mock(User.class);
        Model mockModel = mock(Model.class);
        HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);
        String expected = "redirect:" + Path.Web.QUEUE;
//        when(user.getName()).thenReturn("Ödön");
//        when(mockHttpServletRequest.getSession().getAttribute("game-name")).thenReturn("Test");

    }

    @Test
    public void something() {


    }


}