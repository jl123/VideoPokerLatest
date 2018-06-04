import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

class Hand
{
   static final int MAX_CARDS = 5;
   private final Card[] hand;
   final List<Card> sortedHand;

   final boolean[] switchCard = new boolean[Hand.MAX_CARDS];
   private int numCards = 0;

   //constructor
   Hand()
   {
      hand = new Card[MAX_CARDS];
      sortedHand = new LinkedList<>();
      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         switchCard[k] = true;
      }
      resetHand();
   }

   void resetHand()
   {
      for (int i = 0; i < MAX_CARDS; i++)
      {
         hand[i] = null;
      }
      numCards = 0;
   }

   void takeCard(Card card)
   {
      hand[numCards] = card;
      insert(sortedHand, card);
      numCards++;
   }

   void replaceCard(int cardNum, Card newCard)
   {
      if (cardNum >= 0 && cardNum < numCards)
      {
         remove(sortedHand, hand[cardNum]);
         hand[cardNum] = newCard;
         insert(sortedHand, newCard);
      }
   }

   void draw(Deck deck)
   {
      for (int k = 0; k < MAX_CARDS; k++)
      {
         if (switchCard[k])
         {
            Card tempCard = null;
            try
            {
               tempCard = deck.dealCard();
            } catch (EmptyDeck e)
            {
               System.out.println(e.getMessage());
            }
            remove(sortedHand, this.inspectCard(k));
            this.replaceCard(k, tempCard);
            insert(sortedHand, tempCard);
         }
      }
   }

   @Override
   public String toString()
   {
      StringBuilder retStr = new StringBuilder("Hand = ( ");
      for (int i = 0; i < numCards; i++)
      {
         retStr.append(hand[i].toString());
         if (i < numCards - 1)
            retStr.append(", ");
      }

      retStr.append(" )");
      return retStr.toString();
   }

   //accessor for numCards
   int getNumCards()
   {
      return numCards;
   }

   Card inspectCard(int k)
   {
      return hand[k];
   }

   //helper methods for LinkedList
   private static void insert(List<Card> cardList, Card card)
   {
      ListIterator<Card> iterator;
      Card listX;
      if (card == null)
      {
         return;
      }

      for (iterator = cardList.listIterator(); iterator.hasNext(); )
      {
         listX = iterator.next();
         if (card.compareTo(listX) < 0)
         {
            iterator.previous(); // back up one
            break;
         }
      }
      iterator.add(card);     //add copy to LinkedList+
   }

   //REMOVE
   private static boolean remove(List<Card> cardList, Card card)
   {
      ListIterator<Card> iterator;
      for (iterator = cardList.listIterator(); iterator.hasNext(); )
      {
         if (card.compareTo(iterator.next()) == 0)
         {
            iterator.remove();
            return true;   // we found, we removed, we return
         }
      }
      return false;
   }

   //REMOVEALL
   static boolean removeAll(LinkedList<Card> cardList, Card card)
   {
      boolean retFlag = false, shouldContinue = true;

      while (shouldContinue)
      {
         if (remove(cardList, card))
            retFlag = true;
         else shouldContinue = false;
      }
      return retFlag;
   }
}