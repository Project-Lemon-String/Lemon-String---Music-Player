import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;
import javafx.collections.*;

public class ProfileController implements Initializable {

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
    void addPlaylist(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/create_playlist.fxml"));
        Stage stage = (Stage) linkSearch.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void removePlaylist(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/delete_playlist.fxml"));
        Stage stage = (Stage) linkSearch.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void switchToHome(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
        Stage stage = (Stage) linkSearch.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void switchToProfile(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/profile.fxml"));
        Stage stage = (Stage) linkSearch.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void switchToSearch(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/search.fxml"));
        Stage stage = (Stage) linkSearch.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void initialize(URL location, ResourceBundle resources){
        listForPlaylists.setItems(CurrentUserData.playlistItems);
        profileTitle.setText(CurrentUserData.userSignedIn + "'s Profile & Playlist(s)\n");
    }

}
