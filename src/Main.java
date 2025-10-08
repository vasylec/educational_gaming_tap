
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    public static Stage stage;
    public static Parent root;


    public static Double audio;
    public static boolean fullscreen;
    public static String resolution;

   


    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/mainMenu.fxml"));
        root = loader.load();
        

        Scene scene = new Scene(root, 1280, 720);
        stage.setTitle("FXML Example");
        stage.setScene(scene);
        stage.show();
        stage.setWidth(800);
        stage.setHeight(600);

        stage.show();        
    }
}