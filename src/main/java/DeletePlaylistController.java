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

public class DeletePlaylistController implements Initializable{

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
        JsonObject userList = new JsonObject();
        String userFile = "users.json";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        //Open file
        JsonParser jsonParser = new JsonParser();
        try(FileReader fileReader = new FileReader((userFile));){
            userList = jsonParser.parse(fileReader).getAsJsonObject();
            System.out.println("YAY WE OPENED FILE");
        }
        catch(FileNotFoundException e){
            e.printStackTrace();//System.out.println("File Not Found");
        }
        catch(IOException e){
            e.printStackTrace();//System.out.println("IO");
        }
        catch (Exception e){ //e.printStackTrace();
        }

        JsonObject tempUser = CurrentUserData.currentUser;

        CurrentUserData.playlistItems.removeAll(playlistField.getText());
        CurrentUserData.playlist.remove(playlistField.getText());
        listForPlaylists.setItems(CurrentUserData.playlistItems);

        tempUser.add("playlists", jsonParser.parse(gson.toJson(CurrentUserData.playlist)));
        userList.add(CurrentUserData.userSignedIn, tempUser);

        //Write to file
        try (FileWriter fileWriter = new FileWriter(userFile)){
            gson.toJson(userList,fileWriter);
        }
        catch(IOException e){
            //e.printStackTrace();
            System.out.println("File failed to write");
        }
        catch (Exception e){}
        
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
