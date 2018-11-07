/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theotherhattrick;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author v1nkey
 */
public class Game {
    private static Game game = null;
    private Prop seventhProp;
    private Deck trickDeck;
    private Deck propDeck;
    private Stack<Trick> trickPile;
    private List<Player> players;
    
    private Game() 
    {
        seventhProp = null;
        trickDeck = null;
        propDeck = null;
        trickPile = new Stack();
        players = new ArrayList();
    }
    
    public static Game getInstance()
    {
        if (game == null)
            game = new Game();
        
        return game;
    }
    
    public void initGame(List<PlayerReal> physicalPlayers)
    {
//        JFileChooser fileChooser = new JFileChooser(new File(".."));
//        fileChooser.setFileFilter(new FileNameExtensionFilter("Card files", "csv"));
//        File cardsFile = null;
//        
//        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
//            cardsFile = fileChooser.getSelectedFile();
//        else 
//            return;
        
        List<Object> objCards = new ArrayList();
        CardFactory cf = CardFactory.getInstance();
//        objCards = cf.parse(cardsFile.getAbsolutePath());
        objCards = cf.parse("../cards.csv");
        createDecks(objCards);
        
        trickDeck.shuffleButOne("The Other Hat Trick");
        propDeck.shuffle();
        
        Collections.sort(physicalPlayers);
        for (PlayerReal pr : physicalPlayers)
            players.add(pr);
        
        createBotPlayers(3 - physicalPlayers.size());
        
        deal();
        drawTrick();
        showBoard();
    }
    
    public void playTurn(Player p)
    {
        Trick currentTrick = trickPile.peek();
        
        if (!p.choseTrick(currentTrick))
        {
            drawTrick();
            currentTrick = trickPile.peek();
        }
        
        if (currentTrick.isDoable(p.getHand()))
        {
            if (p.doTrick(currentTrick))
            {   
                p.getPerformedTricks().add(currentTrick);
                System.out.println("Ta-Dah !");
                p.getHand().add(seventhProp);
                System.out.println("Choisissez une carte à remettre au milieu");
                p.showHand();
                Scanner sc = new Scanner(System.in);
                String choice;
                
                do
                {
                    choice = sc.nextLine();
                    if (choice != "0" && choice != "1" && choice != "2")
                        System.out.println("Choisissez une carte à remettre au milieu");
                } while (choice != "0" && choice != "1" && choice != "2");
                
                seventhProp = p.getHand().get(Integer.parseInt(choice));
            }
            else
            {
                System.out.println("Choisissez une carte à retourner");
                p.showHand();
                
                if (p.getHand().get(0).isVisible())
                {
                    if (!p.getHand().get(1).isVisible())
                        p.getHand().get(1).flipCard();
                }
                else if (p.getHand().get(1).isVisible())
                {
                    if (!p.getHand().get(0).isVisible())
                        p.getHand().get(1).flipCard();
                }
                else
                {
                    Scanner sc = new Scanner(System.in);
                    String choice;
                    do
                    {
                        choice = sc.nextLine();
                        if (choice != "0" && choice != "1")
                            System.out.println("Choisissez une carte à retourner");
                    } while (choice != "0" && choice != "1");
                    p.getHand().get(Integer.parseInt(choice)).flipCard();
                }
            }
        }
        else
        {
            System.out.println("Choisissez une carte à retourner");
            p.showHand();

            if (p.getHand().get(0).isVisible())
            {
                if (!p.getHand().get(1).isVisible())
                    p.getHand().get(1).flipCard();
            }
            else if (p.getHand().get(1).isVisible())
            {
                if (!p.getHand().get(0).isVisible())
                    p.getHand().get(1).flipCard();
            }
            else
            {
                Scanner sc = new Scanner(System.in);
                String choice;
                do
                {
                    choice = sc.nextLine();
                    if (choice != "0" && choice != "1")
                        System.out.println("Choisissez une carte à retourner");
                } while (choice != "0" && choice != "1");
                p.getHand().get(Integer.parseInt(choice)).flipCard();
            }
        }
    }
    
    public boolean isEnded()
    {
        if (trickDeck.getCards().isEmpty())
        {
            if (!trickPile.contains("The Other Hat Trick"))
                return true;
        }
    }
    
    private void createDecks(List<Object> objCards)
    {
        List<Card> trickCards = new ArrayList();
        List<Card> propCards = new ArrayList();
        
        for (Object o : objCards)
        {
            if (o.getClass() == Trick.class)
                trickCards.add((Card)o);
            
            else if (o.getClass() == Prop.class)
                propCards.add((Card)o);
        }
        
        trickDeck = new Deck(trickCards);
        propDeck = new Deck(propCards);
    }
    
    private void createBotPlayers(int nbBots)
    {
        for (int i = 0; i < nbBots; i++)
            players.add(new PlayerIA("Bot " + i));
    }
    
    private void deal()
    {
        for (int i = 0; i < 2 * players.size(); i++)
            players.get(i % players.size()).getHand().add((Prop)propDeck.draw());
        
        seventhProp = (Prop)propDeck.draw();
    }
    
    private void drawTrick()
    {
        Trick t = (Trick)trickDeck.draw();
        t.flipCard();
        trickPile.push(t);
    }
    
    public void showBoard()
    {
        for (Player p : players)
        {
            System.out.println(p.getName());
            for (Prop pr : p.getHand())
                System.out.println(pr.toString());
            
            System.out.println("******************");
        }
        System.out.println(seventhProp);
        System.out.println("******************");
        System.out.println(trickPile.peek());
        System.out.println("******************\n");
    }

    public Prop getSeventhProp() { return seventhProp; }
    
    public List<Player> getPlayers() { return players; }
}
