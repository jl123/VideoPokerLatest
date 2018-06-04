import java.util.*;
import java.util.stream.Collectors;

class HandEvaluator
{
   static final int MIN_BET = 1;
   static final int MAX_BET = 5;

   private static LinkedList<Card> sortedHand;
   
   //ODDS
   private static final int royalFlushMaxWin = 800;
   private static final int royalFlushWin = 250;
   private static final int straightFlushWin = 50;
   private static final int quadsWin = 25;
   private static final int fullHouseWin = 9;
   private static final int flushWin = 6;
   private static final int straightWin = 4;
   private static final int setWin = 3;
   private static final int twoPairWin = 2;
   private static final int pairWin = 1;
   


   static String oddsTable()
   {

            return
                  "Pay out table:\n" +
            "Bet:               1     2     3      4      5   \n" +
            "                 ------------------------------- \n" +
            "Royal flush.....| 250 | 500 | 750 | 1000 | 4000 |\n" +
            "Straight flush..| 50  | 100 | 150 | 200  | 250  |\n" +
            "4 of a kind.....| 25  | 50  | 75  | 100  | 125  |\n" +
            "Full house......| 9   | 18  | 27  | 36   | 45   |\n" +
            "Flush...........| 6   | 12  | 18  | 24   | 30   |\n" +
            "Straight........| 4   | 8   | 12  | 16   | 20   |\n" +
            "3 of a kind.....| 3   | 6   | 9   | 12   | 15   |\n" +
            "2 pair..........| 2   | 4   | 6   | 8    | 10   |\n" +
            "Jacks or better.| 1   | 2   | 3   | 4    | 5    |\n" +
            "                 ------------------------------- ";


   }

   public enum handVal{
      royalFlush
            {
               @Override
               public int winVal(int betAmount)
               {
                  if (betAmount == MAX_BET)
                     return MAX_BET * royalFlushMaxWin;
                  else
                     return betAmount * royalFlushWin;
               }
            },
      straightFlush
            {
               @Override
               public int winVal(int betAmount)
               {
                  return betAmount * straightFlushWin;
               }
            },
      quads
            {
               @Override
               public int winVal(int betAmount)
               {
                  return betAmount * quadsWin;
               }
            },
      fullHouse
            {
               @Override
               public int winVal(int betAmount)
               {
                  return betAmount * fullHouseWin;
               }
            },
      flush
            {
               @Override
               public int winVal(int betAmount)
               {
                  return betAmount * flushWin;
               }
            },
      straight
            {
               @Override
               public int winVal(int betAmount)
               {
                  return betAmount * straightWin;
               }
            },
      set
            {
               @Override
               public int winVal(int betAmount)
               {
                  return betAmount * setWin;
               }
            },
      twoPair
            {
               @Override
               public int winVal(int betAmount)
               {
                  return betAmount * twoPairWin;
               }
            },
      highPair
            {
               @Override
               public int winVal(int betAmount)
               {
                  return betAmount * pairWin;
               }
            },
      loser
            {
               @Override
               public int winVal(int betAmount)
               {
                  return 0;
               }
            };

      public abstract int winVal(int betAmount);

      @Override
      public String toString()
      {
         switch (this)
         {
            case royalFlush:
               return "ROYAL FLUSH";
            case straightFlush:
               return "STRAIGHT FLUSH";
            case quads:
               return "4 OF A KIND";
            case fullHouse:
               return "FULL HOUSE";
            case flush:
               return "FLUSH";
            case straight:
               return "STRAIGHT";
            case set:
               return "3 OF A KIND";
            case twoPair:
               return "TWO PAIR";
            case highPair:
               return "HIGH PAIR";
            default:
               return "LOSER";
         }
      }
   }


   static handVal getHandVal(Hand hand)
   {
      sortedHand = new LinkedList<>();
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
         return handVal.highPair;
      if (isPair( sortedHand ))
         return handVal.loser;
      return handVal.loser;
      
   }

   private static void sortHand(Hand hand)
   {
      for (int i = 0; i < hand.getNumCards(); i++)
      {
         insert(sortedHand, hand.inspectCard(i));
      }
   }
   
   private static boolean isRoyalFlush(LinkedList<Card> hand)
   {
      return isFlush(hand) && isStraight(hand) &&
            hand.get(4).getRank() == Card.Rank.ace;
                         
   }
   
   private static boolean isStraightFlush(LinkedList<Card> hand)
   {
      return isFlush(hand) && isStraight(hand) &&
            hand.get(4).getRank() != Card.Rank.ace;
   }
   
   private static boolean isQuads(LinkedList<Card> hand)
   {
      return ( hand.get(0).getRank() == hand.get(1).getRank() &&
            hand.get(0).getRank() == hand.get(2).getRank() &&
            hand.get(0).getRank() == hand.get(3).getRank() )
            ||
            ( hand.get(1).getRank() == hand.get(2).getRank() &&
            hand.get(1).getRank() == hand.get(3).getRank() &&
            hand.get(1).getRank() == hand.get(4).getRank() );
   }
   
   static boolean isFullHouse(List<Card> hand)
   {
      return isPair(hand) && isSet(hand);
   }

   private static boolean isFlush(LinkedList<Card> hand)
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

   private static boolean isStraight(LinkedList<Card> hand)
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
   static boolean isSet(List<Card> hand)
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
   private static boolean isTwoPair(List<Card> hand)
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
   private static boolean isPairJorHigher(List<Card> hand)
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
   private static boolean isPair(List<Card> hand)
   {
      Map<Card.Rank, List<Card>> collect =
            hand.stream().collect(Collectors.groupingBy(Card::getRank));
      int numPairs = 0;

      for (Card.Rank rank : collect.keySet())
      {
         if (collect.get(rank).size() == 2)
         {
            numPairs++;
         }
      }
      return numPairs == 1;
   }

   static void insert(LinkedList<Card> cardList, Card card)
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
   static boolean remove(LinkedList<Card> cardList, Card card)
   {
      ListIterator<Card> iterator;
      for (iterator = cardList.listIterator(); iterator.hasNext(); )
      {
         if (card.compareTo(iterator.next()) == 0 )
         {
            iterator.remove();
            return true;
         }
      }
      return false;
   }

   //REMOVEALL
   @SuppressWarnings("unused")
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
