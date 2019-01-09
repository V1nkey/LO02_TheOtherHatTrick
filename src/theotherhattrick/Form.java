package theotherhattrick;

import javax.swing.*;

public class Form {
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
    private Game game;

    public Form(Game game) {
        this.game = game;
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

    public JPanel getPanel1() {
        return this.panel1;
    }
}