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
public interface PlayStrategy 
{
    public boolean choseTrick(Trick t);
    
    public boolean doTrick(Trick t);
    
    public void turnOverCard();
    
    public void exchangeCard();
    
    public Card discardCard();
}
