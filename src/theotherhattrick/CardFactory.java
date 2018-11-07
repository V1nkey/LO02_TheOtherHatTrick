/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theotherhattrick;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author v1nkey
 */
public class CardFactory implements Parsable {
    private static CardFactory instance = null;
    
    private CardFactory() {}
    
    public static CardFactory getInstance()
    {
        if (instance == null)
            instance = new CardFactory();
        
        return instance;
    }

    @Override
    public List<Object> parse(String fileName) 
    {
        BufferedReader br = null;
        try 
        {
            br = new BufferedReader(new FileReader(fileName));
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(CardFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String line = null;
        List<Object> cards = new ArrayList();
        
        try 
        {
            while ((line = br.readLine()) != null)
            {
                String[] items = line.split(";");
                switch (items[0].toLowerCase())
                {   
                    case "prop":
                        if (items[1] != null)
                            cards.add(new Prop(items[1]));
                        else
                            //Throw exception
                        break;
                    
                    case "trick":
                        List<List<Prop>> combination = new ArrayList();
                        List<Prop> prop1 = new ArrayList();
                        List<Prop> prop2 = new ArrayList();
                        
                        if (items.length == 5)
                        {
                            prop1.add(new Prop(items[3]));
                            prop2.add(new Prop(items[4]));
                            combination.add(prop1);
                            combination.add(prop2);
                        }
                         
                        if (items.length == 6)
                        {
                            prop1.add(new Prop(items[3]));
                            prop1.add(new Prop(items[4]));
                            prop2.add(new Prop(items[5]));
                            combination.add(prop1);
                            combination.add(prop2);
                        }
                         
                        if (items.length == 7)
                        {
                            prop1.add(new Prop(items[3]));
                            prop1.add(new Prop(items[4]));
                            prop2.add(new Prop(items[5]));
                            prop2.add(new Prop(items[6]));
                            combination.add(prop1);
                            combination.add(prop2);
                        }
                        
                        if (!combination.isEmpty())
                            cards.add(new Trick(items[1], Integer.parseInt(items[2]), combination));
                        else
                            //Throw exception
                        break;
                        
                    default:
                        //throw exception
                        break;
                } 
            }
            br.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(CardFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cards;
    }
    
}
