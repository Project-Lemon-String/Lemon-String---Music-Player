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
import java.util.ResourceBundle;
import javazoom.jl.player.Player;

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
        //add multihtreading
        PlaySong ps = new PlaySong();
        new Thread(ps).start();

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
