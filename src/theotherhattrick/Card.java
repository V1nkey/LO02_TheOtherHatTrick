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
public class Card {
    private String name;
    private boolean visible;

    public Card(String name) 
    {
        this.name = name;
        visible = false;
    }

    public Card(String name, boolean visible) 
    {
        this.name = name;
        this.visible = visible;
    }
    
    public void flipCard() { this.visible = !this.visible; }

    @Override
    public String toString() 
    {
        if (visible)
            return name + "\t";
        
        else
            return "Vous ne pouvez pas voir cette carte";
    }
    
    public boolean equals(Object o)
    {
        if (o instanceof String)
        {
            if (this.name.equalsIgnoreCase((String)o))
                return true;
            else
                return false;
        }
        else if (o instanceof Card)
        {
            if (this.name.equalsIgnoreCase(((Card) o).getName()))
                return true;
            else
                return false;
        }
        else
            return false;
    }
    
    public String getName() { return name; }
    
    public boolean isVisible() { return visible; }
}
