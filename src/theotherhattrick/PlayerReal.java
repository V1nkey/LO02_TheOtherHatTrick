/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theotherhattrick;

import java.util.Scanner;

/**
 *
 * @author v1nkey
 */
public class PlayerReal extends Player implements Comparable {
    private int age;
    
    public PlayerReal(String name) 
    {
        super(name);
        age = 0;
    }
    
    public PlayerReal(String name, int age) 
    {
        super(name);
        this.age = age;
    }
    
//    public void play(Game game)
//    {
//        System.out.println("******************");
//        System.out.println(this + " à toi de jouer");
//        System.out.println("******************");
//        
//        if(game.getTrickPile().empty() && !game.getTrickDeck().isEmpty())
//            game.drawTrick();
//        
//        Trick currentTrick = game.getTrickPile().peek();
//        
//        if (!currentTrick.equals(new Card("The Other Hat Trick")))
//        {
//            if(!choseTrick(currentTrick))
//            {
//                game.drawTrick();
//                currentTrick = game.getTrickPile().peek();
//                System.out.println("Trick : " + currentTrick + " : " + currentTrick.getNbPoints() + " pts");
//                System.out.println("******************");
//            }
//        }
//        
//        exchangeCardDialogue();
//        
//        if(currentTrick.isDoable(super.getHand()))
//        {
//            if (doTrick(currentTrick))
//                performedTrickRoutine();
//            
//            else
//            {
//                System.out.println("Trick raté");
//                System.out.println("******************");
//                turnOverCard();
//            }
//        }
//        else
//        {
//            System.out.println("Trick raté");
//            System.out.println("******************");
//            turnOverCard();
//        }
//    }
    
    @Override
    public void turnOverCard()
    {
        if (!super.getHand().get(0).isVisible() && !super.getHand().get(1).isVisible())
        {
            System.out.println("Choisis une carte à retourner");
            showHand(true);
            Scanner sc = new Scanner(System.in);
            String choice;
            do
            {
                choice = sc.nextLine();
                if (!choice.equals("0") && !choice.equals("1"))
                    System.out.println("Choisis une carte à retourner");
            } while (!choice.equals("0") && !choice.equals("1"));
            super.getHand().get(Integer.parseInt(choice)).setVisible(true);
        }
        else
            super.turnOverCardNoChoice();
    }
    
    @Override
    public boolean choseTrick(Trick t)
    {
        Scanner sc = new Scanner(System.in);
        String choice;
        System.out.println("Trick : " + t + " : " + t.getNbPoints() + " pts");
       
        seeHand();
        
        do
        {
            System.out.println("Choisir ce trick ? O / N");
            choice = sc.nextLine().toLowerCase();
            
            if (!choice.equals("o") && !choice.equals("n"))
                System.out.println("Merci d'entrer O ou N");
            
        } while (!choice.equals("o") && !choice.equals("n"));
        
        if (choice.equals("o"))
            return true;
        
        return false;
    }
    
    @Override
    public boolean doTrick(Trick t)
    {
        Scanner sc = new Scanner(System.in);
        String choice;
        System.out.println("Trick : " + t);
        do
        {
            System.out.println("Réaliser ce trick ? O / N");
            choice = sc.nextLine().toLowerCase();
            
            if (!choice.equals("o") && !choice.equals("n"))
                System.out.println("Merci d'entrer O ou N");
            
        } while (!choice.equals("o") && !choice.equals("n"));
        
        if (choice.equals("o"))
            return true;
        
        return false;
    }
    
    @Override
    public void exchangeCard() { exchangeCardDialogue(); }
    
    private void exchangeCardDialogue()
    {
        // A faire : Choisir une de ses cartes
        System.out.println("Choisis une de tes cartes à échanger");
        seeHand();
        Scanner sc = new Scanner(System.in);
        String cardChoice;

        do
        {
            cardChoice = sc.nextLine();
            if (!cardChoice.equals("0") && !cardChoice.equals("1"))
                System.out.println("Choisis une de tes cartes à échanger");
        } while (!cardChoice.equals("0") && !cardChoice.equals("1"));
        
        int ownCardIndex = Integer.parseInt(cardChoice);
        
        // Choix du joueur
        System.out.println("Choisis un joueur avec qui échanger une carte");
        Game.getInstance().showOtherPlayers(this);
        
//        Scanner sc = new Scanner(System.in);
        String playerChoice;
        int currentPlayerIndex = Game.getInstance().getPlayers().indexOf(this);
        int playerChoiceIndex = 0;
        boolean continueDialgue = true;

        do
        {
            playerChoice = sc.nextLine();
            
            if (!playerChoice.equals("0") && !playerChoice.equals("1") && !playerChoice.equals("2"))
                System.out.println("Choisis un joueur avec qui échanger une carte");
            else
            {
                playerChoiceIndex = Integer.parseInt(playerChoice);
                if (playerChoiceIndex == currentPlayerIndex)
                {
                    System.out.println("Tu ne peux pas changer de carte avec toi-même");
                    System.out.println("Choisis un joueur avec qui échanger une carte");
                }
                else
                    continueDialgue = false;
            }
        } while (continueDialgue);
        
        Player otherPlayer = Game.getInstance().getPlayers().get(playerChoiceIndex);
        
        // Choix de la carte du joueur
        System.out.println("Choisis une carte à récupérer");
        otherPlayer.showHand(false);
        do
        {
            cardChoice = sc.nextLine();
            if (!cardChoice.equals("0") && !cardChoice.equals("1"))
                System.out.println("Choisis une carte à récupérer");
        } while (!cardChoice.equals("0") && !cardChoice.equals("1"));
        
        int otherPlayerCardIndex = Integer.parseInt(cardChoice);
        
        // Echange
        super.exchangeCard(ownCardIndex, otherPlayer, otherPlayerCardIndex);
    }
    
    @Override
    public Card discardCard() 
    { 
        System.out.println("Choisissez une carte à remettre au milieu");
        showHand(true);
        Scanner sc = new Scanner(System.in);
        String choice;

        do
        {
            choice = sc.nextLine();
            if (!choice.equals("0") && !choice.equals("1") && !choice.equals("2"))
                System.out.println("Choisissez une carte à remettre au milieu");
        } while (!choice.equals("0") && !choice.equals("1") && !choice.equals("2"));
        
        return super.getHand().remove(Integer.parseInt(choice));
    }
    
    public void seeHand()
    {
        Scanner sc = new Scanner(System.in);
        String choiceHand;
        do
        {
            System.out.println("Voir tes cartes ? O / N");
            choiceHand = sc.nextLine().toLowerCase();
            
            if (!choiceHand.equals("o") && !choiceHand.equals("n"))
                System.out.println("Merci d'entrer O ou N");
            
        } while (!choiceHand.equals("o") && !choiceHand.equals("n"));
        
        if (choiceHand.equals("o"))
        {
            int i = 0;
            for (Card c : super.getHand())
                System.out.println(i++ + " - " + seeCard(c));
        }
    }

    public int getAge() { return age; }

    @Override
    public int compareTo(Object o) 
    {
        PlayerReal pr = (PlayerReal)o;
        return Integer.compare(this.age, pr.age);
    }
}
