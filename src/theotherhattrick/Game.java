/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theotherhattrick;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Stack;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author v1nkey
 */
public class Game extends Observable {
    //Variables
    private static Game game = null;
    private Prop seventhProp;
    private Deck trickDeck;
    private Deck propDeck;
    private Stack<Trick> trickPile;
    private List<Player> players;
    private int tryOnLastTrick;
    private Player currentPlayer;
    private Trick currentTrick;
    
    //Flags
    private boolean running;
    private boolean newTurn;
    private boolean newTrickPicked;
    
    private Game() 
    {
        seventhProp = null;
        trickDeck = null;
        propDeck = null;
        trickPile = new Stack();
        players = new ArrayList();
        tryOnLastTrick = 0;
        
        running = false;
        newTurn = false;
        newTrickPicked = false;
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
//        if (running)
//            return;
//        
//        running = true;
        
        List<Object> objCards;
        CardFactory cf = CardFactory.getInstance();
//        objCards = cf.parse(cardsFile.getAbsolutePath());
        objCards = cf.parse("../cards.csv");
        createDecks(objCards);
        
        trickDeck.shuffleButOne("The Other Hat Trick");
        propDeck.shuffle();
        
        physicalPlayers.sort((PlayerReal p1, PlayerReal p2) -> p1.getAge() < p2.getAge() ? -1 : 1);
        for (PlayerReal pr : physicalPlayers)
            players.add(pr);

        createBotPlayers(3 - physicalPlayers.size());

        deal();
        drawTrick();
    }
    
    public void playTurn(Player p)
    {
        this.currentPlayer = p;
        p.setTrickAlreadyPerformed(false);
        
        newTurn = true;
        currentTrick = trickPile.empty() ? null : trickPile.peek();
        setChanged();
        notifyObservers();
        
        newTurn = false;
        setChanged();
        
//        System.out.print("Test " + p);
        if (currentTrick == null)
        {
            drawTrick();
            currentTrick = trickPile.peek();
            System.out.println("Test : trick null " + currentTrick);
            newTrickPicked = true;
            setChanged();
            notifyObservers();
            
            newTrickPicked = false;
            setChanged();
        }
        else if (!currentTrick.equals(new Card("The Other Hat Trick")))
        {
            if(!p.choseTrick(currentTrick))
            {
                drawTrick();
                currentTrick = trickPile.peek();
                newTrickPicked = true;
                setChanged();
                notifyObservers();
                newTrickPicked = false;
                setChanged();
            }
        }
        else 
            tryOnLastTrick++;
        
        p.exchangeCard();
        
        if(currentTrick.isDoable(p.getHand()))
        {
            if (p.doTrick(currentTrick))
                p.performedTrickRoutine();
            
            else
                p.turnOverCard();
        }
        else
            p.turnOverCard();
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
        {
            PlayerIA ia = new PlayerIA("Bot " + i);
            ia.setStrategy(new Strategy1((ia)));
            players.add(ia);
        }
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
        {
            for (Player p : players)
            {
                if (p.getHand().contains(new Card("The Hat")))
                    p.setPenalty();
                
                if (p.getHand().contains(new Card("The Other Rabbit")))
                    p.setPenalty();
            }
        }
        players.sort((Player p1, Player p2) -> p1.getScore() < p2.getScore() ? 1 : -1);
    }
    
    public void showFinalRanking()
    {   
        int i = 1;
        for (Player p : players)
            System.out.println(i++ + " " + p.toString());
    }
    
    public void showBoard()
    {
        if(trickPile.empty())
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
    
    public List<Player> getOtherPlayers(Player p)
    {
        List<Player> otherPlayers = new ArrayList(players);
        otherPlayers.remove(p);
        return otherPlayers;
    }

    public Prop getSeventhProp() { return seventhProp; }
    
    public void setSeventhProp(Prop p) { seventhProp = p; }
    
    public List<Player> getPlayers() { return players; }

    public Stack<Trick> getTrickPile() { return trickPile; }
    
    public Deck getTrickDeck() { return trickDeck; }

    public Player getCurrentPlayer() { return this.currentPlayer; }
    
    public Trick getCurrentTrick() { return currentTrick; }

    public boolean isRunning() { return running; }   
    
    public boolean isNewTurn() { return newTurn; }

    public boolean isNewTrickPicked() { return newTrickPicked; }
}
