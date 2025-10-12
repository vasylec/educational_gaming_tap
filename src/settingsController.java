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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
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
import javafx.util.Callback;
import javafx.util.Duration;

public class settingsController implements Initializable{
    @FXML
    private ImageView back_button, audio_button, video_button, apply_button, fullscreen_button, game_button, gridViewButton;
    @FXML
    private StackPane background;
    @FXML
    private BorderPane fade;
    @FXML
    private GridPane video_pane, audio_pane, game_pane;
    @FXML
    private Slider audio_slider, effect_slider, speed_slider;
    @FXML
    private ComboBox<ImageView> resolutions;
    

    ImageView[] resolutions_value = {
        new ImageView(new Image("/dependencies/800.png")),
        new ImageView(new Image("/dependencies/1024.png")),
        new ImageView(new Image("/dependencies/1280.png")),
        new ImageView(new Image("/dependencies/1366.png")),
        new ImageView(new Image("/dependencies/1920.png")),
        new ImageView(new Image("/dependencies/2560.png"))
    };
    

    @FXML
    private void apply_button_pressed(){
        apply_button.setImage(new Image("\\dependencies\\apply-pressed.png"));   
    }
    @FXML
    private void apply_button_released(){
        apply_button.setImage(new Image("\\dependencies\\apply.png"));   
    }
    @FXML
    private void back_button_pressed(){
        back_button.setImage(new Image("\\dependencies\\back-pressed.png"));
    }
    @FXML
    private void back_button_released(){
        back_button.setImage(new Image("\\dependencies\\back.png"));
    }
    @FXML
    private void video_button_pressed(){
        video_button.setImage(new Image("\\dependencies\\video-pressed.png"));
    }
    @FXML
    private void video_button_released(){
        video_button.setImage(new Image("\\dependencies\\video.png"));
    }
    @FXML
    private void audio_button_pressed(){
        audio_button.setImage(new Image("\\dependencies\\audio-pressed.png"));
    }
    @FXML
    private void audio_button_released(){
        audio_button.setImage(new Image("\\dependencies\\audio.png"));
    }
    @FXML
    private void yes_no_button_pressed(){    
        fullscreen_button.setImage(new Image("\\dependencies\\yes-no-pressed.png"));
    }
    @FXML
    private void yes_no_button_released(){
        if(Main.fullscreen == true)
            fullscreen_button.setImage(new Image("\\dependencies\\yes.png"));
        else
            fullscreen_button.setImage(new Image("\\dependencies\\no.png"));
    }
    @FXML
    private void yes_no_button_pressed2(){    
        gridViewButton.setImage(new Image("\\dependencies\\yes-no-pressed.png"));
    }
    @FXML
    private void yes_no_button_released2(){
        if(Main.gridView == true)
            gridViewButton.setImage(new Image("\\dependencies\\yes.png"));
        else
            gridViewButton.setImage(new Image("\\dependencies\\no.png"));
    }
    @FXML
    private void game_button_pressed(){
        game_button.setImage(new Image("\\dependencies\\game-pressed.png"));
    }
    @FXML
    private void game_button_released(){
        game_button.setImage(new Image("\\dependencies\\game.png"));
    }


    @FXML
    private void apply_clicked(){
        Main.buttonClick.play();
        Main.backgroundMusic.setVolume(audio_slider.getValue() / 100);

        try{
        String path = resolutions.getValue().getImage().getUrl();
        String fileName = path.substring(path.lastIndexOf("/") + 1);
        
        // System.out.println(path.substring(path.lastIndexOf("/") + 1));


            if(!resolutions.getValue().toString().equals(null))
            switch (fileName) {
                case "800.png":
                    Main.setResolution(800, 600);
                    break;
                case "1024.png":
                    Main.setResolution(1024, 768);
                    break;
                case "1280.png":
                    Main.setResolution(1280, 720);
                    break;
                case "1366.png":
                    Main.setResolution(1366, 768);
                    break;
                case "1920.png":
                    Main.setResolution(1920, 1080);
                    break;
                case "2560.png":
                    Main.setResolution(2560, 1440);
                    break;
            
                default:
                    break;
            }
        }
        catch(Exception e){

        }
        



        Main.eatSound.setVolume(effect_slider.getValue() / 100);
        Main.buttonClick.setVolume(effect_slider.getValue() / 100);
        Main.deadSound.setVolume(effect_slider.getValue()/ 100);
    
    
        if(game_pane.getOpacity() == 1){
            Main.speed = (int) speed_slider.getValue();


            System.out.println(Main.speed);

        }
    
    
    }

