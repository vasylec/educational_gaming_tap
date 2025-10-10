import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javax.xml.crypto.dsig.Manifest;

public class GameController{

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Pane gameOverPane;

    @FXML
    private ImageView bgImage;

    @FXML
    private Label scoreLabel;

    private Timeline timeline;
    private final double baseWidth = 1920;
    private final double baseHeight = 1080;
    private final double cellSize = 50;
    private final Random random = new Random();

    private SnakeNodeArray snakeNodes = new SnakeNodeArray();
    private int[][] matrix = new int[36][20];
    private ImageView appleImage;
    private Point2D applePoint;
    private KeyCode currentDirection = KeyCode.RIGHT;
    private KeyCode currentDirectionGeter = KeyCode.RIGHT;
    private KeyCode lastDirection = KeyCode.RIGHT;
    private KeyCode unvailableDirection = KeyCode.RIGHT;
    private boolean directionChangedThisTick = false;

    private boolean gameOver;    

    public static int score, sessionScore;

    @FXML
    private void test(){
        
        
    }

    
    

    @FXML
    public void initialize() {
        
        
        Main.stage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                if (!Main.gamePaused) {
                    timeline.pause();
                    Main.gamePaused = true;
                } else {
                    timeline.play();
                    Main.gamePaused = false;
                }
            }
        });

        score = 0;
        sessionScore = 0;
        gameOver = false;
        scoreLabel.setText("Score: 0");
        for(int i=0;i<36;i++){
            for(int j=0;j<20;j++){
                matrix[i][j]=0;
            }
        }

        matrix[16][11]=1;
        snakeNodes.addHead(new Point2D(16, 11));
        matrix[17][11]=1;
        snakeNodes.addHead(new Point2D(17, 11));
        matrix[18][11]=1;
        snakeNodes.addHead(new Point2D(18, 11));
        
        
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), _ -> tick()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        bgImage.fitWidthProperty().bind(rootPane.widthProperty());
        bgImage.fitHeightProperty().bind(rootPane.heightProperty());
        bgImage.setPreserveRatio(false);



        rootPane.setFocusTraversable(true);
        javafx.application.Platform.runLater(() -> rootPane.requestFocus());

        rootPane.setOnKeyPressed(event -> {            
            if (!directionChangedThisTick) {
                KeyCode newDir = event.getCode();
                if ((newDir == KeyCode.UP || newDir == KeyCode.DOWN || newDir == KeyCode.LEFT || newDir == KeyCode.RIGHT) && newDir != unvailableDirection) 
                {
                    currentDirectionGeter = newDir;
                    directionChangedThisTick = true;
                }
            }
            
        });
        
    }

    private void tick() {
        
        

        directionChangedThisTick = false;

        currentDirection = currentDirectionGeter;

        appleGenerator();

        switch(currentDirection){
            case UP ->{
                unvailableDirection = KeyCode.DOWN;
            }
            case DOWN ->{
                unvailableDirection = KeyCode.UP;
            }
            case RIGHT ->{
                unvailableDirection = KeyCode.LEFT;
            }
            case LEFT ->{
                unvailableDirection = KeyCode.RIGHT;
            }
            default ->{

            }
        }

        if(currentDirection == unvailableDirection){
            currentDirection = lastDirection;
        }
        
        switch (currentDirection) {
                case UP -> {
                    if((snakeNodes.getHeadPosition().getY()-1<0) || (matrix[(int)snakeNodes.getHeadPosition().getX()][(int)snakeNodes.getHeadPosition().getY()-1]==1)){
                        gameOver();
                        return;
                    }
                    else{

                        matrix[(int)snakeNodes.getHeadPosition().getX()][(int)snakeNodes.getHeadPosition().getY()-1] = 1;
                        snakeNodes.addHead(new Point2D((int)snakeNodes.getHeadPosition().getX(), (int)snakeNodes.getHeadPosition().getY()-1));

                        if(!snakeNodes.getHeadPosition().equals(applePoint)){
                            matrix[(int)snakeNodes.getTailPosition().getX()][(int)snakeNodes.getTailPosition().getY()] = 0;
                            snakeNodes.removeTail();
                        }
                        else{
                            rootPane.getChildren().remove(appleImage);
                            applePoint = null;

                            Main.eatSound.play();
                            score++;
                            scoreLabel.setText("Score: "+score);
                        }

                    }
                }
                case DOWN -> {
                    if((snakeNodes.getHeadPosition().getY()+1>=20) || (matrix[(int)snakeNodes.getHeadPosition().getX()][(int)snakeNodes.getHeadPosition().getY()+1]==1)){
                        gameOver();
                        return;
                    }
                    else{
                        matrix[(int)snakeNodes.getHeadPosition().getX()][(int)snakeNodes.getHeadPosition().getY()+1] = 1;
                        snakeNodes.addHead(new Point2D((int)snakeNodes.getHeadPosition().getX(), (int)snakeNodes.getHeadPosition().getY()+1));

                        if(!snakeNodes.getHeadPosition().equals(applePoint)){
                            matrix[(int)snakeNodes.getTailPosition().getX()][(int)snakeNodes.getTailPosition().getY()] = 0;
                            snakeNodes.removeTail();
                        }
                        else{
                            rootPane.getChildren().remove(appleImage);
                            applePoint = null;

                            Main.eatSound.play();
                            score++;
                            scoreLabel.setText("Score: "+score);
                            
                        }
                    }
                }
                case LEFT -> {
                    if((snakeNodes.getHeadPosition().getX()-1<0) || (matrix[(int)snakeNodes.getHeadPosition().getX()-1][(int)snakeNodes.getHeadPosition().getY()]==1)){
                        gameOver();
                        return;
                    }
                    else{
                        matrix[(int)snakeNodes.getHeadPosition().getX()-1][(int)snakeNodes.getHeadPosition().getY()] = 1;
                        snakeNodes.addHead(new Point2D((int)snakeNodes.getHeadPosition().getX()-1, (int)snakeNodes.getHeadPosition().getY()));

                        if(!snakeNodes.getHeadPosition().equals(applePoint)){
                            matrix[(int)snakeNodes.getTailPosition().getX()][(int)snakeNodes.getTailPosition().getY()] = 0;
                            snakeNodes.removeTail();
                        }
                        else{
                            rootPane.getChildren().remove(appleImage);
                            applePoint = null;

                            Main.eatSound.play();
                            score++;
                            scoreLabel.setText("Score: "+score);
                        }
                    }
                }
                case RIGHT -> {
                    if((snakeNodes.getHeadPosition().getX()+1>=36) || (matrix[(int)snakeNodes.getHeadPosition().getX()+1][(int)snakeNodes.getHeadPosition().getY()]==1)){
                        gameOver();
                        return;
                    }
                    else{
                        matrix[(int)snakeNodes.getHeadPosition().getX()+1][(int)snakeNodes.getHeadPosition().getY()] = 1;
                        snakeNodes.addHead(new Point2D((int)snakeNodes.getHeadPosition().getX()+1, (int)snakeNodes.getHeadPosition().getY()));

                        if(!snakeNodes.getHeadPosition().equals(applePoint)){
                            matrix[(int)snakeNodes.getTailPosition().getX()][(int)snakeNodes.getTailPosition().getY()] = 0;
                            snakeNodes.removeTail();
                        }
                        else{
                            rootPane.getChildren().remove(appleImage);
                            applePoint = null;

                            Main.eatSound.play();
                            score++;
                            scoreLabel.setText("Score: "+score);
                        }
                    }
                }
                default ->{}
            }
            lastDirection = currentDirection;
            snakeShowGraphics();
            // snakeShow();
    }

    private void gameOver() {
        sessionScore += score;
        if(score > Main.databaseHighestScore)
            Main.databaseHighestScore = score;
        score = 0;
        
        Main.save();
        
        gameOver = true;    
        snakeShowGraphics();
        
        Main.backgroundMusic.stop();
        Main.deadSound.play();
        
        gameOverPane.setOpacity(1);
        timeline.stop();

        //TODO


        // javafx.application.Platform.runLater(() -> {
        //     javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        //     alert.setTitle("Game Over");
        //     alert.setHeaderText(null);
        //     alert.setContentText("Ai pierdut jocul!");
        //     alert.showAndWait();
        // });

        System.out.println("Game over");
    }


    private void appleGenerator(){

        if(applePoint == null){

        

            int row = random.nextInt(20);
            int col = random.nextInt(36);

            while(matrix[col][row] == 1){
                row = random.nextInt(20);
                col = random.nextInt(36);
            }

            applePoint = new Point2D(col, row);
            matrix[col][row] = 2;


            double scaleX = bgImage.getBoundsInParent().getWidth() / baseWidth;
            double scaleY = bgImage.getBoundsInParent().getHeight() / baseHeight;

            double realCellWidth = cellSize * scaleX;
            double realCellHeight = cellSize * scaleY;

            double x = 60 * scaleX + col * realCellWidth;
            double y = 40 * scaleY + row * realCellHeight;

            appleImage = new ImageView(new javafx.scene.image.Image("/dependencies/apple.png"));
            appleImage.setFitWidth(realCellWidth);
            appleImage.setFitHeight(realCellHeight);
            appleImage.setPreserveRatio(false);
            appleImage.setLayoutX(x);
            appleImage.setLayoutY(y);

            rootPane.getChildren().add(appleImage);
        }

        
    }

    private void snakeShow(){

        System.out.println(currentDirection);
        for (int y = 0; y < 20; y++) {       
            for (int x = 0; x < 36; x++) {   
                System.out.print(matrix[x][y] + " ");
            }
            System.out.println();
        }
        System.out.println("tick");

        
    }

    private void snakeShowGraphics() {
        // Ștergem vechile ImageView-uri ale șarpelui
        rootPane.getChildren().removeIf(node -> node instanceof ImageView && ((ImageView) node).getId() != null && ((ImageView) node).getId().startsWith("snake"));

        List<SnakeNode> nodes = snakeNodes.getNodes();

        double scaleX = bgImage.getBoundsInParent().getWidth() / baseWidth;
        double scaleY = bgImage.getBoundsInParent().getHeight() / baseHeight;

        double realCellWidth = cellSize * scaleX;
        double realCellHeight = cellSize * scaleY;

        for (int i = 0; i < nodes.size(); i++) {
            SnakeNode current = nodes.get(i);
            SnakeNode prev = i > 0 ? nodes.get(i - 1) : null;
            SnakeNode next = i < nodes.size() - 1 ? nodes.get(i + 1) : null;

            String imagePath = "";

            if(gameOver == false){
                if (i == 0) { // Coada
                    imagePath = getTailImage(current, next);
                } else if (i == nodes.size() - 1) { // Cap
                    imagePath = getHeadImage(current, prev);
                } else { // Corp
                    imagePath = getBodyImage(current, prev, next);
                }
            }
            else{
                if (i == 0) { // Coada
                    imagePath = getTailImage(current, next);
                } else if (i == nodes.size() - 1) { // Cap
                    imagePath = getDeadHeadImage(current, prev);
                } else { // Corp
                    imagePath = getBodyImage(current, prev, next);
                }

            }


            // Încarcă imaginea corect
            ImageView iv = new ImageView(new javafx.scene.image.Image(imagePath));
            iv.setId("snake" + i); // ca să putem șterge mai târziu
            iv.setFitWidth(realCellWidth);
            iv.setFitHeight(realCellHeight);
            iv.setPreserveRatio(false);
            iv.setLayoutX(60 * scaleX + current.getX() * realCellWidth);
            iv.setLayoutY(40 * scaleY + current.getY() * realCellHeight);

            rootPane.getChildren().add(iv);
        }
    }


    private String getHeadImage(SnakeNode head, SnakeNode second) {
        if (head.getX() == second.getX()) {
            return head.getY() < second.getY() ? "/dependencies/head-up.png" : "/dependencies/head-down.png";
        } else {
            return head.getX() < second.getX() ? "/dependencies/head-left.png" : "/dependencies/head-right.png";
        }
    }
    
    private String getDeadHeadImage(SnakeNode head, SnakeNode second){
        if (head.getX() == second.getX()) {
            return head.getY() < second.getY() ? "/dependencies/head-up.gif" : "/dependencies/head-down.gif";
        } else {
            return head.getX() < second.getX() ? "/dependencies/head-left.gif" : "/dependencies/head-right.gif";
        }

    }

    private String getTailImage(SnakeNode tail, SnakeNode second) {
        if (tail.getX() == second.getX()) {
            return tail.getY() < second.getY() ? "/dependencies/body-end-up.png" : "/dependencies/body-end-down.png";
        } else {
            return tail.getX() < second.getX() ? "/dependencies/body-end-left.png" : "/dependencies/body-end-right.png";
        }
    }

    private String getBodyImage(SnakeNode current, SnakeNode prev, SnakeNode next) {
        int dxPrev = current.getX() - prev.getX();
        int dyPrev = current.getY() - prev.getY();
        int dxNext = next.getX() - current.getX();
        int dyNext = next.getY() - current.getY();

        if (dxPrev == 0 && dxNext == 0)
        return "/dependencies/body-vertical.png";

        // Segment orizontal (se mișcă doar pe axa X)
        if (dyPrev == 0 && dyNext == 0)
        return "/dependencies/body-orizontal.png";

      
        
        
        // Curbe
        if ((dyPrev == -1 && dxNext == -1) || (dxPrev == 1 && dyNext == 1)) return "/dependencies/body-curve-left-down.png";
        if ((dxPrev == 1 && dyNext == -1) || (dyPrev == 1 && dxNext == -1) ) return "/dependencies/body-curve-left-up.png";
        if ((dxPrev == -1 && dyNext == 1) || (dyPrev == -1 && dxNext == 1)) return "/dependencies/body-curve-down-right.png";
        if ((dyPrev == 1 && dxNext == 1) || (dxPrev == -1 && dyNext == -1)) return "/dependencies/body-curve-up-right.png";

        
        

       

        return "/dependencies/body-orizontal.png"; // fallback
    }

    



}
