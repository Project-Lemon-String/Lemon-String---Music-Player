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

        JsonObject userList = new JsonObject();
        String userFile = "users.json";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Open file
        JsonParser jsonParser = new JsonParser();
        try(FileReader fileReader = new FileReader((userFile));){
            userList = jsonParser.parse(fileReader).getAsJsonObject();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();//System.out.println("File Not Found");
        }
        catch(IOException e){
            e.printStackTrace();//System.out.println("IO");
        }
        catch (Exception e){ //e.printStackTrace();
        }

        // Flags to keep track of failedLogin
        boolean failedLogin = false;
        JsonObject user;

        //Check if user exists and if password matches our users.json
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
