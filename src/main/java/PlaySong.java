import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javazoom.jl.player.Player;

public class PlaySong implements Runnable{
    public void run(){
        try{
            FileInputStream fis = new FileInputStream("imperial.mp3");
            Player playMP3 = new Player(fis);
            playMP3.play();
        }
        catch(Exception e){}
    }
}