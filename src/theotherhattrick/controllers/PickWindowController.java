package theotherhattrick.controllers;

import theotherhattrick.Game;
import theotherhattrick.Player;
import theotherhattrick.PlayerReal;
import views.PickWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PickWindowController {
    private Game game;
    private PickWindow frame;
    private JButton card00;
    private JButton card01;
    private JButton card10;
    private JButton card11;

    public PickWindowController(PickWindow frame) {
        this.game = Game.getInstance();
        this.frame = frame;
        this.card00 = frame.getCard00();
        this.card01 = frame.getCard01();
        this.card10 = frame.getCard10();
        this.card11 = frame.getCard11();

        card00.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerReal pr = (PlayerReal)Game.getInstance().getCurrentPlayer();
                List<Player> othersPlayers = Game.getInstance().getOtherPlayers(pr);
                pr.setPlayerToExchangeWith(othersPlayers.get(0));
                pr.setOtherCardIndex(0);
                frame.dispose();
            }
        });
        card01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerReal pr = (PlayerReal)Game.getInstance().getCurrentPlayer();
                List<Player> othersPlayers = Game.getInstance().getOtherPlayers(pr);
                pr.setPlayerToExchangeWith(othersPlayers.get(0));
                pr.setOtherCardIndex(1);
                frame.dispose();
            }
        });
        card10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerReal pr = (PlayerReal)Game.getInstance().getCurrentPlayer();
                List<Player> othersPlayers = Game.getInstance().getOtherPlayers(pr);
                pr.setPlayerToExchangeWith(othersPlayers.get(1));
                pr.setOtherCardIndex(0);
                frame.dispose();
            }
        });
        card11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerReal pr = (PlayerReal)Game.getInstance().getCurrentPlayer();
                List<Player> othersPlayers = Game.getInstance().getOtherPlayers(pr);
                pr.setPlayerToExchangeWith(othersPlayers.get(1));
                pr.setOtherCardIndex(1);
                frame.dispose();
            }
        });
    }
}