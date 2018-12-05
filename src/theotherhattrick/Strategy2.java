package theotherhattrick;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Strategy2 implements PlayStrategy {
    public void play(Game game) {
        game.drawTrick();
        Trick currentTrick = game.getTrickPile().peek();
        System.out.println("Trick : " + currentTrick + " : " + currentTrick.getNbPoints() + " pts");
        System.out.println("******************");
    }
}