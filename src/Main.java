
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application{
    public static Stage stage;
    public static Parent root;

    public static Double audio;
    public static boolean fullscreen;
    public static String resolution;

    public static MediaPlayer backgroundMusic;
    
    public static AudioClip eatSound, buttonClick, deadSound;

    public static boolean gamePaused, gridView;

    public static int databaseScore, databaseHighestScore;

    public static SnakeSkin defaultSkin,redSkin;
    public static SnakeSkin selectedSkin;

    public static Image gameBgImage;


    private void loadSkins(){
        defaultSkin = new SnakeSkin("\\dependencies\\snakes\\defaultSnake\\body-orizontal.png","\\dependencies\\snakes\\defaultSnake\\body-vertical.png",
        "\\dependencies\\snakes\\defaultSnake\\body-curve-left-up.png","\\dependencies\\snakes\\defaultSnake\\body-curve-up-right.png","\\dependencies\\snakes\\defaultSnake\\body-curve-left-down.png","\\dependencies\\snakes\\defaultSnake\\body-curve-down-right.png",
        "\\dependencies\\snakes\\defaultSnake\\body-end-up.png","\\dependencies\\snakes\\defaultSnake\\body-end-down.png","\\dependencies\\snakes\\defaultSnake\\body-end-left.png","\\dependencies\\snakes\\defaultSnake\\body-end-right.png",
        "\\dependencies\\snakes\\defaultSnake\\head-up.png","\\dependencies\\snakes\\defaultSnake\\head-down.png","\\dependencies\\snakes\\defaultSnake\\head-left.png","\\dependencies\\snakes\\defaultSnake\\head-right.png",
        "\\dependencies\\snakes\\defaultSnake\\head-up.gif","\\dependencies\\snakes\\defaultSnake\\head-down.gif","\\dependencies\\snakes\\defaultSnake\\head-left.gif","\\dependencies\\snakes\\defaultSnake\\head-right.gif");
        
        redSkin = new SnakeSkin("\\dependencies\\snakes\\redSnake\\body-orizontal.png","\\dependencies\\snakes\\redSnake\\body-vertical.png",
        "\\dependencies\\snakes\\redSnake\\body-curve-left-up.png","\\dependencies\\snakes\\redSnake\\body-curve-up-right.png","\\dependencies\\snakes\\redSnake\\body-curve-left-down.png","\\dependencies\\snakes\\redSnake\\body-curve-down-right.png",
        "\\dependencies\\snakes\\redSnake\\body-end-up.png","\\dependencies\\snakes\\redSnake\\body-end-down.png","\\dependencies\\snakes\\redSnake\\body-end-left.png","\\dependencies\\snakes\\redSnake\\body-end-right.png",
        "\\dependencies\\snakes\\redSnake\\head-up.png","\\dependencies\\snakes\\redSnake\\head-down.png","\\dependencies\\snakes\\redSnake\\head-left.png","\\dependencies\\snakes\\redSnake\\head-right.png",
        "\\dependencies\\snakes\\redSnake\\head-up.gif","\\dependencies\\snakes\\redSnake\\head-down.gif","\\dependencies\\snakes\\redSnake\\head-left.gif","\\dependencies\\snakes\\redSnake\\head-right.gif");
        
    }

    public static void save(){
        try (BufferedWriter wr = new BufferedWriter(new FileWriter("src\\database\\scoreDatabase.txt"))){
            wr.write((databaseScore + GameController.sessionScore) + "");
            wr.newLine();
            wr.write(databaseHighestScore + "");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

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
            eatSound = new AudioClip(getClass().getResource("/dependencies/sound/eat.wav").toExternalForm());
            eatSound.setVolume(1.0);
            
            buttonClick = new AudioClip(getClass().getResource("/dependencies/sound/button_click.wav").toExternalForm());
            buttonClick.setVolume(1.0);

            deadSound = new AudioClip(getClass().getResource("/dependencies/sound/deadSound.wav").toExternalForm());
            deadSound.setVolume(1.0);



            backgroundMusic = new MediaPlayer(new Media(getClass().getResource("\\dependencies\\sound\\background.mp3").toExternalForm()));
            backgroundMusic.setVolume(1);
            backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
            backgroundMusic.play();
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void start(Stage stage) throws Exception {
        initializeSound();
        loadSkins();
        Main.selectedSkin = Main.redSkin;
        Main.stage = stage;
        Main.stage.setFullScreenExitHint("");

        gamePaused = false;
        gridView = true;

        Main.stage.setOnCloseRequest(_ -> {
            save();
            Main.backgroundMusic.stop();
        });

        try (BufferedReader br = new BufferedReader(new FileReader("src\\database\\scoreDatabase.txt"))) {
            databaseScore = Integer.parseInt(br.readLine());
            databaseHighestScore = Integer.parseInt(br.readLine());

            System.out.println(databaseScore);
            System.out.println(databaseHighestScore);


        } catch (Exception e) {
            e.printStackTrace();
        }

        gameBgImage = new Image("dependencies\\snakes\\defaultSnake\\head-down.png");
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainMenu.fxml"));
        root = loader.load();

        

        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Snake Game");
        stage.setScene(scene);
        stage.show();
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setResizable(false);
        stage.getIcons().add(gameBgImage);
        
        stage.show();        
    }

    public static void main(String[] args) {
        launch(args);
    }
}