package theotherhattrick.controllers;

import theotherhattrick.Game;
import theotherhattrick.Player;
import theotherhattrick.PlayerReal;
import theotherhattrick.ThreadScanner;
import views.MainWindow;
import views.PickWindow;

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
                game.setNewTrickPicked(true);

                doTrickBt.setEnabled(false);

                tScanner.setResult("N");
                tScanner.setWaitingResponse(false);

                ((PlayerReal)game.getCurrentPlayer()).setTrickChoice(tScanner.getResult().equals("o"));
                ((PlayerReal)game.getCurrentPlayer()).setTrickChosen(true);

                ((PlayerReal)game.getCurrentPlayer()).exchangeCard();

                ((PlayerReal)game.getCurrentPlayer()).setExchangingCard(true);
                //((PlayerReal)game.getCurrentPlayer()).exchangeCard();
            }
        });

        this.doTrickBt.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                tScanner.setResult("O");
                tScanner.setWaitingResponse(false);

                ((PlayerReal)game.getCurrentPlayer()).setTrickChoice(tScanner.getResult().equals("o"));
                ((PlayerReal)game.getCurrentPlayer()).setTrickChosen(true);

                ((PlayerReal)game.getCurrentPlayer()).setExchangingCard(true);
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
                    ((PlayerReal) p).setOwnCardIndex(0);
                    ((PlayerReal) p).setOwnCardChosen(true);

                    tScanner.setWaitingResponse(false);
                    tScanner.setResult("0");

                    new PickWindow();
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
                    ((PlayerReal) p).setOwnCardIndex(0);
                    ((PlayerReal) p).setOwnCardChosen(true);

                    tScanner.setWaitingResponse(false);
                    tScanner.setResult("1");

                    new PickWindow();
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
                    ((PlayerReal) p).setOwnCardIndex(0);
                    ((PlayerReal) p).setOwnCardChosen(true);

                    tScanner.setWaitingResponse(false);
                    tScanner.setResult("0");

                    new PickWindow();
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
                    ((PlayerReal) p).setOwnCardIndex(1);
                    ((PlayerReal) p).setOwnCardChosen(true);

                    tScanner.setWaitingResponse(false);
                    tScanner.setResult("1");

                    new PickWindow();
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
                    ((PlayerReal) p).setOwnCardIndex(0);
                    ((PlayerReal) p).setOwnCardChosen(true);

                    tScanner.setWaitingResponse(false);
                    tScanner.setResult("0");

                    new PickWindow();
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
                    ((PlayerReal) p).setOwnCardIndex(1);
                    ((PlayerReal) p).setOwnCardChosen(true);

                    tScanner.setWaitingResponse(false);
                    tScanner.setResult("1");

                    new PickWindow();
                }
            }
        });
    }
}
