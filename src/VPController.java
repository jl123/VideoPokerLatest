import javafx.fxml.FXML;

public class VPController
{

   @FXML
   public Hand deal()
   {
      Hand retHand = new Hand();
      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         retHand.takeCard(new Card(Card.Rank.ace, Card.Suit.spades));
      }
      return retHand;
   }

   @FXML
   public Hand draw(Hand origHand, Deck deck)
   {
      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         if (origHand.switchCard[k])
         {
            origHand.replaceCard(k, deck.dealCard());
         }

      }
      return null;
   }
}