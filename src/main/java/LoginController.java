import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.*;
import java.io.*;
import com.google.gson.*;
import java.net.*; 

public class LoginController implements Initializable {

    // Fields from view
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
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        boolean failedLogin = false;
        try{
            String json = "{\r\n        \"remoteMethod\":\"login\",\r\n        \"objectName\":\"UserServices\",\r\n        \"param\": {\r\n            \"username\": \"" + tf_username.getText() + "\",\r\n            \"password\": \"" + tf_password.getText() + "\"\r\n        },\r\n        \"return\": \"java.lang.String\"\r\n    }";
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
                failedLogin = true;
            }            
            
            if(result.equals("failed")){
                failedLogin = true;
            }

        }
        catch(Exception e){}

        // If login credentials were correct then we save it in our global variables
        if(!failedLogin){
            try{

                CurrentUserData.userSignedIn = tf_username.getText();
                JsonElement playlistTemp = CurrentUserData.currentUser.get("playlists");

                CurrentUserData.playlist = gson.fromJson(playlistTemp, ArrayList.class);
                CurrentUserData.playlistItems = FXCollections.observableArrayList(CurrentUserData.playlist);

                CurrentUserData.songSearchList = new ArrayList<>();
                CurrentUserData.songSearchListItems = FXCollections.observableArrayList(CurrentUserData.songSearchList);
            }
            catch(Exception e){}

            // Generate home view if login is correct
            Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
        }
        else{
            //Generate our login failed view is failed.
            Parent root = FXMLLoader.load(getClass().getResource("/login_failed.fxml"));
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
        }
    }

    // Switches to signup view
    @FXML
    void signup(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/signup.fxml"));
        Stage stage = (Stage) linkSignup.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    // Required function in case any logic is needed before rendering the view.
    public void initialize(URL location, ResourceBundle resources){

    }

}
