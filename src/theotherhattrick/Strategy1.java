package theotherhattrick;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Full Random Strategy
public class Strategy1 implements PlayStrategy 
{
//    public void play(Game game) {
//        Trick currentTrick = game.getTrickPile().peek();
//
//        Random random = new Random();
//        List<Player> otherPlayers = new ArrayList<Player>(game.getPlayers());
//        otherPlayers.remove(game.getCurrentPlayer());
//
//        if (currentTrick.getCombination().get(0).contains(game.getCurrentPlayer().getHand().get(0)) || currentTrick.getCombination().get(1).contains(game.getCurrentPlayer().getHand().get(0))) {
//            // changer la carte #1
//            game.getCurrentPlayer().exchangeCard(1, otherPlayers.get(random.nextInt(2)), random.nextInt(2));
//        } else {
//            // changer la carte #0
//            game.getCurrentPlayer().exchangeCard(0, otherPlayers.get(random.nextInt(2)), random.nextInt(2));
//        }
//
//        if (currentTrick.isDoable(game.getCurrentPlayer().getHand())) {
//            game.getCurrentPlayer().performedTrickRoutine();
//        } else {
//            System.out.println("Trick raté");
//            System.out.println("******************");
//            game.getCurrentPlayer().turnOverCard();
//        }
//    }
    private Player p;
    
    public Strategy1(Player p) { this.p = p; }
    
    @Override
    public boolean choseTrick(Trick t) 
    { 
        int nb = (int)(Math.random() * 100 % 2);
        return (nb == 1);
    }

    @Override
    public boolean doTrick(Trick t) { return true; }
    
    @Override
    public void turnOverCard() 
    {
        if (!p.getHand().get(0).isVisible() && !p.getHand().get(1).isVisible()) 
        {
            int cardToFlipIndex = (int) (Math.random() * 100 % 2);
            p.getHand().get(cardToFlipIndex).setVisible(true);
        } else
            p.turnOverCardNoChoice();
    }

    @Override
    public void exchangeCard() 
    {
        Game game = Game.getInstance();
        
        Random random = new Random();
        List<Player> otherPlayers = new ArrayList<Player>(game.getPlayers());
        otherPlayers.remove(p);
        
        int ownCardIndex = random.nextInt(2);
        Player otherPlayer = otherPlayers.get(random.nextInt(2));
        int otherPlayerCardIndex = random.nextInt(2);
        
        p.exchangeCard(ownCardIndex, otherPlayer, otherPlayerCardIndex);
    }

    @Override
    public Card discardCard() 
    {
        Random random = new Random();
        int cardToDiscardIndex = random.nextInt(3);
        
        return p.getHand().remove(cardToDiscardIndex);
    }
}
