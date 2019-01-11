package theotherhattrick.controllers;

import theotherhattrick.Game;
import theotherhattrick.PlayerReal;
import theotherhattrick.ThreadScanner;
import views.DiscardWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiscardWindowController {
    private Game game;
    private JFrame frame;
    private JButton card0;
    private JButton card1;
    private JButton card2;

    private ThreadScanner tScanner;

    public DiscardWindowController(DiscardWindow frame) {
        this.game = Game.getInstance();
        this.frame = frame;
        this.card0 = frame.getCard0();
        this.card1 = frame.getCard1();
        this.card2 = frame.getCard2();
        tScanner = ThreadScanner.getInstance();

        PlayerReal pr = (PlayerReal)Game.getInstance().getCurrentPlayer();
        pr.setDiscardingCard(true);

        card0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerReal pr = (PlayerReal)Game.getInstance().getCurrentPlayer();
                pr.setCardDiscarded(true);
                pr.setCardToBeDiscarded(0);

                tScanner.setResult("0");
                tScanner.setWaitingResponse(false);
                frame.dispose();
            }
        });
        card1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerReal pr = (PlayerReal)Game.getInstance().getCurrentPlayer();
                pr.setCardDiscarded(true);
                pr.setCardToBeDiscarded(1);

                tScanner.setResult("1");
                tScanner.setWaitingResponse(false);

                frame.dispose();
            }
        });
        card2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerReal pr = (PlayerReal)Game.getInstance().getCurrentPlayer();
                pr.setCardDiscarded(true);
                pr.setCardToBeDiscarded(2);

                tScanner.setResult("2");
                tScanner.setWaitingResponse(false);

                frame.dispose();
            }
        });
    }
}