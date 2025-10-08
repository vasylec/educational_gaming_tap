
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    public static Stage stage;

   


    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;


        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/mainMenu.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("FXML Example");
        stage.setScene(scene);
        stage.show();
        stage.setWidth(600);
        stage.setHeight(600);

        stage.show();        
    }
}