
import javafx.application.Application;
import javafx.collections.FXCollections;
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
import java.io.*;
import com.google.gson.*;
import org.json.JSONArray;

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

        boolean failedLogin = false;
        boolean userExists = false;
        JsonObject user;
        //Check if user exists
        try {
            user = userList.get(tf_username.getText()).getAsJsonObject();
            CurrentUserData.currentUser = user;
            System.out.println(tf_username);
            System.out.println(user);

            if(user!=null){
                String checkPassword = user.get("password").getAsString();

                if(!(checkPassword.equals(tf_password.getText()))){
                    failedLogin = true;;
                }
            }else{
                failedLogin = true;
            }
        }
        catch(Exception e){}

        if(!failedLogin){
            try{
                CurrentUserData.userSignedIn = tf_username.getText();
                JsonElement playlistTemp = CurrentUserData.currentUser.get("playlists");

                CurrentUserData.playlist = gson.fromJson(playlistTemp, ArrayList.class);
                CurrentUserData.playlistItems = FXCollections.observableArrayList(CurrentUserData.playlist);
            }
            catch(Exception e){

            }

            
            System.out.println(CurrentUserData.userSignedIn);
            System.out.println(CurrentUserData.currentUser);
            System.out.println(CurrentUserData.playlist);
            System.out.println(CurrentUserData.playlistItems);

            Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
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
