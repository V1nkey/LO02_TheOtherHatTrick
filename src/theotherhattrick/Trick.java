/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theotherhattrick;

import java.util.Map;

/**
 *
 * @author v1nkey
 */
public class Trick extends Card 
{
    private int nbPoints;
    private Map<Prop, Prop> combination;

    public Trick(String name, int nbPoints, Map<Prop, Prop> combination) 
    {
        super(name);
        this.nbPoints = nbPoints;
        this.combination = combination;
    }

    public Trick(String name, int nbPoints, Map<Prop, Prop> combination, boolean visible) 
    {
        super(name, visible);
        this.nbPoints = nbPoints;
        this.combination = combination;
    }

    public int getNbPoints() { return nbPoints; }
    public Map<Prop, Prop> getCombination() { return combination; }
    
}
