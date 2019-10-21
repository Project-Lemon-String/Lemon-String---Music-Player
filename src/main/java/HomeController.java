import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
            String json = verifyRequest("playSong", "SongServices", "imperial.mp3");
            if(!json.equals("FailedToQuery")){
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
            }
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

    public String verifyRequest(String method, String objectName, String songId){
        // Check if valid query from catalog.json
        return "{\r\n        \"remoteMethod\":\"" + method + "\",\r\n        \"objectName\":\"" + objectName + "\",\r\n        \"param\": {\r\n            \"songId\": \"" + songId + "\"\r\n        },\r\n        \"return\": \"java.lang.String\"\r\n    }";
    }

}
