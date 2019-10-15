import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import java.io.FileInputStream;
import java.net.URL;
import java.util.*;
import javazoom.jl.player.Player;
import java.net.*;
import com.google.gson.*;

public class HomeController implements Initializable {

    // Fields from view
    @FXML
    private Text linkHome;

    @FXML
    private Text linkProfile;

    @FXML
    private Text linkSearch;

    @FXML
    private Button btnPlay;

    // Sample play button that is multithreaded so it doesn't pause the program for now.
    // Need to implement pause feature and it's view.
    @FXML
    void playMusic(MouseEvent event) throws Exception{
        JsonParser jsonParser = new JsonParser();
        try{
            String json = "{\r\n        \"remoteMethod\":\"playSong\",\r\n        \"objectName\":\"SongServices\",\r\n        \"param\": {\r\n            \"songId\": \"imperial.mp3\"\r\n        },\r\n        \"return\": \"java.lang.String\"\r\n    }";
            // getting localhost ip 
            InetAddress ip = InetAddress.getByName("localhost"); 
            byte[] buffer = json.getBytes();
            // obtaining input and out streams 
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ip, 1337); 
            DatagramSocket ds = new DatagramSocket(); 
            ds.send(packet);
            byte[] buf = new byte[8192];
            DatagramPacket p = new DatagramPacket(buf, buf.length);
            ds.receive(p);
            String outty = new String(buf, 0, p.getLength());
            JsonObject jo = (JsonObject) jsonParser.parse(outty);
            System.out.println(jo);
            String result = jo.get("ret").getAsString();
        }
        catch(Exception e){}
    }

    // Switches to the Profile page view.
    @FXML
    void switchToProfile(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/profile.fxml"));
        Stage stage = (Stage) linkSearch.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    // Switches to the Search page view.
    @FXML
    void switchToSearch(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/search.fxml"));
        Stage stage = (Stage) linkSearch.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    // Required function in case any logic is needed before rendering the view.
    public void initialize(URL location, ResourceBundle resources){
        
    }

}