    @FXML 
    public void fullscreen_button_action(){
            Main.buttonClick.play();
        
        
            if(Main.fullscreen == false){

                fullscreen_button.setImage(new Image("\\dependencies\\yes.png"));
                Main.fullscreen = true;
                Platform.runLater(() -> Main.stage.setFullScreen(true));
                
            }
            else{
                fullscreen_button.setImage(new Image("\\dependencies\\no.png"));
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
                        Main.stage.getScene().setRoot(new FXMLLoader(getClass().getResource("/fxml/mainMenu.fxml")).load());
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
        speed_slider.setValue(Main.speed);
        audio_slider.setValue(Main.backgroundMusic.getVolume() * 100);
        effect_slider.setValue(Main.eatSound.getVolume() * 100);
        
        game_button.setImage(new Image("\\dependencies\\game-pressed.png"));
        video_pane.setOpacity(0);
        audio_pane.setOpacity(0);
        game_pane.setOpacity(1);
        gridViewButton.setImage(new Image("\\dependencies\\yes.png"));


        if(Main.fullscreen == true)
            fullscreen_button.setImage(new Image("\\dependencies\\yes.png"));
        else
            fullscreen_button.setImage(new Image("\\dependencies\\no.png"));
            
        resolutions.getItems().setAll(resolutions_value);
        

        resolutions.setCellFactory(new Callback<ListView<ImageView>, ListCell<ImageView>>() {
            @Override
            public ListCell<ImageView> call(ListView<ImageView> listView) {
                return new ListCell<ImageView>() {
                    @Override
                    protected void updateItem(ImageView item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            ImageView imageCopy = new ImageView(item.getImage());
                            imageCopy.setFitWidth(100);
                            imageCopy.setPreserveRatio(true);
                            setGraphic(imageCopy);
                        }
                    }
                };
            }
        });
            
        background.setBackground(new Background(new BackgroundImage(
            new Image("\\dependencies\\background.png"), 
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
                video_button.setImage(new Image("\\dependencies\\video-pressed.png"));
                audio_button.setImage(new Image("\\dependencies\\audio.png"));
                game_button.setImage(new Image("\\dependencies\\game.png"));
                audio_pane.setOpacity(0);
                game_pane.setOpacity(0);
                game_pane.setMouseTransparent(true);
                audio_pane.setMouseTransparent(true);
                video_pane.setMouseTransparent(false);
                video_pane.setOpacity(1);
                
            }
        });
        
        audio_button.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                Main.buttonClick.play();
                video_button.setImage(new Image("\\dependencies\\video.png"));
                audio_button.setImage(new Image("\\dependencies\\audio-pressed.png"));
                game_button.setImage(new Image("\\dependencies\\game.png"));
                game_pane.setOpacity(0);
                video_pane.setOpacity(0);
                game_pane.setMouseTransparent(true);
                video_pane.setMouseTransparent(true);
                audio_pane.setMouseTransparent(false);
                audio_pane.setOpacity(1);
                
            }
        });

        game_button.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event arg0) {
                Main.buttonClick.play();
                video_button.setImage(new Image("\\dependencies\\video.png"));
                audio_button.setImage(new Image("\\dependencies\\audio.png"));
                game_button.setImage(new Image("\\dependencies\\game-pressed.png"));
                video_pane.setOpacity(0);
                audio_pane.setOpacity(0);
                video_pane.setMouseTransparent(true);
                audio_pane.setMouseTransparent(true);
                game_pane.setMouseTransparent(false);
                game_pane.setOpacity(1);
               
            }
            
        });

        gridViewButton.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                
                if(Main.gridView == true){
                    Main.gameBgImage = new Image("\\dependencies\\snakeGame-no-grid.png");
                    gridViewButton.setImage(new Image("\\dependencies\\no.png"));
                    Main.gridView = false;
                }
                else{
                    Main.gameBgImage = new Image("\\dependencies\\snakeGame.png");
                    gridViewButton.setImage(new Image("\\dependencies\\yes.png"));
                    Main.gridView = true;
                }
            
            }
        });
    }
    
}
