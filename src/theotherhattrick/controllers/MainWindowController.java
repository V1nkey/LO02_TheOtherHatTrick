package theotherhattrick.controllers;

import theotherhattrick.*;
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
    private JButton switchToBotBt;

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
        this.switchToBotBt = mainWindow.getSwitchToBotBt();

        this.trickBt.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                game.drawTrick();

                tScanner.setResult("N");
                tScanner.setWaitingResponse(false);

                doTrickBt.setEnabled(false);
                enableCurrentPlayerButtons();

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

                doTrickBt.setEnabled(false);
                enableCurrentPlayerButtons();

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
                    ((PlayerReal) p).setOwnCardChosen(true);
                    ((PlayerReal) p).setOwnCardIndex(0);

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
        this.switchToBotBt.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                PlayerReal pr = (PlayerReal)Game.getInstance().getCurrentPlayer();
                pr.setStrategy(new Strategy2());
            }
        });
    }

    private void enableCurrentPlayerButtons()
    {
        switch (game.getPlayers().indexOf(game.getCurrentPlayer()))
        {
            case 0:
                card00.setEnabled(true);
                card01.setEnabled(true);

                card10.setEnabled(false);
                card11.setEnabled(false);
                card20.setEnabled(false);
                card21.setEnabled(false);
                break;

            case 1:
                card10.setEnabled(true);
                card11.setEnabled(true);

                card00.setEnabled(false);
                card01.setEnabled(false);
                card20.setEnabled(false);
                card21.setEnabled(false);
                break;

            case 2:
                card20.setEnabled(true);
                card21.setEnabled(true);

                card00.setEnabled(false);
                card01.setEnabled(false);
                card10.setEnabled(false);
                card11.setEnabled(false);
                break;
        }
    }
}
