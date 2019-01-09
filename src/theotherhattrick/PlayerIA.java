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

    public PlayerIA(String name) {
        super(name);
    }

    public void setStrategy(PlayStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void play(Game game) {
        this.strategy = new StrategyMedium();
        this.strategy.play(game, this);
    }

    @Override
    public boolean choseTrick(Trick t) {
        return true;
    }

    @Override
    public void turnOverCard() {
        if (!super.getHand().get(0).isVisible() && !super.getHand().get(1).isVisible()) {
            int choice = (int) (Math.random() * 100 % 2);
            super.getHand().get(choice).setVisible(true);
        } else
            super.turnOverCard();
    }

    @Override
    public void performedTrickRoutine() {
        Trick t = Game.getInstance().getTrickPile().pop();
        getPerformedTricks().add(t);
        System.out.println("Ta-Dah !");
        getHand().add(Game.getInstance().getSeventhProp());

        for (Card c : super.getHand())
            c.setVisible(false);

        Random random = new Random();
        int newSeventhPropIndex = random.nextInt(3);

        Prop newSeventhProp = getHand().get(newSeventhPropIndex);
        getHand().remove(newSeventhPropIndex);

        Game.getInstance().setSeventhProp(newSeventhProp);
    }
}
