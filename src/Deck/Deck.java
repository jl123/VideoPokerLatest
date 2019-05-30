package Deck;

import java.util.*;

@SuppressWarnings("unused")
public class Deck
{   
   private final Stack<Card> theDeck;

   //constructors
   public Deck()
   {
      theDeck = new Stack<>();

      for (Card.Suit suit : Card.Suit.values())
      {
         for (Card.Rank rank : Card.Rank.values())
         {
            theDeck.push(new Card(rank, suit));
         }
      }
      shuffle();
   }
   //for testing
   public Deck(boolean shouldShuffle)
   {
      theDeck = new Stack<>();
      
      for (Card.Suit suit : Card.Suit.values()) {
         for (Card.Rank rank : Card.Rank.values()) {
            theDeck.push(new Card(rank, suit));
         }
      }
      if (shouldShuffle)
      {
         shuffle();
      }
   }
   //deal
   public Card dealCard() throws EmptyDeckException
   {
      if (theDeck.size() > 0)
         return theDeck.pop();
      throw new EmptyDeckException();
   }

   //not used in this game
   void returnCard(Card card)
   {
      if (card != null)
         theDeck.push(card);

   }

   public int size()
   {
      return this.theDeck.size();
   }
   //inspects card of deck position provided by user
   private Card inspectCard(int k)
   {
      if (k >= 0 && k < theDeck.size())
      {
         return theDeck.get(k);
      }
      return null;
   }

   private void shuffle()
   {
      Collections.shuffle(theDeck);
   }

   //for multi-hand to be added later
   protected void remove(Card card)
   {
      for (Card cardFromDeck : theDeck)
      {
         if (cardFromDeck.compareTo(card) == 0)
         {
            theDeck.remove(cardFromDeck);
            return;
         }
      }
   }
   
}