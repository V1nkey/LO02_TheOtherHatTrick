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
    //Variables
    private int nbPoints;
    private List<List<Prop>> combination;
    
    //Flags
    private boolean currentlyDoable;

    public Trick(String name, int nbPoints, List<List<Prop>> combination) 
    {
        super(name);
        this.nbPoints = nbPoints;
        this.combination = combination;
        
        currentlyDoable = false;
    }

    public Trick(String name, int nbPoints, List<List<Prop>> combination, boolean visible) 
    {
        super(name, visible);
        this.nbPoints = nbPoints;
        this.combination = combination;
        
        currentlyDoable = false;
    }
    
    public boolean isDoable(List<Prop> props)
    {
        currentlyDoable = (combination.get(0).contains(props.get(0)) && combination.get(1).contains(props.get(1))) || (combination.get(0).contains(props.get(1)) && combination.get(1).contains(props.get(0)));
        setChanged();
        notifyObservers();
        return currentlyDoable;
    }
    
    @Override
    public String toString() 
    {
        if (super.isVisible())
            return (super.getName() + " : " + nbPoints + " pts");
        else
            return "Vous ne pouvez pas voir cette carte";
    }

    public int getNbPoints() { return nbPoints; }
    public List<List<Prop>> getCombination() { return combination; }

    public boolean isCurrentlyDoable() { return currentlyDoable; } 
}
