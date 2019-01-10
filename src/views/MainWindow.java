package views;

import theotherhattrick.*;
import theotherhattrick.controllers.MainWindowController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class MainWindow extends JFrame implements Observer, Runnable{
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
    private JButton card10;
    private JButton card11;
    private JButton card20;
    private JButton card21;
    private JButton trickBt;
    private JButton doTrickBt;
    private JLabel propLabel;

    private JOptionPane dialog;

    private Game game;

    private int ownCardIndex;

    public MainWindow() {
        this.game = Game.getInstance();

        setTitle("Main window");
        setSize(700, 500);
        setContentPane(getPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        dialog = new JOptionPane();

        game = Game.getInstance();

        game.addObserver(this);
        game.getSeventhProp().addObserver(this);
        for (Player p : game.getPlayers())
        {
            for (Card c : p.getHand())
                c.addObserver(this);

            p.addObserver(this);
        }

        for (Card c : game.getTrickDeck().getCards())
            c.addObserver(this);

        for (Card c : game.getTrickPile())
            c.addObserver(this);

        MainWindowController mwc = new MainWindowController(this);

        Thread t = new Thread(this);
        t.start();

//        trickBt.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                game.drawTrick();
//                trickBt.setEnabled(false);
//                refreshView();
//            }
//        });

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

    private void refreshView() {
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
            trickBt.setText("Pas de trick disponible");
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

    @Override
    public void run()
    {
        int k = 0;
        do
        {
            refreshView();
            Player currentPlayer = game.getPlayers().get(k%game.getPlayers().size());

            game.playTurn(currentPlayer);
            k++;
        } while (!game.isEnded());
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof Game)
        {
            if (game.isNewTurn())
            {
//                refreshView();
                dialog.showMessageDialog(this, game.getCurrentPlayer() + " à toi de jouer","A toi !", JOptionPane.INFORMATION_MESSAGE);
                trickBt.setEnabled(true);
            }

            if (game.isNewTrickPicked())
            {
                trickBt.setText(game.getCurrentTrick().toString());
                trickBt.setEnabled(false);
            }
        }

        if (o instanceof Player)
        {
            if (((Player) o).isTrickAlreadyPerformed())
            {
                if (((Player) o).isPerformTrick())
                    JOptionPane.showMessageDialog(this, "TA-DAH !","Bien joué !", JOptionPane.INFORMATION_MESSAGE);

                else
                    JOptionPane.showMessageDialog(this, "Trick raté!","Loupé !", JOptionPane.INFORMATION_MESSAGE);
            }

            if (((Player) o).isCardDiscarded())
            {
                propLabel.setText(game.getSeventhProp().toString());

                switch (game.getPlayers().indexOf(o))
                {
                    case 0:
                        card00.setText(((Player) o).getHand().get(0).getName());
                        card01.setText(((Player) o).getHand().get(1).getName());
                        break;

                    case 1:
                        card10.setText(((Player) o).getHand().get(0).getName());
                        card11.setText(((Player) o).getHand().get(1).getName());
                        break;

                    case 2:
                        card20.setText(((Player) o).getHand().get(0).getName());
                        card21.setText(((Player) o).getHand().get(1).getName());
                }
            }
        }

        if (o instanceof PlayerReal)
        {
            if (((PlayerReal) o).isChosingTrick())
                dialog.setEnabled(false);

            if (((PlayerReal) o).isExchangingCard())

            if (((PlayerReal) o).isPerformingTrick())

            if (((PlayerReal) o).isNeedToTurn())

            if (((PlayerReal) o).isDiscardingCard());
        }

        if (o instanceof Trick)
        {
            if (((Trick) o).isBeingPerformed() && !((Trick) o).isCurrentlyDoable())
                JOptionPane.showMessageDialog(this, "Trick raté!","Loupé !", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public JButton getCard00() { return card00; }
    public JButton getCard01() { return card01; }
    public JButton getCard10() { return card10; }
    public JButton getCard11() { return card11; }
    public JButton getCard20() { return card20; }
    public JButton getCard21() { return card21; }
    public JButton getTrickBt() { return trickBt; }
    public JButton getDoTrickBt() { return doTrickBt; }
}