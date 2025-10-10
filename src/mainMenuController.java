
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.scene.Parent;

public class mainMenuController implements Initializable{
    @FXML
    private ImageView play_button, settings_button, exit_button, inventory_button;

    @FXML
    private AnchorPane fade;

    @FXML
    private StackPane background;

    @FXML
    private void play_button_pressed(){
        play_button.setImage(new Image("dependencies\\play-pressed.png"));
    }
    @FXML
    private void play_button_released(){
        play_button.setImage(new Image("dependencies\\play.png"));
        
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
    @FXML
    private void inventory_button_pressed(){
        inventory_button.setImage(new Image("dependencies\\inventory-pressed.png"));
    }
    @FXML
    private void inventory_button_released(){
        inventory_button.setImage(new Image("dependencies\\inventory.png"));

    }
    








    private FadeTransition transition_fade;
    private FadeTransition transition_for_enter(){
        transition_fade = new FadeTransition(Duration.seconds(1), fade);
        transition_fade.setFromValue(0);
        transition_fade.setToValue(1);

        return transition_fade;
    }

    private FadeTransition transition_for_exit(String where){
        
        transition_fade = new FadeTransition(Duration.seconds(1), fade);
        transition_fade.setFromValue(1);
        transition_fade.setToValue(0);

        transition_fade.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("DEBUG");
                try {
                    switch (where) {
                        case "settings":
                        Parent scene = new FXMLLoader(getClass().getResource("fxml/settings.fxml")).load();    
                        scene.getStylesheets().add(getClass().getResource("style/slider-style.css").toExternalForm());
                        
                        
                        Platform.runLater(() -> Main.stage.getScene().setRoot(scene));
                        break;
                        case "game":
            
            
                            Main.stage.setScene(new Scene(new FXMLLoader(getClass().getResource("fxml/game.fxml")).load()));

                         
                            
                        break;
                        case "exit":
                            Main.stage.close();
                        break;
                    
                        default:
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        
        return transition_fade;       
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        transition_for_enter().play();
        
        BackgroundImage bgImage = new BackgroundImage(
            new Image("dependencies\\background.png"), 
            BackgroundRepeat.NO_REPEAT, 
            BackgroundRepeat.NO_REPEAT, 
            BackgroundPosition.CENTER, 
            new BackgroundSize(100, 100, true, true, false, true)
        );

        background.setBackground(new Background(bgImage));

        // TODO: Implemnt play button
        play_button.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                Main.buttonClick.play();
                
                transition_for_exit("game").play();
                
                

            }
        });
        
        
        exit_button.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                Main.buttonClick.play();
                transition_for_exit("exit").play();;
            }
        });

        settings_button.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                Main.buttonClick.play();
                transition_for_exit("settings").play();;
            }
        });
    }    



   
}
