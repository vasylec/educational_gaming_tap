import javax.swing.*;
import java.awt.*;

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Desenăm imaginea întinsă pe tot panoul
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
    }