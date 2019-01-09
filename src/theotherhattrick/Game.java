/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theotherhattrick;

import java.io.File;
import java.util.*;
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
    private LinkedList<Trick> trickPile;
    private List<Player> players;
    private int tryOnLastTrick;
    
    private Game() 
    {
        seventhProp = null;
        trickDeck = null;
        propDeck = null;
        trickPile = new LinkedList();
        players = new ArrayList();
        tryOnLastTrick = 0;
    }
    
    public static Game getInstance()
    {
        if (game == null)
            game = new Game();
        
        return game;
    }
    
    public void initGame(List<PlayerReal> physicalPlayers)
    {
        JFileChooser fileChooser = new JFileChooser(new File(".."));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Card files", "csv"));
        File cardsFile = null;

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            cardsFile = fileChooser.getSelectedFile();
        else
            return;
        
        List<Object> objCards;
        CardFactory cf = CardFactory.getInstance();
        objCards = cf.parse(cardsFile.getAbsolutePath());
        //objCards = cf.parse("../cards.csv");
        createDecks(objCards);
        
        trickDeck.shuffleButOne("The Other Hat Trick");
        propDeck.shuffle();

        if (!physicalPlayers.isEmpty())
            physicalPlayers.sort((PlayerReal p1, PlayerReal p2) -> p1.getAge() < p2.getAge() ? -1 : 1);

        for (PlayerReal pr : physicalPlayers)
            players.add(pr);

        createBotPlayers(3 - physicalPlayers.size());

        deal();
    }
    
    public void playTurn(Player p)
    {
        p.play(this);

        if (!trickPile.isEmpty() && trickPile.peek().equals("The Other Hat Trick"))
            tryOnLastTrick++;
    }
    
    public boolean isEnded()
    {
        if (trickDeck.getCards().isEmpty())
        {
            if (!trickPile.contains(new Card("The Other Hat Trick")))
                return true;
            
            else if (tryOnLastTrick == players.size())
                return true;
            
            else 
                return false;
        }
        else 
            return false;
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
    
    public void drawTrick()
    {
        Trick t = (Trick)trickDeck.draw();
        t.setVisible(true);
        trickPile.push(t);
    }
    
    public void endGame()
    {
        for (Player p : players)
                p.countPoints();

        if (trickPile.contains(new Card("The Other Hat Trick")))
            for (Player p : players)
            {
                if (p.getHand().contains(new Card("The Hat")))
                    p.setPenalty();
                
                if (p.getHand().contains(new Card("The Other Rabbit")))
                    p.setPenalty();
            }
        players.sort((Player p1, Player p2) -> p1.getScore() < p2.getScore() ? 1 : -1);
        showFinalRanking();
    }
    
    private void showFinalRanking()
    {
        int i = 1;
        for (Player p : players)
            System.out.println(i++ + " " + p.toString());
    }
    
    public void showBoard()
    {
        if(trickPile.isEmpty())
            drawTrick();
        
        System.out.println("\n******************");
        for (Player p : players)
        {
            p.updateScore();
            System.out.println(p);
            for (Prop pr : p.getHand())
                System.out.println(pr.toString());
            
            System.out.println("******************");
        }
        System.out.println(seventhProp);
        System.out.println("******************");
        System.out.println(trickPile.peek());
        System.out.println("******************\n");
    }
    
    public void showOtherPlayers(Player currentPlayer)
    {
        int i = 0;
        for (Player p : players)
        {
            if (!p.equals(currentPlayer))
                System.out.println(i + " : " + p.toString());
            i++;
        }
    }

    public Prop getSeventhProp() { return seventhProp; }
    
    public void setSeventhProp(Prop p) { seventhProp = p; }
    
    public List<Player> getPlayers() { return players; }

    public LinkedList<Trick> getTrickPile() { return trickPile; }
    
    public Deck getTrickDeck() { return trickDeck; }

}
