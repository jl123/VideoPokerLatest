import java.util.*;

public class HandVal
{
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
   public static enum handVal{ royalFlush, straightFlush, quads, fullHouse, 
      flush, straight, set, twoPair, pair, loser };
      
   
   
   handVal myVal;
   
   LinkedList<Card> hand = new LinkedList<Card>();
  
   public static handVal getHandVal(LinkedList<Card> hand)
   {
      if (isRoyalFlush(hand))
         return handVal.royalFlush;
      if  (isStraightFlush(hand))
         return handVal.straightFlush;
      if (isQuads(hand))
         return handVal.quads;
      if (isFullHouse(hand))
         return handVal.fullHouse;
      if (isFlush(hand))
         return handVal.flush;
      if (isStraight(hand))
         return handVal.straight;
      if (isSet(hand))
         return handVal.set;
      if (isPairJorHigher(hand))
         return handVal.pair;
      return handVal.loser;
      
   }
   
   public static int amountWon(LinkedList<Card> hand, int bet)
   {
     return 1;
   }
   
   public static boolean isRoyalFlush(LinkedList<Card> hand)
   {
      return isFlush(hand) && isStraight(hand) &&
            hand.get(4).getRank() == Card.Rank.ace;
                         
   }
   
   public static boolean isStraightFlush(LinkedList<Card> hand)
   {
      return isFlush(hand) && isStraight(hand) &&
            hand.get(4).getRank() != Card.Rank.ace;
                         
   }
   
   public static boolean isQuads(LinkedList<Card> hand)
   {
      return ( hand.get(0).getRank() == hand.get(1).getRank() &&
            hand.get(0).getRank() == hand.get(2).getRank() &&
            hand.get(0).getRank() == hand.get(3).getRank() )
            ||
            ( hand.get(1).getRank() == hand.get(2).getRank() &&
            hand.get(1).getRank() == hand.get(3).getRank() &&
            hand.get(1).getRank() == hand.get(4).getRank() );
   }
   
   public static boolean isFullHouse(LinkedList<Card> hand)
   {
      return ( hand.get(0).getRank().value == hand.get(1).getRank().value &&
            hand.get(0).getRank().value == hand.get(2).getRank().value &&
            hand.get(3).getRank() == hand.get(4).getRank() )
            ||
            ( hand.get(2).getRank().value == hand.get(3).getRank().value &&
            hand.get(2).getRank().value == hand.get(4).getRank().value &&
            hand.get(0).getRank().value == hand.get(1).getRank().value );
   }
   
   public static boolean isFlush(LinkedList<Card> hand)
   {
      return hand.get(0).getSuit() == hand.get(1).getSuit() &&
             hand.get(0).getSuit() == hand.get(2).getSuit() &&
             hand.get(0).getSuit() == hand.get(3).getSuit() &&
             hand.get(0).getSuit() == hand.get(4).getSuit();
   }
   
   static boolean isStraight(LinkedList<Card> hand)
   {
      int firstCardVal = hand.get(0).getRank().value;
      
      return firstCardVal == hand.get(1).getRank().value - 1 &&
            firstCardVal == hand.get(2).getRank().value - 2 &&
            firstCardVal == hand.get(3).getRank().value - 3 &&
            firstCardVal == hand.get(4).getRank().value - 4;
   }
   //incomplete--------get rid of .value?
   static boolean isSet(LinkedList<Card> hand)
   {
      return !isFullHouse(hand) && !isQuads(hand) &&
            ( ( hand.get(0).getRank().value == hand.get(1).getRank().value &&
            hand.get(0).getRank().value == hand.get(2).getRank().value  )
            ||
            ( hand.get(1).getRank().value == hand.get(2).getRank().value &&
            hand.get(1).getRank().value == hand.get(3).getRank().value )
            ||
            ( hand.get(2).getRank().value == hand.get(3).getRank().value &&
            hand.get(2).getRank().value == hand.get(4).getRank().value ) );
   }
   //incomplete
   static boolean isTwoPair(LinkedList<Card> hand)
   {
      return (!isQuads(hand) && !isSet(hand) &&
            ( hand.get(0).getRank() == hand.get(1).getRank()
            && hand.get(2).getRank() == hand.get(3).getRank()
            || 
            hand.get(3).getRank() == hand.get(4).getRank() )
            ||
            ( hand.get(1).getRank() == hand.get(2).getRank()
            && hand.get(3).getRank() == hand.get(4).getRank() )
            );
   }
   //incomplete
   static boolean isPairJorHigher(LinkedList<Card> hand)
   {
      return !isQuads(hand) && !isFullHouse(hand) && !isSet(hand) 
            && !isTwoPair(hand) && !isFullHouse(hand)
            && ( hand.get(3).getRank() == hand.get(4).getRank() 
            && hand.get(3).getRank().value > Card.Rank.ten.value );
   }
   
   static boolean pairAnalyzer(LinkedList<Card> hand, int card1, int card2)
   {
      return hand.get(card1).getRank() == hand.get(card2).getRank();
   }
}
