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

public class InventoryController implements Initializable {
    @FXML
    private ListView<SkinDefinition> skinList;
    @FXML
    private Label coinsLabel;
    @FXML
    private Label selectedLabel;
    @FXML
    private BorderPane fade;
    // @FXML
    // private Label statusLabel;

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
        updateSelectedLabel();
        transition_for_enter().play();
        // statusLabel.setText("");
    }

    private void setupList() {
        ObservableList<SkinDefinition> items = FXCollections.observableArrayList(Main.getUnlockedSkins());
        items.sort((a, b) -> a.getDisplayName().compareToIgnoreCase(b.getDisplayName()));
        skinList.setItems(items);
        skinList.setPlaceholder(new Label("Nu există skinuri de afișat."));
        skinList.setCellFactory(_ -> new SkinCell());
    }

    private void refreshCoins() {
        coinsLabel.setText("Coins: " + Main.coins);
    }

    private void updateSelectedLabel() {
        SkinDefinition selected = Main.getSkinById(Main.selectedSkinId).orElse(null);
        if (selected != null) {
            selectedLabel.setText("Selected Skin: " + selected.getDisplayName());
        } else {
            selectedLabel.setText("Selected Skin: -");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        playClick();
        transition_for_exit().play();
        transition_fade.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                Platform.runLater(() -> switchScene("/fxml/mainMenu.fxml"));
            }
        });
    }

    @FXML
    private void handleOpenShop(ActionEvent event) {
        playClick();
        transition_for_exit().play();
        transition_fade.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                Platform.runLater(() -> switchScene("/fxml/shop.fxml"));
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

    private class SkinCell extends ListCell<SkinDefinition> {
        private final ImageView preview = new ImageView();
        private final Label nameLabel = new Label();
        private final Button equipButton = new Button("USE");
        private final Label equippedLabel = new Label("");
        private final HBox layout = new HBox(16, preview, nameLabel, equippedLabel, equipButton);

        SkinCell() {
            preview.setFitWidth(48);
            preview.setFitHeight(48);
            preview.setPreserveRatio(true);
            equippedLabel.setVisible(false);
            equippedLabel.getStyleClass().add("equipped-label");
            equipButton.getStyleClass().add("primary-button");
            HBox.setHgrow(nameLabel, Priority.ALWAYS);
            equipButton.setOnAction(_ -> {
                SkinDefinition skin = getItem();
                if (skin == null) {
                    return;
                }
                playClick();
                boolean changed = Main.selectSkin(skin.getId());
                if (changed) {
                    // statusLabel.setText("Skin selectat: " + skin.getDisplayName());
                

                    updateSelectedLabel();
                    refreshCoins();
                    skinList.refresh();
                } else {
                    // statusLabel.setText("Skinul este deja activ.");
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

            if (skin.getId().equals(Main.selectedSkinId)) {
                equipButton.setText("Hey, you are using this skin");
                equipButton.setDisable(true);
            }
            else{
                equipButton.setText("USE");
                equipButton.setDisable(false);
            }

            Image previewImage = new Image(skin.getPreviewImagePath(), true);
            preview.setImage(previewImage);
            nameLabel.setText(skin.getDisplayName());

            boolean isSelected = skin.getId().equals(Main.selectedSkinId);
            equippedLabel.setVisible(isSelected);
            equippedLabel.setManaged(isSelected);
            equipButton.setDisable(isSelected);
            setGraphic(layout);
        }
    }
}
