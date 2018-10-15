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
        CardFactory cf = CardFactory.getInstance();
        List<Object> cards = new ArrayList();
        
        cards = cf.parse("cards.csv");
        for (Object c : cards)
            System.out.println(c.toString());
    }
    
}
