package theotherhattrick;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PickWindow {
    private JFrame frame;
    private JPanel panel1;
    private JButton card00;
    private JButton card01;
    private JButton card10;
    private JButton card11;
    private JLabel label0;
    private JLabel label1;
    private ArrayList<JButton> buttons;
    private List<Player> players;
    private MainWindow mainWindow;

    public PickWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        card00.addActionListener(new ActionListener() { // ajouter le contrôleur
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.exchange(players.get(0), 0);
                frame.dispose();
            }
        });
        card01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.exchange(players.get(0), 1);
                frame.dispose();
            }
        });
        card10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.exchange(players.get(1), 0);
                frame.dispose();
            }
        });
        card11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.exchange(players.get(1), 1);
                frame.dispose();
            }
        });
    }

    public void showCards(List<Player> players, Player currentPlayer) {
        this.buttons = new ArrayList<JButton>();
        this.players = new ArrayList<Player>();

        this.buttons.add(card00);
        this.buttons.add(card01);
        this.buttons.add(card10);
        this.buttons.add(card11);

        int i = 0;
        for (Player player : players) {
            if (player != currentPlayer) {
                this.players.add(player);
                buttons.get(i).setText(player.getHand().get(0).toString());
                i++;
                buttons.get(i).setText(player.getHand().get(1).toString());
                i++;
            }
        }

        label0.setText(this.players.get(0).getName());
        label1.setText(this.players.get(1).getName());
    }

    public void addFrame(JFrame frame) {
        this.frame = frame;
    }

    public JPanel getPanel() {
        return this.panel1;
    }
}