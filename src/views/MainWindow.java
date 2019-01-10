package views;

import theotherhattrick.Game;
import theotherhattrick.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private JPanel panel1;
    private JPanel bottomPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel centerPanel;
    private JLabel player0Label;
    private JLabel player1Label;
    private JLabel player2Label;
    private JButton card00;
    private JButton card01;
    private JButton card11;
    private JButton card10;
    private JButton card20;
    private JButton card21;
    private JButton trickBt;
    private JButton doTrickBt;
    private JLabel propLabel;
    private Game game;
    private int ownCardIndex;

    public MainWindow() {
        this.game = Game.getInstance();

        setTitle("Main window");
        setSize(700, 500);
        setContentPane(getPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        trickBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.drawTrick();
                trickBt.setEnabled(false);
                refreshView();
            }
        });

        doTrickBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableButtons();
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
                //int ownCardIndex = 0;
                ownCardIndex = 0;
                chooseCard();
            }
        });
        card01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //int ownCardIndex = 1;
                ownCardIndex = 1;
                chooseCard();
            }
        });
        card10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //int ownCardIndex = 0;
                ownCardIndex = 0;
                chooseCard();
            }
        });
        card11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // int ownCardIndex = 1;
                ownCardIndex = 1;
                chooseCard();
            }
        });
        card20.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //int ownCardIndex = 0;
                ownCardIndex = 0;
                chooseCard();
            }
        });
        card21.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //int ownCardIndex = 1;
                ownCardIndex = 1;
                chooseCard();
            }
        });
    }

    public void refreshView() {
        player0Label.setText(game.getPlayers().get(0).toString());
        player1Label.setText(game.getPlayers().get(1).toString());
        player2Label.setText(game.getPlayers().get(2).toString());

        card00.setText(game.getPlayers().get(0).getHand().get(0).getName());
        card01.setText(game.getPlayers().get(0).getHand().get(1).getName());

        card10.setText(game.getPlayers().get(1).getHand().get(0).getName());
        card11.setText(game.getPlayers().get(1).getHand().get(1).getName());

        card20.setText(game.getPlayers().get(2).getHand().get(0).getName());
        card21.setText(game.getPlayers().get(2).getHand().get(1).getName());

        propLabel.setText(game.getSeventhProp().getName());

        if (game.getTrickPile().peek() == null)
            trickBt.setText("Rien");
        else
            trickBt.setText(game.getTrickPile().peek().toString());
    }

    public void enableButtons() {
        card00.setEnabled(true);
        card01.setEnabled(true);
        card10.setEnabled(true);
        card11.setEnabled(true);
        card20.setEnabled(true);
        card21.setEnabled(true);
    }

    public void chooseCard() {
        PickWindow pickWindow = new PickWindow(this);

        JFrame frame = new JFrame("PickWindow");
        pickWindow.addFrame(frame);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(600, 200);
        frame.setContentPane(pickWindow.getPanel());
        frame.setVisible(true);

        pickWindow.showCards(game.getPlayers(), game.getCurrentPlayer());
    }

    public void exchange(Player player, int index) {
        game.getCurrentPlayer().exchangeCard(ownCardIndex, player, index);
        refreshView();

        boolean trickReussi = true;
        if (trickReussi) {
            DiscardWindow discardWindow = new DiscardWindow(this);

            JFrame frame = new JFrame("DiscardWindow");
            discardWindow.addFrame(frame);
            frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            frame.setSize(600, 100);
            frame.setContentPane(discardWindow.getPanel());
            frame.setVisible(true);

            game.getCurrentPlayer().getHand().add(game.getSeventhProp());
            discardWindow.showCards(game.getCurrentPlayer());
        }
    }

    public void putSeventhProp(int indexProp) {
        game.setSeventhProp(game.getCurrentPlayer().getHand().get(indexProp));
        game.getCurrentPlayer().getHand().remove(indexProp);
        refreshView();
    }

    public JPanel getPanel() {
        return this.panel1;
    }
}