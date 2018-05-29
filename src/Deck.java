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
            theCards.push(new Card(rank, suit));
         }
      }
      if (shouldShuffle)
      {
         Collections.shuffle(theCards);
      }
   }
   //deal
   public Card dealCard() throws OutOfCardsException
   {
      if (theCards.size() > 0)
         return theCards.pop();
      throw new OutOfCardsException("Deck is empty.");
   }

   //
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
   private Card inspectCard(int k)
   {
      if (k >= 0 && k < theCards.size())
         return theCards.get(k);
      return null;
   }
   
}

class OutOfCardsException extends Exception
{
   OutOfCardsException(String message){ super(message); }
}