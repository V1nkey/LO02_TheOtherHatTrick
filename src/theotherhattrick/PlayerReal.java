/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theotherhattrick;

import java.util.Scanner;

/**
 *
 * @author v1nkey
 */
public class PlayerReal extends Player implements Comparable {
    private int age;
    
    //Flags for the console view
    private boolean chosingTrick;
    private boolean trickChosen;
    private boolean trickChoice;
    
    private boolean exchangingCard;
    private boolean ownCardChosen;
    private boolean otherCardChosen;
    private int ownCardIndex;
    private Player playerToExchangeWith;
    private int otherCardIndex;
    
    private boolean performingTrick;
    
    private boolean needToTurn;
    private boolean cardTurned;
    private int cardToBeTurned;
    
    private boolean discardingCard;
    private boolean cardDiscarded;
    private int cardToBeDiscarded;
    
    //ENLEVER LES BOUCLES ET LES VARIABLES SI CA MARCHE AVEC LA VUE GRAPHIQUE
    
    public PlayerReal(String name) 
    {
        super(name);
        age = 0;
        
        trickChosen = false;
        trickChoice = false;
        exchangingCard = false;
        ownCardChosen = false;
        otherCardChosen = false;
        needToTurn = false;
        cardTurned = false;
        discardingCard = false;
    }
    
    public PlayerReal(String name, int age) 
    {
        super(name);
        this.age = age;
    }
    
    @Override
    public boolean choseTrick(Trick t)
    {
        chosingTrick = true;
        setChanged();
        notifyObservers();
        
//        while (!trickChosen);
        
        trickChosen = false;
        setChanged();
        return trickChoice;
    }
    
    @Override
    public void exchangeCard() 
    { 
        exchangingCard = true;
        setChanged();
        notifyObservers();
        
        selectCardToGive();
        selectCardToGet();
        super.exchangeCard(ownCardIndex, playerToExchangeWith, otherCardIndex);
        setChanged();
    }
    
    private void selectCardToGive()
    {
        while (!ownCardChosen);
        ownCardChosen = false;
    }
    
    private void selectCardToGet()
    {
        while (!otherCardChosen);
        otherCardChosen = false;
    }
    
    @Override
    public boolean doTrick(Trick t)
    {
        setPerformingTrick(true);
        setChanged();
        notifyObservers();
        
        if (!isTrickAlreadyPerformed())
        {
            while(!isPerformTrickChosen());
            setPerformTrickChosen(false);
        }
        
        setTrickAlreadyPerformed(true);
        setChanged();
        
        boolean willBeDone = isPerformTrick();
        setPerformTrick(willBeDone);
//        setChanged();
        
        return willBeDone;
    }
    
    @Override
    public void turnOverCard()
    {
        if (!super.getHand().get(0).isVisible() && !super.getHand().get(1).isVisible())
        {
            needToTurn = true;
            setChanged();
            notifyObservers();
            
            
            
            super.getHand().get(cardToBeTurned).setVisible(true);
        }
        else
            super.turnOverCardNoChoice();
    }
    
    @Override
    public Card discardCard() 
    { 
        discardingCard = true;
        setChanged();
        notifyObservers();
        
        return super.getHand().remove(cardToBeDiscarded);
    }
    
    @Override
    public int compareTo(Object o) 
    {
        PlayerReal pr = (PlayerReal)o;
        return Integer.compare(this.age, pr.age);
    }
    
    public int getAge() { return age; }
    
    public boolean isChosingTrick() { return chosingTrick; }
    public void setChosingTrick(boolean chosingTrick) { this.chosingTrick = chosingTrick; }

    public boolean isTrickChosen() { return trickChosen; }
    public void setTrickChosen(boolean hasChosenTrick) { this.trickChosen = hasChosenTrick; }
    public void setTrickChoice(boolean choiceTrick) { this.trickChoice = choiceTrick; }

    public boolean isExchangingCard() { return exchangingCard; }
    public void setExchangingCard(boolean exchangingCard) { this.exchangingCard = exchangingCard; }

    public boolean isOwnCardChosen() { return ownCardChosen; }
    public void setOwnCardChosen(boolean ownCardChosen) { this.ownCardChosen = ownCardChosen; }
    public void setOwnCardIndex(int ownCardIndex) { this.ownCardIndex = ownCardIndex; }

    public boolean isOtherCardChosen() { return otherCardChosen; }
    public void setOtherCardChosen(boolean otherCardChosen) { this.otherCardChosen = otherCardChosen; }
    public void setOtherCardIndex(int otherCardIndex) { this.otherCardIndex = otherCardIndex; }

    public void setPlayerToExchangeWith(Player playerToExchangeWith) { this.playerToExchangeWith = playerToExchangeWith; }

    public boolean isPerformingTrick() { return performingTrick; }
    public void setPerformingTrick(boolean performingTrick) { this.performingTrick = performingTrick; }
    
    public boolean isNeedToTurn() { return needToTurn; }
    public void setNeedToTurn(boolean needToTurn) { this.needToTurn = needToTurn; }
    public void setCardTurned(boolean cardTurned) { this.cardTurned = cardTurned; }
    public void setCardToBeTurned(int cardToBeTurned) { this.cardToBeTurned = cardToBeTurned; }

    public boolean isDiscardingCard() { return discardingCard; }
    public void setDiscardingCard(boolean discardingCard) { this.discardingCard = discardingCard; }
    public void setCardToBeDiscarded(int cardToBeDiscarded) { this.cardToBeDiscarded = cardToBeDiscarded; }
    public void setCardDiscarded(boolean cardDiscarded) { this.cardDiscarded = cardDiscarded; }
}
