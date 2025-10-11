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
    private int growth = 0;

    private boolean gameOver;    

    public static int score, sessionScore;

    private String 
    apple = "/dependencies/apple.png",
    cherry = "/dependencies/cherry.png",
    grapes = "/dependencies/grapes.png",
    star = "/dependencies/star.png";
    ;

    @FXML
    private void test(){
        
        
    }

    
    

    @FXML
    public void initialize() {
        bgImage.setImage(Main.gameBgImage);
        
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


        double x = Math.random() * 100;

        if(x < 5){
            appleGenerator(star);
        }
        else if(x < 10){
            appleGenerator(grapes);
        }
        else if(x < 30){
            appleGenerator(cherry);
        }
        else{
            appleGenerator(apple);
        }

        

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
        
        // TODO: GROWTH

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
                            
                            if(growth > 0){
                                growth--;
                            }
                            else{
                                matrix[(int)snakeNodes.getTailPosition().getX()][(int)snakeNodes.getTailPosition().getY()] = 0;
                                snakeNodes.removeTail();       
                            }
                        }
                        else{
                            


                            String path = appleImage.getImage().getUrl();
                            String fileName = path.substring(path.lastIndexOf("/") + 1);

                            if(fileName.equals("apple.png")){
                                score++;
                            }
                            else if(fileName.equals("cherry.png")){
                                score += 3;
                                growth += 2;
                            }
                            else if(fileName.equals("grapes.png")){
                                score += 5;
                                growth += 4;
                            }
                            else if(fileName.equals("star.png")){
                                score += 50;
                                growth += 49;    
                            }





                            rootPane.getChildren().remove(appleImage);
                            applePoint = null;

                            Main.eatSound.play();
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
                            if(growth > 0){
                                growth--;
                            }
                            else{
                                matrix[(int)snakeNodes.getTailPosition().getX()][(int)snakeNodes.getTailPosition().getY()] = 0;
                                snakeNodes.removeTail();
                            }
                        }
                        else{
                            String path = appleImage.getImage().getUrl();
                            String fileName = path.substring(path.lastIndexOf("/") + 1);

                            if(fileName.equals("apple.png")){
                                score++;
                            }
                            else if(fileName.equals("cherry.png")){
                                score += 3;
                                growth += 2;
                            }
                            else if(fileName.equals("grapes.png")){
                                score += 5;
                                growth += 4;
                            }
                            else if(fileName.equals("star.png")){
                                score += 50;
                                growth += 49;    
                            }
                            
                            
                            rootPane.getChildren().remove(appleImage);
                            applePoint = null;

                            Main.eatSound.play();
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
                            if(growth > 0){
                                growth--;
                            }
                            else{
                                matrix[(int)snakeNodes.getTailPosition().getX()][(int)snakeNodes.getTailPosition().getY()] = 0;
                                snakeNodes.removeTail();
                            }
                        }
                        else{
                            String path = appleImage.getImage().getUrl();
                            String fileName = path.substring(path.lastIndexOf("/") + 1);

                            if(fileName.equals("apple.png")){
                                score++;
                            }
                            else if(fileName.equals("cherry.png")){
                                score += 3;
                                growth += 2;
                            }
                            else if(fileName.equals("grapes.png")){
                                score += 5;
                                growth += 4;
                            }
                            else if(fileName.equals("star.png")){
                                score += 50;
                                growth += 49;    
                            }
                            
                            rootPane.getChildren().remove(appleImage);
                            applePoint = null;

                            Main.eatSound.play();
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
                            if(growth > 0){
                                growth--;
                            }
                            else{
                                matrix[(int)snakeNodes.getTailPosition().getX()][(int)snakeNodes.getTailPosition().getY()] = 0;
                                snakeNodes.removeTail();
                            }
                        }
                        else{
                            String path = appleImage.getImage().getUrl();
                            String fileName = path.substring(path.lastIndexOf("/") + 1);

                            if(fileName.equals("apple.png")){
                                score++;
                            }
                            else if(fileName.equals("cherry.png")){
                                score += 3;
                                growth += 2;
                            }
                            else if(fileName.equals("grapes.png")){
                                score += 5;
                                growth += 4;
                            }
                            else if(fileName.equals("star.png")){
                                score += 50;
                                growth += 49;    
                            }
                            
                            rootPane.getChildren().remove(appleImage);
                            applePoint = null;

                            Main.eatSound.play();
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

        //TODO: Game Over


        // javafx.application.Platform.runLater(() -> {
        //     javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        //     alert.setTitle("Game Over");
        //     alert.setHeaderText(null);
        //     alert.setContentText("Ai pierdut jocul!");
        //     alert.showAndWait();
        // });

        System.out.println("Game over");
    }


    private void appleGenerator(String appleSkin){

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

            appleImage = new ImageView(new javafx.scene.image.Image(appleSkin));
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
            return head.getY() < second.getY() ? Main.selectedSkin.getHead_up() : Main.selectedSkin.getHead_down();
        } else {
            return head.getX() < second.getX() ? Main.selectedSkin.getHead_left() : Main.selectedSkin.getHead_right();
        }
    }
    
    private String getDeadHeadImage(SnakeNode head, SnakeNode second){
        if (head.getX() == second.getX()) {
            return head.getY() < second.getY() ? Main.selectedSkin.getDead_head_up() : Main.selectedSkin.getDead_head_down();
        } else {
            return head.getX() < second.getX() ? Main.selectedSkin.getDead_head_left() : Main.selectedSkin.getDead_head_right();
        }

    }

    private String getTailImage(SnakeNode tail, SnakeNode second) {
        if (tail.getX() == second.getX()) {
            return tail.getY() < second.getY() ? Main.selectedSkin.getBody_end_up() : Main.selectedSkin.getBody_end_down();
        } else {
            return tail.getX() < second.getX() ? Main.selectedSkin.getBody_end_left() : Main.selectedSkin.getBody_end_right();
        }
    }

    private String getBodyImage(SnakeNode current, SnakeNode prev, SnakeNode next) {
        int dxPrev = current.getX() - prev.getX();
        int dyPrev = current.getY() - prev.getY();
        int dxNext = next.getX() - current.getX();
        int dyNext = next.getY() - current.getY();

        if (dxPrev == 0 && dxNext == 0)
        return Main.selectedSkin.getVertical_body();

        // Segment orizontal (se mișcă doar pe axa X)
        if (dyPrev == 0 && dyNext == 0)
        return Main.selectedSkin.getHorizontal_body();

      
        
        
        // Curbe
        if ((dyPrev == -1 && dxNext == -1) || (dxPrev == 1 && dyNext == 1)) return Main.selectedSkin.getBody_curve_ld();
        if ((dxPrev == 1 && dyNext == -1) || (dyPrev == 1 && dxNext == -1) ) return Main.selectedSkin.getBody_curve_lu();
        if ((dxPrev == -1 && dyNext == 1) || (dyPrev == -1 && dxNext == 1)) return Main.selectedSkin.getBody_curve_rd();
        if ((dyPrev == 1 && dxNext == 1) || (dxPrev == -1 && dyNext == -1)) return Main.selectedSkin.getBody_curve_ru();

        
        

       

        return Main.selectedSkin.getHorizontal_body(); // fallback
    }

    



}
