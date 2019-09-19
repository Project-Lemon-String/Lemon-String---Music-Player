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
import java.util.*;
import java.io.*;
import com.google.gson.*;

public class SignupController implements Initializable {

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

    @FXML
    void login(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Stage stage = (Stage) linkLogin.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void signup(MouseEvent event) throws Exception {
        //Create JSON
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

        
        JsonObject userDetails = new JsonObject();
        userDetails.addProperty("email", tf_email.getText());
        userDetails.addProperty("password", pf_password.getText());
        
        JsonArray playlist = new JsonArray();
        userDetails.add("playlists", playlist);

        boolean userExists = false;

        //Check if user exists
        try {
            JsonObject user = userList.get(tf_username.getText()).getAsJsonObject();
            userExists = true;
        }
        catch (NullPointerException e){}
        catch (Exception e){}

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

    public void initialize(URL location, ResourceBundle resources){

    }

}
