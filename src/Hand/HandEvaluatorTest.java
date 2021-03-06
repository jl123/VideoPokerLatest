//import Model.Card;
//import Hand.Hand;
//import Hand.HandEvaluator;
//import org.junit.Test;
//import java.util.LinkedList;
//import static org.junit.Assert.*;
//
//public class HandEvaluatorTest
//{
//   @Test
//   public void removeTest()
//   {
//      Hand hand = new Hand();
//      Card testCards[] = new Card[5];
//      LinkedList<Card> sortedHand = new LinkedList<>();
//
//      int cardDel = 3;
//
//      testCards[0] = new Card(Card.Rank.ten, Card.Suit.spades);
//      testCards[1] = new Card(Card.Rank.ten, Card.Suit.hearts);
//      testCards[2] = new Card(Card.Rank.ten, Card.Suit.clubs);
//      testCards[3] = new Card(Card.Rank.ten, Card.Suit.diamonds);
//      testCards[4] = new Card(Card.Rank.ace, Card.Suit.spades);
//      placeCards(hand, testCards);
//
//      for (final Card testCard : testCards)
//      {
//         HandEvaluator.insert(sortedHand, testCard);
//      }
//      assertEquals(true, HandEvaluator.remove(sortedHand, testCards[cardDel]));
//   }
//
//   @Test
//   public void royalFlushTest()
//   {
//      Hand hand = new Hand();
//      Card testCards[] = new Card[5];
//
//      testCards[0] = new Card(Card.Rank.ten, Card.Suit.spades);
//      testCards[1] = new Card(Card.Rank.king, Card.Suit.spades);
//      testCards[2] = new Card(Card.Rank.jack, Card.Suit.spades);
//      testCards[3] = new Card(Card.Rank.queen, Card.Suit.spades);
//      testCards[4] = new Card(Card.Rank.ace, Card.Suit.spades);
//      placeCards(hand, testCards);
//      System.out.println(hand.toString());
//      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.ROYAL_FLUSH);
//   }
//
//   @Test
//   public void straightFlushTest()
//   {
//      Hand hand = new Hand();
//      Card testCards[] = new Card[5];
//
//      testCards[0] = new Card(Card.Rank.ten, Card.Suit.spades);
//      testCards[1] = new Card(Card.Rank.king, Card.Suit.spades);
//      testCards[2] = new Card(Card.Rank.jack, Card.Suit.spades);
//      testCards[3] = new Card(Card.Rank.queen, Card.Suit.spades);
//      testCards[4] = new Card(Card.Rank.nine, Card.Suit.spades);
//      placeCards(hand, testCards);
//      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.STRAIGHT_FLUSH);
//   }
//
//   @Test
//   public void quadsTest()
//   {
//      Hand hand = new Hand();
//      Card testCards[] = new Card[5];
//
//      testCards[0] = new Card(Card.Rank.ten, Card.Suit.spades);
//      testCards[1] = new Card(Card.Rank.ten, Card.Suit.clubs);
//      testCards[2] = new Card(Card.Rank.ten, Card.Suit.hearts);
//      testCards[3] = new Card(Card.Rank.ten, Card.Suit.diamonds);
//      testCards[4] = new Card(Card.Rank.ace, Card.Suit.spades);
//      placeCards(hand, testCards);
//      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.QUADS);
//   }
//
//   @Test
//   public void fullHouseTest()
//   {
//      Hand hand = new Hand();
//      Card testCards[] = new Card[5];
//
//      testCards[0] = new Card(Card.Rank.ten, Card.Suit.spades);
//      testCards[1] = new Card(Card.Rank.ten, Card.Suit.clubs);
//      testCards[2] = new Card(Card.Rank.ten, Card.Suit.hearts);
//      testCards[3] = new Card(Card.Rank.two, Card.Suit.diamonds);
//      testCards[4] = new Card(Card.Rank.two, Card.Suit.spades);
//      placeCards(hand, testCards);
//      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.FULL_HOUSE);
//   }
//
//   @Test
//   public void flushTest()
//   {
//      Hand hand = new Hand();
//      Card testCards[] = new Card[5];
//
//      testCards[0] = new Card(Card.Rank.ten, Card.Suit.spades);
//      testCards[1] = new Card(Card.Rank.nine, Card.Suit.spades);
//      testCards[2] = new Card(Card.Rank.three, Card.Suit.spades);
//      testCards[3] = new Card(Card.Rank.two, Card.Suit.spades);
//      testCards[4] = new Card(Card.Rank.ace, Card.Suit.spades);
//      placeCards(hand, testCards);
//      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.FLUSH);
//   }
//
//   @Test
//   public void straightTestWheel()
//   {
//      Hand hand = new Hand();
//      Card testCards[] = new Card[5];
//
//      testCards[0] = new Card(Card.Rank.ace, Card.Suit.diamonds);
//      testCards[1] = new Card(Card.Rank.four, Card.Suit.clubs);
//      testCards[2] = new Card(Card.Rank.five, Card.Suit.spades);
//      testCards[3] = new Card(Card.Rank.three, Card.Suit.spades);
//      testCards[4] = new Card(Card.Rank.two, Card.Suit.diamonds);
//      placeCards(hand, testCards);
//      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.STRAIGHT);
//   }
//
//   @Test
//   public void straightTest()
//   {
//      Hand hand = new Hand();
//      Card testCards[] = new Card[5];
//
//      testCards[0] = new Card(Card.Rank.ten, Card.Suit.diamonds);
//      testCards[1] = new Card(Card.Rank.nine, Card.Suit.clubs);
//      testCards[2] = new Card(Card.Rank.seven, Card.Suit.spades);
//      testCards[3] = new Card(Card.Rank.eight, Card.Suit.spades);
//      testCards[4] = new Card(Card.Rank.six, Card.Suit.diamonds);
//      placeCards(hand, testCards);
//      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.STRAIGHT);
//   }
//
//   @Test
//   public void setTest()
//   {
//      Hand hand = new Hand();
//      Card testCards[] = new Card[5];
//
//      testCards[0] = new Card(Card.Rank.ten, Card.Suit.diamonds);
//      testCards[1] = new Card(Card.Rank.ten, Card.Suit.clubs);
//      testCards[2] = new Card(Card.Rank.ten, Card.Suit.spades);
//      testCards[3] = new Card(Card.Rank.eight, Card.Suit.spades);
//      testCards[4] = new Card(Card.Rank.six, Card.Suit.diamonds);
//      placeCards(hand, testCards);
//      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.SET);
//   }
//
//   @Test
//   public void twoPairTest()
//   {
//      Hand hand = new Hand();
//      Card testCards[] = new Card[5];
//
//      testCards[0] = new Card(Card.Rank.ten, Card.Suit.diamonds);
//      testCards[1] = new Card(Card.Rank.ten, Card.Suit.clubs);
//      testCards[2] = new Card(Card.Rank.seven, Card.Suit.spades);
//      testCards[3] = new Card(Card.Rank.eight, Card.Suit.spades);
//      testCards[4] = new Card(Card.Rank.seven, Card.Suit.diamonds);
//      placeCards(hand, testCards);
//      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.TWO_PAIR);
//   }
//
//   @Test
//   public void testLowPair()
//   {
//      Hand hand = new Hand();
//      Card testCards[] = new Card[5];
//
//      testCards[0] = new Card(Card.Rank.ten, Card.Suit.spades);
//      testCards[1] = new Card(Card.Rank.ten, Card.Suit.diamonds);
//      testCards[2] = new Card(Card.Rank.jack, Card.Suit.spades);
//      testCards[3] = new Card(Card.Rank.queen, Card.Suit.spades);
//      testCards[4] = new Card(Card.Rank.ace, Card.Suit.spades);
//      placeCards(hand, testCards);
//      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.LOSER);
//   }
//
//   @Test
//   public void testHighPair()
//   {
//      Hand hand = new Hand();
//      Card testCards[] = new Card[5];
//
//      testCards[0] = new Card(Card.Rank.jack, Card.Suit.diamonds);
//      testCards[1] = new Card(Card.Rank.king, Card.Suit.spades);
//      testCards[2] = new Card(Card.Rank.jack, Card.Suit.spades);
//      testCards[3] = new Card(Card.Rank.queen, Card.Suit.spades);
//      testCards[4] = new Card(Card.Rank.ace, Card.Suit.spades);
//      placeCards(hand, testCards);
//      assertEquals(HandEvaluator.getHandVal(hand), HandEvaluator.handVal.HIGH_PAIR);
//   }
//
//
//   private void placeCards(Hand hand, Card[] cards)
//   {
//      for (final Card card : cards) hand.takeCard(card);
//   }
//}
//
//
//
