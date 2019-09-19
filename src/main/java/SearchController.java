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

        CurrentUserData.songSearchListItems.clear();

        String keyword = tf_search.getText();

        JsonParser jsonParser =  new JsonParser();
        JsonArray musicList = new JsonArray();

        try(FileReader fileReader = new FileReader(("music.json"));){
            musicList = jsonParser.parse(fileReader).getAsJsonArray();
        }
        catch(FileNotFoundException e){}
        catch(IOException e){}

        ArrayList<JsonObject> matches = new ArrayList<>();

        for(int i = 0; i < musicList.size(); i++){
            JsonObject songInfo = musicList.get(i).getAsJsonObject();
            JsonObject artist = songInfo.get("artist").getAsJsonObject();
            JsonObject song = songInfo.get("song").getAsJsonObject();
            String artistName = artist.get("name").getAsString();
            String songTitle = song.get("title").getAsString();
            if(artistName.equalsIgnoreCase(keyword) || songTitle.equalsIgnoreCase(keyword)){
                matches.add(songInfo);
            }
        }

        for(JsonObject el: matches){
            CurrentUserData.songSearchListItems.add(el.get("artist").getAsJsonObject().get("name").getAsString() + " - " + el.get("song").getAsJsonObject().get("title").getAsString());
        }

        System.out.print(CurrentUserData.songSearchListItems);

        listForSongs.setItems(CurrentUserData.songSearchListItems);

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

    public void initialize(URL location, ResourceBundle resources){
        listForSongs.setItems(CurrentUserData.songSearchListItems);
    }

}
