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
    private List<Player> players;
    
    private Game() 
    {
        seventhProp = null;
        trickDeck = null;
        propDeck = null;
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
        JFileChooser fileChooser = new JFileChooser(new File(".."));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Card files", "csv"));
        File cardsFile = null;
        
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            cardsFile = fileChooser.getSelectedFile();
        else 
            return;
        
        List<Object> objCards = new ArrayList();
        CardFactory cf = CardFactory.getInstance();
        objCards = cf.parse(cardsFile.getAbsolutePath());
        createDecks(objCards);
        
        trickDeck.shuffleButOne("The Other Hat Trick");
        propDeck.shuffle();
        
        Collections.sort(physicalPlayers);
        for (PlayerReal pr : physicalPlayers)
            players.add(pr);
        
        createBotPlayers(3 - physicalPlayers.size());
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
}
