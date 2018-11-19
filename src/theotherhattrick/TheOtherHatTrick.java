/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theotherhattrick;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author v1nkey
 */
public class TheOtherHatTrick {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Game game = Game.getInstance();
        
        List<PlayerReal> realPlayers = new ArrayList();
        for (int i = 0; i < 3; i++)
            realPlayers.add(new PlayerReal("Player " + i, 6-i));
        
        int k = 0;
        game.initGame(realPlayers);
        
        do
        {
            Player currentPlayer = game.getPlayers().get(k%game.getPlayers().size());
            game.playTurn(currentPlayer);
            game.showBoard();
            k++;
        } while (!game.isEnded());
    }
}
