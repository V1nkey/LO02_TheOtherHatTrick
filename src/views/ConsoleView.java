/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.util.Observable;
import java.util.Observer;
import theotherhattrick.Card;
import theotherhattrick.Game;
import theotherhattrick.Player;
import theotherhattrick.Prop;

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
            printBoard();
            
            System.out.println("******************");
            System.out.println(currentPlayer + " à toi de jouer");
            System.out.println("******************");
            
            game.playTurn(currentPlayer);
            k++;
        } while (!game.isEnded());
        game.endGame();
        
        int i = 0;
        for (Player p : game.getPlayers())
            System.out.println(++i + " " + p.toString());
    }
    
    //Ajouter des booleans de checkpoint dans Player, 
    //dans update, vérifier où on en est dans la méthode play et adapter le texte en fonction
    
    public void printBoard()
    {
        if(game.getTrickPile().empty())
            game.drawTrick();
        
        System.out.println("\n******************");
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
        System.out.println(game.getTrickPile().peek());
        System.out.println("******************\n");
    }
        
    @Override
    public void update(Observable o, Object arg) 
    { 
        
    }
}
