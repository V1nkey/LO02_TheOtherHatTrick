/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theotherhattrick;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author v1nkey
 */
public class Deck {
    private List<Card> cards;

    public Deck(List<Card> cards) { this.cards = cards; }
    
    public void shuffle() { Collections.shuffle(cards); }
    
    public void shuffleButOne(String cardName)
    {
        Card elementOnTop = null;
        int i = 0;
        
        while(i < cards.size() && cards.get(i).getName().compareToIgnoreCase(cardName) != 0)
            i++;
        
        if (i < cards.size())
        {
            elementOnTop = cards.remove(i);
            shuffle();
            cards.add(elementOnTop);
        }
        else
            shuffle();
    }
    
    public Card draw() 
    {
        try 
        {
            return cards.remove(0);
        } 
        catch (Exception e) 
        {
            return null;
        }
    }
    
    public boolean isEmpty() { return cards.isEmpty(); }

    public List<Card> getCards() { return cards; }
}
