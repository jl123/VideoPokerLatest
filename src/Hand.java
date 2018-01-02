import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

class Hand
{
   public static int MAX_CARDS = 5;
   private Card[] myCards;
   boolean[] switchCard = new boolean[Hand.MAX_CARDS];
   private int numCards = 0;

   //evaluation variables
   private boolean straightFlush, quads, fullHouse, flush, straight, set;
   boolean twoPair, pair;
   private int highCard;
   private int lowCard;

   //constructor
   Hand()
   {
      myCards = new Card[MAX_CARDS];
      resetHand();
   }

   void resetHand()
   {
      for (int i = 0; i < MAX_CARDS; i++)
      {
         myCards[i] = null;
      }
      numCards = 0;
   }

   boolean takeCard(Card card)
   {
      myCards[numCards] = card;
      numCards++;
      return true;
   }

   public boolean replaceCard(int cardNum, Card newCard)
   {
      if (cardNum >= 0 && cardNum < numCards)
      {
         myCards[cardNum] = newCard;
         return true;
      }
      return false;
   }

   public void draw(Deck deck)
   {
      for (int k = 0; k < MAX_CARDS; k++)
      {
         if (switchCard[k])
         {

            //remove(sortedHand, playerHand.inspectCard(k));
            this.replaceCard(k, deck.dealCard());
            //insert(sortedHand, tempCard);
         }
      }
   }
   public String toString()
   {
      String retStr = new String();
      retStr = "Hand = ( ";
      for (int i = 0; i < numCards; i++)
      {
         retStr = retStr + myCards[i].toString();
         if (i < numCards - 1)
            retStr = retStr + ", ";
      }

      retStr = retStr + " )";
      return retStr;
   }

   //accessor for numCards
   int getNumCards()
   {
      return numCards;
   }

   Card inspectCard(int k)
   {
      return myCards[k];
   }
}