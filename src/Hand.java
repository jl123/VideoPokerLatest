import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

class Hand
{
   public static int MAX_CARDS = 5;
   private Card[] hand;
   public static LinkedList sortedHand;

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

      handVal getHandVal(Hand hand)
      {
         sortedHand = new LinkedList<Card>();
         //sortedHand.clear();
         sortHand(hand);

         if ( isRoyalFlush(sortedHand) )
            return handVal.royalFlush;
         if  ( isStraightFlush( sortedHand ) )
            return handVal.straightFlush;
         if  (isQuads( sortedHand ) )
            return handVal.quads;
         if ( isFullHouse( sortedHand ) )
            return handVal.fullHouse;
         if ( isFlush( sortedHand ) )
            return handVal.flush;
         if ( isStraight( sortedHand ) )
            return handVal.straight;
         if ( isSet( sortedHand ) )
            return handVal.set;
         if (isTwoPair( sortedHand ) )
            return handVal.twoPair;
         if (isPairJorHigher( sortedHand ) )
            return handVal.pair;
         return handVal.loser;

      }
      //incomplete
      public int amountWon(LinkedList<Card> hand, int bet)
      {
         return 1;
      }

      public void sortHand(Hand hand)
      {
         for (int i = 0; i < hand.getNumCards(); i++)
         {
            insert(sortedHand, hand.inspectCard(i));
         }
         sortedHand.toString();
      }

      public boolean isRoyalFlush(LinkedList<Card> hand)
      {
         return isFlush(hand) && isStraight(hand) &&
               hand.get(4).getRank() == Card.Rank.ace;

      }

      public boolean isStraightFlush(LinkedList<Card> hand)
      {
         return isFlush(hand) && isStraight(hand) &&
               hand.get(4).getRank() != Card.Rank.ace;
      }

      public boolean isQuads(LinkedList<Card> hand)
      {
         return ( hand.get(0).getRank() == hand.get(1).getRank() &&
               hand.get(0).getRank() == hand.get(2).getRank() &&
               hand.get(0).getRank() == hand.get(3).getRank() )
               ||
               ( hand.get(1).getRank() == hand.get(2).getRank() &&
                     hand.get(1).getRank() == hand.get(3).getRank() &&
                     hand.get(1).getRank() == hand.get(4).getRank() );
      }

      public boolean isFullHouse(LinkedList<Card> hand)
      {
         return isPair(hand) && isSet(hand);
      }

      public boolean isFlush(LinkedList<Card> hand)
      {
         Map<Card.Suit, List<Card>> collect = hand.stream().collect(Collectors.groupingBy(Card::getSuit));

         for (Card.Suit suit: collect.keySet())
         {
            if (collect.get(suit).size() == 5)
            {
               return true;
            }
         }
         return false;
      }

      boolean isStraight(LinkedList<Card> hand)
      {
         int firstCardVal = hand.get(0).getRank().value;

         return ( firstCardVal == hand.get(1).getRank().value - 1 &&
               firstCardVal == hand.get(2).getRank().value - 2 &&
               firstCardVal == hand.get(3).getRank().value - 3 &&
               firstCardVal == hand.get(4).getRank().value - 4 )
               ||
               ( hand.get(0).getRank() == Card.Rank.two &&
                     hand.get(1).getRank() == Card.Rank.three &&
                     hand.get(2).getRank() == Card.Rank.four &&
                     hand.get(3).getRank() == Card.Rank.five &&
                     hand.get(4).getRank() == Card.Rank.ace );
      }
      //incomplete--------get rid of .value?
      boolean isSet(LinkedList<Card> hand)
      {

         Map<Card.Rank, List<Card>> collect = hand.stream().collect(Collectors.groupingBy(Card::getRank));

         for (Card.Rank rank: collect.keySet())
         {
            if (collect.get(rank).size() == 3)
            {
               return true;
            }
         }
         return false;
      }
      //incomplete
      boolean isTwoPair(LinkedList<Card> hand)
      {
         Map<Card.Rank, List<Card>> collect = hand.stream().collect(Collectors.groupingBy(Card::getRank));
         int numPairs = 0;
         for (Card.Rank rank: collect.keySet())
         {
            if (collect.get(rank).size() == 2)
            {
               numPairs++;
            }
         }
         return numPairs == 2;
      }
      //incomplete
      boolean isPairJorHigher(LinkedList<Card> hand)
      {
         Map<Card.Rank, List<Card>> collect = hand.stream().collect(Collectors.groupingBy(Card::getRank));
         int numPairs = 0;
         int rankVal = 0;

         for (Card.Rank rank: collect.keySet())
         {
            if (collect.get(rank).size() == 2)
            {
               numPairs++;
               rankVal = rank.getValue();
            }
         }
         return numPairs == 1 && rankVal > 10;
      }
      boolean isPair(LinkedList<Card> hand)
      {
         Map<Card.Rank, List<Card>> collect =
               hand.stream().collect(Collectors.groupingBy(Card::getRank));
         int numPairs = 0;

         for (Card.Rank rank: collect.keySet())
         {
            if (collect.get(rank).size() == 2)
            {
               numPairs++;
            }
         }
         return numPairs == 1;
      }

      boolean pairAnalyzer(LinkedList<Card> hand, int card1, int card2)
      {
         return hand.get(card1).getRank() == hand.get(card2).getRank();
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