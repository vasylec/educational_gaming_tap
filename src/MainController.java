import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Node;


public class MainController {
    @FXML
    private Pane mainPane;
    @FXML
    private Button buttonStart;

    public void ButtonStart(ActionEvent event){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource("fxml/game.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(parent));
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
