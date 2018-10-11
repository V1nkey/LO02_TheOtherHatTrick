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
public class Game {
    private static Game game;
    private Prop seventhProp;
    
    private Game()
    {
        initGame();
    }
    
    public static Game getInstance()
    {
        if (game == null)
            return game = new Game();
        
            return game;
    }
    
    private void initGame()
    {
        
    }
}
