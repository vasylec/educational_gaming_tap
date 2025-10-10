import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game extends Application{

    

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/game.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1920, 1080);
        stage.setTitle("Snake Game");
        stage.setScene(scene);
        stage.setResizable(true);

        stage.show();

        
    }
    
}
