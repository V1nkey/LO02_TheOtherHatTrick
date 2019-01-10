/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theotherhattrick;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author v1nkey
 */
public abstract class Player extends Observable {
    //Variables
    private String name;
    private List<Trick> performedTricks;
    private List<Prop> hand;
    private int score;

    //Flags
    private boolean performTrick;
    private boolean performTrickChosen;
    private boolean trickAlreadyPerformed;

    public Player(String name)
    {
        this.name = name;
        performedTricks = new ArrayList();
        hand = new ArrayList();
        score = 0;
    }

    public void updateScore() { score = countPoints(); }

    public int countPoints()
    {
        int nbPoints = 0;
        for (Trick t : performedTricks)
            nbPoints += t.getNbPoints();

        return nbPoints;
    }

    public abstract void exchangeCard();

    public void exchangeCard(int ownCardIndex, Player otherPlayer, int otherPlayerCardIndex)
    {
        Card cardToGive = hand.remove(ownCardIndex);
        Card cardToGet = otherPlayer.getHand().remove(otherPlayerCardIndex);

        hand.add((Prop)cardToGet);
        otherPlayer.getHand().add((Prop)cardToGive);

        setChanged();
        notifyObservers();
    }

    public abstract boolean choseTrick(Trick t);

    public abstract boolean doTrick(Trick t);

    public abstract Card discardCard();
    //    {
//        System.out.println("Choisissez une carte à remettre au milieu");
//        showHand(true);
//        Scanner sc = new Scanner(System.in);
//        String choice;
//
//        do
//        {
//            choice = sc.nextLine();
//            if (!choice.equals("0") && !choice.equals("1") && !choice.equals("2"))
//                System.out.println("Choisissez une carte à remettre au milieu");
//        } while (!choice.equals("0") && !choice.equals("1") && !choice.equals("2"));
//        
//        return hand.remove(Integer.parseInt(choice));
//    }
    public abstract void turnOverCard();

    public void turnOverCardNoChoice()
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
//        System.out.println("Ta-Dah !");
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

    public void showHand(boolean allCardsVisible, int index)
    {
        if (allCardsVisible)
            for (Card c : hand)
                System.out.println(index++ + " - " + c.getName());
        else
            for (Card c : hand)
                System.out.println(index++ + " - " + c);
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

    @Override
    public String toString() { return (name + " : " + score + " pts"); }

    public String getName() { return name; }

    public List<Prop> getHand() { return hand; }

    public List<Trick> getPerformedTricks() { return performedTricks; }

    public int getScore() { return score; }

    public boolean isPerformTrick() { return performTrick; }
    public void setPerformTrick(boolean performTrick) { this.performTrick = performTrick; }

    public boolean isPerformTrickChosen() { return performTrickChosen; }
    public void setPerformTrickChosen(boolean performTrickChosen) { this.performTrickChosen = performTrickChosen; }

    public boolean isTrickAlreadyPerformed() { return trickAlreadyPerformed; }
    public void setTrickAlreadyPerformed(boolean trickAlreadyPerformed) { this.trickAlreadyPerformed = trickAlreadyPerformed; }
}
