import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;
import javafx.collections.*;
import java.io.*;
import com.google.gson.*;
import java.net.*;

public class DeletePlaylistController implements Initializable{

    // Fields from view
    @FXML
    private Text linkHome;

    @FXML
    private Text linkProfile;

    @FXML
    private Text linkSearch;

    @FXML
    private Text profileTitle;

    @FXML
    private ImageView profileIcon;

    @FXML
    private ListView<String> listForPlaylists;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnRemove;

    @FXML
    private Button exitButton;

    @FXML
    private TextField playlistField;

    @FXML
    private Button btnDelete;

    @FXML
    void addPlaylist(MouseEvent event) throws Exception {

    }

    @FXML
    void deletePlaylist(MouseEvent event) throws Exception {
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();

        try{
            String json = "{\r\n        \"remoteMethod\":\"deletePlaylist\",\r\n        \"objectName\":\"UserServices\",\r\n        \"param\": {\r\n            \"username\": \"" + CurrentUserData.userSignedIn + "\",\r\n            \"playlistName\": \"" + playlistField.getText() + "\"\r\n        },\r\n        \"return\": \"java.lang.String\"\r\n    }";
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
                String userDataStr = jo.get("userData").getAsString();
                CurrentUserData.currentUser = new JsonParser().parse(userDataStr).getAsJsonObject();
            }
            catch(Exception e){
            }

        }
        catch(Exception e){}

        // If login credentials were correct then we save it in our global variables

        try{
            JsonObject tempUser = CurrentUserData.currentUser;
            
            CurrentUserData.playlist.remove(playlistField.getText());
            CurrentUserData.playlistItems.removeAll(playlistField.getText());
            listForPlaylists.setItems(CurrentUserData.playlistItems);

            tempUser.add("playlists", jsonParser.parse(gson.toJson(CurrentUserData.playlist)));
        }
        catch(Exception e){

        }

        Parent root = FXMLLoader.load(getClass().getResource("/profile.fxml"));
        Stage stage = (Stage) linkSearch.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void removePlaylist(MouseEvent event) throws Exception{

    }

    @FXML
    void returnToProfile(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/profile.fxml"));
        Stage stage = (Stage) linkSearch.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void switchToHome(MouseEvent event) {

    }

    @FXML
    void switchToProfile(MouseEvent event) {

    }

    @FXML
    void switchToSearch(MouseEvent event) {

    }
    
    // Required function in case any logic is needed before rendering the view.
    public void initialize(URL location, ResourceBundle resources){
        profileTitle.setText(CurrentUserData.userSignedIn + "'s Profile & Playlist(s)\n");
    }
}
