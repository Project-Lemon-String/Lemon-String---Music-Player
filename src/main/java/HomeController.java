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

    @FXML
    private Text linkHome;

    @FXML
    private Text linkProfile;

    @FXML
    private Text linkSearch;

    @FXML
    private Button btnPlay;

    @FXML
    void playMusic(MouseEvent event) throws Exception{
        //add multihtreading
        PlaySong ps = new PlaySong();
        new Thread(ps).start();

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
        
    }

}
