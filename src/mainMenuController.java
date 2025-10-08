
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class mainMenuController implements Initializable{
    @FXML
    private ImageView play_button, settings_button, exit_button;

    @FXML
    private void play_button_pressed(){
        play_button.setImage(new Image("dependencies\\play-pressed.png"));;
    }
    @FXML
    private void play_button_released(){
        play_button.setImage(new Image("dependencies\\play.png"));;
    }



    @FXML
    private void settings_button_pressed(){
        settings_button.setImage(new Image("dependencies\\settings-pressed.png"));
    }
    @FXML
    private void settings_button_released(){
        settings_button.setImage(new Image("dependencies\\settings.png"));
    }


    @FXML
    private void exit_button_pressed(){
        exit_button.setImage(new Image("dependencies\\exit-pressed.png"));
    }
    @FXML
    private void exit_button_released(){
        exit_button.setImage(new Image("dependencies\\exit.png"));

    }
    

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
