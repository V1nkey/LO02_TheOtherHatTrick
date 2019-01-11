package theotherhattrick.controllers;

import theotherhattrick.Game;
import theotherhattrick.Player;
import theotherhattrick.PlayerReal;
import theotherhattrick.ThreadScanner;
import views.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindowController
{
    private Game game;

    private MainWindow mainWindow;
    private ThreadScanner tScanner;

    private JButton card00;
    private JButton card01;
    private JButton card10;
    private JButton card11;
    private JButton card20;
    private JButton card21;
    private JButton trickBt;
    private JButton doTrickBt;

    public MainWindowController(MainWindow mainWindow)
    {
        game = Game.getInstance();

        this.mainWindow = mainWindow;
        this.tScanner = ThreadScanner.getInstance();

        this.card00 = mainWindow.getCard00();
        this.card01 = mainWindow.getCard01();
        this.card10 = mainWindow.getCard10();
        this.card11 = mainWindow.getCard11();
        this.card20 = mainWindow.getCard20();
        this.card21 = mainWindow.getCard21();
        this.trickBt = mainWindow.getTrickBt();
        this.doTrickBt = mainWindow.getDoTrickBt();

        this.trickBt.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                game.drawTrick();
                tScanner.setWaitingResponse();
            }
        });

        this.doTrickBt.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                switch (game.getPlayers().indexOf(game.getCurrentPlayer()))
                {
                    case 0:
                        card10.setEnabled(false);
                        card11.setEnabled(false);
                        card20.setEnabled(false);
                        card21.setEnabled(false);
                        break;

                    case 1:
                        card00.setEnabled(false);
                        card01.setEnabled(false);
                        card20.setEnabled(false);
                        card21.setEnabled(false);
                        break;

                    case 2:
                        card00.setEnabled(false);
                        card01.setEnabled(false);
                        card10.setEnabled(false);
                        card11.setEnabled(false);
                        break;
                }
            }
        });

        this.card00.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Player p = game.getPlayers().get(0);
                if (p instanceof PlayerReal)
                {
                    ((PlayerReal) p).setOwnCardChosen(true);
                    ((PlayerReal) p).setOwnCardIndex(0);
                }
            }
        });

        this.card01.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Player p = game.getPlayers().get(0);
                if (p instanceof PlayerReal)
                {
                    ((PlayerReal) p).setOwnCardChosen(true);
                    ((PlayerReal) p).setOwnCardIndex(1);
                }
            }
        });

        this.card10.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Player p = game.getPlayers().get(1);
                if (p instanceof PlayerReal)
                {
                    ((PlayerReal) p).setOwnCardChosen(true);
                    ((PlayerReal) p).setOwnCardIndex(0);
                }
            }
        });

        this.card11.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Player p = game.getPlayers().get(1);
                if (p instanceof PlayerReal)
                {
                    ((PlayerReal) p).setOwnCardChosen(true);
                    ((PlayerReal) p).setOwnCardIndex(1);
                }
            }
        });

        this.card20.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Player p = game.getPlayers().get(2);
                if (p instanceof PlayerReal)
                {
                    ((PlayerReal) p).setOwnCardChosen(true);
                    ((PlayerReal) p).setOwnCardIndex(0);
                }
            }
        });

        this.card21.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Player p = game.getPlayers().get(2);
                if (p instanceof PlayerReal)
                {
                    ((PlayerReal) p).setOwnCardChosen(true);
                    ((PlayerReal) p).setOwnCardIndex(1);
                }
            }
        });
    }
}
