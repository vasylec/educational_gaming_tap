
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/game.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("FXML Example");
        stage.setScene(scene);
        stage.show();
        stage.setWidth(500);
        stage.setHeight(500);

        stage.show();
    }
}