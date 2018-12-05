package theotherhattrick;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Strategy1 implements PlayStrategy {
    public void play(Game game) {
        Trick currentTrick = game.getTrickPile().peek();

        Random random = new Random();
        List<Player> otherPlayers = new ArrayList<Player>(game.getPlayers());
        otherPlayers.remove(game.getCurrentPlayer());

        if (currentTrick.getCombination().get(0).contains(game.getCurrentPlayer().getHand().get(0)) || currentTrick.getCombination().get(1).contains(game.getCurrentPlayer().getHand().get(0))) {
            // changer la carte #1
            game.getCurrentPlayer().exchangeCard(1, otherPlayers.get(random.nextInt(2)), random.nextInt(2));
        } else {
            // changer la carte #0
            game.getCurrentPlayer().exchangeCard(0, otherPlayers.get(random.nextInt(2)), random.nextInt(2));
        }

        if (currentTrick.isDoable(game.getCurrentPlayer().getHand())) {
            game.getCurrentPlayer().performedTrickRoutine();
        } else {
            System.out.println("Trick raté");
            System.out.println("******************");
            game.getCurrentPlayer().turnOverCard();
        }
    }
}
