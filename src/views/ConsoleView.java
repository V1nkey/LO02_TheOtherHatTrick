/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import theotherhattrick.Card;
import theotherhattrick.Game;
import theotherhattrick.Player;
import theotherhattrick.PlayerReal;
import theotherhattrick.Prop;
import theotherhattrick.Trick;

/**
 *
 * @author v1nkey
 */
public class ConsoleView implements Observer, Runnable 
{
    private Game game;
    
    public ConsoleView()
    {
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
        
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() 
    {
        int k = 0;
        do
        {
            Player currentPlayer = game.getPlayers().get(k%game.getPlayers().size());
            
            game.playTurn(currentPlayer);
            k++;
        } while (!game.isEnded());
        game.endGame();
        
        game.showFinalRanking();
    }
    
    //Ajouter des booleans de checkpoint dans Player, 
    //dans update, vérifier où on en est dans la méthode play et adapter le texte en fonction
    
    public void printBoard()
    {
        System.out.println("******************");
        for (Player p : game.getPlayers())
        {
            p.updateScore();
            System.out.println(p);
            for (Prop pr : p.getHand())
                System.out.println(pr.toString());
            
            System.out.println("******************");
        }
        System.out.println(game.getSeventhProp());
        System.out.println("******************");
        
        if (game.getCurrentTrick() == null)
            System.out.println("Pas de trick disponible");
        else
            System.out.println(game.getCurrentTrick());
        System.out.println("******************");
    }
        
    @Override
    public void update(Observable o, Object arg) 
    { 
        if (o instanceof Game)
        {
            if (game.isNewTurn())
            {
                System.out.println("******************");
                System.out.println(game.getCurrentPlayer() + " à toi de jouer");
                System.out.println("******************\n");
                printBoard();
            }
            
            if (game.isNewTrickPicked())
            {
                System.out.println(game.getCurrentTrick());
                System.out.println("******************");
            }
        }
        
        if (o instanceof Player)
        {
            if (((Player) o).isTrickAlreadyPerformed())
            {
                if (((Player) o).isPerformTrick())
                {
                    System.out.println("TA-DAH ! Trick réussi !");
                    System.out.println("******************");
                }
                else
                {
                    System.out.println("Trick raté");
                    System.out.println("******************\n");
                }
            }
        }
        
        if (o instanceof PlayerReal)
        {
            if (((PlayerReal) o).isChosingTrick())
                chosingTrick((PlayerReal) o);
            
            if (((PlayerReal) o).isExchangingCard())
                exchangingCard((PlayerReal) o);
            
            if (((PlayerReal) o).isPerformingTrick())
                performingTrick((PlayerReal) o);
            
            if (((PlayerReal) o).isNeedToTurn())
                turningOverCard((PlayerReal) o);
            
            if (((PlayerReal) o).isDiscardingCard())
                discardingCard((PlayerReal) o);
        }
        
        if (o instanceof Trick)
        {
            if (((Trick) o).isBeingPerformed() && !((Trick) o).isCurrentlyDoable())
            {
                System.out.println("Trick raté !");
                System.out.println("******************\n");
            }
        }
    }
    
    private void chosingTrick(PlayerReal pr)
    {
        pr.setChosingTrick(false);
        pr.showHand(true);

        Scanner sc = new Scanner(System.in);
        String choice = "";
        do
        {
            System.out.println("Choisir ce trick ? O / N");
            choice = sc.nextLine().toLowerCase();

            if (!choice.equals("o") && !choice.equals("n"))
                System.out.println("Merci d'entrer O ou N");

        } while (!choice.equals("o") && !choice.equals("n"));

        pr.setTrickChoice(choice.equals("o"));
        pr.setTrickChosen(true);
    }
    
    private void exchangingCard(PlayerReal pr)
    {
        pr.setExchangingCard(false);
        Scanner sc = new Scanner(System.in);
        String cardChoice = "";
        
        //Choix de sa propre carte
        System.out.println("Choisis une de tes cartes à échanger : 0 ou 1");
        do
        {
            cardChoice = sc.nextLine();
            if (!cardChoice.equals("0") && !cardChoice.equals("1"))
                System.out.println("Choisis une de tes cartes à échanger : 0 ou 1");
        } while (!cardChoice.equals("0") && !cardChoice.equals("1"));

        pr.setOwnCardIndex(Integer.parseInt(cardChoice));
        pr.setOwnCardChosen(true);
        
        //Choix de la 2ème carte
        List<Player> otherPlayers = game.getOtherPlayers(pr);
        List<Card> gettableCards = new ArrayList();
        
        int k = 0;
        System.out.println("Choisis une carte à récupérer : ");
        for (Player p : otherPlayers)
        {
            gettableCards.addAll(p.getHand());
            p.showHand(false, k);
            k+=2;
        }
        
        int cardIndex = -1;
        boolean isEntryValid = false;
        do
        {
            cardChoice = sc.nextLine();
            isEntryValid = tryParse(cardChoice);
            
            if (isEntryValid)
            {
                cardIndex = Integer.parseInt(cardChoice);
                if (cardIndex < 0 || cardIndex > gettableCards.size() - 1)
                    System.out.println("Choisis une carte à récupérer");
            }
            else
                System.out.println("Choisis une carte à récupérer");
        } while (!isEntryValid || (cardIndex < 0 || cardIndex > gettableCards.size() - 1));
        
        int playerIndex = cardIndex / 2;
        pr.setPlayerToExchangeWith(otherPlayers.get(playerIndex));
        pr.setOtherCardIndex(cardIndex%2);
        pr.setOtherCardChosen(true);
    }
    
    private void performingTrick(PlayerReal pr)
    {
        pr.setPerformingTrick(false);
        
        Scanner sc = new Scanner(System.in);
        String choice = "";
        do
        {
            System.out.println("Réaliser ce trick ? O / N");
            choice = sc.nextLine().toLowerCase();

            if (!choice.equals("o") && !choice.equals("n"))
                System.out.println("Merci d'entrer O ou N");

        } while (!choice.equals("o") && !choice.equals("n"));

        pr.setPerformTrick(choice.equals("o"));
        pr.setPerformTrickChosen(true);
    }
    
    private void turningOverCard(PlayerReal pr)
    {
        pr.setNeedToTurn(false);
        
        System.out.println("Choisis une carte à retourner");
        pr.showHand(true);
        Scanner sc = new Scanner(System.in);
        String choice;
        do
        {
            choice = sc.nextLine();
            if (!choice.equals("0") && !choice.equals("1"))
                System.out.println("Choisis une carte à retourner");
        } while (!choice.equals("0") && !choice.equals("1"));
        
        pr.setCardToBeTurned(Integer.parseInt(choice));
        pr.setCardTurned(true);
    }
    
    private void discardingCard(PlayerReal pr)
    {
        pr.setDiscardingCard(false);
        
        System.out.println("Choisissez une carte à remettre au milieu");
        pr.showHand(true);
        Scanner sc = new Scanner(System.in);
        String choice;

        do
        {
            choice = sc.nextLine();
            if (!choice.equals("0") && !choice.equals("1") && !choice.equals("2"))
                System.out.println("Choisissez une carte à remettre au milieu");
        } while (!choice.equals("0") && !choice.equals("1") && !choice.equals("2"));
        
        pr.setCardToBeDiscarded(Integer.parseInt(choice));
        pr.setCardDiscarded(true);
    }
    
    private boolean tryParse(String str)
    {
        try 
        {  
            Integer.parseInt(str);  
            return true;  
        } 
        catch (NumberFormatException e) 
        {  
            return false;  
        }  
    }
}
