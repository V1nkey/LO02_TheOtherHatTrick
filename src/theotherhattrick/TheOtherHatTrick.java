/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theotherhattrick;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author v1nkey
 */
public class TheOtherHatTrick {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Game game = Game.getInstance();

        MainWindow mainWindow = new MainWindow(game);

        JFrame frame = new JFrame("MainWindow");
        frame.setSize(700, 500);
        frame.setContentPane(mainWindow.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        List<PlayerReal> realPlayers = new ArrayList();
        for (int i = 0; i < 2; i++) {
            int age = (int) (Math.random() * 40) + 10;
            System.out.println(i + " " + age);
            realPlayers.add(new PlayerReal("Player " + i, age));
        }
        int k = 0;
        game.initGame(realPlayers);

        do {
            Player currentPlayer = game.getPlayers().get(k % game.getPlayers().size());
            game.showBoard();
            mainWindow.refreshView();
            game.playTurn(currentPlayer);
            k++;
            mainWindow.refreshView();
        } while (!game.isEnded());

        game.endGame();
    }
}
