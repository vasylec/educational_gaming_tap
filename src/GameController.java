import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.List;
import java.util.Random;

public class GameController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView bgImage;

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

    @FXML
    public void initialize() {

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
        
        
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), _unused -> tick()));
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

        switch(lastDirection){
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
        timeline.stop();

        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("Ai pierdut jocul!");
            alert.showAndWait();
        });

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

            appleImage = new ImageView(new javafx.scene.image.Image("/dependencies/images (12).jpg"));
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

            if (i == 0) { // Coada
                imagePath = getTailImage(current, next);
            } else if (i == nodes.size() - 1) { // Cap
                imagePath = getHeadImage(current, prev);
            } else { // Corp
                imagePath = getBodyImage(current, prev, next);
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
            return head.getY() < second.getY() ? "/dependencies/snake_head_up.jpg" : "/dependencies/snake_head_down.jpg";
        } else {
            return head.getX() < second.getX() ? "/dependencies/snake_head_left.jpg" : "/dependencies/snake_head_right.jpg";
        }
    }

    private String getTailImage(SnakeNode tail, SnakeNode second) {
        if (tail.getX() == second.getX()) {
            return tail.getY() < second.getY() ? "/dependencies/snake_tail_up.jpg" : "/dependencies/snake_tail_down.jpg";
        } else {
            return tail.getX() < second.getX() ? "/dependencies/snake_tail_left.jpg" : "/dependencies/snake_tail_right.jpg";
        }
    }

    private String getBodyImage(SnakeNode current, SnakeNode prev, SnakeNode next) {
        int dxPrev = current.getX() - prev.getX();
        int dyPrev = current.getY() - prev.getY();
        int dxNext = next.getX() - current.getX();
        int dyNext = next.getY() - current.getY();

        if (dxPrev == dxNext) return "/dependencies/snake_body_vertical.jpg";
        if (dyPrev == dyNext) return "/dependencies/snake_body_horizontal.jpg";

        // Curbe
        if ((dxPrev == -1 && dyNext == -1) || (dyPrev == -1 && dxNext == -1)) return "/dependencies/snake_curve_lu.jpg";
        if ((dxPrev == 1 && dyNext == -1) || (dyPrev == -1 && dxNext == 1)) return "/dependencies/snake_curve_ru.jpg";
        if ((dxPrev == -1 && dyNext == 1) || (dyPrev == 1 && dxNext == -1)) return "/dependencies/snake_curve_ld.jpg";
        if ((dxPrev == 1 && dyNext == 1) || (dyPrev == 1 && dxNext == 1)) return "/dependencies/snake_curve_rd.jpg";

        return "/dependencies/snake_body_horizontal.jpg"; // fallback
    }



}
