package views;

import theotherhattrick.*;
import theotherhattrick.controllers.MainWindowController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class MainWindow extends JFrame implements Observer, Runnable {
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
    private JButton seventhPropBt;

    private JOptionPane dialog;

    private Game game;
    private MainWindowController mwc;
    private ThreadScanner tScanner;

    private int ownCardIndex;

    public MainWindow() {
        this.game = Game.getInstance();

        setTitle("Main window");
        setSize(1000, 700);
        setContentPane(getPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        dialog = new JOptionPane();

        game = Game.getInstance();
        tScanner = ThreadScanner.getInstance();

        game.addObserver(this);
        game.getSeventhProp().addObserver(this);
        for (Player p : game.getPlayers()) {
            for (Card c : p.getHand())
                c.addObserver(this);

            p.addObserver(this);
        }

        for (Card c : game.getTrickDeck().getCards())
            c.addObserver(this);

        for (Card c : game.getTrickPile())
            c.addObserver(this);

        mwc = new MainWindowController(this);

        Thread t = new Thread(this);
        t.start();
    }

    private void refreshView() {
        player0Label.setText(game.getPlayers().get(0).toString());
        player1Label.setText(game.getPlayers().get(1).toString());
        player2Label.setText(game.getPlayers().get(2).toString());

        card00.setText(game.getPlayers().get(0).getHand().get(0).toString());
        putImageProp(card00);
        card01.setText(game.getPlayers().get(0).getHand().get(1).toString());
        putImageProp(card01);

        card10.setText(game.getPlayers().get(1).getHand().get(0).toString());
        putImageProp(card10);
        card11.setText(game.getPlayers().get(1).getHand().get(1).toString());
        putImageProp(card11);

        card20.setText(game.getPlayers().get(2).getHand().get(0).toString());
        putImageProp(card20);
        card21.setText(game.getPlayers().get(2).getHand().get(1).toString());
        putImageProp(card21);

        seventhPropBt.setText(game.getSeventhProp().toString());
        putImageProp(seventhPropBt);

        if (game.getTrickPile().peek() == null)
            trickBt.setText("Pas de trick disponible");
        else {
            trickBt.setText(game.getTrickPile().peek().toString());
            putImageTrick(trickBt);
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

    public void chooseCard() {
        PickWindow pickWindow = new PickWindow();
        pickWindow.showCards();
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
        refreshView();
        while(isVisible());
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Game) {
            if (game.isNewTurn()) {
//                refreshView();
                dialog.showMessageDialog(this, game.getCurrentPlayer() + " à toi de jouer", "A toi !", JOptionPane.INFORMATION_MESSAGE);
                trickBt.setEnabled(true);
                doTrickBt.setEnabled(true);
            }

            if (game.isNewTrickPicked()) {
                trickBt.setText(game.getCurrentTrick().toString());
                trickBt.setEnabled(false);
            }

            if (game.isExchangeDone())
                refreshView();
        }

        if (o instanceof Player) {
            if (((Player) o).isTrickAlreadyPerformed()) {
                if (((Player) o).isPerformTrick())
                    JOptionPane.showMessageDialog(this, "TA-DAH !", "Bien joué !", JOptionPane.INFORMATION_MESSAGE);

                else
                    JOptionPane.showMessageDialog(this, "Trick raté!", "Loupé !", JOptionPane.INFORMATION_MESSAGE);
            }

            if (((Player) o).isCardDiscarded()) {
                propLabel.setText(game.getSeventhProp().toString());

                switch (game.getPlayers().indexOf(o)) {
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

        if (o instanceof PlayerReal) {
            if (((PlayerReal) o).isChosingTrick())
                dialog.setEnabled(false);

            if (((PlayerReal) o).isExchangingCard())
            {
                if (!((PlayerReal) o).isOwnCardChosen())
                {
                    doTrickBt.setEnabled(false);
                    enableCurrentPlayerButtons();
                }
                else if (!((PlayerReal) o).isOtherCardChosen())
                    new PickWindow();
            }

            if (((PlayerReal) o).isPerformingTrick())
            {
                int choice = JOptionPane.showConfirmDialog(this, "Réaliser ce trick ?","Veuillez confirmer votre choix",JOptionPane.YES_NO_OPTION);

                if (choice == 0)
                {
                    tScanner.setWaitingResponse(false);
                    tScanner.setResult("O");
                }
                else
                {
                    tScanner.setWaitingResponse(false);
                    tScanner.setResult("N");
                }
            }

            if (((PlayerReal) o).isNeedToTurn())

            if (((PlayerReal) o).isDiscardingCard())
            {
                JOptionPane.showMessageDialog(this, "TA-DAH !","Bien joué !", JOptionPane.INFORMATION_MESSAGE);
                new DiscardWindow();
            }
        }

        if (o instanceof Trick) {
            if (((Trick) o).isBeingPerformed() && !((Trick) o).isCurrentlyDoable())
                JOptionPane.showMessageDialog(this, "Trick raté!", "Loupé !", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void putImageProp(JButton button) {
        ImageIcon image = new ImageIcon("theotherhattrick/resources/props/FaceCacheTricks.jpeg");

        if (button.getText().toString().equals("Carrots")) {
            image = new ImageIcon("theotherhattrick/resources/props/Carrots.png");
        } else if (button.getText().toString().equals("The Hat")) {
            image = new ImageIcon("theotherhattrick/resources/props/TheHat.png");
        } else if (button.getText().toString().equals("The Rabbit")) {
            image = new ImageIcon("theotherhattrick/resources/props/TheRabbit.png");
        } else if (button.getText().toString().equals("The Other Rabbit")) {
            image = new ImageIcon("theotherhattrick/resources/props/TheOtherRabbit.png");
        } else if (button.getText().toString().equals("The Lettuce")) {
            image = new ImageIcon("theotherhattrick/resources/props/TheLettuce.png");
        }
        image = new ImageIcon(image.getImage().getScaledInstance(50, 70, Image.SCALE_DEFAULT));
        button.setIcon(image);
    }

    private void putImageTrick(JButton button) {
        ImageIcon image = new ImageIcon("theotherhattrick/resources/FaceCacheTricks.jpeg");
        String trick = Game.getInstance().getTrickPile().peek().getName();

        if (trick.equals("The Carrot Hat Trick")) {
            image = new ImageIcon("theotherhattrick/resources/tricks/TheCarrotHatTrick.png");
        } else if (trick.equals("The Hungry Rabbit")) {
            image = new ImageIcon("theotherhattrick/resources/tricks/TheHungryRabbit.png");
        } else if (trick.equals("The Vegetable Patch")) {
            image = new ImageIcon("theotherhattrick/resources/tricks/TheVegetablePatch.png");
        } else if (trick.equals("The Slightly Easier Hat Trick")) {
            image = new ImageIcon("theotherhattrick/resources/tricks/TheSlightlyEasierHatTrick.png");
        } else if (trick.equals("The Other Hat Trick")) {
            image = new ImageIcon("theotherhattrick/resources/tricks/TheOtherHatTrick.png");
        } else if (trick.equals("The Hat Trick")) {
            image = new ImageIcon("theotherhattrick/resources/tricks/TheHatTrick.png");
        } else if (trick.equals("The Bunch Of Carrots")) {
            image = new ImageIcon("theotherhattrick/resources/tricks/TheBunchOfCarrots.png");
        } else if (trick.equals("The Rabbit That Didn't Like Carrots")) {
            image = new ImageIcon("theotherhattrick/resources/tricks/TheRabbitThatDidn'tLikeCarrots.png");
        } else if (trick.equals("The Pair Of Rabbits")) {
            image = new ImageIcon("theotherhattrick/resources/tricks/ThePairOfRabbits.png");
        } else if (trick.equals("The Vegetable Hat Trick")) {
            image = new ImageIcon("theotherhattrick/resources/tricks/TheOtherHatTrick.png");
        }

        image = new ImageIcon(image.getImage().getScaledInstance(125, 75, Image.SCALE_DEFAULT));
        button.setIcon(image);
    }

    public JButton getCard00() {
        return card00;
    }

    public JButton getCard01() {
        return card01;
    }

    public JButton getCard10() {
        return card10;
    }

    public JButton getCard11() {
        return card11;
    }

    public JButton getCard20() {
        return card20;
    }

    public JButton getCard21() {
        return card21;
    }

    public JButton getTrickBt() {
        return trickBt;
    }

    public JButton getDoTrickBt() {
        return doTrickBt;
    }

    public void enableCurrentPlayerButtons()
    {
        switch (game.getPlayers().indexOf(game.getCurrentPlayer()))
        {
            case 0:
                card00.setEnabled(true);
                card01.setEnabled(true);

                doTrickBt.setEnabled(false);
                trickBt.setEnabled(false);

                card10.setEnabled(false);
                card11.setEnabled(false);
                card20.setEnabled(false);
                card21.setEnabled(false);
                break;

            case 1:
                card10.setEnabled(true);
                card11.setEnabled(true);

                doTrickBt.setEnabled(false);
                trickBt.setEnabled(false);

                card00.setEnabled(false);
                card01.setEnabled(false);
                card20.setEnabled(false);
                card21.setEnabled(false);
                break;

            case 2:
                card20.setEnabled(true);
                card21.setEnabled(true);

                doTrickBt.setEnabled(false);
                trickBt.setEnabled(false);

                card00.setEnabled(false);
                card01.setEnabled(false);
                card10.setEnabled(false);
                card11.setEnabled(false);
                break;
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        bottomPanel.setMinimumSize(new Dimension(0, 0));
        bottomPanel.setPreferredSize(new Dimension(0, 125));
        panel1.add(bottomPanel, BorderLayout.SOUTH);
        player0Label = new JLabel();
        player0Label.setHorizontalAlignment(0);
        player0Label.setText("Label");
        bottomPanel.add(player0Label);
        card00 = new JButton();
        card00.setEnabled(false);
        card00.setHideActionText(true);
        card00.setText("Button");
        bottomPanel.add(card00);
        card01 = new JButton();
        card01.setEnabled(false);
        card01.setText("Button");
        bottomPanel.add(card01);
        leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        leftPanel.setPreferredSize(new Dimension(300, 0));
        panel1.add(leftPanel, BorderLayout.WEST);
        player1Label = new JLabel();
        player1Label.setHorizontalAlignment(0);
        player1Label.setText("Label");
        leftPanel.add(player1Label);
        card10 = new JButton();
        card10.setEnabled(false);
        card10.setText("Button");
        card10.setVerticalAlignment(0);
        card10.setVerticalTextPosition(0);
        leftPanel.add(card10);
        card11 = new JButton();
        card11.setEnabled(false);
        card11.setText("Button");
        leftPanel.add(card11);
        rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        rightPanel.setPreferredSize(new Dimension(300, 0));
        panel1.add(rightPanel, BorderLayout.EAST);
        player2Label = new JLabel();
        player2Label.setText("Label");
        rightPanel.add(player2Label);
        card20 = new JButton();
        card20.setEnabled(false);
        card20.setText("Button");
        rightPanel.add(card20);
        card21 = new JButton();
        card21.setEnabled(false);
        card21.setText("Button");
        rightPanel.add(card21);
        centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.add(centerPanel, BorderLayout.CENTER);
        trickBt = new JButton();
        trickBt.setText("Button");
        centerPanel.add(trickBt);
        doTrickBt = new JButton();
        doTrickBt.setText("Faire le trick");
        centerPanel.add(doTrickBt);
        seventhPropBt = new JButton();
        seventhPropBt.setEnabled(false);
        seventhPropBt.setText("");
        centerPanel.add(seventhPropBt);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}