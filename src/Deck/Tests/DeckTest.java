package Deck.Tests;
import Deck.Deck;
import Deck.EmptyDeckException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTest
{
   @Test
   public void test1()
   {
      Deck deck = new Deck(true);
      System.out.println("DECK SIZE: " +deck.size());
      assertEquals(deck.size(), 52);
   }

   @Test
   public void test2()
   {
      Deck deck = new Deck(false);
      for (int i = 0; i < 55; i++)
      {
         try
         {
            System.out.println(deck.dealCard());
         }
         catch(EmptyDeckException e)
         {
            System.out.println(e.getMessage());
         }
      }
   }

//   @Test
//   public void removeTest()
//   {
//      Deck deck = new Deck(false);
//      Card card = new Card(Card.Rank.eight, Card.Suit.spades);
//      deck.remove(card);
//      assertEquals(deck.size(), 51);
//   }
}