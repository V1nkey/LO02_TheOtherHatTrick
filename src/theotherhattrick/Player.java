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
public class Player {
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
    
    public int countPoints()
    {
        score = 0;
        for (Trick t : performedTricks)
            score += t.getNbPoints();
        
        return score;
    }
    
    public void exchangeCard(int cardIndex, Player p, int otherIndex)
    {
        Prop tmp = hand.remove(cardIndex);
        hand.add(p.getHand().remove(otherIndex));
        p.getHand().add(tmp);
    }
    
    public boolean choseTrick(Trick t)
    {
        Scanner sc = new Scanner(System.in);
        String choice;
        System.out.println("Trick : " + t);
        do
        {
            System.out.println("Choisir ce trick ? O / N");
            choice = sc.nextLine().toLowerCase();
            
            if (choice != "o" && choice != "n")
                System.out.println("Merci d'entrer O ou N");
            
        } while (choice != "o" && choice != "n");
        
        if (choice == "o")
            return true;
        
        return false;
    }
    
    public boolean doTrick(Trick t)
    {
        Scanner sc = new Scanner(System.in);
        String choice;
        System.out.println("Trick : " + t);
        do
        {
            System.out.println("Réaliser ce trick ? O / N");
            choice = sc.nextLine().toLowerCase();
            
            if (choice != "o" && choice != "n")
                System.out.println("Merci d'entrer O ou N");
            
        } while (choice != "o" && choice != "n");
        
        if (choice == "o")
            return true;
        
        return false;
    }
    
    public Card discardCard(int index)
    {
        return hand.remove(index);
    }
    
    public void showHand()
    {
        int i = 0;
        for (Card c : hand)
        {
            if(c.isVisible())
                System.out.println(i++ + " - " + c);
            
            else
            {
                c.flipCard();
                System.out.println(i++ + " - " + c);
                c.flipCard();
            }    
        }
    }
    
    public int compareTo(Object o) 
    {
        Player p = (Player)o;
        return Integer.compare(this.score, p.score);
    }
    
    public void setPenalty() { score -= 3; }
    
    public String getName() { return name; }

    public List<Prop> getHand() { return hand; }

    public List<Trick> getPerformedTricks() { return performedTricks; }
    
    public int getScore() { return score; }
}
