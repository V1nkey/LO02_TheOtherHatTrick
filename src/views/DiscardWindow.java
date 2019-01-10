package views;

import theotherhattrick.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiscardWindow {
    private JFrame frame;
    private JPanel panel1;
    private JButton card0;
    private JButton card1;
    private JButton card2;
    private MainWindow mainWindow;

    public DiscardWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        card0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.putSeventhProp(0);
                frame.dispose();
            }
        });
        card1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.putSeventhProp(1);
                frame.dispose();
            }
        });
        card2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.putSeventhProp(2);
                frame.dispose();
            }
        });
    }

    public void showCards(Player currentPlayer) {
        card0.setText(currentPlayer.getHand().get(0).getName());
        card1.setText(currentPlayer.getHand().get(1).getName());
        card2.setText(currentPlayer.getHand().get(2).getName());
    }

    public void addFrame(JFrame frame) {
        this.frame = frame;
    }

    public JPanel getPanel() {
        return this.panel1;
    }
}
