package theotherhattrick;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StrategyEasy implements PlayStrategy {
    public void play(Game game, PlayerIA player) {
        System.out.println("******************");
        System.out.println("Au " + player + " de jouer");
        System.out.println("******************");

        if (game.getTrickPile().empty() && !game.getTrickDeck().isEmpty())
            game.drawTrick();

        Trick currentTrick = game.getTrickPile().peek();
        System.out.println("Trick : " + currentTrick + " : " + currentTrick.getNbPoints() + " pts");
        System.out.println("******************");

        if (!currentTrick.equals(new Card("The Other Hat Trick"))) {
            if (currentTrick.isDoable(player.getHand()) || ((!currentTrick.getCombination().get(0).contains(player.getHand().get(0))) && (!currentTrick.getCombination().get(1).contains(player.getHand().get(0)))) && (!currentTrick.getCombination().get(0).contains(player.getHand().get(1))) && (!currentTrick.getCombination().get(1).contains(player.getHand().get(1)))) { // si il ne peut pas du tout, changer de trick
                game.drawTrick();
                currentTrick = game.getTrickPile().peek();
                System.out.println("Trick : " + currentTrick + " : " + currentTrick.getNbPoints() + " pts");
                System.out.println("******************");
            }
        }

        Random random = new Random();
        List<Player> otherPlayers = new ArrayList<Player>(game.getPlayers());
        otherPlayers.remove(player);

        if (currentTrick.getCombination().get(0).contains(player.getHand().get(0)) || currentTrick.getCombination().get(1).contains(player.getHand().get(0))) {
            // changer la carte #1
            player.exchangeCard(1, otherPlayers.get(random.nextInt(2)), random.nextInt(2));
        } else {
            // changer la carte #0
            player.exchangeCard(0, otherPlayers.get(random.nextInt(2)), random.nextInt(2));
        }

        if (currentTrick.isDoable(game.getCurrentPlayer().getHand())) {
            game.getCurrentPlayer().performedTrickRoutine();
        } else {
            System.out.println("Trick raté");
            System.out.println("******************");
            player.turnOverCard();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException exception) {}
    }
}