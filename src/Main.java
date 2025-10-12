import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage stage;
    public static Parent root;

    public static boolean fullscreen;
    public static String resolution;

    public static MediaPlayer backgroundMusic;
    public static AudioClip eatSound, buttonClick, deadSound;

    public static boolean gamePaused, gridView;

    public static int databaseScore, databaseHighestScore, coins, speed;

    public static String selectedSkinId = "default";
    public static SnakeSkin selectedSkin;
    public static final List<SkinDefinition> skinDefinitions = new ArrayList<>();

    public static Image gameBgImage;

    private static final Path SCORE_PATH = Paths.get("src", "database", "scoreDatabase.txt");
    private static final Path SKIN_PATH = Paths.get("src", "database", "skinsDatabase.txt");

    private void loadSkins() {
        skinDefinitions.clear();
        skinDefinitions.add(new SkinDefinition(
    "default",
    "Clasic",
    0,
    getClass().getResource("/dependencies/snakes/defaultSnake/head-right.png").toExternalForm(),
    new SnakeSkin(
        getClass().getResource("/dependencies/snakes/defaultSnake/body-orizontal.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/defaultSnake/body-vertical.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/defaultSnake/body-curve-left-up.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/defaultSnake/body-curve-up-right.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/defaultSnake/body-curve-left-down.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/defaultSnake/body-curve-down-right.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/defaultSnake/body-end-up.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/defaultSnake/body-end-down.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/defaultSnake/body-end-left.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/defaultSnake/body-end-right.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/defaultSnake/head-up.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/defaultSnake/head-down.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/defaultSnake/head-left.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/defaultSnake/head-right.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/defaultSnake/head-up.gif").toExternalForm(),
        getClass().getResource("/dependencies/snakes/defaultSnake/head-down.gif").toExternalForm(),
        getClass().getResource("/dependencies/snakes/defaultSnake/head-left.gif").toExternalForm(),
        getClass().getResource("/dependencies/snakes/defaultSnake/head-right.gif").toExternalForm()
    ),
    true
));

skinDefinitions.add(new SkinDefinition(
    "red",
    "Red Snake",
    250,
    getClass().getResource("/dependencies/snakes/redSnake/head-right.png").toExternalForm(),
    new SnakeSkin(
        getClass().getResource("/dependencies/snakes/redSnake/body-orizontal.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/redSnake/body-vertical.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/redSnake/body-curve-left-up.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/redSnake/body-curve-up-right.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/redSnake/body-curve-left-down.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/redSnake/body-curve-down-right.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/redSnake/body-end-up.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/redSnake/body-end-down.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/redSnake/body-end-left.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/redSnake/body-end-right.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/redSnake/head-up.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/redSnake/head-down.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/redSnake/head-left.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/redSnake/head-right.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/redSnake/head-up.gif").toExternalForm(),
        getClass().getResource("/dependencies/snakes/redSnake/head-down.gif").toExternalForm(),
        getClass().getResource("/dependencies/snakes/redSnake/head-left.gif").toExternalForm(),
        getClass().getResource("/dependencies/snakes/redSnake/head-right.gif").toExternalForm()
    ),
    false
));

skinDefinitions.add(new SkinDefinition(
    "neon",
    "Neon",
    400,
    getClass().getResource("/dependencies/snakes/futuristicSnake/head-right.png").toExternalForm(),
    new SnakeSkin(
        getClass().getResource("/dependencies/snakes/futuristicSnake/body-orizontal.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/futuristicSnake/body-vertical.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/futuristicSnake/body-curve-left-up.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/futuristicSnake/body-curve-up-right.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/futuristicSnake/body-curve-left-down.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/futuristicSnake/body-curve-down-right.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/futuristicSnake/body-end-up.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/futuristicSnake/body-end-down.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/futuristicSnake/body-end-left.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/futuristicSnake/body-end-right.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/futuristicSnake/head-up.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/futuristicSnake/head-down.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/futuristicSnake/head-left.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/futuristicSnake/head-right.png").toExternalForm(),
        getClass().getResource("/dependencies/snakes/futuristicSnake/head-up.gif").toExternalForm(),
        getClass().getResource("/dependencies/snakes/futuristicSnake/head-down.gif").toExternalForm(),
        getClass().getResource("/dependencies/snakes/futuristicSnake/head-left.gif").toExternalForm(),
        getClass().getResource("/dependencies/snakes/futuristicSnake/head-right.gif").toExternalForm()
    ),
    false
));


        selectedSkinId = "default";
        selectedSkin = skinDefinitions.get(0).getSkin();
    }

    private void ensureScoreFile() throws IOException {
        if (!Files.exists(SCORE_PATH)) {
            Files.createDirectories(SCORE_PATH.getParent());
            Files.write(SCORE_PATH, Arrays.asList("0", "0", "0"));
        }
    }

    private void loadScoreData() {
        try {
            ensureScoreFile();
            List<String> lines = Files.readAllLines(SCORE_PATH);
            databaseScore = parseLine(lines, 0);
            databaseHighestScore = parseLine(lines, 1);
            coins = lines.size() > 2 ? parseLine(lines, 2) : databaseScore;
            saveScoreData(); // ensure file has 3 lines
        } catch (IOException e) {
            e.printStackTrace();
            databaseScore = 0;
            databaseHighestScore = 0;
            coins = 0;
        }
    }

    private void loadSkinData() {
        skinDefinitions.forEach(skin -> skin.setUnlocked("default".equals(skin.getId())));
        selectedSkinId = "default";
        selectedSkin = getSkinById("default").map(SkinDefinition::getSkin).orElse(selectedSkin);

        try {
            if (!Files.exists(SKIN_PATH)) {
                saveSkinData();
                return;
            }

            List<String> lines = Files.readAllLines(SKIN_PATH);
            for (String line : lines) {
                if (!line.contains("=")) {
                    continue;
                }
                String[] parts = line.split("=", 2);
                if (parts.length < 2) {
                    continue;
                }
                String key = parts[0].trim();
                String value = parts[1].trim();

                if ("selected".equalsIgnoreCase(key)) {
                    selectedSkinId = value;
                } else if ("unlocked".equalsIgnoreCase(key)) {
                    Arrays.stream(value.split(","))
                        .map(String::trim)
                        .filter(id -> !id.isEmpty())
                        .forEach(id -> getSkinById(id).ifPresent(def -> def.setUnlocked(true)));
                }
            }

            SkinDefinition selectedDefinition = getSkinById(selectedSkinId)
                .filter(SkinDefinition::isUnlocked)
                .orElseGet(() -> getSkinById("default").orElse(null));

            if (selectedDefinition != null) {
                selectedSkinId = selectedDefinition.getId();
                selectedSkin = selectedDefinition.getSkin();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        saveScoreData();
        saveSkinData();
    }

    private static void saveScoreData() {
        try {
            Files.createDirectories(SCORE_PATH.getParent());
            List<String> lines = Arrays.asList(
                String.valueOf(Math.max(0, databaseScore)),
                String.valueOf(Math.max(0, databaseHighestScore)),
                String.valueOf(Math.max(0, coins))
            );
            Files.write(SCORE_PATH, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveSkinData() {
        try {
            Files.createDirectories(SKIN_PATH.getParent());
            String unlocked = skinDefinitions.stream()
                .filter(SkinDefinition::isUnlocked)
                .map(SkinDefinition::getId)
                .collect(Collectors.joining(","));
            List<String> lines = Arrays.asList(
                "selected=" + selectedSkinId,
                "unlocked=" + unlocked
            );
            Files.write(SKIN_PATH, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int parseLine(List<String> lines, int index) {
        if (index >= lines.size()) {
            return 0;
        }
        try {
            return Integer.parseInt(lines.get(index).trim());
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    public static Optional<SkinDefinition> getSkinById(String id) {
        return skinDefinitions.stream()
            .filter(def -> def.getId().equalsIgnoreCase(id))
            .findFirst();
    }

    public static List<SkinDefinition> getUnlockedSkins() {
        return skinDefinitions.stream()
            .filter(SkinDefinition::isUnlocked)
            .collect(Collectors.toList());
    }

    public static List<SkinDefinition> getLockedSkins() {
        return skinDefinitions.stream()
            .filter(def -> !def.isUnlocked())
            .collect(Collectors.toList());
    }

    public static boolean unlockSkin(String skinId) {
        Optional<SkinDefinition> optionalSkin = getSkinById(skinId);
        if (optionalSkin.isEmpty()) {
            return false;
        }

        SkinDefinition definition = optionalSkin.get();
        if (definition.isUnlocked()) {
            return true;
        }

        if (coins < definition.getPrice()) {
            return false;
        }

        coins -= definition.getPrice();
        definition.setUnlocked(true);
        save();
        return true;
    }

    public static boolean selectSkin(String skinId) {
        Optional<SkinDefinition> optionalSkin = getSkinById(skinId);
        if (optionalSkin.isEmpty()) {
            return false;
        }

        SkinDefinition definition = optionalSkin.get();
        if (!definition.isUnlocked()) {
            return false;
        }

        selectedSkinId = definition.getId();
        selectedSkin = definition.getSkin();
        saveSkinData();
        return true;
    }

    public static void onSessionComplete(int sessionScore) {
        if (sessionScore <= 0) {
            return;
        }

        databaseScore += sessionScore;
        coins += sessionScore;
        if (sessionScore > databaseHighestScore) {
            databaseHighestScore = sessionScore;
        }
        saveScoreData();
    }

    public static void setResolution(double width, double height) {
        Main.stage.setWidth(width);
        Main.stage.setHeight(height);
    }

    public static double getDisplayResolutionWidth() {
        return Screen.getPrimary().getBounds().getWidth();
    }

    public static double getDisplayResolutionHeight() {
        return Screen.getPrimary().getBounds().getHeight();
    }

    private void initializeSound() {
        try {
            eatSound = new AudioClip(getClass().getResource("/dependencies/sound/eat.wav").toExternalForm());
            eatSound.setVolume(1.0);

            buttonClick = new AudioClip(getClass().getResource("/dependencies/sound/button_click.wav").toExternalForm());
            buttonClick.setVolume(1.0);

            deadSound = new AudioClip(getClass().getResource("/dependencies/sound/deadSound.wav").toExternalForm());
            deadSound.setVolume(1.0);

            Media background = new Media(getClass().getResource("/dependencies/sound/background.mp3").toExternalForm());
            backgroundMusic = new MediaPlayer(background);
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
        loadScoreData();
        loadSkinData();

        Main.stage = stage;
        Main.stage.setFullScreenExitHint("");
        Main.speed = 1;

        gamePaused = false;
        gridView = true;

        Main.stage.setOnCloseRequest(_ -> {
            save();
            if (Main.backgroundMusic != null) {
                Main.backgroundMusic.stop();
            }
        });

        gameBgImage = new Image(getClass().getResource("\\dependencies\\snakeGame.png").toExternalForm());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainMenu.fxml"));
        root = loader.load();

        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Șarpele TAP");
        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResource("/dependencies/snakes/defaultSnake/head-down.png").toExternalForm()));
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
