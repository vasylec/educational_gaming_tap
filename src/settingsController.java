import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class settingsController implements Initializable{
    @FXML
    private ImageView back_button, audio_button, video_button;

    @FXML
    private StackPane background;


    @FXML
    private BorderPane fade;

    @FXML
    private GridPane video_pane, audio_pane;





    FadeTransition transition_fade;
    private FadeTransition transition_for_exit(){
        transition_fade = new FadeTransition(Duration.seconds(1), fade);
        transition_fade.setFromValue(1);
        transition_fade.setToValue(0);
        transition_fade.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                try {                        
                    Main.stage.setScene(new Scene(new FXMLLoader(getClass().getResource("fxml/mainMenu.fxml")).load(), 600, 400));
                } catch (IOException e) {
                    e.printStackTrace();
                }    
            }
            
        });

        return transition_fade;
    };
    private FadeTransition transition_for_enter(){
        transition_fade = new FadeTransition(Duration.seconds(1), fade);
        transition_fade.setFromValue(0);
        transition_fade.setToValue(1);

        return transition_fade;
    };


    @FXML
    public void test(){
        if(Main.fullscreen == false){
            Main.fullscreen = true;
            Main.stage.setFullScreen(true);
        }
        else{
            Main.fullscreen = false;
            Main.stage.setFullScreen(false);
        }
    }


    @FXML
    public void test2(){
        Main.root.setScaleX(200.0 / 600.0);
        Main.root.setScaleY(200.0 / 400.0);
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        transition_for_enter().play();
        
        video_pane.setOpacity(0);
        audio_pane.setOpacity(0);

        

        background.setBackground(new Background(new BackgroundImage(
            new Image("dependencies\\background.png"), 
            BackgroundRepeat.NO_REPEAT, 
            BackgroundRepeat.NO_REPEAT, 
            BackgroundPosition.CENTER, 
            new BackgroundSize(100, 100, true, true, false, true)
        )));
      



        back_button.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                transition_for_exit().play();
            }
        });

        video_button.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                audio_pane.setOpacity(0);
                audio_pane.setMouseTransparent(true);
                video_pane.setMouseTransparent(false);
                video_pane.setOpacity(1);
            }
        });
        
        audio_button.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                video_pane.setOpacity(0);
                video_pane.setMouseTransparent(true);
                audio_pane.setMouseTransparent(false);
                audio_pane.setOpacity(1);
            }
        });

    }
    
}
