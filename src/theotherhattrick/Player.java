/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theotherhattrick;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author v1nkey
 */
public abstract class Player {
    private String name;
    private List<Trick> performedTricks;
    private List<Prop> hand;
    private int score;

    public Player(String name) 
    {
        this.name = name;
        performedTricks = new ArrayList();
        hand = new ArrayList();
        score = 0;
    }
    
    public abstract void play(Game game);
    
    public void updateScore() { score = countPoints(); }
    
    public int countPoints()
    {
        int nbPoints = 0;
        for (Trick t : performedTricks)
            nbPoints += t.getNbPoints();
        
        return nbPoints;
    }
    
    public void exchangeCard(int ownCardIndex, Player otherPlayer, int otherPlayerCardIndex)
    {
        Card cardToGive = hand.remove(ownCardIndex);
        Card cardToGet = otherPlayer.getHand().remove(otherPlayerCardIndex);
        
        hand.add((Prop)cardToGet);
        otherPlayer.getHand().add((Prop)cardToGive);
    }
    
    public abstract boolean choseTrick(Trick t);
    
    public Card discardCard() 
    { 
        System.out.println("Choisissez une carte à remettre au milieu");
        showHand(true);
        Scanner sc = new Scanner(System.in);
        String choice;

        do
        {
            choice = sc.nextLine();
            if (!choice.equals("0") && !choice.equals("1") && !choice.equals("2"))
                System.out.println("Choisissez une carte à remettre au milieu");
        } while (!choice.equals("0") && !choice.equals("1") && !choice.equals("2"));
        
        return hand.remove(Integer.parseInt(choice));
    }
    
    public void turnOverCard()
    {
        if (hand.get(0).isVisible())
        {
            if (!hand.get(1).isVisible())
                hand.get(1).setVisible(true);
        }
        else if (hand.get(1).isVisible())
        {
            if (!hand.get(0).isVisible())
                hand.get(0).setVisible(true);
        }
    }
    
    public void performedTrickRoutine()
    {
        Trick t = Game.getInstance().getTrickPile().pop();
        performedTricks.add(t);
        System.out.println("Ta-Dah !");
        hand.add(Game.getInstance().getSeventhProp());
        
        for (Card c : hand)
            c.setVisible(false);
                
        Prop newSevethProp = (Prop)discardCard();
        
        Game.getInstance().setSeventhProp(newSevethProp);
    }
    
    public String seeCard(Card c) { return c.getName(); }
    
    public void showHand(boolean allCardsVisible)
    {
        int i = 0;
        if (allCardsVisible)
            for (Card c : hand)
                System.out.println(i++ + " - " + c.getName());
        else
            for (Card c : hand)
                System.out.println(i++ + " - " + c);
    }
    
    public void setPenalty() { score -= 3; }
    
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Player)
        {
            Player p = (Player)o;
            if (this.name.equalsIgnoreCase(p.getName()))
                return true;
            else
                return false;
        }
        else
            return false;
    }
    
    @Override
    public int hashCode() { return name.length(); }
    
    public int compareTo(Object o) 
    {
        Player p = (Player)o;
        return Integer.compare(this.score, p.score);
    }
    
    @Override
    public String toString() { return (name + " : " + score + " pts"); }
    
    public String getName() { return name; }

    public List<Prop> getHand() { return hand; }

    public List<Trick> getPerformedTricks() { return performedTricks; }
    
    public int getScore() { return score; }
}
