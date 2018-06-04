import org.junit.Test;

import static org.junit.Assert.*;

public class DeckTest
{

   @Test
   public void test1()
   {
      Deck deck = new Deck(true);
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
         catch(EmptyDeck e)
         {
            System.out.println(e.getMessage());
         }
      }
   }
}