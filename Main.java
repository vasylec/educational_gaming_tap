import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Main extends JFrame{

    public Main(){
        setTitle("Educational Gaming - Snake Game");
        setSize(800, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        // getContentPane().setBackground(new Color(50,168,66));

        setContentPane(new BackgroundPanel("dependencies/ground.png"));




        // JImageIcon icon = new JImageIcon("dependencies/snake.png");
        

        ImageIcon icon = new ImageIcon("dependencies/snake.png");
        JLabel label = new JLabel(icon);
        label.setBounds(100, 100, icon.getIconWidth(), icon.getIconHeight());
        add(label);
        

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("W"), "moveUp");
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("A"), "moveLeft");
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("S"), "moveDown");
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("D"), "moveRight");









        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setLocation(label.getX(), label.getY() - 2);
                

                // Optional: oprește când ajunge în susul ferestrei
                if (label.getY() > 800) {
                    label.setLocation(label.getX(), 0);
                }
            }
        });
        Timer timer1 = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setLocation(label.getX(), label.getY() + 2);
                

                // Optional: oprește când ajunge în susul ferestrei
                if (label.getY() > 800) {
                    label.setLocation(label.getX(), 0);
                }
            }
        });
        Timer timer2 = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setLocation(label.getX() - 2, label.getY());
                

                // Optional: oprește când ajunge în susul ferestrei
                if (label.getY() > 800) {
                    label.setLocation(label.getX(), 0);
                }
            }
        });
        Timer timer3 = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setLocation(label.getX() + 2, label.getY());
                

                // Optional: oprește când ajunge în susul ferestrei
                if (label.getY() > 800) {
                    label.setLocation(label.getX(), 0);
                }
            }
        });



        getRootPane().getActionMap().put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer1.stop();
                timer2.stop();
                timer3.stop();
                timer.start();
            }
        });        
        getRootPane().getActionMap().put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                timer2.stop();
                timer3.stop();
                timer1.start();
            }
        });        
        getRootPane().getActionMap().put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                timer1.stop();
                timer3.stop();
                timer2.start();
            }
        });        
        getRootPane().getActionMap().put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                timer1.stop();
                timer2.stop();
                timer3.start();
            }
        });        
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main frame = new Main();
                frame.setVisible(true);
             
            }
        });
    }
}