
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

public class mainMenuController implements Initializable{
    @FXML
    private ImageView play_button, settings_button, exit_button;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        play_button.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event arg0) {
                
                try {
                    
                    Main.stage.setScene(new Scene(new FXMLLoader(getClass().getResource("fxml/game.fxml")).load(), 600, 400));
                    
                } catch (IOException e) {
                }


            }
            
        });
        
        
        exit_button.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                Main.stage.close();
            }
        });

        settings_button.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event arg0) {
                try {
                   
                    Main.stage.setScene(new Scene(new FXMLLoader(getClass().getResource("fxml/settings.fxml")).load(), 600, 400));
                    
                } catch (IOException e) {
                }

            }
            
        });
    }    



   
}
