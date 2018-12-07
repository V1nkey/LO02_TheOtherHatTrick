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
public interface PlayStrategy {
    public void play(Game game, PlayerIA player);
    
    /*
        TO DO :
        Creer une methode abstraite play() dans Player
        Redéfinir play() dans PlayerReal en utilisant les autres méthodes de Player
        Redéfinir play() dans PlayerIA en utilisant la Strategy
        Implémenter les différentes Strategy
        Clear le code dans Game
    */
}
