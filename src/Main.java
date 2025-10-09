
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application{
    public static Stage stage;
    public static Parent root;


    public static Double audio;
    public static boolean fullscreen;
    public static String resolution;

    public static MediaPlayer backgroundMusic;
    
    public static AudioClip eatSound, buttonClick;
    

    public static void setResolution(double width, double height){
        Main.stage.setWidth(width);
        Main.stage.setHeight(height);
    }

    public static double getDisplayResolutionWidth(){
        return Screen.getPrimary().getBounds().getWidth();
    }
    public static double getDisplayResolutionHeight(){
        return Screen.getPrimary().getBounds().getHeight();
    }

    private void initializeSound(){
        try {
            eatSound = new AudioClip(getClass().getResource("dependencies/sound/eat.wav").toExternalForm());
            eatSound.setVolume(1.0);
            
            buttonClick = new AudioClip(getClass().getResource("dependencies/sound/button_click.wav").toExternalForm());
            buttonClick.setVolume(1.0);

            backgroundMusic = new MediaPlayer(new Media(getClass().getResource("dependencies\\sound\\background.mp3").toExternalForm()));
            backgroundMusic.setVolume(1);
            backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
            backgroundMusic.play();
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scaleRoot(Parent root, Scene scene) {
    double baseWidth = 1280;
    double baseHeight = 720;

    // Calculăm raportul dintre dimensiunea actuală și cea de bază
    double scaleX = scene.getWidth() / baseWidth;
    double scaleY = scene.getHeight() / baseHeight;
    double scale = Math.min(scaleX, scaleY); // păstrează proporțiile corecte

    // Aplicăm scalarea uniformă
    root.setScaleX(scale);
    root.setScaleY(scale);

    // 💡 Centrează conținutul (scapă de marginile albe)
    double offsetX = (scene.getWidth() - baseWidth * scale) / 2;
    double offsetY = (scene.getHeight() - baseHeight * scale) / 2;
    root.setTranslateX(offsetX);
    root.setTranslateY(offsetY);
}

    
    

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
        Main.fullscreen = false;

        stage.setFullScreenExitHint("");
        initializeSound();


        

        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/mainMenu.fxml"));
        root = loader.load();
        Scene scene = new Scene(root, 1280, 720);

        

        stage.setTitle("FXML Example");
        stage.setScene(scene);
        stage.show();
        stage.setWidth(800);
        stage.setHeight(600);
        
        stage.show();        
    }

    public static void main(String[] args) {
        launch(args);
    }
}