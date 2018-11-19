/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theotherhattrick;

/**
 *
 * @author v1nkey
 */
public class PlayerIA extends Player {
    private PlayStrategy strategy;

    public PlayerIA(String name) {
        super(name);
    }

    public void setStrategy(PlayStrategy strategy) { this.strategy = strategy; }
    
    @Override
    public void play(Game game) { strategy.play(game); }
    
    @Override
    public boolean choseTrick(Trick t)
    {
        return true;
    }
    
    @Override
    public void turnOverCard()
    {
        if (!super.getHand().get(0).isVisible() && !super.getHand().get(1).isVisible())
        {
            int choice = (int)(Math.random()) % 2;
            super.getHand().get(choice).setVisible(true);
        }
        else
            super.turnOverCard();
    }
}
