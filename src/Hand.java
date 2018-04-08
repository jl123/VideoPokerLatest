import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

class Hand
{
   public static int MAX_CARDS = 5;
   private Card[] hand;
   public LinkedList sortedHand;

   boolean[] switchCard = new boolean[Hand.MAX_CARDS];
   private int numCards = 0;

   //evaluation variables
   private boolean straightFlush, quads, fullHouse, flush, straight, set;
   boolean twoPair, pair;
   private int highCard;
   private int lowCard;

   public static final int MAX_BET = 5;

   //ODDS
   public static final int royalFlushMaxWin = 800;
   public static final int royalFlushWin = 250;
   public static final int straightFlushWin = 50;
   public static final int quadsWin = 25;
   public static final int fullHouseWin = 9;
   public static final int flushWin = 6;
   public static final int straightWin = 4;
   public static final int setWin = 3;
   public static final int twoPairWin = 2;
   public static final int pairWin = 1;

   private static int winnings;

   public enum handVal{
      royalFlush, straightFlush, quads, fullHouse,
      flush, straight, set, twoPair, pair, loser;

      @Override
      public String toString()
      {
         switch (this)
         {
            case royalFlush:
               return "ROYAL FLUSH";
            case straightFlush:
               return "STRAIGHT FLUSH";
            case twoPair:
               return "TWO PAIR";
            default:
               return "LOSER";
         }
      }
   }

   //constructor
   Hand()
   {
      hand = new Card[MAX_CARDS];
      sortedHand = new LinkedList<Card>();
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

   boolean takeCard(Card card)
   {
      hand[numCards] = card;
      insert(sortedHand, card);
      numCards++;
      return true;
   }

   public boolean replaceCard(int cardNum, Card newCard)
   {
      if (cardNum >= 0 && cardNum < numCards)
      {
         remove(sortedHand, hand[cardNum]);
         hand[cardNum] = newCard;
         insert(sortedHand, newCard);
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
            Card tempCard = deck.dealCard();

            remove(sortedHand, this.inspectCard(k));
            this.replaceCard(k, tempCard);
            insert(sortedHand, tempCard);
         }
      }
   }

   @Override
   public String toString()
   {
      String retStr = new String();
      retStr = "Hand = ( ";
      for (int i = 0; i < numCards; i++)
      {
         retStr = retStr + hand[i].toString();
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
      return hand[k];
   }

   //helper methods for LinkedList
   static void insert(LinkedList<Card> cardList, Card card)
   {
      ListIterator<Card> iter;
      Card listX;
      if (card == null)
      {
         return;
      }

      for (iter = cardList.listIterator(); iter.hasNext(); )
      {
         listX = iter.next();
         if (card.compareTo(listX) < 0)
         {
            iter.previous(); // back up one
            break;
         }
      }
      iter.add(card);     //add copy to LinkedList+
   }

   //REMOVE
   static boolean remove(LinkedList<Card> cardList, Card card)
   {
      ListIterator<Card> iter;
      for (iter = cardList.listIterator(); iter.hasNext(); )
      {
         if (card.compareTo(iter.next()) == 0 )
         {
            iter.remove();
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

   private class HandEvaluator
   {
      private HandEvaluator(){}

      private int betReturn(handVal val, int bet)
      {
         switch(val)
         {
            case royalFlush:
               if (bet == MAX_BET)
               {
                  return royalFlushMaxWin * bet;
               }
               else
               {
                  return royalFlushWin * bet;
               }
            default: return 0;

         }
      }

      void insert(LinkedList<Card> cardList, Card card)
      {
         ListIterator<Card> iter;
         Card listX;
         if (card == null)
         {
            return;
         }

         for (iter = cardList.listIterator(); iter.hasNext(); )
         {
            listX = iter.next();
            if (card.compareTo(listX) < 0)
            {
               iter.previous(); // back up one
               break;
            }
         }
         iter.add(card);     //add copy to LinkedList+
      }

      //REMOVE
      boolean remove(LinkedList<Card> cardList, Card card)
      {
         ListIterator<Card> iter;
         for (iter = cardList.listIterator(); iter.hasNext(); )
         {
            if (card.compareTo(iter.next()) == 0 )
            {
               iter.remove();
               return true;   // we found, we removed, we return
            }
         }
         return false;
      }

      //REMOVEALL
      boolean removeAll(LinkedList<Card> cardList, Card card)
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
}