/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theotherhattrick;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author v1nkey
 */
public class Player {
    protected String name;
    protected List<Trick> performedTricks;
    protected List<Prop> hand;

    public Player(String name) 
    {
        this.name = name;
        performedTricks = new ArrayList();
        hand = new ArrayList();
    }
    
    public int countPoints()
    {
        int playerPoints = 0;
        
        for (Trick t : performedTricks)
            playerPoints += t.getNbPoints();
        
        return playerPoints;
    }
    
    public void exchangeCard(Card card)
    {
        
    }
    
    public void doTrick(Trick t)
    {
        
    }
    
    public String getName() { return name; }

    public List<Prop> getHand() { return hand; }
}
