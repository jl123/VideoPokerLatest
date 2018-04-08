import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class GuiGame
{
   private int bet;
   private int credits;
   Deck deck;
   Hand playerHand;

   public GuiGame()
   {
      bet = 5;
      credits = 1000;
   }

   public Hand newHand()
   {
      deck = new Deck(true);
      playerHand = new Hand();
      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         playerHand.switchCard[k] = false;
         playerHand.takeCard(deck.dealCard());
      }
      credits -= bet;
      return playerHand;
   }

   public Hand draw()
   {
      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         if (playerHand.switchCard[k])
            playerHand.replaceCard(k, deck.dealCard());
      }
      return playerHand;
   }

   public int evaluateHand()
   {
      int creditsWon = HandEvaluator.amountWon(playerHand.sortedHand, bet);
      credits += creditsWon;
      return creditsWon;
   }

   public int getCredits(){ return credits; }

   public int getBet(){ return bet; }

   public boolean setBet(int bet)
   {
      if ( bet >= HandEvaluator.MIN_BET && bet <= HandEvaluator.MAX_BET)
      {
         this.bet = bet;
         return true;
      }
      return false;
   }
}