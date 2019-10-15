import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;
import java.io.*;
import java.util.ArrayList;
import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.*;

public class SearchController implements Initializable{

    @FXML
    private Text linkHome;

    @FXML
    private Text linkProfile;

    @FXML
    private Text linkSearch;

    @FXML
    private Text profileTitle;

    @FXML
    private TextField tf_search;

    @FXML
    private ListView<String> listForSongs;

    @FXML
    void search(MouseEvent event) throws Exception{
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        CurrentUserData.songSearchListItems.clear();

        try{
            String json = "{\r\n        \"remoteMethod\":\"searchSong\",\r\n        \"objectName\":\"UserServices\",\r\n        \"param\": {\r\n            \"key\": \"" + tf_search.getText() + "\"\r\n        },\r\n        \"return\": \"java.lang.String\"\r\n    }";
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

            //CurrentUserData.currentUser = jo.get("userData").getAsString();
            try{
                JsonArray finalMatch = (JsonArray) jo.get("finalMatches");
                
                for(JsonElement song: finalMatch){
                    CurrentUserData.songSearchListItems.add(song.getAsString());
                }

                listForSongs.setItems(CurrentUserData.songSearchListItems);
            }
            catch(Exception e){
            }            
            

        }
        catch(Exception e){}

        Parent root = FXMLLoader.load(getClass().getResource("/search.fxml"));
        Stage stage = (Stage) linkSearch.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void switchToHome(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
        Stage stage = (Stage) linkSearch.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void switchToProfile(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/profile.fxml"));
        Stage stage = (Stage) linkSearch.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void switchToSearch(MouseEvent event) {

    }

    // Required function in case any logic is needed before rendering the view.
    public void initialize(URL location, ResourceBundle resources){
        listForSongs.setItems(CurrentUserData.songSearchListItems);
    }

}
