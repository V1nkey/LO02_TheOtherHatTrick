/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theotherhattrick;

import java.util.List;

/**
 *
 * @author v1nkey
 */
public class Trick extends Card 
{
    private int nbPoints;
    private List<Prop> combination;

    public Trick(int nbPoints, List<Prop> combination, String name) 
    {
        super(name);
        this.nbPoints = nbPoints;
        this.combination = combination;
    }

    public Trick(int nbPoints, List<Prop> combination, String name, boolean visible) 
    {
        super(name, visible);
        this.nbPoints = nbPoints;
        this.combination = combination;
    }

    public int getNbPoints() { return nbPoints; }
    public List<Prop> getCombination() { return combination; }
    
}
