/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theotherhattrick;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author v1nkey
 */
public class Game {
    private static Game game;
    private Prop seventhProp;
    
    private Game()
    {
        initGame();
    }
    
    public static Game getInstance()
    {
        if (game == null)
            return game = new Game();
        
            return game;
    }
    
    public void initGame()
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
        
        List<Card> trickCards = new ArrayList();
        List<Card> propCards = new ArrayList();
        
        for (Object o : objCards)
        {
            if (o.getClass() == Trick.class)
                trickCards.add((Card)o);
            
            else if (o.getClass() == Prop.class)
                propCards.add((Card)o);
        }
        
        Deck trickDeck = new Deck(trickCards);
        Deck propDeck = new Deck(propCards);
        
        trickDeck.shuffleButOne("The Other Hat Trick");
        propDeck.shuffle();
        
    }
}
