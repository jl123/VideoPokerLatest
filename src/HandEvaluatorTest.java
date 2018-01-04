import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class HandEvaluatorTest
{
   @Test
   public void removeTest()
   {
      Hand hand = new Hand();
      Card testCards[] = new Card[5];
      LinkedList<Card> sortedHand = new LinkedList<>();

      int cardDel = 3;

      testCards[0] = new Card(Card.Rank.ten, Card.Suit.spades);
      testCards[1] = new Card(Card.Rank.ten, Card.Suit.hearts);
      testCards[2] = new Card(Card.Rank.ten, Card.Suit.clubs);
      testCards[3] = new Card(Card.Rank.ten, Card.Suit.diamonds);
      testCards[4] = new Card(Card.Rank.ace, Card.Suit.spades);
      placeCards(hand, testCards);
      System.out.println(hand.toString());

      for (int k = 0; k < testCards.length; k++)
      {
         HandEvaluator.insert(sortedHand, testCards[k]);
      }
      System.out.println(sortedHand.toString());
      HandEvaluator.remove(sortedHand, testCards[cardDel]);
      System.out.println(sortedHand.toString());

   }

   @Test
   public void royalFlushTest()
   {
      Hand hand = new Hand();
      Card testCards[] = new Card[5];

      testCards[0] = new Card(Card.Rank.ten, Card.Suit.spades);
      testCards[1] = new Card(Card.Rank.king, Card.Suit.spades);
      testCards[2] = new Card(Card.Rank.jack, Card.Suit.spades);
      testCards[3] = new Card(Card.Rank.queen, Card.Suit.spades);
      testCards[4] = new Card(Card.Rank.ace, Card.Suit.spades);
      placeCards(hand, testCards);
      System.out.println(hand.toString());
      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.royalFlush);
   }

   @Test
   public void straightFlushTest()
   {
      Hand hand = new Hand();
      Card testCards[] = new Card[5];

      testCards[0] = new Card(Card.Rank.ten, Card.Suit.spades);
      testCards[1] = new Card(Card.Rank.king, Card.Suit.spades);
      testCards[2] = new Card(Card.Rank.jack, Card.Suit.spades);
      testCards[3] = new Card(Card.Rank.queen, Card.Suit.spades);
      testCards[4] = new Card(Card.Rank.nine, Card.Suit.spades);
      placeCards(hand, testCards);
      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.straightFlush);
   }

   @Test
   public void quadsTest()
   {
      Hand hand = new Hand();
      Card testCards[] = new Card[5];

      testCards[0] = new Card(Card.Rank.ten, Card.Suit.spades);
      testCards[1] = new Card(Card.Rank.ten, Card.Suit.clubs);
      testCards[2] = new Card(Card.Rank.ten, Card.Suit.hearts);
      testCards[3] = new Card(Card.Rank.ten, Card.Suit.diamonds);
      testCards[4] = new Card(Card.Rank.ace, Card.Suit.spades);
      placeCards(hand, testCards);
      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.quads);
   }

   @Test
   public void fullHouseTest()
   {
      Hand hand = new Hand();
      Card testCards[] = new Card[5];

      testCards[0] = new Card(Card.Rank.ten, Card.Suit.spades);
      testCards[1] = new Card(Card.Rank.ten, Card.Suit.clubs);
      testCards[2] = new Card(Card.Rank.ten, Card.Suit.hearts);
      testCards[3] = new Card(Card.Rank.two, Card.Suit.diamonds);
      testCards[4] = new Card(Card.Rank.two, Card.Suit.spades);
      placeCards(hand, testCards);
      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.fullHouse);
   }

   @Test
   public void flushTest()
   {
      Hand hand = new Hand();
      Card testCards[] = new Card[5];

      testCards[0] = new Card(Card.Rank.ten, Card.Suit.spades);
      testCards[1] = new Card(Card.Rank.nine, Card.Suit.spades);
      testCards[2] = new Card(Card.Rank.three, Card.Suit.spades);
      testCards[3] = new Card(Card.Rank.two, Card.Suit.spades);
      testCards[4] = new Card(Card.Rank.ace, Card.Suit.spades);
      placeCards(hand, testCards);
      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.flush);
   }

   @Test
   public void straightTestWheel()
   {
      Hand hand = new Hand();
      Card testCards[] = new Card[5];

      testCards[0] = new Card(Card.Rank.ace, Card.Suit.diamonds);
      testCards[1] = new Card(Card.Rank.four, Card.Suit.clubs);
      testCards[2] = new Card(Card.Rank.five, Card.Suit.spades);
      testCards[3] = new Card(Card.Rank.three, Card.Suit.spades);
      testCards[4] = new Card(Card.Rank.two, Card.Suit.diamonds);
      placeCards(hand, testCards);
      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.straight);
   }

   @Test
   public void straightTest()
   {
      Hand hand = new Hand();
      Card testCards[] = new Card[5];

      testCards[0] = new Card(Card.Rank.ten, Card.Suit.diamonds);
      testCards[1] = new Card(Card.Rank.nine, Card.Suit.clubs);
      testCards[2] = new Card(Card.Rank.seven, Card.Suit.spades);
      testCards[3] = new Card(Card.Rank.eight, Card.Suit.spades);
      testCards[4] = new Card(Card.Rank.six, Card.Suit.diamonds);
      placeCards(hand, testCards);
      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.straight);
   }

   @Test
   public void setTest()
   {
      Hand hand = new Hand();
      Card testCards[] = new Card[5];

      testCards[0] = new Card(Card.Rank.ten, Card.Suit.diamonds);
      testCards[1] = new Card(Card.Rank.ten, Card.Suit.clubs);
      testCards[2] = new Card(Card.Rank.ten, Card.Suit.spades);
      testCards[3] = new Card(Card.Rank.eight, Card.Suit.spades);
      testCards[4] = new Card(Card.Rank.six, Card.Suit.diamonds);
      placeCards(hand, testCards);
      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.set);
   }

   @Test
   public void twoPairTest()
   {
      Hand hand = new Hand();
      Card testCards[] = new Card[5];

      testCards[0] = new Card(Card.Rank.ten, Card.Suit.diamonds);
      testCards[1] = new Card(Card.Rank.ten, Card.Suit.clubs);
      testCards[2] = new Card(Card.Rank.seven, Card.Suit.spades);
      testCards[3] = new Card(Card.Rank.eight, Card.Suit.spades);
      testCards[4] = new Card(Card.Rank.seven, Card.Suit.diamonds);
      placeCards(hand, testCards);
      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.twoPair);
   }

   @Test
   public void testLowPair()
   {
      Hand hand = new Hand();
      Card testCards[] = new Card[5];

      testCards[0] = new Card(Card.Rank.ten, Card.Suit.spades);
      testCards[1] = new Card(Card.Rank.ten, Card.Suit.diamonds);
      testCards[2] = new Card(Card.Rank.jack, Card.Suit.spades);
      testCards[3] = new Card(Card.Rank.queen, Card.Suit.spades);
      testCards[4] = new Card(Card.Rank.ace, Card.Suit.spades);
      placeCards(hand, testCards);
      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.loser);
   }

   @Test
   public void testHighPair()
   {
      Hand hand = new Hand();
      Card testCards[] = new Card[5];

      testCards[0] = new Card(Card.Rank.jack, Card.Suit.diamonds);
      testCards[1] = new Card(Card.Rank.king, Card.Suit.spades);
      testCards[2] = new Card(Card.Rank.jack, Card.Suit.spades);
      testCards[3] = new Card(Card.Rank.queen, Card.Suit.spades);
      testCards[4] = new Card(Card.Rank.ace, Card.Suit.spades);
      placeCards(hand, testCards);
      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.pair);
   }


   public void placeCards(Hand hand, Card[] cards)
   {
      for (int i = 0; i < cards.length; i++)
         hand.takeCard(cards[i]);

   }
}



