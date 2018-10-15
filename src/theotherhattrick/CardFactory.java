/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theotherhattrick;

import java.io.BufferedReader;
import java.io.File;
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
    private static CardFactory instance;
    
    private CardFactory() {}
    
    public static CardFactory getInstance()
    {
        if (instance == null)
            return new CardFactory();
        
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
                        Map<Prop, Prop> combination = new HashMap();
                        if (items.length == 5)
                        {
                            combination.put(new Prop(items[3]), new Prop(items[4]));
                            if (items.length == 7)
                            {
                                combination.put(new Prop(items[5]), new Prop(items[6]));
                                if (items.length == 9)
                                {
                                    combination.put(new Prop(items[7]), new Prop(items[8]));
                                    if (items.length == 11)
                                        combination.put(new Prop(items[9]), new Prop(items[10]));
                                }
                            }
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
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(CardFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cards;
    }
    
}
