/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theotherhattrick;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        System.out.println("******************");
        System.out.println("Au " + this + " de jouer");
        System.out.println("******************");

        if (game.getTrickPile().empty() && !game.getTrickDeck().isEmpty())
            game.drawTrick();

        Trick currentTrick = game.getTrickPile().peek();
        System.out.println("Trick : " + currentTrick + " : " + currentTrick.getNbPoints() + " pts");
        System.out.println("******************");

        if (!currentTrick.equals(new Card("The Other Hat Trick"))) {
            if (currentTrick.isDoable(this.getHand()) || ((!currentTrick.getCombination().get(0).contains(this.getHand().get(0))) && (!currentTrick.getCombination().get(1).contains(this.getHand().get(0)))) && (!currentTrick.getCombination().get(0).contains(this.getHand().get(1))) && (!currentTrick.getCombination().get(1).contains(this.getHand().get(1)))) { // si il ne peut pas du tout, changer de trick
                this.strategy = new Strategy2();
                this.strategy.play(game);
            }
            this.strategy = new Strategy1();
            this.strategy.play(game);

        } else {
            this.strategy = new Strategy1();
            this.strategy.play(game);
        }
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

        Random random = new Random();
        int newSeventhPropIndex = random.nextInt(3);

        Prop newSeventhProp = getHand().get(newSeventhPropIndex);
        getHand().remove(newSeventhPropIndex);

        Game.getInstance().setSeventhProp(newSeventhProp);
    }
}
