import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.*;
import com.google.gson.*;
import java.net.*; 

public class SignupController implements Initializable {

    // Fields from view
    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_email;

    @FXML
    private PasswordField pf_password;

    @FXML
    private Button btnSignup;

    @FXML
    private Text linkLogin;

    // Switches to the login view.
    @FXML
    void login(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Stage stage = (Stage) linkLogin.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void signup(MouseEvent event) throws Exception {
        //Open file and gather list of users from users.json
        JsonParser jsonParser = new JsonParser();

        try{
            String json = "{\r\n        \"remoteMethod\":\"signup\",\r\n        \"objectName\":\"UserServices\",\r\n        \"param\": {\r\n            \"username\": \"" + tf_username.getText() + "\",\r\n            \"password\": \"" + pf_password.getText() + "\",\r\n            \"email\": \"" + tf_email.getText() + "\"\r\n        },\r\n        \"return\": \"java.lang.String\"\r\n    }";
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

        }
        catch(Exception e){}

    }

    // Required function in case any logic is needed before rendering the view.
    public void initialize(URL location, ResourceBundle resources){

    }

}
