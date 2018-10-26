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
public class PlayerReal extends Player implements Comparable {
    private int age;
    
    public PlayerReal(String name) {
        super(name);
        age = 0;
    }
    
    public PlayerReal(String name, int age) {
        super(name);
        this.age = age;
    }
    
    public void seeCard()
    {
        for (Card card : hand)
            System.out.println(card.toString());
    }

    public int getAge() { return age; }

    @Override
    public int compareTo(Object o) 
    {
        PlayerReal pr = (PlayerReal)o;
        return Integer.compare(this.age, pr.age);
    }
}
