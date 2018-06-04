import java.util.*;

@SuppressWarnings("unused")
class Deck
{   
   private final Stack<Card> theCards;

   //constructors
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
   Card dealCard() throws EmptyDeck
   {
      if (theCards.size() > 0)
         return theCards.pop();
      throw new EmptyDeck();
   }

   //
   void returnCard(Card card)
   {
      if (card != null)
         theCards.push(card);

   }

   int size()
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

   //for multi-hand to be added later
   void remove(Card card)
   {

   }
   
}

class EmptyDeck extends Exception
{
   EmptyDeck(){ super("Deck is empty."); }
}