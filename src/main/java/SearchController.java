import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SearchController {

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
    private ListView<?> listForSongs;

    @FXML
    void search(MouseEvent event) {

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

}
