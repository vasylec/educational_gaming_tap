import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SnakeSkin {

    private final Image horizontal_body, vertical_body;
    private final Image body_curve_lu, body_curve_ru, body_curve_ld, body_curve_rd;
    private final Image body_end_up, body_end_down, body_end_left, body_end_right;
    private final Image head_up, head_down, head_left, head_right;
    private final Image dead_head_up, dead_head_down, dead_head_left, dead_head_right;

    public SnakeSkin(
        String horizontal_body, String vertical_body,
        String body_curve_lu, String body_curve_ru, String body_curve_ld, String body_curve_rd,
        String body_end_up, String body_end_down, String body_end_left, String body_end_right,
        String head_up, String head_down, String head_left, String head_right,
        String dead_head_up, String dead_head_down, String dead_head_left, String dead_head_right
    ) {
        // Încarcă doar imaginile o singură dată
        this.horizontal_body = new Image(horizontal_body);
        this.vertical_body = new Image(vertical_body);

        this.body_curve_lu = new Image(body_curve_lu);
        this.body_curve_ru = new Image(body_curve_ru);
        this.body_curve_ld = new Image(body_curve_ld);
        this.body_curve_rd = new Image(body_curve_rd);

        this.body_end_up = new Image(body_end_up);
        this.body_end_down = new Image(body_end_down);
        this.body_end_left = new Image(body_end_left);
        this.body_end_right = new Image(body_end_right);

        this.head_up = new Image(head_up);
        this.head_down = new Image(head_down);
        this.head_left = new Image(head_left);
        this.head_right = new Image(head_right);

        this.dead_head_up = new Image(dead_head_up);
        this.dead_head_down = new Image(dead_head_down);
        this.dead_head_left = new Image(dead_head_left);
        this.dead_head_right = new Image(dead_head_right);
    }

    // Gettere care creează mereu un ImageView nou
    public ImageView getHorizontal_body() { return new ImageView(horizontal_body); }
    public ImageView getVertical_body() { return new ImageView(vertical_body); }

    public ImageView getBody_curve_lu() { return new ImageView(body_curve_lu); }
    public ImageView getBody_curve_ru() { return new ImageView(body_curve_ru); }
    public ImageView getBody_curve_ld() { return new ImageView(body_curve_ld); }
    public ImageView getBody_curve_rd() { return new ImageView(body_curve_rd); }

    public ImageView getBody_end_up() { return new ImageView(body_end_up); }
    public ImageView getBody_end_down() { return new ImageView(body_end_down); }
    public ImageView getBody_end_left() { return new ImageView(body_end_left); }
    public ImageView getBody_end_right() { return new ImageView(body_end_right); }

    public ImageView getHead_up() { return new ImageView(head_up); }
    public ImageView getHead_down() { return new ImageView(head_down); }
    public ImageView getHead_left() { return new ImageView(head_left); }
    public ImageView getHead_right() { return new ImageView(head_right); }

    public ImageView getDead_head_up() { return new ImageView(dead_head_up); }
    public ImageView getDead_head_down() { return new ImageView(dead_head_down); }
    public ImageView getDead_head_left() { return new ImageView(dead_head_left); }
    public ImageView getDead_head_right() { return new ImageView(dead_head_right); }
}
