import com.google.gson.JsonObject;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CurrentUserData{
    public static String userSignedIn;
    public static JsonObject currentUser;
    public static ArrayList<String> playlist;
    public static ObservableList<String> playlistItems;
    public static ArrayList<String> songSearchList;
    public static ObservableList<String> songSearchListItems;
}