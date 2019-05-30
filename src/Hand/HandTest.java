//import Deck.Card;
//import Hand.Hand;
//import Hand.HandEvaluator;
//import org.junit.Test;
//
//import static junit.framework.TestCase.assertFalse;
//import static junit.framework.TestCase.assertTrue;
//import static org.junit.Assert.assertEquals;
//
//public class HandTest
//{
//   @Test
//   public void Test1()
//   {
//      Card aaa = new Card(Card.Rank.jack, Card.Suit.hearts);
//      Card bbb = new Card(Card.Rank.jack, Card.Suit.spades);
//      Card ccc = new Card(Card.Rank.jack, Card.Suit.clubs);
//      Card ddd = new Card(Card.Rank.ten, Card.Suit.clubs);
//      Card eee = new Card(Card.Rank.three, Card.Suit.hearts);
//      Hand testHand = new Hand();
//      testHand.takeCard(aaa);
//      testHand.takeCard(bbb);
//      testHand.takeCard(ccc);
//      testHand.takeCard(ddd);
//      testHand.takeCard(eee);
//      System.out.println("HAND: " + testHand.toString());
//      assertFalse(HandEvaluator.isFullHouse(testHand.sortedHand));
//      assertTrue(HandEvaluator.isSet(testHand.sortedHand));
//   }
//
//}