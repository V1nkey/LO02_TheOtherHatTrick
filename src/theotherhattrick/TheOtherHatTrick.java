/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theotherhattrick;

import java.util.ArrayList;
import java.util.List;
import views.ConsoleView;
import views.GraphicView;

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
        for (int i = 0; i < 1; i++)
        {
            int age = (int)(Math.random()*40) + 10;
            System.out.println(i + " " + age);
            realPlayers.add(new PlayerReal("Player " + i, age));
        }
        int k = 0;
        game.initGame(realPlayers);
        
//        GraphicView gv = new GraphicView();
        ConsoleView cv = new ConsoleView();
//        do
//        {
//            Player currentPlayer = game.getPlayers().get(k%game.getPlayers().size());
//            game.showBoard();
//            game.playTurn(currentPlayer);
//            k++;
//        } while (!game.isEnded());
//        
//        game.endGame();
    }
}
