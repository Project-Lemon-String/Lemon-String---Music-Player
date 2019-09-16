
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
import java.net.URL;
import java.util.ResourceBundle;
import java.util.*;

public class LoginController implements Initializable {

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField tf_password;

    @FXML
    private Button btnLogin;

    @FXML
    private Text linkSignup;

    @FXML
    void login(MouseEvent event) throws Exception{

        if(tf_username.getText().equals("Bryan")){
            if(tf_password.getText().equals("password")){
                Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.setScene(new Scene(root));
            }
        }else{
            Parent root = FXMLLoader.load(getClass().getResource("/login_failed.fxml"));
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
        }
    }

    @FXML
    void signup(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/signup.fxml"));
        Stage stage = (Stage) linkSignup.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void initialize(URL location, ResourceBundle resources){

    }

}
