package pl.edu.agh.edu.kis.pz1.util;
import org.junit.Test;
import pl.edu.agh.kis.pz1.util.Player;
import java.net.Socket;
import static junit.framework.TestCase.*;
import static junit.framework.TestCase.assertTrue;


public class PlayerTest {

    @Test
    public void replace_n_test(){
        Socket socket = new Socket();
        Player player = new Player(socket, 1, 2);
        player.take_5();
        Integer [] arr = {1,2,3};
        Integer [] arr2 = {2,3,4};
        Integer [] arr3 = {0,2,4};
        Integer [] arr4 = {1,0,3};


        assertTrue(player.replace_n(arr));
        assertTrue(player.replace_n(arr2));
        assertTrue(player.replace_n(arr3));
        assertTrue(player.replace_n(arr4));


        Integer [] narr = {-1,2,3};
        Integer [] narr2 = {5,3,4};
        Integer [] narr3 = {0,2,4,2,3,4};
        Integer [] narr4 = {3,2,0,1,4,5};

        assertFalse(player.replace_n(narr));
        assertFalse(player.replace_n(narr2));
        assertFalse(player.replace_n(narr3));
        assertFalse(player.replace_n(narr4));
    }
}
