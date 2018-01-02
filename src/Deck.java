import java.util.Random;
import java.util.*;

class Deck
{   
   private final Stack<Card> theCards;
   //private final  Stack <Card> dealtCards;

   //constuctors
   Deck(boolean shouldShuffle)
   {
      theCards = new Stack<>();
      
      for (Card.Suit suit : Card.Suit.values())
      {
         for (Card.Rank rank : Card.Rank.values())
         {
            //Card card = new Card(rank, suit);
            theCards.push(new Card(rank, suit));
         }
      }
      if (shouldShuffle)
         Collections.shuffle(theCards);
   }
   //deal
   public Card dealCard()
   {
      if (theCards.size() > 0)
         return theCards.pop();
      return null;
   }
   //prob not necessary
   void returnCard(Card card)
   {
      if (card != null)
         theCards.push(card);

   }
   public int size()
   {
      return this.theCards.size();
   }
   //inspects card of deck position provided by user
   Card inspectCard(int k)
   {
      if (k >= 0 && k < theCards.size())
         return theCards.get(k);
      return null;
   }
   
}