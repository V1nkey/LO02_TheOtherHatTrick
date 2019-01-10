package theotherhattrick;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindoww {
    private JPanel panel1;
    private JPanel bottomPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JLabel player0Label;
    private JLabel player1Label;
    private JLabel player2Label;
    private JButton card00;
    private JButton card01;
    private JButton card11;
    private JButton card10;
    private JButton card20;
    private JButton card21;
    private JButton trick;
    private JButton prop;
    private JButton btDoTrick;
    private Game game;

    public MainWindoww(Game game) {
        this.game = game;

        trick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.drawTrick();
                trick.setEnabled(false);
                refreshView();
            }
        });

        btDoTrick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.getCurrentPlayer() == game.getPlayers().get(0)) {
                    card10.setEnabled(false);
                    card11.setEnabled(false);
                    card20.setEnabled(false);
                    card21.setEnabled(false);

                } else if (game.getCurrentPlayer() == game.getPlayers().get(1)) {
                    card00.setEnabled(false);
                    card01.setEnabled(false);
                    card20.setEnabled(false);
                    card21.setEnabled(false);

                } else if (game.getCurrentPlayer() == game.getPlayers().get(2)) {
                    card00.setEnabled(false);
                    card01.setEnabled(false);
                    card10.setEnabled(false);
                    card11.setEnabled(false);
                }
            }
        });

        card00.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ownCardIndex = 0;
                int player = 0;
            }
        });
        card01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ownCardIndex = 1;
                int player = 0;
            }
        });
        card10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ownCardIndex = 0;
                int player = 1;
            }
        });
        card11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ownCardIndex = 1;
                int player = 1;
            }
        });
        card20.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ownCardIndex = 0;
                int player = 2;
            }
        });
        card21.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ownCardIndex = 1;
                int player = 2;
            }
        });
    }

    public void refreshView() {
        player0Label.setText(game.getPlayers().get(0).toString());
        player1Label.setText(game.getPlayers().get(1).toString());
        player2Label.setText(game.getPlayers().get(2).toString());

        card00.setText(game.getPlayers().get(0).getHand().get(0).toString());
        card01.setText(game.getPlayers().get(0).getHand().get(1).toString());

        card10.setText(game.getPlayers().get(1).getHand().get(0).toString());
        card11.setText(game.getPlayers().get(1).getHand().get(1).toString());

        card20.setText(game.getPlayers().get(2).getHand().get(0).toString());
        card21.setText(game.getPlayers().get(2).getHand().get(1).toString());

        if (game.getTrickPile().peek() != null)
            trick.setText(game.getTrickPile().peek().toString());
        else
            trick.setText("Rien");
        prop.setText(game.getSeventhProp().toString());
    }

    public void disableButtons(int intPlayer) {
        if (intPlayer == 0) {
            card00.setEnabled(false);
            card01.setEnabled(false);
        }
        else if (intPlayer == 1) {
            card10.setEnabled(false);
            card11.setEnabled(false);
        }
        else if (intPlayer == 2) {
            card20.setEnabled(false);
            card21.setEnabled(false);
        }
    }

    public void enableButtons() {
        card00.setEnabled(true);
        card01.setEnabled(true);
        card10.setEnabled(true);
        card11.setEnabled(true);
        card20.setEnabled(true);
        card21.setEnabled(true);
    }

    public JPanel getPanel() {
        return this.panel1;
    }
}