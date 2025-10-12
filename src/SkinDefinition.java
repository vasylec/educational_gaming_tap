import javafx.scene.image.Image;

/**
 * Represents a snake skin option with metadata used by the inventory
 * and shop flows.
 */
public class SkinDefinition {
    private final String id;
    private final String displayName;
    private final int price;
    private final String previewImagePath;
    private final SnakeSkin skin;
    private boolean unlocked;

    public SkinDefinition(String id, String displayName, int price, String previewImagePath, SnakeSkin skin, boolean unlocked) {
        this.id = id;
        this.displayName = displayName;
        this.price = price;
        this.previewImagePath = previewImagePath;
        this.skin = skin;
        this.unlocked = unlocked;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getPrice() {
        return price;
    }

    public String getPreviewImagePath() {
        return previewImagePath;
    }

    public Image createPreviewImage(double size) {
        Image img = new Image(previewImagePath);
        if (img.isError()) {
            return new Image("\\dependencies\\snakes\\defaultSnake\\head-right.png");
        }
        return img;
    }

    public SnakeSkin getSkin() {
        return skin;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
}
