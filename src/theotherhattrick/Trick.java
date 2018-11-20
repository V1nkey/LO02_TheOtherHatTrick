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
    private List<List<Prop>> combination;

    public Trick(String name, int nbPoints, List<List<Prop>> combination) 
    {
        super(name);
        this.nbPoints = nbPoints;
        this.combination = combination;
    }

    public Trick(String name, int nbPoints, List<List<Prop>> combination, boolean visible) 
    {
        super(name, visible);
        this.nbPoints = nbPoints;
        this.combination = combination;
    }
    
    public boolean isDoable(List<Prop> props)
    {
        return ((combination.get(0).contains(props.get(0)) && combination.get(1).contains(props.get(1))) || (combination.get(0).contains(props.get(1)) && combination.get(1).contains(props.get(0))));
    }

    public int getNbPoints() { return nbPoints; }
    public List<List<Prop>> getCombination() { return combination; }
    
}
