import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Duration;

public class ShopController implements Initializable {
    @FXML
    private ListView<SkinDefinition> shopList;
    @FXML
    private Label coinsLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private BorderPane fade;

    private FadeTransition transition_fade;
    private FadeTransition transition_for_enter(){
        transition_fade = new FadeTransition(Duration.seconds(1), fade);
        transition_fade.setFromValue(0);
        transition_fade.setToValue(1);

        return transition_fade;
    }
    private FadeTransition transition_for_exit(){
        transition_fade = new FadeTransition(Duration.seconds(1), fade);
        transition_fade.setFromValue(1);
        transition_fade.setToValue(0);

        return transition_fade;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshCoins();
        setupList();
        statusLabel.setText("Select a skin to buy.");
        transition_for_enter().play();
        
    }

    private void setupList() {
        ObservableList<SkinDefinition> items = FXCollections.observableArrayList(Main.skinDefinitions);
        items.sort((a, b) -> a.getDisplayName().compareToIgnoreCase(b.getDisplayName()));
        shopList.setItems(items);
        shopList.setPlaceholder(new Label("Nu sunt disponibile skinuri."));
        shopList.setCellFactory(_ -> new ShopCell());
    }

    private void refreshCoins() {
        coinsLabel.setText("Monede: " + Main.coins);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        playClick();
        transition_for_exit().play();
        transition_fade.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                Platform.runLater(() -> switchScene("/fxml/inventory.fxml"));
            }          
        });
    }

    private void playClick() {
        if (Main.buttonClick != null) {
            Main.buttonClick.play();
        }
    }

    private void switchScene(String resource) {
        try {
            Parent parent = new FXMLLoader(getClass().getResource(resource)).load();
            Scene scene = Main.stage.getScene();
            scene.setRoot(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ShopCell extends ListCell<SkinDefinition> {
        private final ImageView preview = new ImageView();
        private final Label nameLabel = new Label();
        private final Label priceLabel = new Label();
        private final Button actionButton = new Button();
        private final HBox layout = new HBox(16, preview, nameLabel, priceLabel, actionButton);

        ShopCell() {
            preview.setFitWidth(48);
            preview.setFitHeight(48);
            preview.setPreserveRatio(true);
            HBox.setHgrow(nameLabel, Priority.ALWAYS);
            actionButton.getStyleClass().add("primary-button");
            actionButton.setOnAction(_ -> {
                SkinDefinition skin = getItem();
                if (skin == null) {
                    return;
                }
                playClick();
                if (!skin.isUnlocked()) {
                    boolean unlocked = Main.unlockSkin(skin.getId());
                    if (unlocked) {
                        statusLabel.setText("You bought skin: " + skin.getDisplayName() + "!");
                        refreshCoins();
                        shopList.refresh();
                    } else {
                        statusLabel.setText("Insufficient coins: " + skin.getDisplayName() + ".");
                    }
                } else if (skin.getId().equals(Main.selectedSkinId)) {
                    statusLabel.setText("Skin: " + skin.getDisplayName() + " is already used.");
                } else {
                    boolean selected = Main.selectSkin(skin.getId());
                    if (selected) {
                        statusLabel.setText("Selected skin: " + skin.getDisplayName());
                        shopList.refresh();
                    }
                }
            });
        }

        @Override
        protected void updateItem(SkinDefinition skin, boolean empty) {
            super.updateItem(skin, empty);
            if (empty || skin == null) {
                setGraphic(null);
                return;
            }

            Image image = new Image(skin.getPreviewImagePath(), true);
            preview.setImage(image);

            nameLabel.setText(skin.getDisplayName());
            priceLabel.setText("Price: " + skin.getPrice());

            if (skin.isUnlocked()) {
                if (skin.getId().equals(Main.selectedSkinId)) {
                    actionButton.setText("Hey, you are using this skin");
                    actionButton.setDisable(true);
                } else {
                    actionButton.setText("USE");
                    actionButton.setDisable(false);
                }
            } else {
                actionButton.setText("BUY");
                boolean canAfford = Main.coins >= skin.getPrice();
                actionButton.setDisable(!canAfford);
            }

            setGraphic(layout);
        }
    }
}
