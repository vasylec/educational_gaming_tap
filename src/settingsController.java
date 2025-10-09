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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class settingsController implements Initializable{
    @FXML
    private ImageView back_button, audio_button, video_button, apply_button, fullscreen_button;
    @FXML
    private StackPane background;
    @FXML
    private BorderPane fade;
    @FXML
    private GridPane video_pane, audio_pane;
    @FXML
    private Slider audio_slider, effect_slider;
    @FXML
    private ComboBox<ImageView> resolutions;

    String[] resolutions_value = {
        "800x600",
        "1024x768",
        "1280x720",
        "1366x768",
        "1920x1080",
        "2560x1440"
    };
    

    @FXML
    private void apply_button_pressed(){
        apply_button.setImage(new Image("dependencies\\apply-pressed.png"));   
    }
    @FXML
    private void apply_button_released(){
        apply_button.setImage(new Image("dependencies\\apply.png"));   
    }
    @FXML
    private void back_button_pressed(){
        back_button.setImage(new Image("dependencies\\back-pressed.png"));
    }
    @FXML
    private void back_button_released(){
        back_button.setImage(new Image("dependencies\\back.png"));
    }
    @FXML
    private void video_button_pressed(){
        video_button.setImage(new Image("dependencies\\video-pressed.png"));
    }
    @FXML
    private void video_button_released(){
        video_button.setImage(new Image("dependencies\\video.png"));
    }
    @FXML
    private void audio_button_pressed(){
        audio_button.setImage(new Image("dependencies\\audio-pressed.png"));
    }
    @FXML
    private void audio_button_released(){
        audio_button.setImage(new Image("dependencies\\audio.png"));
    }
    @FXML
    private void yes_no_button_pressed(){    
        fullscreen_button.setImage(new Image("dependencies\\yes-no-pressed.png"));
    }
    @FXML
    private void yes_no_button_released(){
        if(Main.fullscreen == true)
            fullscreen_button.setImage(new Image("dependencies\\yes.png"));
        else
            fullscreen_button.setImage(new Image("dependencies\\no.png"));
    }


    @FXML
    private void apply_clicked(){
        Main.buttonClick.play();
        Main.backgroundMusic.setVolume(audio_slider.getValue() / 100);


        if(!resolutions.getValue().toString().equals(null))
        switch (resolutions.getValue().toString()) {
            case "800x600":
                Main.setResolution(800, 600);
                break;
            case "1024x768":
                Main.setResolution(1024, 768);
                
                break;
            case "1280x720":
                Main.setResolution(1280, 720);
                break;
            case "1366x768":
                Main.setResolution(1366, 768);
                break;
            case "1920x1080":
                Main.setResolution(1920, 1080);
                break;
            case "2560x1440":
                Main.setResolution(2560, 1440);
                break;
        
            default:
                break;
        }
        



        Main.buttonClick.setVolume(effect_slider.getValue() / 100);
        Main.eatSound.setVolume(effect_slider.getValue() / 100);
        Main.buttonClick.setVolume(effect_slider.getValue() / 100);
    }

    @FXML 
    public void fullscreen_button_action(){
        
        
        
            if(Main.fullscreen == false){

                fullscreen_button.setImage(new Image("dependencies\\yes.png"));
                Main.fullscreen = true;
                Platform.runLater(() -> Main.stage.setFullScreen(true));
                
            }
            else{
                fullscreen_button.setImage(new Image("dependencies\\no.png"));
                Main.fullscreen = false;
                
                Platform.runLater(() -> Main.stage.setFullScreen(false));
            }
    }



    FadeTransition transition_fade;
    private FadeTransition transition_for_exit(){
        transition_fade = new FadeTransition(Duration.seconds(1), fade);
        transition_fade.setFromValue(1);
        transition_fade.setToValue(0);
        transition_fade.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {                    
                Platform.runLater(() -> {
                    try {
                        Main.stage.getScene().setRoot(new FXMLLoader(getClass().getResource("fxml/mainMenu.fxml")).load());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });   
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
    public void test2(){
        Main.root.setScaleX(200.0 / 600.0);
        Main.root.setScaleY(200.0 / 400.0);
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        transition_for_enter().play();
        audio_slider.setValue(Main.backgroundMusic.getVolume() * 100);
        effect_slider.setValue(Main.eatSound.getVolume() * 100);
        
        video_pane.setOpacity(1);
        video_button.setImage(new Image("dependencies\\video-pressed.png"));
        audio_pane.setOpacity(0);

        if(Main.fullscreen == true)
            fullscreen_button.setImage(new Image("dependencies\\yes.png"));
        else
            fullscreen_button.setImage(new Image("dependencies\\no.png"));
            

        

        // resolutions.getItems().addAll(resolutions_value);

        resolutions.getItems().addAll(
            new ImageView(new Image("dependencies/video.png"))
        );
       
        

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
                Main.buttonClick.play();
                transition_for_exit().play();
            }
        });

        video_button.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                Main.buttonClick.play();
                video_button.setImage(new Image("dependencies\\video-pressed.png"));
                audio_button.setImage(new Image("dependencies\\audio.png"));
                audio_pane.setOpacity(0);
                audio_pane.setMouseTransparent(true);
                video_pane.setMouseTransparent(false);
                video_pane.setOpacity(1);
            }
        });
        
        audio_button.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                Main.buttonClick.play();
                video_button.setImage(new Image("dependencies\\video.png"));
                audio_button.setImage(new Image("dependencies\\audio-pressed.png"));
                video_pane.setOpacity(0);
                video_pane.setMouseTransparent(true);
                audio_pane.setMouseTransparent(false);
                audio_pane.setOpacity(1);
            }
        });

    }
    
}
