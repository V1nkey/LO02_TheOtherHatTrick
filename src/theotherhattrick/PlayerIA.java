/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theotherhattrick;

import java.util.Random;

/**
 * @author v1nkey
 */
public class PlayerIA extends Player {
    private PlayStrategy strategy;

    public PlayerIA(String name) { super(name); }

    public PlayerIA(String name, PlayStrategy ps)
    {
        super(name);
        strategy = ps;
    }

    public void setStrategy(PlayStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public boolean choseTrick(Trick t) { return strategy.choseTrick(t); }

    @Override
    public void turnOverCard() { strategy.turnOverCard(); }

    @Override
    public void performedTrickRoutine() {
        Trick t = Game.getInstance().getTrickPile().pop();
        getPerformedTricks().add(t);
        updateScore();
        System.out.println("Ta-Dah !");
        getHand().add(Game.getInstance().getSeventhProp());

        for (Card c : getHand())
            c.setVisible(false);

        Random random = new Random();
        int newSeventhPropIndex = random.nextInt(3);

        Prop newSeventhProp = getHand().get(newSeventhPropIndex);
        getHand().remove(newSeventhPropIndex);

        Game.getInstance().setSeventhProp(newSeventhProp);
    }

    @Override
    public void exchangeCard() { strategy.exchangeCard(); }

    @Override
    public boolean doTrick(Trick t)
    {
        setTrickAlreadyPerformed(true);
        setChanged();

        boolean willBeDone = strategy.doTrick(t);
        setPerformTrick(willBeDone);
        setChanged();
        notifyObservers();

        return willBeDone;
    }

    @Override
    public Card discardCard() { return strategy.discardCard(); }

    public boolean hasStrategy() {
        if (this.strategy == null)
            return false;
        return true;
    }
}