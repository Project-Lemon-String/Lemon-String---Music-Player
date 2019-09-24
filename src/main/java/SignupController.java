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

        //Create JSON and GSON objects
        JsonObject userList = new JsonObject();
        String userFile = "users.json";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        //Open file and gather list of users from users.json
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

        // Saving our email and pass in a JSONObject
        JsonObject userDetails = new JsonObject();
        userDetails.addProperty("email", tf_email.getText());
        userDetails.addProperty("password", pf_password.getText());
        
        // Create a playlist object in our current user to keep track of their playlists.
        JsonArray playlist = new JsonArray();
        userDetails.add("playlists", playlist);

        // User exists flag
        boolean userExists = false;

        //Check if user exists
        try {
            JsonObject user = userList.get(tf_username.getText()).getAsJsonObject();
            userExists = true;
        }
        catch (NullPointerException e){}
        catch (Exception e){}

        // if user doesn't exist, don't add it and error out.
        // if user does exist, add user.
        if(!userExists){
            userList.add(tf_username.getText(), userDetails);
            
            try (FileWriter fileWriter = new FileWriter(userFile)){
                gson.toJson(userList,fileWriter);
            }
            catch(IOException e){
                //e.printStackTrace();
                System.out.println("File failed to write");
            }
            catch (Exception e){}
        }
        else{
            System.out.println("File add user.");
        }
    }

    // Required function in case any logic is needed before rendering the view.
    public void initialize(URL location, ResourceBundle resources){

    }

}
